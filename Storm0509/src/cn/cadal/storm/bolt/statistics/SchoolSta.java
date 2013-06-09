package cn.cadal.storm.bolt.statistics;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SchoolSta extends AbstractSta{
	
	@Override
	public void deal(String str) {
		/*
		 * str -- "10.15.62.78 07018720 00000031 Yanfei"
		 */
		String[] strSplit = str.split(" ");
		
		// match and chance times of diffirent school
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH");
		String dateStr = sdf.format(date);
		
		java.sql.Connection con = null;
		String schoolNo = "";

		try{
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			con = DriverManager.getConnection("jdbc:cassandra://10.15.62.75:9160/MyKeyspace2");

			// get the number of school
			schoolNo = this.postgresqlMatchIp(strSplit[0]);
			
			// get times of school in cassandra
			String search = "select " + schoolNo + " from cadalSchTimeSta where key = '" + dateStr + "';" ;
			
			// System.out.println("SchoolSta --->  " + search);
			
			PreparedStatement statement = con.prepareStatement(search);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()){
				int times = rs.getInt(1);
				times++;
				String insert = "update cadalSchTimeSta set " + schoolNo +  " = " + times + " where key = '" + dateStr + "';";
				// System.out.println("SchoolSta --->  " + insert);
				statement.execute(insert);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Main function
	 */
	public static void main(String [] Args) {
		SchoolSta ss = new SchoolSta();
		
		ss.deal("10.15.62.79 07018720 00000032 Yanfei");
		ss.deal("202.120.5.79 07018720 00000032 Yanfei");
	}
}
