package cn.cadal.rec.ol.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author hongxin
 * 
 */
public class DBAgent {
	public String DBName;
	public static DBConnectionManager DBCM = DBConnectionManager.getInstance();

	/**
	 * Construct functions
	 */
	public DBAgent(){}
	public DBAgent(String dbName){
		this.DBName = dbName;
	}
	
	/**
	 * ִ�в�ѯSQL���
	 * 
	 * @param sql
	 *            sql���
	 * @param param
	 *            ֵ��
	 * @param type
	 *            ֵ���ͼ�
	 * @return �����
	 */
	@SuppressWarnings("unchecked")
	public List executeQuery(String sql, Object[] param, int[] type) {
		ResultSet rs = null; 
		List list = null;
		Connection conn = DBCM.getConnection(DBName);
		PreparedStatement prsts = null;
		
		try {
			prsts = conn.prepareStatement(sql);
			for (int i = 1; i <= param.length; i++) {
				prsts.setObject(i, param[i - 1], type[i - 1]);
			}
			rs = prsts.executeQuery();
			list = new ArrayList();
			ResultSetMetaData rsm = rs.getMetaData();
			Map map = null;
			while (rs.next()) {
				map = new HashMap();
				for (int i = 1; i <= rsm.getColumnCount(); i++) {
					map.put(rsm.getColumnName(i),
							rs.getObject(rsm.getColumnName(i)));
				}
				list.add(map);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCM.freeConnection(DBName, conn); 
		}
		return list;

	}

	/**
	 * ִ������ɾ����SQL���
	 * 
	 * @param sql
	 *            sql���
	 * @param param
	 *            ֵ��
	 * @param type
	 *            ֵ���ͼ�
	 * @return ��Ӱ�������
	 */
	public int executeUpdate(String sql, Object[] param, int[] type) {
		int rows = 0;
		Connection conn = DBCM.getConnection(DBName);
		PreparedStatement prsts = null;
		
		try {
			prsts = conn.prepareStatement(sql);
			for (int i = 1; i <= param.length; i++) {
				prsts.setObject(i, param[i - 1], type[i - 1]);
			}
			rows = prsts.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBCM.freeConnection(DBName, conn);
		}
		return rows;
	}

	
	public static void main(String[] args) {
		//for test
//		DBAgent db = new DBAgent("cadalrectest-77");
//		int[] type = new int[2];
//		Object[] param = new Object[2];
//		
//		type[0] = java.sql.Types.INTEGER;
//		type[1] = java.sql.Types.INTEGER;
//		
//		param[0] = 11;
//		param[1] = 23;
//		String sql = "select * from B_INFO where auto_id between ? and ?";
//		
//		List<Map> list = db.executeQuery(sql,param,type);
//		for(int i = 0;i<list.size();++i){
//			System.out.println("----------------");
//			Map map = list.get(i);
//			for(Iterator it = map.keySet().iterator();it.hasNext();){
//				String columnName = it.next().toString();
//				System.out.println(columnName + "=" + map.get(columnName)==null?"":map.get(columnName).toString());
//			}
//		}
	}
}