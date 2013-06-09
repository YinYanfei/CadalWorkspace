package cn.cadal.storm.bolt.cassandra;

import java.nio.ByteBuffer;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InitDataLog {

	java.sql.Connection con = null;
	
	/**
	 * Construct functions
	 */
	public InitDataLog() {
	}
	
	/**
	 * Store into cassandra	-- tuple metadata
	 */
	public void Store(String str) {
		String [] strList = str.split(" ");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
		String dateStr = sdf.format(date);
		
		java.sql.Connection con = null;
		
		try{
			String sea = "insert into cadalLog (KEY, ip, bookid, pageid, username) values('" 
				+ dateStr + "', '" + strList[0] + "', '" + strList[1] + "', '" + strList[2] + "', '" + strList[3] + "');";
			
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			con = DriverManager.getConnection("jdbc:cassandra://10.15.62.75:9160/MyKeyspace2");
			PreparedStatement statement = con.prepareStatement(sea);
			statement.execute(sea);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* 
     * 将String转换为bytebuffer，以便插入cassandra 
     */  
	public static ByteBuffer toByteBuffer(String value) {
		try {
			return ByteBuffer.wrap(value.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		InitDataLog initDataLog = new InitDataLog();
		initDataLog.Store("10.15.62.110 37018720 00000037");
	}

}
