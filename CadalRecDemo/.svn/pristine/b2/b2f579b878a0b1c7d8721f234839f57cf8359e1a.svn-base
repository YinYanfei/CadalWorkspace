package cn.cadal.rec.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Book {

	public static String  url = "jdbc:postgresql://10.15.62.71:5432/cadal_metadata_full_dbo2";
	public static String  username = "cadal";
	public static String  password = "Cadal205";

	public static Connection con = connectPG(url, username, password);
	
	private String bookNo;
	private String bookName;
	private String press;
	private String author;

	public static Connection connectPG(String url, String username, String password) {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			try {
				con = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return con;
	}
	
	public Book() {
	}

	public Book(String bookNo, String bookName, String press, String author) {
		this.bookName = bookName;
		this.bookNo = bookNo;
		this.press = press;
		this.author = author;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// for test
		List<String> list = Book.getBookInfo("03006888");
		System.out.println(list.get(0));
		System.out.println(list.get(1));
		System.out.println(list.get(2));
		System.out.println(list.get(3));
		try {
			Book.con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param bookNo
	 *            书号
	 * @return List<String> [ bookNo bookName bookPress bookAuthor ]
	 * 
	 */
	public static List<String> getBookInfo(String bookNo) {
		List<String> info = new ArrayList<String>();
		String sql = "select * from cbook where \"BookNo\"='" + bookNo + "'";
		ResultSet rs = null;
		boolean flag = false;
		try {
			Statement statement = con.createStatement();
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				flag = true;
				String bookName = rs.getString("Title");
				String author = rs.getString("Creator");
				String press = rs.getString("Publisher");
				info.add(bookNo);
				info.add(bookName);
				info.add(press);
				info.add(author);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (flag == false)
			return null;
		else
			return info;
	}

	/**
	 * 
	 * @param bookid
	 *            处理之后的id（1到1670000+）
	 * @return bookNo 数据库中的8位书号
	 */
	public static String getBookNobyId(int bookid) {
		String bookNo = "";
		String filePath = "D:\\CADAL\\Recommendation\\common\\bookno_bookid.map";
		File f = new File(filePath);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				String[] arr = temp.split(" ");
				if (Integer.parseInt(arr[0]) == bookid) {
					bookNo = arr[1];
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return bookNo;
	}

	/**
	 * 
	 * @param bookNo
	 *            数据库中的8位书号
	 * @return bookid 处理之后的id（1到1670000+）
	 */
	public static int getidByBookNo(String bookNo) {
		int id = 0;
		String filePath = "D:\\CADAL\\Recommendation\\common\\bookno_bookid.map";
		File f = new File(filePath);
		BufferedReader br = null;
		boolean flag = false;
		try {
			br = new BufferedReader(new FileReader(f));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				String[] arr = temp.split(" ");
				id++;
				if (arr[1].equals(bookNo)) {
					flag = true;
					break;
				}
			}
			if (flag == false) {
				return -1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return id;
	}
	
	public String getBookNo() {
		return this.bookNo;
	}

	public String getBookName() {
		return this.bookName;
	}

	public String getPress() {
		return this.press;
	}

	public String getAuthor() {
		return this.author;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public void setPress(String press) {
		this.press = press;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
