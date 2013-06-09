package cn.cadal.auth.java.control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PgAccess {
	public Connection con = null;
	public ResultSet rs;

	public PgAccess() {
		String Username = "cadal";      // 数据库用户名
		String userPasswd = "Cadal205"; // 密码
		String url = "jdbc:postgresql:" + "//10.15.62.71:5432/cadal_metadata_full_dbo2";

		try {
			Class.forName("org.postgresql.Driver");
			System.out.println(url);
			con = DriverManager.getConnection(url, Username, userPasswd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean OperatorPg(String sql) {	
		try {			
			Statement statement = con.createStatement();
			rs = statement.executeQuery(sql);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally{
			try {
				this.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		// Test
//		PgAccess pa = new PgAccess();
//		String sql = "SELECT * FROM \"cbook\" where \"BookNo\" = \'07018720\'";
//
//		pa.OperatorPg(sql);
//		ResultSet rs = pa.rs;
//		
//		try {
//			rs.next();
//			String BookNo = rs.getString("BookNo");
//			String Title = rs.getString("Title");
//			String Creator = rs.getString("Creator");
//			String Subject = rs.getString("Subject");
//			String Publisher = rs.getString("Publisher");
//			
//			System.out.println(BookNo + " #### " + Title.replaceAll("\n", " | ") + " #### " + Creator.replaceAll("\n", " | ") + " #### " + Subject.replaceAll("\n", " | ") + " #### " + Publisher.replaceAll("\n", " | ") + "\n");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
}
