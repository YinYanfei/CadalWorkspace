package cn.edu.zju.cadal.utils;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.mysql.jdbc.Statement;

public class MySQLUtil {

	private static JDBCUtil jdbcUtil = null;

	/**
	 * This function is used to insert into db
	 * 
	 * @param ip
	 * @param userName
	 * @return
	 */
	@SuppressWarnings("static-access")
	public boolean insert(String ip, String userName) {
		Statement stmt = null;
		ResultSet rs = null;
		final String TABLENAME = "CurrentDoubtableIP";
		String sql = "";
		
		try{
			stmt = (Statement) this.jdbcUtil.getConn().createStatement();
			int times = 1;
			String time = this.curTime();
			int valid = 1;
			
			String sqlQuery = "SELECT Times FROM " + TABLENAME + " WHERE IP = \"" + ip + "\";";
			rs = stmt.executeQuery(sqlQuery);
			if(rs.next()) {
				times = rs.getInt("Times");
				sql = "UPDATE " + TABLENAME + " SET Times = " + (times + 1) + ",Valid=" + valid + " WHERE IP=\"" + ip + "\";";
				this.insertIntoCollector(ip, userName, time, (times + 1));
			}else{
				sql = "INSERT INTO " + TABLENAME + " VALUE(\"" + ip + "\",\"" + time + "\"," + times + ",\"" + userName + "\"," + valid + ");";
				this.insertIntoCollector(ip, userName, time, times);
			}
			stmt.execute(sql);

			return true;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.jdbcUtil.release(stmt, rs);
		}
		
		return false;
	}

	/**
	 * Insert into table CollectDoubtableIP
	 * 
	 * @param ip
	 * @param userName
	 * @param time
	 * @param times
	 */
	public void insertIntoCollector(String ip, String userName, String time, int times) {
		Statement stmt = null;
		ResultSet rs = null;
		final String TABLENAME = "CollectDoubtableIP";
		String sql = "";
		
		try{
			stmt = (Statement) this.jdbcUtil.getConn().createStatement();
			sql = "INSERT INTO " + TABLENAME + "(Day, IP, Time, Times, UserName) VALUES( \"" + time.substring(0, time.indexOf(" ")) + "\",\"" 
					+ ip + "\",\"" + time + "\"," + times + ",\"" + userName + "\");";
			
			System.out.println(sql);
			
			stmt.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.jdbcUtil.release(stmt, rs);
		}
	}
	
	/**
	 * Query all rows from tableName
	 * 
	 * @param tableName
	 * @return
	 */
	@SuppressWarnings("static-access")
	public ResultSet query(String tableName) {
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = (Statement) this.jdbcUtil.getConn().createStatement();
			rs = stmt.executeQuery("select * from " + tableName + ";");

			return rs;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Query rows from tableName, limited by where statement
	 * 
	 * @param tableName
	 * @param resCol
	 * @param where
	 * @return
	 */
	@SuppressWarnings("static-access")
	public ResultSet query(String tableName, List<String> resCol, String where) {
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = (Statement) this.jdbcUtil.getConn().createStatement();

			String columns = "";
			for(String singleCol:resCol) {
				columns += "," + singleCol;
			}
			columns = columns.substring(1);

			String sql = "SELECT " + columns + " FROM " + tableName + " WHERE " + where + ";";
			
			rs = stmt.executeQuery(sql);

			return rs;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * To delete all rows from tableName
	 * 
	 * @param tableName
	 * @return 
	 */
	@SuppressWarnings("static-access")
	public void delete(String tableName) {
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = (Statement) this.jdbcUtil.getConn().createStatement();

			String sql = "DELETE FROM " + tableName + ";";
			
			stmt.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.jdbcUtil.release(stmt, rs);
		}
	}

	/**
	 * To delete rows from tableName, limited by where statement
	 * 
	 * @param tableName
	 * @param where
	 */
	@SuppressWarnings("static-access")
	public void delete(String tableName, String where) {
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = (Statement) this.jdbcUtil.getConn().createStatement();
			String sql = "DELETE FROM " + tableName + " WHERE " + where + ";";
			
			stmt.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.jdbcUtil.release(stmt, rs);
		}
	}

	/**
	 * To judge where ip is existing in table 'CurrentDoubtableIP' or not
	 * 
	 * @param ip
	 * @return - true : ip is existing - false : ip is not existing
	 */
	@SuppressWarnings("static-access")
	public boolean isExist(String ip) {
		Statement stmt = null;
		ResultSet rs = null;
		final String TABLENAME = "CurrentDoubtableIP";
		
		try{
			stmt = (Statement) this.jdbcUtil.getConn().createStatement();
			String sql = "SELECT * FROM " + TABLENAME + " WHERE IP = \"" + ip + "\" LIMIT 1;";
			rs = stmt.executeQuery(sql);
			
			if(rs.next()) {
				return rs.getBoolean("Valid");
			}
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			this.jdbcUtil.release(stmt, rs);
		}
		
		return false;
	}
	
	/**
	 * Update Valid in table CurrentDoubtableIP
	 * 
	 * @param sql
	 */
	@SuppressWarnings("static-access")
	public void updateValid(String ip, int valid) {
		Statement stmt = null;
		ResultSet rs = null;
		
		try{
			stmt = (Statement) this.jdbcUtil.getConn().createStatement();
			String sql = "UPDATE CurrentDoubtableIP SET Valid = " + valid + " WHERE IP = \"" + ip + "\";";
			
			stmt.execute(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.jdbcUtil.release(stmt, rs);
		}
	}

	/**
	 * To get current time, structure
	 * 
	 * @return
	 */
	private String curTime() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			String timeStr = sdf.format(new Date());

			return timeStr;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	/**
	 * Translate Time format to long(ms)
	 * 
	 * @param time
	 * @return
	 */
	public long tranTime(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
			long resTime = sdf.parse(time).getTime();

			return resTime;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return -1;
	}

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TEST
		MySQLUtil mysqlUtil = new MySQLUtil();

		// query function test
		ResultSet rs = mysqlUtil.query("CurrentDoubtableIP");
		try {
			while (rs.next()) {
				System.out.println(rs.getString("IP"));
				System.out.println(rs.getString("UserName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		rs.close();
		
		// query function test - 2
//		String tableName = "CurrentDoubtableIP";
//		
//		List<String> colArr = new ArrayList<String>();
//		colArr.add("UserName");
//		colArr.add("IP");
//		colArr.add("Times");
//		
//		String where = "Times > 2";
//		
//		ResultSet rs = mysqlUtil.query(tableName, colArr, where);
//		try {
//			while (rs.next()) {
//				System.out.print(rs.getString("IP"));
//				System.out.print(" ");
//				System.out.print(rs.getString("UserName"));
//				System.out.print(" ");
//				System.out.println(rs.getInt("Times"));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		rs.close();
	
		// delete function test 
//		String tableName = "testDel";
//		String where = "Times = 1";
//		
//		mysqlUtil.delete(tableName, where);
		
		// delete function test - 2
//		String tableName = "testDel";
//		mysqlUtil.delete(tableName);
		
		// isExist function test
//		String ip = "60.55.10.105";
//		System.out.println(mysqlUtil.isExist(ip));
		
		// insert function test
//		mysqlUtil.insert("10.15.62.200", "彦飞");
		
		// tranTime function test
//		System.out.println(mysqlUtil.tranTime("2013-11-11 14:40:20.819"));

		// updateValid
//		mysqlUtil.updateValid("10.15.62.104", 1);
	}
}
