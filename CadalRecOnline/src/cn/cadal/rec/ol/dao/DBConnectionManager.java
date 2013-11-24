package cn.cadal.rec.ol.dao;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import java.sql.Connection;

/**
 * ���ݿ����ӳع�����
 * 
 * @author hongxin
 * 
 */
public class DBConnectionManager {
	static private DBConnectionManager instance;// Ψһ���ݿ����ӳع���ʵ����
	static private int clients; // �ͻ�������
	private Vector drivers = new Vector();// ������Ϣ
	private Hashtable pools = new Hashtable();// ���ӳ�

	/**
	 * ʵ����������
	 */
	public DBConnectionManager() {
		this.init();
	}

	/**
	 * �õ�Ψһʵ��������
	 * 
	 * @return
	 */
	static synchronized public DBConnectionManager getInstance() {
		if (instance == null) {
			instance = new DBConnectionManager();
		}
		return instance;

	}

	/**
	 * �ͷ�����
	 * 
	 * @param name
	 * @param con
	 */
	public void freeConnection(String name, Connection con) {
		DBConnectionPool pool = (DBConnectionPool) pools.get(name);// �������ֵõ����ӳ�
		if (pool != null)
			pool.freeConnection(con);// �ͷ�����
	}

	/**
	 * �������ӳص�����name���õ�һ������
	 * 
	 * @param name
	 * @return
	 */
	public Connection getConnection(String name) {
		DBConnectionPool pool = null;
		Connection con = null;
		pool = (DBConnectionPool) pools.get(name);// �������л�ȡ���ӳ�
		con = pool.getConnection();// ��ѡ�������ӳ��л������
//		if (con != null)
//			System.out.println("�õ����ӡ�����");
//		else
//			System.out.println("û�еõ����ӡ�����");
		return con;
	}

	/**
	 * �ͷ���������
	 */
	public synchronized void release() {
		Enumeration allpools = pools.elements();
		while (allpools.hasMoreElements()) {
			DBConnectionPool pool = (DBConnectionPool) allpools.nextElement();
			if (pool != null)
				pool.release();
		}
		pools.clear();
	}

	/**
	 * �������ӳ�
	 * 
	 * @param dsb
	 */
	private void createPools(DSConfigBean dsb) {
		DBConnectionPool dbpool = new DBConnectionPool();
		dbpool.setName(dsb.getName());
		dbpool.setDriver(dsb.getDriver());
		dbpool.setUrl(dsb.getUrl());
		dbpool.setUser(dsb.getUsername());
		dbpool.setPassword(dsb.getPassword());
		dbpool.setMaxConn(dsb.getMaxconn());
//		System.out.print(dsb.getName() + "�������������:" + dsb.getMaxconn());
		pools.put(dsb.getName(), dbpool);
	}

	/**
	 * ��ʼ�����ӳصĲ���
	 */
	private void init() {
		// ������������
		this.loadDrivers();
		// �������ӳ�
		Iterator alldriver = drivers.iterator();
		while (alldriver.hasNext()) {
			this.createPools((DSConfigBean) alldriver.next());
//			System.out.println(" �������ӳء�����");
		}
//		System.out.println("�������ӳ���ϡ�����\n");
	}

	/**
	 * ������������
	 * 
	 * @param 
	 */
	private void loadDrivers() {
		ParseDSConfig pd = new ParseDSConfig();
		// ��ȡ���ݿ������ļ�
//		drivers = pd.readConfigInfo("/usr/local/rec/bin/libRec/ds.config.xml");
		drivers = pd.readConfigInfo("ds.config.xml");
//		System.out.println("in loadDrivers �����������򡣡���");
	}
	public static void main(String[] args) {
		// for test
		DBConnectionManager db = DBConnectionManager.getInstance();
	}
}