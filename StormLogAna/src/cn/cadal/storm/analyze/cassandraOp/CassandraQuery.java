package cn.cadal.storm.analyze.cassandraOp;

import java.util.List;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.SuperColumn;

public class CassandraQuery {
	
	private Connector connector = null;
	private Cassandra.Client client = null;
	private CassandraUtil cassandraUtil = null;
	
	public CassandraQuery() throws Exception {
		this.connector = new Connector();
		this.client = this.connector.connect();
		this.cassandraUtil = new CassandraUtil();
	}
	
	public void finalize() {
		this.connector.finalize();
	}

	/**
	 * Query and Check item is exist or not
	 * 
	 * return times of item
	 */
	public int QueryIpUser(String ip, String name){
		String IP_KEY = ip;
		String NAME_SUPER_KEY = name;
		String COLUMN_NAME = "times";
		String COLUMN_FAMILY = "IpUser";
		
		try{
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY;
			columnPath.super_column = this.cassandraUtil.toByteBuffer(NAME_SUPER_KEY);
			
			ColumnOrSuperColumn columnOrSuperColumn = client.get(this.cassandraUtil.toByteBuffer(IP_KEY), columnPath, ConsistencyLevel.ONE);

			SuperColumn superColumn = columnOrSuperColumn.getSuper_column();
			
			List<Column> columns = superColumn.getColumns();
			
			for(Column col : columns) {
				String title = new String(col.getName(), "UTF-8") ;
				
				if(title.equals(COLUMN_NAME)){
					return Integer.parseInt(new String(col.getValue(), "UTF-8"));     // get "times" column and return 
				}
			}
		}catch(Exception e){
			return 0;
		}

		return 0;
	}
	
	/**
	 * Query and Check item is exist or not
	 * 
	 * return times of item
	 */
	public int QueryUserIp(String ip, String name){
		String NAME_KEY = name;
		String IP_SUPER_KEY = ip;
		String COLUMN_NAME = "times";
		String COLUMN_FAMILY = "UserIp";
		
		try{
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY;
			columnPath.super_column = this.cassandraUtil.toByteBuffer(IP_SUPER_KEY);
			
			ColumnOrSuperColumn columnOrSuperColumn = client.get(this.cassandraUtil.toByteBuffer(NAME_KEY), columnPath, ConsistencyLevel.ONE);

			SuperColumn superColumn = columnOrSuperColumn.getSuper_column();
			
			List<Column> columns = superColumn.getColumns();
			
			for(Column col : columns) {
				String title = new String(col.getName(), "UTF-8") ;
				if(title.equals(COLUMN_NAME)){
					return Integer.parseInt(new String(col.getValue(), "UTF-8"));     // get "times" column and return 
				}
			}
		}catch(Exception e){
			return 0;
		}
		
		return 0;
	}
		
	/**
	 * Query and Check BookIp
	 * 
	 * return times of column is exist, 0 or not
	 */
	public int QueryBookIp(String bookid, String ip) {
		String BOOKID_KEY = bookid;
		String IP_SUPER_KEY = ip;
		String COLUMN_NAME = "times";
		String COLUMN_FAMILY = "BookIp";

		try {
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY;
			columnPath.super_column = this.cassandraUtil.toByteBuffer(IP_SUPER_KEY);

			ColumnOrSuperColumn columnOrSuperColumn = client.get(this.cassandraUtil.toByteBuffer(BOOKID_KEY), columnPath,ConsistencyLevel.ONE);

			SuperColumn superColumn = columnOrSuperColumn.getSuper_column();

			List<Column> columns = superColumn.getColumns();


			for (Column col : columns) {
				String title = new String(col.getName(), "UTF-8");
				if (title.equals(COLUMN_NAME)) {
					return Integer.parseInt(new String(col.getValue(), "UTF-8"));
				}
			}

		} catch (Exception e) {
			return 0;
		}

		return 0;
	}
	
	/**
	 * Query and Check BookUser
	 * 
	 * return times of column is exist, 0 or not
	 */
	public int QueryBookUser(String bookid, String user) {
		String BOOKID_KEY = bookid;
		String USER_SUPER_KEY = user;
		String COLUMN_NAME = "times";
		String COLUMN_FAMILY = "BookUser";
		
		try{
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY;
			columnPath.super_column = this.cassandraUtil.toByteBuffer(USER_SUPER_KEY);
			
			ColumnOrSuperColumn columnOrSuperColumn = client.get(this.cassandraUtil.toByteBuffer(BOOKID_KEY), columnPath, ConsistencyLevel.ONE);
			
			SuperColumn superColumn = columnOrSuperColumn.getSuper_column();
			
			List<Column> columns = superColumn.getColumns();
			
			for(Column col : columns) {
				String title = new String(col.getName(), "UTF-8");
				if(title.equals(COLUMN_NAME)) {
					return Integer.parseInt(new String(col.getValue(), "UTF-8"));
				}
			}
			
		}catch(Exception e) {
			return 0;
		}
		
		return 0;
	}
	
	/**
	 * Query and Check IpBookPage
	 * 
	 * return times of column is exist, 0 or not
	 */
	public int QueryIpBookPage(String ip, String book) {
		String IP_KEY = ip;
		String BOOKID_SUPER_KEY = book;
		String COLUMN_FAMILY = "IpBookPage";
		String COLUMN_NAME = "times";
		
		try{
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY;
			columnPath.super_column = this.cassandraUtil.toByteBuffer(BOOKID_SUPER_KEY);
			
			ColumnOrSuperColumn columnOrSuperColumn = client.get(this.cassandraUtil.toByteBuffer(IP_KEY), columnPath, ConsistencyLevel.ONE);
			
			SuperColumn superColumn = columnOrSuperColumn.getSuper_column();
			
			List<Column> columns = superColumn.getColumns();
			
			for(Column col : columns) {
				String title = new String(col.getName(), "UTF-8");
				if(title.equals(COLUMN_NAME)) {
					return Integer.parseInt(new String(col.getValue(), "UTF-8"));
				}
			}
			
		}catch(Exception e){
			return 0;
		}
		
		return 0;
	}

	/**
	 * Query and Check UserBookPage
	 * 
	 * return times of column is exist, 0 or not
	 */
	public int QueryUserBookPage(String user, String book) {
		String USER_KEY = user;
		String BOOK_SUPER_KEY = book;
		String COLUMN_FAMILY = "UserBookPage";
		String COLUMN_NAME = "times";
		
		try{
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY;
			columnPath.super_column = this.cassandraUtil.toByteBuffer(BOOK_SUPER_KEY);
			
			ColumnOrSuperColumn columnOrSuperColumn = client.get(this.cassandraUtil.toByteBuffer(USER_KEY), columnPath, ConsistencyLevel.ONE);
			
			SuperColumn superColumn = columnOrSuperColumn.getSuper_column();
			
			List<Column> columns = superColumn.getColumns();
			
			for(Column col : columns) {
				String title = new String(col.getName(), "UTF-8");
				if(title.equals(COLUMN_NAME)) {
					return Integer.parseInt(new String(col.getValue(), "UTF-8"));
				}
			}
			
		}catch(Exception e) {
			return 0;
		}
		
		return 0;
	}	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
//			CassandraQuery cassandraQuery = new CassandraQuery();
			
//			System.out.println(cassandraQuery.QueryBookUser("07018720", "zju"));
//			System.out.println(cassandraQuery.QueryBookIp("07018720", "10.15.62.10"));
		
//			System.out.println(cassandraQuery.QueryIpUser("10.15.62.10", "Yanfei"));
//			System.out.println(cassandraQuery.QueryUserIp("10.15.62.20", "Yanfei"));
			
//			System.out.println(cassandraQuery.QueryIpBookPage("10.10.10.10", "00000001"));
//			System.out.println(cassandraQuery.QueryUserBookPage("cadal", "00000001"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}

