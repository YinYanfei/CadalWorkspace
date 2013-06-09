package cn.cadal.storm.util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateInfo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Date
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH");
		String dateStr = sdf.format(date);
		
		System.out.println(dateStr);

		// Ip-deal
		String str = "10.15.62.103";
		System.out.println(str.substring(0, str.indexOf('.')));
		
		// cassandra op
		java.sql.Connection con = null;
		
		try{
			String sea = "select times from cadalStatistics where key = '2012-05-15_12';";
			
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			con = DriverManager.getConnection("jdbc:cassandra://10.15.62.75:9160/MyKeyspace2");
			PreparedStatement statement = con.prepareStatement(sea);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				int times = rs.getInt("times");
				System.out.println(times);
				times += 10;
				System.out.println(times);
				String ins = "update cadalStatistics set times = " + times + " where key = '2012-05-15_12';";
				statement.execute(ins);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}

		
	}

}
