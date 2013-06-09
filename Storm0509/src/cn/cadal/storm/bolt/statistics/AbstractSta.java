package cn.cadal.storm.bolt.statistics;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class AbstractSta {

	public abstract void deal(String str);
	
	/**
	 * Match ip to determine school
	 * @param ip
	 * @return name of school, it means not school ip if return "0"
	 */
	public String postgresqlMatchIp(String ip) {
		String [] strSplit = ip.split("\\.");
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			Connection conn;
			conn = DriverManager.getConnection(
					"jdbc:postgresql://10.15.62.99:5432/cadal_metadata_full_dbo2", "cadal",
					"Cadal205");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select \"GroupID\" from ciptable where \"IpAddress\" = '" + ip + "';");
			
			// match
			if(rs.next()) {
				return rs.getString(1);
			}
			
			rs = st.executeQuery("select \"GroupID\" from ciptable where \"IpAddress\" = '" + strSplit[0] + "." + strSplit[1] + "." + strSplit[2] + "';");
			
			if(rs.next()) {
				return rs.getString(1);
			}
			
			rs = st.executeQuery("select \"GroupID\" from ciptable where \"IpAddress\" = '" + strSplit[0] + "." + strSplit[1] + "';");
			if(rs.next()) {
				return rs.getString(1);
			}
			
			return "0";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * Match bookid ro find the type of book
	 * @param bookid
	 * @return type of book, "" means abnormal
	 */
	public String postgresqlMatchBook(String bookid) {
		try {
			Class.forName("org.postgresql.Driver").newInstance();
			Connection conn;
			conn = DriverManager.getConnection(
					"jdbc:postgresql://10.15.62.99:5432/cadal_metadata_full_dbo2", "cadal",
					"Cadal205");
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery("select \"BookType\" from cbook where \"BookNo\" = '" + bookid + "';");
			
			// match
			if(rs.next()) {
				return rs.getString(1);
			}else{
				return "book";
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "book";
	}
	
}
