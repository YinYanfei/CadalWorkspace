package cn.cadal.rec.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QueryInfoFromPg {
	Connection con = null;

	/**
	 * Construct function
	 */
	public QueryInfoFromPg() {
		// link db
		String Username = "cadal"; // dbusername
		String userPasswd = "Cadal205"; // passwd
		String url = "jdbc:postgresql:"
				+ "//10.15.62.71:5432/cadal_metadata_full_dbo2";
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

	/**
	 * search in postgreSQL and write result into file
	 */
	public List<Book> QueryMetaData(List<String> bookidList) {
		String Title = "";
		String Creator = "";
		String Publisher = "";
		String BookType = "";

		List<Book> bookInfo = new ArrayList<Book>();

		try {
			Statement statement = this.con.createStatement();
			String bookid = "";

			for (int i = 0; i < bookidList.size(); ++i) {
				bookid = bookidList.get(i);
				String sql = "SELECT \"Title\", \"Creator\", \"Publisher\", \"BookType\" FROM \"cbook\" where \"BookNo\" = \'"
						+ bookid + "\';";

				ResultSet rs = statement.executeQuery(sql);

				if (!rs.next()) {
					System.out.println("No result for bookid: " + bookid);
				} else {
					do {
						Book bookInfoUniq = new Book();

						Title = rs.getString("Title");
						Creator = rs.getString("Creator");
						Publisher = rs.getString("Publisher");
						BookType = rs.getString("BookType");

						bookInfoUniq.setBookNo(bookid);
						bookInfoUniq.setBookName(Title);
						bookInfoUniq.setAuthor(Creator);
						bookInfoUniq.setPress(Publisher);
						bookInfoUniq.setBookType(BookType);

						bookInfo.add(bookInfoUniq);
					} while (rs.next());
				}
			}

			this.con.close();
			return bookInfo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (this.con != null) {
				try {
					this.con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * search in postgreSQL for single bookid
	 */
	public Book QueryMetaDataUniq(String bookid) {
		String Title = "";
		String Creator = "";
		String Publisher = "";

		Book bookInfo = new Book();

		try {
			Statement statement = this.con.createStatement();

			String sql = "SELECT \"Title\", \"Creator\", \"Publisher\" FROM \"cbook\" where \"BookNo\" = \'" + bookid + "\';";

			ResultSet rs = statement.executeQuery(sql);

			if (!rs.next()) {
				System.out.println("No result for bookid: " + bookid);
			} else {
				do {
					Title = rs.getString("Title");
					Creator = rs.getString("Creator");
					Publisher = rs.getString("Publisher");

					bookInfo.setBookNo(bookid);
					bookInfo.setBookName(Title);
					bookInfo.setAuthor(Creator);
					bookInfo.setPress(Publisher);
				} while (rs.next());
			}

			this.con.close();
			return bookInfo;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (this.con != null) {
				try {
					this.con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		QueryInfoFromPg qifp = new QueryInfoFromPg();

//		List<String> bookidList = new ArrayList<String>();
//
//		bookidList.add("07018720");
//		bookidList.add("09001803");
//		bookidList.add("07005756");
//
//		List<Book> bookInfo = qifp.QueryMetaData(bookidList);
//
//		for (int i = 0; i < bookInfo.size(); ++i) {
//			System.out.println("---------------");
//			Book book = bookInfo.get(i);
//			System.out.println(book.getBookNo() + "  " + book.getBookName()
//					+ "  " + book.getAuthor() + "  " + book.getPress());
//		}
//
//		String bookid = "04100918";
//		
//		Book bookInfo = qifp.QueryMetaDataUniq(bookid);
//		
//		System.out.println(bookInfo.getBookNo());
//		System.out.println(bookInfo.getBookName());
//		System.out.println(bookInfo.getAuthor());
//		System.out.println(bookInfo.getPress());
	}

}
