package cn.cadal.storm.bolt.statistics;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadalSta extends AbstractSta{
	
	/**
	 * str is "10.15.62.78 07018720 00000031 Yanfei"
	 */
	@Override
	public void deal(String str) {
		/*
		 * I need do nothng here, just add times!
		 */
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH");
		String dateStr = sdf.format(date);
		
		java.sql.Connection con = null;
		try{
			// Search
			String sea = "select times from cadalStatistics where key = '" + dateStr + "';";
			
			// System.out.println("CadalSta  ===>  "  + sea);
			
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			con = DriverManager.getConnection("jdbc:cassandra://10.15.62.75:9160/MyKeyspace2");
			PreparedStatement statement = con.prepareStatement(sea);
			ResultSet rs = statement.executeQuery();
			
			// Update
			while(rs.next()){
				int times = rs.getInt("times");
				times++;
				String upd = "update cadalStatistics set times = " + times + " where key = '" + dateStr +"';";
				// System.out.println("CadalSta  ===>  "  + upd);
				statement.execute(upd);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test Main Function
	 * @param args
	 */
	public static void main(String [] args) {
		CadalSta cadalSta = new CadalSta();
		// cadalSta.deal("89890");
		// cadalSta.postgresqlMatchIp("202.120.11");
		System.out.println(cadalSta.postgresqlMatchBook("06344628"));

	}
	
}
