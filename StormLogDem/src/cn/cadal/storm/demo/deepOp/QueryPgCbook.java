package cn.cadal.storm.demo.deepOp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cadal.storm.demo.util.Constants;

public class QueryPgCbook {

	static private final Log LOG = LogFactory.getLog(QueryPgCbook.class);
	
	// Connection variable
	private Connection con = null;

	/**
	 * Construnct function
	 */
	public QueryPgCbook(){
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
	 * @param listBooknoPara
	 * @return
	 */
	public List<String> GetBookTitle(List<List<String>> listBookno) {
		List<String> listTitle = new ArrayList<String>();
		
		try{
			Statement statement = con.createStatement();
			
			for(int i = 0; i < listBookno.size(); ++i){
				String sql = "SELECT \"Title\" FROM \"cbook\" where \"BookNo\"='" + listBookno.get(i).get(0) + "'";
				
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
		
		return listTitle;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		
	}

}
