package cn.cadal.dis.java.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SqlTimes {
	Connection conn = null;
	final int UPPER = 10;

	private Connection ConnectMySql(String url, String username, String passwd) {
		Connection con = null;
		int count = 0;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			con = DriverManager.getConnection(url, username, passwd);
			
			while((con==null)&&((count++)<this.UPPER)){
				con = DriverManager.getConnection(url, username, passwd);
				System.out.println("数据库连接超过1次");
				Thread.sleep(500);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return con;
	}

	public SqlTimes(){}
	
	/**
	 * 
	 * @param sql
	 * @return
	 */
	private int query(String sql) {
		int times = 0;
		try {
			//String url = "jdbc:mysql://10.15.62.63:3306/CadalDis?autoReconnect=true&failOverReadOnly=false&maxReconnects=10";
			String url = "jdbc:mysql://10.15.62.230:3306/CadalDis";
			String username = "root";
			String password = "Cadal205";
			this.conn = ConnectMySql(url, username, password);
			
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);

			while(rs.next()){
				times = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		return times;
	}

	/**
	 * 
	 * @param min 
	 *             2013-04-11 14:00
	 * @param name
	 *             "VT_ViewTimes"或者"VT_BookTimes"或者"VT_UserTimes"
	 * @return     name属性对应的次数
	 */
	public int QueryViewTimes(String min, String name) {
		int times = 0;
		if(name.equals("VT_ViewTimes")||name.equals("VT_BookTimes")||name.equals("VT_UserTimes")){
		String sql = "select " + name + " from ViewTimes where VT_Date = \""
				+ min + "\"";
		times = this.query(sql);
		return times;
		}
		else{
			System.out.println("查询的属性名错误");
			return 0;
		}
			
	}
	
	/**
	 * 
	 * @param start
	 *               2013-04-11 14:00 
	 * @param end
	 *               2013-04-11 19:00
	 * @param name
	 *               "VT_ViewTimes"或者"VT_BookTimes"或者"VT_UserTimes"
	 * @return       name属性对应的次数
	 */
	public int QueryViewTimes(String start, String end, String name) {
		int times = 0;
		if(name.equals("VT_ViewTimes")||name.equals("VT_BookTimes")||name.equals("VT_UserTimes")){
			String sql = "select sum(" + name +  ") from ViewTimes where VT_Date >= \"" +  start + "\"" + " and " + "VT_Date "  + " < " + "\"" +end +"\"";
			times = this.query(sql);
			return times;
		}
		else
		{
			System.out.println("查询的属性名错误");
			return 0;
		}
			
	}

	public static void main(String[] args) {
		
//		SqlTimes sqlTimes = new SqlTimes();
//		String start = "2013-04-01 08:00:00";
//		String end = "2013-04-17 10:00:00";
//		String name = "VT_UserTimes";
		//String name = "VT_ViewTimes";
		//String name = "VT_BookTimes";
		//String name = "VT_IpTimes";
//		int times = sqlTimes.QueryViewTimes(start,end, name);
//		System.out.println(times);
//		
		//int times2 = sqlTimes.QueryViewTimes(start, name);
		//System.out.println(times2);
	}
}
