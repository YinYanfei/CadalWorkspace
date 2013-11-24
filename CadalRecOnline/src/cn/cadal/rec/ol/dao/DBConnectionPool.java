package cn.cadal.rec.ol.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;



/**
 * 
 * @author hongxin
 * ���ݿ����ӳ��� 
 */
public class DBConnectionPool {
	private Connection con = null;
	private int inUsed = 0;// ʹ�õ�������
	private ArrayList<Connection> freeConnections = new ArrayList<Connection>();// ������������
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
	 * �������ӳ�
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
	 * ���꣬�ͷ�����
	 * 
	 * @param conn
	 */
	public synchronized void freeConnection(Connection conn) {
		this.freeConnections.add(conn);
		this.inUsed--;
	}

	/**
	 * �����ӳ���õ�����
	 * 
	 * @return
	 */
	public synchronized Connection getConnection() {
		Connection con = null;
		if (this.freeConnections.size() > 0) {
			con = (Connection) this.freeConnections.get(0);
			this.freeConnections.remove(0);// ������ӷ����ȥ�ˣ��ʹӿ���������ɾ��
			if (con == null)
				con = getConnection();// �����������
		} else {
			con = newConnection();// �½�����
		}
		if (this.maxConn == 0 || this.maxConn < this.inUsed) {
			System.out.println("�������������");
			con = null;// ����������������ȴ�
		}
		if (con != null) {
			this.inUsed++;
			//System.out.println("�õ�  " + this.poolName + " ������, ����" + inUsed + "����������ʹ��");
		}
		return con;
	}

	/**
	 * ����������
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
	 * �ͷ�ȫ������
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