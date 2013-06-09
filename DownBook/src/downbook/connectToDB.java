package downbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class connectToDB {
	public static Map bookInfo = new HashMap();
	public String bookNum;
	Connection con = null;
	public int errorFlag;

	// 构造函数
	public connectToDB() {
		this.errorFlag = 0; // 正常情况为0;

		// 连数据库
		String driverName = "com.mysql.jdbc.Driver"; // 驱动程序名
		String Username = Constants.USERNAME; // 数据库用户名
		String userPasswd = Constants.PWD; // 密码
		String url = "jdbc:postgresql:" + Constants.DBURL;

		// 加载驱动程序以连接数据库
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

	// 设置查询的书号
	// 连接数据库
	public int connectDB(String id) {
		bookNum = id;
		BookInfo bookin = new BookInfo();
		try {
			Statement statement = con.createStatement();
			// **************************************************************************
			// 查 cbook 表
			String sql = "SELECT * FROM \"cbook\"  WHERE \"BookNo\" =" + "'"
					+ bookNum + "'";
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			if (!rs.next()) {
				errorFlag = 1; // 表示数据库中没有相关的记录
				return 1;

				// 调用出错程序
			}

			do {
				bookin.hostId = rs.getString("hostId");
				// System.out.println (hostId + '\n');
				bookin.path2 = rs.getString("Path");
				System.out.println(bookin.path2 + '\n');
				bookin.title = rs.getString("title");
			} while (rs.next());
			// **************************************************************************
			// 查 contenthost 表
			sql = "SELECT * FROM \"contenthost\"  WHERE \"id\" =" + "'"
					+ bookin.hostId + "'";
			rs = statement.executeQuery(sql);

			if (!rs.next()) {
				errorFlag = 1; // 表示数据库中没有相关的记录
				// 调用出错程序
				return 1;
			}

			do {
				System.out.println("444");
				bookin.Username = rs.getString("Username");
				// System.out.println (Username + '\n');
				bookin.Password = rs.getString("Password");
				// System.out.println (Password + '\n');
				bookin.path1 = rs.getString("Path");

				System.out.println(bookin.path1 + '\n');
				bookin.FtpHost = rs.getString("Host");
				System.out.println(bookin.FtpHost + '\n');
			} while (rs.next());
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// *************************************************
		// 数据后期处理
		processData(bookin);
		return 0;
	}

	public void processData(BookInfo bookin) {
		// *************************************************
		// 数据后期处理

		// / ftp 处理
		String temp = bookin.FtpHost.substring(6, bookin.FtpHost.length());
		bookin.FtpHost = temp;
		// / path 的拼接
		if (bookin.path1.endsWith("/")) {
			String temp_path = bookin.path1.substring(0,
				bookin.path1.length() - 1);
			bookin.path1 = temp_path;
		}

		if (bookin.path2.startsWith(bookin.path1))
			bookin.real_path = bookin.path2;
		else
			bookin.real_path = bookin.path1 + bookin.path2;

		//
		bookInfo.put(bookNum, bookin);
	}
	
	/**
	 * Test this class 
	 */
	public static void main(String[] args) {
		connectToDB ct = new connectToDB();
		ct.bookNum = "33047407";
		BookInfo bookin = new BookInfo();
		
		String id = "";
		String BookID = "";
		String ISBN = "";
		
		try{
			Statement statement = ct.con.createStatement();

			String sql = "SELECT * FROM \"book_ISBN\"  WHERE \"BookID\" =" + "'"
					+ ct.bookNum + "'";
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			if (!rs.next()) {
				System.out.println("没有查找到数据");
			}else{
				do {
					id = rs.getString("id");
					BookID = rs.getString("BookID");
					ISBN = rs.getString("ISBN");
				} while (rs.next());

				System.out.println("-------------查出了相关的数据-------------");
				System.out.println(id);
				System.out.println(BookID);
				System.out.println(ISBN);
				
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
}
