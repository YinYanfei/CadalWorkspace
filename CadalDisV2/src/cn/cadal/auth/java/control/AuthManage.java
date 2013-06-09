package cn.cadal.auth.java.control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthManage {

	private PgAccess pa = null;

	/**
	 * Construct function
	 */
	public AuthManage() {
		this.pa = new PgAccess();
	}
	
	/**
	 * To get school list info
	 * @return 45+香港大学#46+上海交大# ... ... 47+中国农业大学#49+中科院#
	 */
	public String QuerySchoolInfo() {
		String result = "";
		
		try{
			String sql = "select \"GroupID\", \"Name\" from \"cgroup\" where \"GroupID\"=71;";
			if(this.pa.OperatorPg(sql)){
				ResultSet rs = this.pa.getRs();
				
				int GroupID;
				String SchoolName = "";
				
				while (rs.next()){
					GroupID = rs.getInt("GroupID");
					SchoolName = rs.getString("Name");
					
					result += String.valueOf(GroupID) + "+" + SchoolName + "#";
				}

			}else{
				result = "error";
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = "error";
		}
		
		return result;
	}
	
	/**
	 * Query Ip list info
	 * @param schoolid
	 * @return 202.120.0#202.120.1#202.120.2# ... ... 202.120.3#202.120.4#202.120.5#
	 */
	public String QueryIpInfo(int schoolid) {
		String result = "";
		
		try{
			String sql = "select \"IpAddress\" from \"ciptable\" where \"GroupID\"=" + schoolid + ";";
			if(this.pa.OperatorPg(sql)){
				ResultSet rs = this.pa.getRs();
				
				String ip = "";
				
				while (rs.next()){
					ip = rs.getString("IpAddress");
					
					result += ip + "#";
				}
			}else{
				result = "error";
			}
		}catch(Exception e) {
			e.printStackTrace();
			result = "error";
		}finally{
			try {
				this.pa.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * Add School info
	 * @param id : group id [primary in db]
	 * @param name : group's name(or school's name)
	 * @return  true: success
	 * 		   false: fail
	 */
	public boolean AddSchoolInfo(int id, String name) {
		PreparedStatement preparedStatement = null;
		boolean result = true;
		
		try{
			String sql = "INSERT INTO cgroup(\"GroupID\", \"Name\", \"Description\") VALUES (?, ?, ?);";
			preparedStatement = this.pa.con.prepareStatement(sql);
			
			preparedStatement.setInt(1, id);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, name);
			
			result = (preparedStatement.executeUpdate() == 0? false : true);
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
		}finally{
			try {
				this.pa.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	/**
	 * Update School info
	 * @param newId : new id
	 * @param name  : newname
	 * @param oldId : 修改oldId所指定的行
	 * @return  true: success
	 * 		   false: fail
	 */
	public boolean UpdateSchoolInfo(int newId, String name, int oldId) {
		PreparedStatement preparedStatement = null;
		boolean result = true;
		
		try{
			String sql = "UPDATE cgroup SET \"GroupID\"=?, \"Name\"=?, \"Description\"=? WHERE \"GroupID\"=?;";
			preparedStatement = this.pa.con.prepareStatement(sql);
			
			preparedStatement.setInt(1, newId);
			preparedStatement.setString(2, name);
			preparedStatement.setString(3, name);
			preparedStatement.setInt(4, oldId);
			
			result = (preparedStatement.executeUpdate() == 0? false : true);
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
		}finally{
			try {
				this.pa.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * Delete school info
	 * @param id : delete school info appointed by 'id'
	 * @return  true : success
	 * 		   false : fail
	 */
	public boolean DeleteSchoolInfo(int id) {
		PreparedStatement preparedStatement = null;
		boolean result = true;
		
		try{
			String sql = "delete from \"cgroup\" where \"GroupID\"=?;";
			preparedStatement = this.pa.con.prepareStatement(sql);
			
			preparedStatement.setInt(1, id);
			
			result = (preparedStatement.executeUpdate() == 0? false : true);
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
		}finally{
			try {
				this.pa.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	/**
	 * Add ip info
	 * @param schoolid
	 * @param ip
	 * @return  true : success
	 * 		   false : fail
	 */
	public boolean AddIpInfo(int schoolid, String ip) {
		PreparedStatement preparedStatement = null;
		boolean result = true;
		
		try{
			String sql = "INSERT INTO ciptable(\"IpAddress\", \"GroupID\") VALUES (?, ?);";
			preparedStatement = this.pa.con.prepareStatement(sql);
			
			preparedStatement.setString(1, ip);
			preparedStatement.setInt(2, schoolid);
			
			result = (preparedStatement.executeUpdate() == 0? false : true);
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
		}finally{
			try {
				this.pa.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	/**
	 * Update ip info
	 * @param newIp
	 * @param id
	 * @param oldIp
	 * @return  true: success
	 * 		   false: fail
	 */
	public boolean UpdateIpInfo(String newIp, int id, String oldIp) {
		PreparedStatement preparedStatement = null;
		boolean result = true;
		
		try{
			String sql = "UPDATE ciptable SET \"IpAddress\"=?, \"GroupID\"=? WHERE \"IpAddress\" = ?;";
			preparedStatement = this.pa.con.prepareStatement(sql);
			
			preparedStatement.setString(1, newIp);
			preparedStatement.setInt(2, id);
			preparedStatement.setString(3, oldIp);
			
			result = (preparedStatement.executeUpdate() == 0? false : true);
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
		}finally{
			try {
				this.pa.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	/**
	 * Update ip info
	 * @param newIp
	 * @param id
	 * @param oldIp
	 * @param oldId
	 * @return  true: success
	 * 		   false: fail
	 */
	public boolean UpdateIpInfo(String newIp, int id, String oldIp, int oldId) {
		PreparedStatement preparedStatement = null;
		boolean result = true;
		
		try{
			String sql = "UPDATE ciptable SET \"IpAddress\"=?, \"GroupID\"=? WHERE \"IpAddress\" = ? and \"GroupID\" = ?;";
			preparedStatement = this.pa.con.prepareStatement(sql);
			
			preparedStatement.setString(1, newIp);
			preparedStatement.setInt(2, id);
			preparedStatement.setString(3, oldIp);
			preparedStatement.setInt(4, oldId);
			
			result = (preparedStatement.executeUpdate() == 0? false : true);
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
		}finally{
			try {
				this.pa.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
	
	/**
	 * Delete ip info
	 * @param ip
	 * @return  true : success
	 * 		   false : fail
	 */
	public boolean DeleteIpInfo(String ip) {
		PreparedStatement preparedStatement = null;
		boolean result = true;
		
		try{
			String sql = "delete from \"ciptable\" where \"IpAddress\"=?;";
			preparedStatement = this.pa.con.prepareStatement(sql);
			
			preparedStatement.setString(1, ip);
			
			result = (preparedStatement.executeUpdate() == 0? false : true);
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
		}finally{
			try {
				this.pa.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	/**
	 * Delete ip info
	 * @param ip
	 * @return  true : success
	 * 		   false : fail
	 */
	public boolean DeleteIpInfo(String ip, int id) {
		PreparedStatement preparedStatement = null;
		boolean result = true;
		
		try{
			String sql = "delete from \"ciptable\" where \"IpAddress\"=? and \"GroupID\" = ?;";
			preparedStatement = this.pa.con.prepareStatement(sql);
			
			preparedStatement.setString(1, ip);
			preparedStatement.setInt(2, id);
			
			result = (preparedStatement.executeUpdate() == 0? false : true);
		}catch(Exception e) {
			e.printStackTrace();
			result = false;
		}finally{
			try {
				this.pa.con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public PgAccess getPa() {
		return pa;
	}

	public void setPa(PgAccess pa) {
		this.pa = pa;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		AuthManage am = new AuthManage();
//		
//		System.out.println(am.DeleteSchoolInfo(23));
	}

}
