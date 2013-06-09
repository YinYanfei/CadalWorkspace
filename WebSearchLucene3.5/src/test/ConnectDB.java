package test;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class ConnectDB {
	@SuppressWarnings("unchecked")
	public String bookNum;
	Connection con = null;
	public int errorFlag;

	// 构造函数
	public ConnectDB() {
		this.errorFlag = 0; // 正常情况为0;

		// 连数据库
		String Username = "cadal"; // 数据库用户名
		String userPasswd = "Cadal205"; // 密码
		String url = "jdbc:postgresql:" + "//10.15.62.71:5432/cadal_metadata_full_dbo2";

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
		String username = "cadal"; // 数据库用户名
		String userPasswd = "Cadal205"; // 密码
		String url = "jdbc:postgresql:" + "//10.15.62.71:5432/cadal_metadata_full_dbo2";
		
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
//				result = rs.getString("ISBN");
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
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		
		
		ConnectDB ct = new ConnectDB();
		ct.bookNum = "33047407";
		System.out.println(ct.findISBN("33047407"));
		
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
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
				System.out.println(rs);
				do {
					System.out.println("----");
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
	*/
	public static void main(String[] args) {
		ConnectDB ct = new ConnectDB();
		
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
		// 对应数据库的字段
		String  BookType= "";
		String  BookNo= "";
		String  Title= "";
		String  Creator= "";
		String  Subject= "";
		String  Publisher= "";
		
		int num = 0;
		String addr = "D:/20130228-IndexData2";
		
		try{
			Statement statement = ct.con.createStatement();
//			String sql = "SELECT * FROM \"cbook\" where \"BookType\" = 'audio'";
//			String sql = "SELECT * FROM \"cbook\" where \"BookType\" = 'dissertation'";
//			String sql = "SELECT * FROM \"cbook\" where \"BookType\" = 'modern'";
//			String sql = "SELECT * FROM \"cbook\" where \"BookType\" = 'journal'";
//			String sql = "SELECT * FROM \"cbook\" where \"BookType\" = 'minguo'";
//			String sql = "SELECT * FROM \"cbook\" where \"BookType\" = 'english'";
			String sql = "SELECT * FROM \"cbook\" where \"BookType\" = 'anc'";
			ResultSet rs = statement.executeQuery(sql);
			
			System.out.println("Good");
			
			// 文件打开
//			FileWriter writerAncient = new FileWriter(addr+"audio.txt", true);
//			FileWriter writerDissertation = new FileWriter(addr+"dissertation.txt", true);
//			FileWriter writerModern = new FileWriter(addr+"modern.txt", true);
//			FileWriter writerJournal = new FileWriter(addr+"journal.txt", true);
//			FileWriter writerMinguo = new FileWriter(addr+"minguo.txt", true);
//			FileWriter writerEnglish = new FileWriter(addr+"english.txt", true);
			FileWriter writerEnglish = new FileWriter(addr+"ancient123456.txt", true);
			
			FileWriter writerError = new FileWriter(addr+"error2.txt", true);
			
			System.out.println("Files Created, DB Connecting.");
			
			if (!rs.next()) {
				System.out.println("没有查找到数据");
			}else{
				do {
					BookType = rs.getString("BookType");
					BookNo = rs.getString("BookNo");
					Title = rs.getString("Title");
					Creator = rs.getString("Creator");
					Subject = rs.getString("Subject");
					Publisher = rs.getString("Publisher");
					
					// 按照booktype的不同存入不同的文件
//					if(BookType.equals("audio")){
//						writerAncient.write(BookNo + " #### " + Title.replaceAll("\n", " | ") + " #### " + Creator.replaceAll("\n", " | ") + " #### " + Subject.replaceAll("\n", " | ") + " #### " + Publisher.replaceAll("\n", " | ") + "\n");
//					}
//					}else if(BookType.equals("dissertation")) {
//						writerDissertation.write(BookNo + " #### " + Title.replaceAll("\n", " | ") + " #### " + Creator.replaceAll("\n", " | ") + " #### " + Subject.replaceAll("\n", " | ") + " #### " + Publisher.replaceAll("\n", " | ") + "\n");
//					}
//					else if(BookType.equals("modern")) {
//						writerModern.write(BookNo + " #### " + Title.replaceAll("\n", " | ") + " #### " + Creator.replaceAll("\n", " | ") + " #### " + Subject.replaceAll("\n", " | ") + " #### " + Publisher.replaceAll("\n", " | ") + "\n");
//					}
//					else if(BookType.equals("book")) {
//						writerEnglish.write(BookNo + " #### " + Title.replaceAll("\n", " | ") + " #### " + Creator.replaceAll("\n", " | ") + " #### " + Subject.replaceAll("\n", " | ") + " #### " + Publisher.replaceAll("\n", " | ") + "\n");
//					}else if(BookType.equals("journal")) {
//						writerJournal.write(BookNo + " #### " + Title.replaceAll("\n", " | ") + " #### " + Creator.replaceAll("\n", " | ") + " #### " + Subject.replaceAll("\n", " | ") + " #### " + Publisher.replaceAll("\n", " | ") + "\n");
//					}else if(BookType.equals("minguo")) {
//						writerMinguo.write(BookNo + " #### " + Title.replaceAll("\n", " | ") + " #### " + Creator.replaceAll("\n", " | ") + " #### " + Subject.replaceAll("\n", " | ") + " #### " + Publisher.replaceAll("\n", " | ") + "\n");
//					}else 
//					if(BookType.equals("english")) {
						writerEnglish.write(BookNo + " #### " + Title.replaceAll("\n", " | ") + " #### " + Creator.replaceAll("\n", " | ") + " #### " + Subject.replaceAll("\n", " | ") + " #### " + Publisher.replaceAll("\n", " | ") + "\n");
//					}
//					
//					else {
//						writerError.write(BookNo + " #### " + Title.replaceAll("\n", " | ") + " #### " + Creator.replaceAll("\n", " | ") + " #### " + Subject.replaceAll("\n", " | ") + " #### " + Publisher.replaceAll("\n", " | ") + "\n");
//					}
					
					// 计数
					++num;
				} while (rs.next());
			}
			
			System.out.println(num);
			
			// 文件关闭
//			writerAncient.close();
//			writerDissertation.close();
//			writerModern.close();
//			writerJournal.close();
//			writerMinguo.close();
			writerEnglish.close();
			//writerError.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		} 
	}

}
