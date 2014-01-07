package cn.edu.zju.cadal.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtil {
	private  static Connection conn;

	private JDBCUtil() {
		super();
	}

	/**
	 * Create connection and init objects
	 * 
	 * @return
	 */
	public  static synchronized Connection getConn() {
		if (conn == null) {
			try {
//				// 将属性文件加载
//				File file = new File("jdbc.properties");
//				// 将文件转化为输入流
//				InputStream is = new FileInputStream(file);
//				// 创建属性对象
//				Properties prop = new Properties();
//				// 将属性流加载到属性对象中
//				prop.load(is);
				
				// 加载驱动文件 
				Class.forName("com.mysql.jdbc.Driver");

				// 创建连接从属性文件取出相应值连接
				conn = DriverManager.getConnection("jdbc:mysql://10.15.62.75:3306/CadalSec",
						"cadal", "Cadal205");

			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}

		return conn;
	}
	
	/**
	 * Close and release resource
	 * 
	 * @param pstmt
	 * @param rs
	 */
	public static void release(Statement pstmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
