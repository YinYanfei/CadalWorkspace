package cn.cadal.storm.analyze.bolt.util;

import cn.cadal.storm.analyze.cassandraOp.CassandraInsert;
import cn.cadal.storm.analyze.cassandraOp.CassandraQuery;

public class BookUserBoltUtil {

	private CassandraQuery cassandraQuery = null;
	private CassandraInsert cassandraInsert = null;

	public BookUserBoltUtil() {
		try {
			this.cassandraQuery = new CassandraQuery();
			this.cassandraInsert = new CassandraInsert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * Deal with IpUserTimeBook string, just like "28.29.58.165# #03/Aug/2012:05:44:01#00061041" 
	 * 		or "28.29.58.165#Yanfei#03/Aug/2012:05:44:01#00061041"
	 * 
	 * Return: True for all success, and false on another side
	 */
	public boolean DealBookUser(String ipUserTimeBook) {
		
		boolean boolOne = false;
		boolean boolTwo = false;
		
		int times = 0;
		
		String[] ipUserTimeBookList = ipUserTimeBook.split("#");
		
		// BookIp Column Family check and insert
		if((times = this.CheckBookIpCF(ipUserTimeBookList[3], ipUserTimeBookList[0])) > 0){
			boolOne = this.InsertIntoBookIp(ipUserTimeBookList[0], ipUserTimeBookList[3], ipUserTimeBookList[2], times);
		}else{
			boolOne = this.InsertIntoBookIp(ipUserTimeBookList[0], ipUserTimeBookList[3], ipUserTimeBookList[2], 0);
		}
		
		// BookUser Column Family check and insert
		if(ipUserTimeBookList[1].equals(" ")) {
			boolTwo = true;					// UserName is NULL, and not need to insert anything
		}else{
			if((times = this.CheckBookUserCF(ipUserTimeBookList[3], ipUserTimeBookList[1])) > 0) {
				boolTwo = this.InsertIntoBookUser(ipUserTimeBookList[1], ipUserTimeBookList[3], ipUserTimeBookList[2], times);
			}else{
				boolTwo = this.InsertIntoBookUser(ipUserTimeBookList[1], ipUserTimeBookList[3], ipUserTimeBookList[2], 0);
			}
		}

		return boolOne & boolTwo;
	}

	/**
	 * Check BookIp CF
	 */
	private int CheckBookIpCF(String bookid, String ip){
		return this.cassandraQuery.QueryBookIp(bookid, ip);
	}

	/**
	 * Check BookUser CF
	 */
	private int CheckBookUserCF(String bookid, String user){
		return this.cassandraQuery.QueryBookUser(bookid, user);
	}

	/**
	 * Insert into BookIp CF
	 */
	private boolean InsertIntoBookIp(String ip, String bookid, String last, int times){
		return this.cassandraInsert.InsertIntoBookIp(ip, bookid, last, times + 1);
	}

	/**
	 * Insert into BookUser CF
	 */
	private boolean InsertIntoBookUser(String user, String bookid, String last, int times){
		return this.cassandraInsert.InsertIntoBookUser(user, bookid, last, times + 1);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		String str = "28.29.58.165# #03/Aug/2012:05:44:01#00061041";
		
		String[] strList = str.split("#");
		
		for (String s : strList) {
			System.out.println(s);
		}

	}
}
