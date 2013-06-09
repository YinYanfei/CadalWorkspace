package cn.cadal.storm.exp.pg.bookTitle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BookTitle {

	static private final Log LOG = LogFactory.getLog(BookTitle.class);
	
	// Connection variable
	private Connection con = null;

	/**
	 * Construnct function
	 */
	public BookTitle(){
		String Username = Constants.USERNAME;
		String userPasswd = Constants.PASSWD;
		String url = "jdbc:postgresql:" + Constants.DBURL;

		try {
			Class.forName("org.postgresql.Driver");
			System.out.println(url);
			con = DriverManager.getConnection(url, Username, userPasswd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 
	 * @param listBooknopara
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> Preprocess(List<String> listBooknoPara) {
		List<String> listStr = new ArrayList<String>();
		
		for(int i = 1; i < listBooknoPara.size(); ++i) {
			String strTmp = listBooknoPara.get(i);
			listStr.add(strTmp.substring(0, strTmp.indexOf("_")));
		}
		
		HashSet hs = new HashSet(listStr);
		listStr.clear();
		listStr.addAll(hs);
		
		return listStr;
	}
	
	/**
	 * 
	 * @param listBooknoPara
	 * @return
	 */
	public List<String> GetBookTitle(List<String> listBooknoPara) {
		List<String> listTitle = new ArrayList<String>();
		
		List<String> listBookno = this.Preprocess(listBooknoPara);

		/* ------------------ V2 ----------------------		
		try{
			Statement statement = con.createStatement();
			
			for(int i = 0; i < listBookno.size(); ++i){
				String sql = "SELECT \"Title\" FROM \"cbook\" where \"BookNo\"='" + listBookno.get(i) + "'";
				
				ResultSet rs = statement.executeQuery(sql);
				if(rs.next()){
					String title = rs.getString("Title");
					listTitle.add(title);
				}
			}
			
			return listTitle;
		}catch(Exception e) {
			LOG.warn("PG access: " + listBookno.get(0));
			e.printStackTrace();
		}
		*/
		
		return listBookno;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		BookTitle bt = new BookTitle();
//		
//		List<String> listBookno = new ArrayList<String>();
//		
//		listBookno.add("07018720_1.0.0.0.0");
//		listBookno.add("07018721_1.2.2.2.0");
//		listBookno.add("07018720_2.0.0.0.0");
//		
//		List<String> listTitle = bt.GetBookTitle(listBookno);
//		
//		for(int i = 0; i < listTitle.size(); ++i){
//			System.out.println(listTitle.get(i));
//		}
		
//		List<String> listTmp = bt.Preprocess(listBookno);
//		
//		for(int i = 0 ; i < listTmp.size(); ++i) {
//			System.out.println(listTmp.get(i));
//		}

	}

}
