package cn.cadal.storm.rec.postgresql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class UserNameAndID {

	// Store <username, useid> information
	public Map<String, Integer> useNameID = null;
	
	// Connection variable
	private Connection con = null;
	
	/**
	 * Construnct function
	 */
	public UserNameAndID(){
		// userNameID init
		this.useNameID = new HashMap<String, Integer>();
		
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
	 * To get UserName and UserID
	 */
	public int GetUserNameAndID(){
		
		try {
			Statement statement = con.createStatement();

			// get userid and emailaddress(username)
			String sql = "SELECT \"userId\", \"EmailAddress\" FROM \"cuser\"";
			
			ResultSet rs = statement.executeQuery(sql);
			if (!rs.next()) {
				return 1;
			}

			do {
				this.useNameID.put(rs.getString("EmailAddress"), rs.getInt("userId"));
			} while (rs.next());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		UserNameAndID userNameAndID = new UserNameAndID();
//		
//		userNameAndID.GetUserNameAndID();
//		
//		// output test
//		System.out.println(userNameAndID.useNameID.size());
//		
//		Iterator iter = userNameAndID.useNameID.keySet().iterator(); 
//		while (iter.hasNext()) { 
//		    Object key = iter.next(); 
//		    Object val = userNameAndID.useNameID.get(key);
//		    
//		    System.out.println(key.toString() + " -- -- " + val.toString());
//		}
	}

}
