package cn.cadal.storm.util;

import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class PreInsertCadalSta {

	/**
	 * insert function
	 */
	public void Insert(String year, String month, int start, int days) {
		java.sql.Connection con = null;
		String key = "";
		int times = 0;
		String day = "";
		
		try{
			for(int i = start; i <= days; ++i ){
				
				if(i < 10) {
					day = "0" + String.valueOf(i);
				}else{
					day = String.valueOf(i);
				}
				
				for(int j = 0; j < 24; ++j) {
					if(j < 10) {
						key = year + "-" + month + "-" + day + "_0" + String.valueOf(j);
					}else{
						key = year + "-" + month + "-" + day + "_" + String.valueOf(j);
					}
			    	String sea = "insert into cadalStatistics(key, times) values('" + key + "', " + times + ");";
			
			    	Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			    	con = DriverManager.getConnection("jdbc:cassandra://10.15.62.75:9160/---");
			    	PreparedStatement statement = con.prepareStatement(sea);
			    	
			    	statement.execute();
				}
				
				key = "";
				day = "";
			}
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		PreInsertCadalSta pics = new PreInsertCadalSta();
		
		String year = "2012";
		String month = "12";
		int start = 1;
		int days = 31;
		
		pics.Insert(year, month, start, days);
	}

}
