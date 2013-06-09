package cn.cadal.audio.index;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetMetadata {
	public String bookNum;
	Connection con = null;
	public int errorFlag;

	// 构造函数
	public GetMetadata() {
		this.errorFlag = 0; // 正常情况为0;

		// 连数据库
		String Username = "cadal";      // 数据库用户名
		String userPasswd = "Cadal205"; // 密码
		String url = "jdbc:postgresql:" + "//10.15.62.99:5432/cadal_metadata_full_dbo2";

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
	
	public String findISBN(String bookid) {
		// 连数据库
		String username = "cadal";      // 数据库用户名
		String userPasswd = "Cadal205"; // 密码
		String url = "jdbc:postgresql:" + "//10.15.62.99:5432/cadal_metadata_full_dbo2";
		
		String result = "";
		
		// 加载驱动程序以连接数据库
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println(url);
			Connection con2 = DriverManager.getConnection(url, username, userPasswd);
			
			Statement statement = con2.createStatement();
			String sql = "SELECT * FROM \"cbook\"";
			ResultSet rs = statement.executeQuery(sql);
			if (!rs.next()) {
				
				System.out.println("没有查找到数据");
			}else{
				System.out.println(rs.getFetchSize());
			}
			
			return result;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * main函数从数据库里面得到相关的数据，然后存入文件
	 * 详细的代码在231服务器上
	 * @param args
	 */
	public static void main(String[] args) {
		GetMetadata ct = new GetMetadata();
		
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		// 对应数据库的字段
		String  BookNo= "";
		String  Title= "";
		String  Creator= "";
		String  Subject= "";
		String  Publisher= "";
		
		int num = 0;
		String addr = "E:/audio";
		
		try{
			Statement statement = ct.con.createStatement();
			String sql = "SELECT * FROM \"cbook\" where \"HostID\" = '51'";
			ResultSet rs = statement.executeQuery(sql);
			
			System.out.println("Good");
			
			// 文件打开
			FileWriter writerAudio = new FileWriter(addr+"audio-20120806.txt", true);
			
			System.out.println("Files Created, DB Connecting.");
			
			if (!rs.next()) {
				System.out.println("没有查找到数据");
			}else{
				do {
					BookNo = rs.getString("BookNo");
					Title = rs.getString("Title");
					Creator = rs.getString("Creator");
					Subject = rs.getString("Subject");
					Publisher = rs.getString("Publisher");
					
					// 写入文件
					writerAudio.write(BookNo + " #### " + Title.replaceAll("\n", " | ") + " #### " + Creator.replaceAll("\n", " | ") + " #### " + Subject.replaceAll("\n", " | ") + " #### " + Publisher.replaceAll("\n", " | ") + "\n");

					// 计数
					++num;
				} while (rs.next());
			}
			
			System.out.println(num);
			
			// 文件关闭
			writerAudio.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
	}

}
