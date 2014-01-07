package cn.edu.zju.cadal.hbase.insertInfo;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;

public class Utils {

	public static String TABLE_NAME = "access-log";
	
	private Configuration conf;
	public static HTable htable;
	
	/**
	 * Construct function
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	public Utils(String tableName) throws Exception {
		this.conf = HBaseConfiguration.create();
		this.conf.set("hbase.zookeeper.quorum", "10.15.62.72");
		this.conf.set("hbase.zookeeper.property.clientPort", "2182");
		this.conf.set("hbase.master", "10.15.62.72:60000");
		
		this.htable = new HTable(this.conf, tableName);
	}
	
	/**
	 * Close HTable handler
	 */
	public void CloseHTable(){
		try {
			this.htable.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Transfer IP address to Integer
	 * 
	 * @param ip
	 * @return
	 */
	public int TransferIPToInt(String ip) {
		byte[] byteArr = new byte[4];
		try{
			String [] ipArray = ip.split("\\.");
			byteArr[0] = (byte)(Integer.valueOf(ipArray[0]) & 0xFF);
			byteArr[1] = (byte)(Integer.valueOf(ipArray[1]) & 0xFF);
			byteArr[2] = (byte)(Integer.valueOf(ipArray[2]) & 0xFF);
			byteArr[3] = (byte)(Integer.valueOf(ipArray[3]) & 0xFF);
			
			int res = (byteArr[3] & 0xFF) | ((byteArr[2]<<8) & 0xFF00) | ((byteArr[1]<<16) & 0xFF0000) | ((byteArr[0]<<24) & 0xFF000000);
			return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	/**
	 * Transfer Integer to IP address
	 * 
	 * @param ipInt
	 * @return
	 */
	public String TransferIntToIp(int ipInt) {
		return new StringBuilder().append(((ipInt >> 24) & 0xff)).append('.')
				.append((ipInt >> 16) & 0xff).append('.').append(
						(ipInt >> 8) & 0xff).append('.').append((ipInt & 0xff))
				.toString();
	}
	
	/**
	 * Transfer Date to Long
	 * 25/Sep/2013:00:10:29
	 */
	public long TransferDateToLong(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("d/MMM/yyyy:hh:mm:ss",Locale.ENGLISH);
		try {
			Date date = sdf.parse(dateString);
			
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	/**
	 * Transfer Long to Date String
	 */
	public String TransferLongToDate(long dateLong) {
		return (new Date(dateLong)).toString();
	}

	/**
	 * Main function
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}
	
}

