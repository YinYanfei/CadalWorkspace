package cn.cadal.storm.analyze.bolt.util;

import cn.cadal.storm.analyze.cassandraOp.CassandraInsert;
import cn.cadal.storm.analyze.cassandraOp.CassandraQuery;

public class UserIpBoltUtil {
	
	private CassandraQuery cassandraQuery = null;
	private CassandraInsert cassandraInsert = null;

	public UserIpBoltUtil() {
		try {
			this.cassandraQuery = new CassandraQuery();
			this.cassandraInsert = new CassandraInsert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Parameter: ipNameTime, just like "10.15.62.72#Yanfei#12/Aug/2012:12:03:34"
	 * Return:
	 * 		true : this string is inserted into cassandra database successfully
	 * 		false: failled to insert this item
	 */
	public boolean DealLog(String ipNameTime) {
		String[] ipNameTimeList = ipNameTime.split("#");
		
		int times;
		
		boolean boolOne = true;
		boolean boolTwo = true;
		
		// Deal with IpUser Comlumn Family
		if((times = this.CheckIpUserCF(ipNameTimeList[0], ipNameTimeList[1])) > 0) {
			boolOne = this.InsertIntoIpUser(ipNameTimeList[0], ipNameTimeList[1], ipNameTimeList[2], times);
		}else{
			boolOne = this.InsertIntoIpUser(ipNameTimeList[0], ipNameTimeList[1], ipNameTimeList[2], 0);
		}
		
		// Deal with UserIp Column Family
		if ((times = this.CheckUserIpCF(ipNameTimeList[0], ipNameTimeList[1])) > 0) {
			boolTwo = this.InsertIntoUserIp(ipNameTimeList[0], ipNameTimeList[1], ipNameTimeList[2], times);
		}else{
			boolTwo = this.InsertIntoUserIp(ipNameTimeList[0], ipNameTimeList[1], ipNameTimeList[2], 0);
		}
		
		return boolOne & boolTwo;
	}
	
	/**
	 * Check item is exist in Column Family 'IpUser' or not
	 * 
	 * return 0 or natural number over zero
	 * 				 0: for none exist
	 *  natural number: for exist
	 */
	private int CheckIpUserCF(String ip, String name) {
		return this.cassandraQuery.QueryIpUser(ip, name);
	}

	/**
	 * Check item is exist in Column Family 'UserIp' or not
	 * 
	 * return 0 or natural number over zero
	 * 				 0: for none exist
	 *  natural number: for exist
	 */
	private int CheckUserIpCF(String ip, String name) {
		return this.cassandraQuery.QueryUserIp(ip, name);
	}
	
	/**
	 * Insert into IpUser Column Family
	 * 
	 * return true or false
	 */
	private boolean InsertIntoIpUser(String ip, String name, String time, int times){
		return this.cassandraInsert.InsertIpUser(ip, name, time, times + 1);
	}
	
	/**
	 * Insert into UserIp Column Family
	 * 
	 * return true or false
	 */
	private boolean InsertIntoUserIp(String ip, String name, String time, int times){
		return this.cassandraInsert.InsertUserIp(ip, name, time, times + 1);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		UserIpBoltUtil uibu = new UserIpBoltUtil();
//		
//		uibu.DealLog("10.15.62.72#Yanfei#12/Aug/2012:23:54:09");

		boolean b1 = true;
		boolean b2 = false;
		System.out.println(b1 & b2);
	}

}
