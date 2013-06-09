package cn.cadal.dis.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SqlDetail {
	Connection conn = null;
	final int UPPER = 10;

	private Connection ConnectMySql(String url, String username, String passwd) {
		Connection con = null;
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			con = DriverManager.getConnection(url, username, passwd);

			while ((con == null) && ((count++) < this.UPPER)) {
				con = DriverManager.getConnection(url, username, passwd);
				System.out.println("数据库连接超过1次");
				Thread.sleep(500);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return con;
	}

	private String query(String sql) {
		String result = "";
		boolean isFirst = true;
		try {
			//String url = "jdbc:mysql://10.15.62.63:3306/CadalDis?autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
			String url = "jdbc:mysql://10.15.62.230:3306/CadalDis";
			String username = "root";
			String password = "Cadal205";
			this.conn = ConnectMySql(url, username, password);

			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			List<String> list = new ArrayList<String>();
			while (rs.next()) {
				if (!list.contains(rs.getString(1))) {
					list.add(rs.getString(1));
				}
			}
			for (String str : list) {
				if (isFirst) {
					result = result + str;
					isFirst = false;
				} else {
					result = str + "$" + result;
				}
			}
			this.conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return result;
	}

	/**
	 * 
	 * @param time
	 *            "2013-04-11 17:00"
	 * @param name
	 *            "VD_BookId"或者"VD_UserName"或者"VD_IP"
	 * @return 用$符号连接的name字段对应的值。07018720$07018722$07018723$
	 */
	public String queryViewDetail(String time, String name) {
		String result = "";
		if (name.equals("VD_BookId") || name.equals("VD_UserName")
				|| name.equals("VD_IP")) {
			String sql = "select " + name
					+ " from ViewDetail where VD_Date = \"" + time + "\"";
			result = query(sql);
			return result;
		} else
			return "查询的属性名错误";
	}

	/**
	 * 
	 * @param start
	 *            "2013-04-11 17:00"
	 * @param end
	 *            "2013-04-11 21:00"
	 * @param name
	 *            "VD_BookId"或者"VD_UserName"或者"VD_IP"
	 * @return 用$符号连接的name字段对应的值。07018720$07018722$07018723$
	 */
	public String queryViewDetail(String start, String end, String name) {
		String result = "";
		if (name.equals("VD_BookId") || name.equals("VD_UserName")
				|| name.equals("VD_IP")) {
			String sql = "select " + name
					+ " from ViewDetail where VD_Date >= \"" + start + "\""
					+ " and " + "VD_Date " + " < " + "\"" + end + "\"";
			result = query(sql);
			return result;
		} else
			return "查询的属性名错误";
	}

	public static void main(String[] args) {
//		SqlDetail sqlDetail = new SqlDetail();
//		String start = "2013-04-17 08:00";
//		String end = "2013-04-17 10:00";
//		String name = "VD_UserName";
//		//String name = "VD_BookId";
//		//String name = "VD_IP";
//		// System.out.println(sqlDetail.queryViewDetail(start, name));
//		System.out.println(sqlDetail.queryViewDetail(start, end, name));
	}
}
