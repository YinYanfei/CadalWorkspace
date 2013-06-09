package cn.cadal.storm.analyze.bolt.util;

import cn.cadal.storm.analyze.cassandraOp.CassandraInsert;
import cn.cadal.storm.analyze.cassandraOp.CassandraQuery;

public class BookPageBoltUtil {
	
	private CassandraQuery cassandraQuery = null;
	private CassandraInsert cassandraInsert = null;

	public BookPageBoltUtil() {
		try {
			this.cassandraQuery = new CassandraQuery();
			this.cassandraInsert = new CassandraInsert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deal with BookPage bolt
	 * 
	 * Parameter: just like "10.15.62.10#Yanfei#12/Aug/2012:09:23:59#07018720#12" 
	 * 					 or "10.15.62.10# #12/Aug/2012:09:23:59#07018720#12"
	 */
	public boolean DealBookPage(String ipNameTimeBookPage) {
		
		String[] ipNameTimeBookPageList = ipNameTimeBookPage.split("#");
		
		String ip = ipNameTimeBookPageList[0];
		String user = ipNameTimeBookPageList[1];
		String time = ipNameTimeBookPageList[2];
		String book = ipNameTimeBookPageList[3];
		String page = ipNameTimeBookPageList[4];
		
		boolean boolOne = false;
		boolean boolTwo = false;
		
		int times = 0;
		
		// IpBookPage Column Family check and insert operator
		if((times = this.QueryIpBookPageCF(ip, book)) > 0) {
			boolOne = this.InsertIntoIpBookPageCF(ip, book, page, times + 1, time);
		}else{
			boolOne = this.InsertIntoIpBookPageCF(ip, book, page, 1, time);
		}
		
		// UserBookPage Column Family operator
		if(user.equals(" ")) {
			boolTwo = true;				// None user name item
		}else{
			if((times = this.QueryUserBookPageCF(user, book)) > 0){
				boolTwo = this.InsertIntoUserBookPageCF(user, book, page, times + 1, time);
			}else{
				boolTwo = this.InsertIntoUserBookPageCF(user, book, page, 1, time);
			}
		}
		
		return boolOne & boolTwo;
	}
	
	/**
	 * Query and check item is exist in IpBookPage CF or not
	 * 
	 * return : 0 for nor exist
	 * 		   >0 for times of this book
	 */
	private int QueryIpBookPageCF(String ip, String book) {
		return this.cassandraQuery.QueryIpBookPage(ip, book);
	}

	/**
	 * Query and check item is exist in UserBookPage CF or not
	 * 
	 * return : 0 for nor exist
	 * 		   >0 for times of this book
	 */
	private int QueryUserBookPageCF(String user, String book) {
		return this.cassandraQuery.QueryUserBookPage(user, book);
	}

	/**
	 * Insert into IpBookPage CF
	 * 
	 * return : TRUE for success, and FALSE for another side
	 */
	private boolean InsertIntoIpBookPageCF(String ip, String book, String page, int times, String time) {
		return this.cassandraInsert.InsertIntoIpBookPage(ip, book, page, times, time);
	}
	
	/**
	 * Insert into UserBookPage CF
	 * 
	 * return : TRUE for success, and FALSE for another side
	 */
	private boolean InsertIntoUserBookPageCF(String user, String book, String page, int times, String time) {
		return this.cassandraInsert.InsertIntoUserBookPage(user, book, page, times, time);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		BookPageBoltUtil bpbu = new BookPageBoltUtil();
//		
//		String line = "10.10.10.10#cadal#14/Sep/2000:01:01:01#00000001#12";
//
//		boolean resultBoolean =  bpbu.DealBookPage(line);
//		
//		if(resultBoolean) {
//			System.out.println("Nice");
//		}else{
//			System.out.println("Paining!");
//		}
	}

}
