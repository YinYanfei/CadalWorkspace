package cn.cadal.storm.analyze.bolt.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import cn.cadal.storm.analyze.cassandraOp.CassandraInsert;

public class QueryBookBoltUtil {
	
	private CassandraInsert cassandraInsert = null;
	private TimeConvert timeConvert = null;
	private List<String[]> userQueryInfo = null;		// To store  username-info List {ip, time, query, key}
	private List<String[]> ipQueryInfo   = null;		// To store ipAddress-info List {user, time, query, key}
	
	private String IPSIGNAL = "ip";
	private String NAMESIGNAL = "name";
	
	public QueryBookBoltUtil() {
		this.userQueryInfo = new ArrayList<String[]>();
		this.ipQueryInfo   = new ArrayList<String[]>();
		try {
			this.timeConvert = new TimeConvert();
			this.cassandraInsert = new CassandraInsert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deal with QueryBook relationship
	 * 
	 * return true for correct dealing, and false for another situation
	 */
	public boolean DealQueryBook(String queryBookUserType) {
		String [] strList = queryBookUserType.split("#");
				
		if(strList[strList.length - 1].equals("QUERY")){
			// QUERY
			String IP    = strList[0];
			String USER  = strList[1];
			String TIME  = strList[2];
			String QUERY = strList[3];
			
			this.DealQueryRecord(IP, USER, TIME, QUERY);
		}else{
			// BOOK
			String IP   = strList[0];
			String USER = strList[1];
			String TIME = strList[2];
			String BOOK = strList[3];
			
			this.DealBookRecord(IP, USER, TIME, BOOK);
		}
		
		return true;
	}
	
	/**
	 * Deal with new query recoerd
	 * 
	 * Input: 
	 * 		ip: 10.10.10.10
	 * 	  user: Yanfei(or " ")
	 *    time: 06/Dec/2012:01:30:39
	 *   query: %E6%9C%B1%E8%87%AA%E6%B8%85%E6%95%A3%E6%96%87%E9%9B%86
	 *   
	 * Purpose: 
	 * 		-- Insert new query item into list
	 * 		-- Check whether items over 45min, delete if it is true
	 * 
	 * Care: 
	 * 		-- need consider both userQueryInfo and ipQueryInfo
	 * 	    -- userQueryInfo and ipQueryInfo include a special item: "UUID: 067e6162-3b6f-4ae2-a171-2470b63dff00"
	 * 		   this item is generated the first time query word insert into cassandra
	 */
	private void DealQueryRecord(String ip, String user, String time, String query) {
		String timeNormal = this.timeConvert.NormalTime(time);
		
		// insert into ipQueryInfo and userQueryInfo
		String[] ipArr = {ip, timeNormal, query, String.valueOf(UUID.randomUUID())};
		this.ipQueryInfo.add(ipArr);
		if(!user.equals(" ")){
			String[] userArr = {user, timeNormal, query, String.valueOf(UUID.randomUUID())};			
			this.userQueryInfo.add(userArr);
		}
		
		// remove time over 30 min
		// -- ipQueryBook
		while(this.ipQueryInfo.size() > 0) {
			String timeOldIp = this.ipQueryInfo.get(0)[1];
			if(this.CompareTime(timeOldIp, timeNormal)) {
				this.ipQueryInfo.remove(0);
			}else{
				break;
			}
		}
		// -- userQueryBook
		while(this.userQueryInfo.size() > 0){
			String timeOldUser = this.userQueryInfo.get(0)[1];
			if(this.CompareTime(timeOldUser, timeNormal)){
				this.userQueryInfo.remove(0);
			}else{
				break;
			}
		}
	}

	/**
	 * Deal with a recoerd include book N.O.
	 * 
	 * Input: 
	 * 		ip: 10.10.10.10
	 * 	  user: Yanfei(or " ")
	 *    time: 06/Dec/2012:01:30:39
	 *    book: 07018720
	 *   
	 * Purpose: 
	 * 		-- Check whether the IP or USER is exist or not in userQueryInfo and ipQueryInfo
	 * 		-- if IP or USER is exist, then insert record into cassandra, abandon on another side
	 * 
	 * Care: 
	 * 		-- need consider UserQueryBook, IpQueryBook and QueryBookKey CF
	 */
	private void DealBookRecord(String ip, String user, String time, String book){
		String timeNormal = this.timeConvert.NormalTime(time);
		
		// ip-time-book
		int index = 0;
		int len = this.ipQueryInfo.size();
		for(; index < len; ++index) {
			if(this.ipQueryInfo.get(index)[0].equals(ip)) {
				break;
			}
		}
		
		if(index < this.ipQueryInfo.size()) {
			this.InsertIntoIpQueryBook(ip, this.ipQueryInfo.get(index)[3], this.ipQueryInfo.get(index)[2], book, timeNormal);
			this.InsertIntoQueryBookKey(timeNormal, ip, this.IPSIGNAL);
		}
		
		// user-time-book
		if(!user.equals(" ")) {
			int indexUser = 0;
			int lenUser = this.userQueryInfo.size();
			for(; indexUser < lenUser; ++indexUser) {
				if(this.userQueryInfo.get(indexUser)[0].equals(user)){
					break;
				}
			}
			
			if(indexUser < this.userQueryInfo.size()) {
				this.InsertIntoUserQueryBook(user, this.userQueryInfo.get(indexUser)[3], this.userQueryInfo.get(indexUser)[2], book, timeNormal);
				this.InsertIntoQueryBookKey(timeNormal, user, this.NAMESIGNAL);
			}
		}
		
	}

	/**
	 * Insert into IpQueryBook Column Family
	 * 
	 * return true or false
	 * 
	 * parameter:
	 * 		-- ip: the key of UserQueryBook CF
	 * 		-- super_uuid: the super key of UserQueryBook CF
	 * 		-- book: book id, the title of column will be inserted
	 * 		-- time: value of column inserted
	 */
	private boolean InsertIntoIpQueryBook(String ip, String super_uuid, String query, String book, String time){
		return this.cassandraInsert.InsertIpQueryBook(ip, super_uuid, query, book, time);
	}
	
	/**
	 * Insert into UserQueryBook Column Family
	 * 
	 * return true or false
	 * 
	 * parameter:
	 * 		-- name: the key of UserQueryBook CF
	 * 		-- super_uuid: the super key of UserQueryBook CF
	 * 		-- book: book id, the title of column will be inserted
	 * 		-- time: value of column inserted
	 */
	private boolean InsertIntoUserQueryBook(String name, String super_uuid, String query, String book, String time){
		return this.cassandraInsert.InsertUserQueryBook(name, super_uuid, query, book, time);
	}

	/**
	 * Insert into QueryBookKey Column Family
	 * 
	 * return true or false
	 * 
	 * parameter
	 * 		-- signal: "user" for 201210name
	 * 				   "ip" for 201210ip
	 */
	private boolean InsertIntoQueryBookKey(String time, String content, String signal){	
		String strSub = time.substring(0, time.indexOf(" "));
		int tmp = strSub.indexOf("-");
		String key = strSub.substring(0, tmp) + strSub.substring(tmp + 1, tmp + 3) + signal;
		
		return this.cassandraInsert.InsertQueryBookKey(key, content, time);
	}

	/**
	 * Compare time
	 * 
	 * return:
	 * 		--  true: newTime - oldTime  > 30min
	 * 		-- false: newTime - oldTime <= 30min
	 */
	private boolean CompareTime(String oldTime, String newTime){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try{
			Date oldTimeDate = formatter.parse(oldTime);
			Date newTimeDate = formatter.parse(newTime);
			
			long milliSecond = newTimeDate.getTime() - oldTimeDate.getTime();
			
			// later then 30mins then return true
			if(milliSecond > 1800000) {
				return true;
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		QueryBookBoltUtil qbbu = new QueryBookBoltUtil();
//		
//		String input1 = "202.201.163.5#Yanfei#06/Dec/2012:01:30:39#%E6%9C%B1%E8%87%AA%E6%B8%85%E6%95%A3%E6%96%87%E9%9B%86#QUERY";
//		String input2 = "202.201.163.5#Yanfei#06/Dec/2012:01:30:39#90909090#BOOK";
//		String input3 = "202.201.163.5# #06/Dec/2012:01:30:39#90909090#BOOK";
//		
//		qbbu.DealQueryBook(input1);
//		qbbu.DealQueryBook(input2);
//		qbbu.DealQueryBook(input3);

//		String ip = "10";
//		String name = "Yanfei";
//		String time = "06/Dec/2012:01:30:39";
//		String query = "%E6%9C%B1%E8%87%AA%E6%B8";
//		String key = "";
//		
//		List<String[]> strList = new ArrayList<String[]>();
//		
//		String[] str1 = {"30", name, time, query, key};
//		String[] str2 = {"40", name, time, query, key};
//		String[] str3 = {"50", name, time, query, key};
//		String[] str4 = {"10", name, time, query, key};
//		
//		strList.add(str1);
//		strList.add(str2);
//		strList.add(str3);
//		strList.add(str4);
//		
//		for(int i = 0; i < strList.size(); ++i) {
//			System.out.println(strList.get(i)[0]);
//		}
//		
//		strList.remove(0);
//
//		System.out.println("------------");
//		
//		for(int i = 0; i < strList.size(); ++i) {
//			System.out.println(strList.get(i)[0]);
//		}

//		String strTime = "2012-12-12 09:10:45";
//		String strSub = strTime.substring(0, strTime.indexOf(" "));
//		int tmp = strSub.indexOf("-");
//		String strNewTime = strSub.substring(0, tmp) + strSub.substring(tmp + 1, tmp + 3);
//		
//		System.out.println(strNewTime);
		
		
	}

}
