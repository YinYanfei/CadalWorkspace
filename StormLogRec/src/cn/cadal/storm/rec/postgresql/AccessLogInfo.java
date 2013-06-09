package cn.cadal.storm.rec.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccessLogInfo {
	
	// BookIdPage Map
	public Map<String, List<String>> bookIdPageMap = null;
	
	// Connection variable
	private Connection con = null;
	
	/**
	 * Construnct function
	 */
	public AccessLogInfo(){

		// Init 
		this.bookIdPageMap = new HashMap<String, List<String>>();
		
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
	 * Get BookId-PageList Info
	 */
	public void GetBookIdPage(String userName) {
		
		this.bookIdPageMap = new HashMap<String, List<String>>();
		
		try{
			Statement statement = con.createStatement();

			// get userid and emailaddress(username)
			String sql = "SELECT \"bookId\", \"pageId\" FROM \"accesslog\" where \"userName\"='" + userName + "'";
			
			ResultSet rs = statement.executeQuery(sql);
			
			while (rs.next()) {
				String bookid = rs.getString("bookId");
				String pageid = rs.getString("pageId");
				
				if(this.bookIdPageMap.containsKey(bookid)){
					List<String> listTmp = this.bookIdPageMap.get(bookid);
					
					listTmp.add(this.Remove0(pageid));
					
					this.bookIdPageMap.put(bookid, listTmp);
				}else{
					List<String> listNew = new ArrayList<String>();
					listNew.add(this.Remove0(pageid));
					this.bookIdPageMap.put(bookid, listNew);
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Remove all '0' at start of Str
	 */
	private String Remove0(String str) {
		try{
			while(str.substring(0, 1).equals("0")) {
				str = str.substring(1);
			}
			
			return str;
		}catch(Exception e){
			return "1";
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		AccessLogInfo accessLogInfo = new AccessLogInfo();
//		
//		accessLogInfo.GetBookIdPage("wjhwb");
//		
//		System.out.println(accessLogInfo.bookIdPageMap.size());
//		
//		accessLogInfo.GetBookIdPage("zju");
//		
//		System.out.println(accessLogInfo.bookIdPageMap.size());
//		
//		accessLogInfo.GetBookIdPage("zju");
//		
//		System.out.println(accessLogInfo.bookIdPageMap.size());
//		
//		accessLogInfo.GetBookIdPage("Yanfei");
//		
//		System.out.println(accessLogInfo.bookIdPageMap.size());
		
		// Output
//		Iterator iter = accessLogInfo.bookIdPageMap.keySet().iterator(); 
//		while (iter.hasNext()) { 
//		    Object key = iter.next(); 
//		    Object val = accessLogInfo.bookIdPageMap.get(key);
//		    
//		    System.out.println(key.toString() + " -- -- " + val.toString());
//		}
//		
//		System.out.println(accessLogInfo.bookIdPageMap.size());
	}

}
