package cn.cadal.rec.ol.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;



/**
 * 
 * @author hongxin
 * 数据库连接池类 
 */
public class DBConnectionPool {
	private Connection con = null;
	private int inUsed = 0;// 使用的连接数
	private ArrayList<Connection> freeConnections = new ArrayList<Connection>();// 空闲连接容器
	private int minConn;
	private int maxConn;
	private String poolName;
	private String password;
	private String url;
	private String driver;
	private String user;

	public DBConnectionPool() {
	}

	/**
	 * 创建连接池
	 * 
	 * @param name
	 * @param driver
	 * @param URL
	 * @param user
	 * @param password
	 * @param maxConn
	 */
	public DBConnectionPool(String name, String driver, String URL,
			String user, String password, int maxConn) {
		this.poolName = name;
		this.driver = driver;
		this.url = URL;
		this.user = user;
		this.password = password;
		this.maxConn = maxConn;
	}

	/**
	 * 用完，释放连接
	 * 
	 * @param conn
	 */
	public synchronized void freeConnection(Connection conn) {
		this.freeConnections.add(conn);
		this.inUsed--;
	}

	/**
	 * 从连接池里得到连接
	 * 
	 * @return
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		if (this.freeConnections.size() > 0) {
			con = (Connection) this.freeConnections.get(0);
			this.freeConnections.remove(0);// 如果连接分配出去了，就从空闲连接里删除
			if (con == null)
				con = getConnection();// 继续获得连接
		} else {
			con = newConnection();// 新建连接
		}
		if (this.maxConn == 0 || this.maxConn < this.inUsed) {
//			System.out.println("超过最大连接数");
			con = null;// 超过最大连接数，等待
		}
		if (con != null) {
			this.inUsed++;
//			System.out.println("得到  " + this.poolName + " 的连接, 现有" + inUsed + "个连接正在使用");
		}
		return con;
	}

	/**
	 * 创建新连接
	 * 
	 * @return
	 */
	public Connection newConnection() {
		try {
			Class.forName(driver);
			con = (Connection) DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("Cannot find DB Driver!");
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("Cannot create Connection!");
		}
		return con;
	}

	/**
	 * 释放全部连接
	 */
	public synchronized void release() {
		Iterator allConns = this.freeConnections.iterator();
		while (allConns.hasNext()) {
			Connection con = (Connection) allConns.next();
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		this.freeConnections.clear();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public int getMaxConn() {
		return maxConn;
	}

	public void setMaxConn(int maxConn) {
		this.maxConn = maxConn;
	}

	public int getMinConn() {
		return minConn;
	}

	public void setMinConn(int minConn) {
		this.minConn = minConn;
	}

	public String getPoolName() {
		return poolName;
	}

	public void setName(String name) {
		this.poolName = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
