package cn.edu.zju.cadal.utils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.hadoop.hbase.util.Bytes;

public class HBaseUtil {
	
	public static int numOfRegion = 7;

	/**
	 * Translate IP to long
	 * 
	 * @param ipStr
	 * @return
	 */
	public static long ip2long(String ipStr) {
		String[] ips = ipStr.split("[.]");

		return (Long.parseLong(ips[0]) << 24) + (Long.parseLong(ips[1]) << 16)
				+ (Long.parseLong(ips[2]) << 8) + Long.parseLong(ips[3]);
	}

	/**
	 * Translate long to IP
	 * 
	 * @param ipLong
	 * @return
	 */
	public static String long2ip(long ipLong) {
		long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		long num = 0;
		StringBuffer ipInfo = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			num = (ipLong & mask[i]) >> (i * 8);
			if (i > 0) {
				ipInfo.insert(0, ".");
			}
			ipInfo.insert(0, Long.toString(num, 10));
		}
		return ipInfo.toString();
	}

	/**
	 * MD5 hash for username, ip and bookid
	 * 
	 * @param str
	 * @return
	 */
	public static String md5Hash(String str) {
		try {
			byte[] byteArr = str.getBytes();

			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(byteArr);
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0) 
					i += 256;
				if (i < 16) 
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			
			return buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * RowKey begin with timeStamp need link salt prefix, to balance operation
	 * on different regions.
	 * 
	 * @param timestamp
	 * @return
	 */
	public static int saltPre(long timestamp) {
		return (int) (timestamp % HBaseUtil.numOfRegion);
	}
	
	/**
	 * Generate common row key: salt + timestamp + ip + user
	 * 
	 * @param timestamp
	 * @param ip
	 * @param user
	 * @return
	 */
	public static byte[] rowKey(long timestamp, String ip, String user) {
		byte [] saltByte = Bytes.toBytes(HBaseUtil.saltPre(timestamp));
		byte [] timestampByte = Bytes.toBytes(timestamp);
		byte [] ipByte = Bytes.toBytes(HBaseUtil.ip2long(ip));
		byte [] md5UserByte = Bytes.toBytes(HBaseUtil.md5Hash(user));
		
		byte[] byteArr = new byte[saltByte.length + timestampByte.length + ipByte.length + md5UserByte.length];
		
		int lenByteArr = 0;
		System.arraycopy(saltByte, 0, byteArr, 0, saltByte.length);
		lenByteArr += saltByte.length;
		
		System.arraycopy(timestampByte, 0, byteArr, lenByteArr, timestampByte.length);
		lenByteArr += timestampByte.length;
		
		System.arraycopy(ipByte, 0, byteArr, lenByteArr, ipByte.length);
		lenByteArr += ipByte.length;
		
		System.arraycopy(md5UserByte, 0, byteArr, lenByteArr, md5UserByte.length);
		
		return byteArr;
	}

	/**
	 * Generate rowkey: ip + timeatamp
	 * 
	 * @param ip
	 * @param timestamp
	 * @return
	 */
	public static byte[] rowkeyIpTime(String ip, long timestamp) {
		byte [] timestampByte = Bytes.toBytes(timestamp);
		byte [] ipByte = Bytes.toBytes(HBaseUtil.ip2long(ip));
		
		byte[] byteArr = new byte[timestampByte.length + ipByte.length];
		
		System.arraycopy(ipByte, 0, byteArr, 0, ipByte.length);
		System.arraycopy(timestampByte, 0, byteArr, ipByte.length, timestampByte.length);
		
		return byteArr;
	}
	
	/**
	 * Generate rowkey: user + timestamp
	 * 
	 * @param user
	 * @param timestamp
	 * @return
	 */
	public static byte[] rowkeyUserTime(String user, long timestamp) {
		byte [] timestampByte = Bytes.toBytes(timestamp);
		byte [] md5UserByte = Bytes.toBytes(HBaseUtil.md5Hash(user));
		
		byte[] byteArr = new byte[timestampByte.length + md5UserByte.length];
		
		System.arraycopy(md5UserByte, 0, byteArr, 0, md5UserByte.length);
		System.arraycopy(timestampByte, 0, byteArr, md5UserByte.length, timestampByte.length);
		
		return byteArr;
	}
	
	/**
	 * Generate rowkey: bookid + timestamp
	 * 
	 * @param bookid
	 * @param timestamp
	 * @return
	 */
	public static byte[] rowkeyBookTime(String bookid, long timestamp) {
		byte [] timestampByte = Bytes.toBytes(timestamp);
		byte [] bookByte = Bytes.toBytes(bookid);
		
		byte[] byteArr = new byte[timestampByte.length + bookByte.length];
		
		System.arraycopy(bookByte, 0, byteArr, 0, bookByte.length);
		System.arraycopy(timestampByte, 0, byteArr, bookByte.length, timestampByte.length);
		
		return byteArr;
	}
	
	
	/**
	 * 把字符串形式的时间转化成byte数组
	 * @param time yyyy-MM-dd HH:mm:ss
	 * @return byte[]
	 */
	public static byte[] time2ByteArr(String time){
		try{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date s = format.parse(time);
			long l = s.getTime() * 1000 ;
			return Bytes.toBytes(l);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static byte[] rowkeyRegionTime(int region,String time){
		byte[] rowkey = new byte[12];
		System.arraycopy(Bytes.toBytes(region), 0, rowkey, 0, 4);
		System.arraycopy(HBaseUtil.time2ByteArr(time), 0, rowkey, 4, 8);
		return rowkey;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TEST
//		String ipStr = "10.15.62.75";
//		long ipLong = 168771147;
//		String userName = "�㽭��ѧ";
//		
//		System.out.println(HBaseUtil.ip2long(ipStr));
//		System.out.println(HBaseUtil.long2ip(ipLong));
//		System.out.println(HBaseUtil.md5Hash(userName));
//		
//		long timestamp = System.currentTimeMillis();
//		System.out.println(timestamp);
//		System.out.println(HBaseUtil.saltPre(timestamp));
//
//		System.out.println("-----------------------");
//		HBaseUtil.rowKey(timestamp, ipStr, userName);
	}
}
