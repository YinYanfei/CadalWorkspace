package cn.cadal.cql;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CqlOp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		java.sql.Connection con = null;
		
		try{
			String sea = "select name from school where key = 'a234';";
			
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			
			con = DriverManager.getConnection("jdbc:cassandra://10.15.62.75:9160/MyKeyspace2");
			
			PreparedStatement statement = con.prepareStatement(sea);
			
			//statement.execute(sea);
			
			ResultSet rs = statement.executeQuery();
						
			while(rs.next()){
				System.out.println(rs.getString(1) + "  " );
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
