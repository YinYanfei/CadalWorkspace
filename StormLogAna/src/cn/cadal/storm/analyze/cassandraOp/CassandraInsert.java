package cn.cadal.storm.analyze.cassandraOp;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SuperColumn;

import cn.cadal.storm.analyze.bolt.util.TimeConvert;

public class CassandraInsert {
	
	private Connector connector = null;
	private Cassandra.Client client = null;
	private CassandraUtil cassandraUtil = null;
	private TimeConvert tc = null;
	
	public CassandraInsert() throws Exception {
		this.connector = new Connector();
		this.client = this.connector.connect();
		this.cassandraUtil = new CassandraUtil();
		this.tc = new TimeConvert();
	}
	
	public void finalize() {
		this.connector.finalize();
	}
	
	/**
	 * Insert into IpUser Column Family
	 */
	public boolean InsertIpUser(String ip, String name, String time, int times) {
		System.out.println("------------InsertIpUser--------------");
		String IP_KEY = ip;
		String NAME_SUPER_KEY = name;
		String TIMES = String.valueOf(times);
		String LAST = this.tc.NormalTime(time);
		
		String COLUMN_NAME_TIMES = "times";
		String COLUMN_NAME_LAST  = "last";
		
		String COLUMN_FAMILY_NAME = "IpUser";
		
		try {
			Map<ByteBuffer, Map<String, List<Mutation>>> outerMap = new HashMap<ByteBuffer, Map<String, List<Mutation>>>();
			List<Mutation> columnToAdd = new ArrayList<Mutation>();

			long timeStamp = System.currentTimeMillis();

			// Construct Column
			Column idColumnTimes = new Column();
			idColumnTimes.setName(this.cassandraUtil.toByteBuffer(COLUMN_NAME_TIMES));
			idColumnTimes.setValue(this.cassandraUtil.toByteBuffer(TIMES));
			idColumnTimes.setTimestamp(timeStamp);

			Column idColumnLast = new Column();
			idColumnLast.setName(this.cassandraUtil.toByteBuffer(COLUMN_NAME_LAST));
			idColumnLast.setValue(this.cassandraUtil.toByteBuffer(LAST));
			idColumnLast.setTimestamp(timeStamp);

			List<Column> cols = new ArrayList<Column>();
			cols.add(idColumnTimes);
			cols.add(idColumnLast);

			// Map Key-super_key-column
			Map<String, List<Mutation>> innerMap = new HashMap<String, List<Mutation>>();

			Mutation columns = new Mutation();
			ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
			SuperColumn superColumn = new SuperColumn();

			superColumn.name = this.cassandraUtil.toByteBuffer(NAME_SUPER_KEY);
			superColumn.columns = cols;

			columnOrSuperColumn.super_column = superColumn;
			columns.setColumn_or_supercolumn(columnOrSuperColumn);

			columnToAdd.add(columns);

			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY_NAME;
			columnPath.setSuper_column(this.cassandraUtil.toByteBuffer(NAME_SUPER_KEY));
			columnPath.setSuper_columnIsSet(true);

			innerMap.put(COLUMN_FAMILY_NAME, columnToAdd);

			// Insert Operator
			outerMap.put(this.cassandraUtil.toByteBuffer(IP_KEY), innerMap);

			client.batch_mutate(outerMap, ConsistencyLevel.ONE);
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Insert into UserIp Column Family
	 */
	public boolean InsertUserIp(String ip, String name, String time, int times) {
		System.out.println("------------InsertUserIp--------------");
		String NAME_KEY = name;
		String IP_SUPER_KEY = ip;
		String TIMES = String.valueOf(times);
		String LAST = this.tc.NormalTime(time);
		
		String COLUMN_NAME_TIMES = "times";
		String COLUMN_NAME_LAST  = "last";
		
		String COLUMN_FAMILY_NAME = "UserIp";
		
		try {
			Map<ByteBuffer, Map<String, List<Mutation>>> outerMap = new HashMap<ByteBuffer, Map<String, List<Mutation>>>();
			List<Mutation> columnToAdd = new ArrayList<Mutation>();

			long timeStamp = System.currentTimeMillis();

			// Construct Column
			Column idColumnTimes = new Column();
			idColumnTimes.setName(this.cassandraUtil.toByteBuffer(COLUMN_NAME_TIMES));
			idColumnTimes.setValue(this.cassandraUtil.toByteBuffer(TIMES));
			idColumnTimes.setTimestamp(timeStamp);

			Column idColumnLast = new Column();
			idColumnLast.setName(this.cassandraUtil.toByteBuffer(COLUMN_NAME_LAST));
			idColumnLast.setValue(this.cassandraUtil.toByteBuffer(LAST));
			idColumnLast.setTimestamp(timeStamp);

			List<Column> cols = new ArrayList<Column>();
			cols.add(idColumnTimes);
			cols.add(idColumnLast);

			// Map Key-super_key-column
			Map<String, List<Mutation>> innerMap = new HashMap<String, List<Mutation>>();

			Mutation columns = new Mutation();
			ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
			SuperColumn superColumn = new SuperColumn();

			superColumn.name = this.cassandraUtil.toByteBuffer(IP_SUPER_KEY);
			superColumn.columns = cols;

			columnOrSuperColumn.super_column = superColumn;
			columns.setColumn_or_supercolumn(columnOrSuperColumn);

			columnToAdd.add(columns);

			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY_NAME;
			columnPath.setSuper_column(this.cassandraUtil.toByteBuffer(IP_SUPER_KEY));
			columnPath.setSuper_columnIsSet(true);

			innerMap.put(COLUMN_FAMILY_NAME, columnToAdd);

			// Insert Operator
			outerMap.put(this.cassandraUtil.toByteBuffer(NAME_KEY), innerMap);

			client.batch_mutate(outerMap, ConsistencyLevel.QUORUM);
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Insert into BookIp CF
	 */
	public boolean InsertIntoBookIp(String ip, String bookid, String last, int times) {
		System.out.println("------------InsertIntoBookIp--------------");
		String BOOKID_KEY = bookid;
		String IP_SUPER_KEY = ip;
		String TIMES = String.valueOf(times);
		String LAST = this.tc.NormalTime(last);
		
		String COLUMN_NAME_TIMES = "times";
		String COLUMN_NAME_LAST  = "last";
		
		String COLUMN_FAMILY_NAME = "BookIp";
		
		try{
			Map<ByteBuffer, Map<String, List<Mutation>>> outerMap = new HashMap<ByteBuffer, Map<String, List<Mutation>>>();
			List<Mutation> columnToAdd = new ArrayList<Mutation>();

			long timeStamp = System.currentTimeMillis();

			// Construct Column
			Column idColumnTimes = new Column();
			idColumnTimes.setName(this.cassandraUtil.toByteBuffer(COLUMN_NAME_TIMES));
			idColumnTimes.setValue(this.cassandraUtil.toByteBuffer(TIMES));
			idColumnTimes.setTimestamp(timeStamp);

			Column idColumnLast = new Column();
			idColumnLast.setName(this.cassandraUtil.toByteBuffer(COLUMN_NAME_LAST));
			idColumnLast.setValue(this.cassandraUtil.toByteBuffer(LAST));
			idColumnLast.setTimestamp(timeStamp);

			List<Column> cols = new ArrayList<Column>();
			cols.add(idColumnTimes);
			cols.add(idColumnLast);
			
			// Map Key-super_key-column
			Map<String, List<Mutation>> innerMap = new HashMap<String, List<Mutation>>();

			Mutation columns = new Mutation();
			ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
			SuperColumn superColumn = new SuperColumn();

			superColumn.name = this.cassandraUtil.toByteBuffer(IP_SUPER_KEY);
			superColumn.columns = cols;

			columnOrSuperColumn.super_column = superColumn;
			columns.setColumn_or_supercolumn(columnOrSuperColumn);

			columnToAdd.add(columns);

			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY_NAME;
			columnPath.setSuper_column(this.cassandraUtil.toByteBuffer(IP_SUPER_KEY));
			columnPath.setSuper_columnIsSet(true);

			innerMap.put(COLUMN_FAMILY_NAME, columnToAdd);

			// Insert Operator
			outerMap.put(this.cassandraUtil.toByteBuffer(BOOKID_KEY), innerMap);

			client.batch_mutate(outerMap, ConsistencyLevel.QUORUM);
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Insert into BookUser CF
	 */
	public boolean InsertIntoBookUser(String user, String bookid, String last, int times){
		System.out.println("------------InsertIntoBookUser--------------");
		String BOOKID_KEY = bookid;
		String USER_SUPER_KEY = user;
		String TIMES = String.valueOf(times);
		String LAST = this.tc.NormalTime(last);
		
		String COLUMN_NAME_TIMES = "times";
		String COLUMN_NAME_LAST  = "last";
		
		String COLUMN_FAMILY_NAME = "BookUser";
		
		try{
			Map<ByteBuffer, Map<String, List<Mutation>>> outerMap = new HashMap<ByteBuffer, Map<String, List<Mutation>>>();
			List<Mutation> columnToAdd = new ArrayList<Mutation>();

			long timeStamp = System.currentTimeMillis();

			// Construct Column
			Column idColumnTimes = new Column();
			idColumnTimes.setName(this.cassandraUtil.toByteBuffer(COLUMN_NAME_TIMES));
			idColumnTimes.setValue(this.cassandraUtil.toByteBuffer(TIMES));
			idColumnTimes.setTimestamp(timeStamp);

			Column idColumnLast = new Column();
			idColumnLast.setName(this.cassandraUtil.toByteBuffer(COLUMN_NAME_LAST));
			idColumnLast.setValue(this.cassandraUtil.toByteBuffer(LAST));
			idColumnLast.setTimestamp(timeStamp);

			List<Column> cols = new ArrayList<Column>();
			cols.add(idColumnTimes);
			cols.add(idColumnLast);
			
			// Map Key-super_key-column
			Map<String, List<Mutation>> innerMap = new HashMap<String, List<Mutation>>();

			Mutation columns = new Mutation();
			ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
			SuperColumn superColumn = new SuperColumn();

			superColumn.name = this.cassandraUtil.toByteBuffer(USER_SUPER_KEY);
			superColumn.columns = cols;

			columnOrSuperColumn.super_column = superColumn;
			columns.setColumn_or_supercolumn(columnOrSuperColumn);

			columnToAdd.add(columns);

			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY_NAME;
			columnPath.setSuper_column(this.cassandraUtil.toByteBuffer(USER_SUPER_KEY));
			columnPath.setSuper_columnIsSet(true);

			innerMap.put(COLUMN_FAMILY_NAME, columnToAdd);

			// Insert Operator
			outerMap.put(this.cassandraUtil.toByteBuffer(BOOKID_KEY), innerMap);

			client.batch_mutate(outerMap, ConsistencyLevel.QUORUM);
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	/**
	 * Insert into IpBookPage CF
	 */
	public boolean InsertIntoIpBookPage(String ip, String book, String page, int times, String time) {
		System.out.println("------------InsertIntoIpBookPage--------------");
		String IP_KEY = ip;
		String BOOK_SUPER_KEY = book;
		
		String PAGE_TITLE = page;
		String PAGE_VALUE = this.tc.NormalTime(time);
		
		String TIMES_TITLE = "times";
		String TIMES_VALUE = String.valueOf(times);
		
		String COLUMN_FAMILY = "IpBookPage";
		
		try{
			Map<ByteBuffer, Map<String, List<Mutation>>> outerMap = new HashMap<ByteBuffer, Map<String, List<Mutation>>>();
			List<Mutation> columnToAdd = new ArrayList<Mutation>();

			long timeStamp = System.currentTimeMillis();
			
			// Construct Column
			Column idColumnTimes = new Column();
			idColumnTimes.setName(this.cassandraUtil.toByteBuffer(TIMES_TITLE));
			idColumnTimes.setValue(this.cassandraUtil.toByteBuffer(TIMES_VALUE));
			idColumnTimes.setTimestamp(timeStamp);

			Column idColumnPage = new Column();
			idColumnPage.setName(this.cassandraUtil.toByteBuffer(PAGE_TITLE));
			idColumnPage.setValue(this.cassandraUtil.toByteBuffer(PAGE_VALUE));
			idColumnPage.setTimestamp(timeStamp);

			List<Column> cols = new ArrayList<Column>();
			cols.add(idColumnTimes);
			cols.add(idColumnPage);

			// Map Key-super_key-column
			Map<String, List<Mutation>> innerMap = new HashMap<String, List<Mutation>>();

			Mutation columns = new Mutation();
			ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
			SuperColumn superColumn = new SuperColumn();

			superColumn.name = this.cassandraUtil.toByteBuffer(BOOK_SUPER_KEY);
			superColumn.columns = cols;

			columnOrSuperColumn.super_column = superColumn;
			columns.setColumn_or_supercolumn(columnOrSuperColumn);

			columnToAdd.add(columns);
			
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY;
			columnPath.setSuper_column(this.cassandraUtil.toByteBuffer(BOOK_SUPER_KEY));
			columnPath.setSuper_columnIsSet(true);

			innerMap.put(COLUMN_FAMILY, columnToAdd);
			
			// Insert Operator
			outerMap.put(this.cassandraUtil.toByteBuffer(IP_KEY), innerMap);

			client.batch_mutate(outerMap, ConsistencyLevel.ONE);

			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Insert into UserBookPage CF
	 */
	public boolean InsertIntoUserBookPage(String user, String book, String page, int times, String time) {
		System.out.println("------------InsertIntoUserBookPage--------------");
		String USER_KEY = user;
		String BOOK_SUPER_KEY = book;
		
		String PAGE_TITLE = page;
		String PAGE_VALUE = this.tc.NormalTime(time);
		
		String TIMES_TITLE = "times";
		String TIMES_VALUE = String.valueOf(times);
		
		String COLUMN_FAMILY = "UserBookPage";
		
		try{
			Map<ByteBuffer, Map<String, List<Mutation>>> outerMap = new HashMap<ByteBuffer, Map<String, List<Mutation>>>();
			List<Mutation> columnToAdd = new ArrayList<Mutation>();

			long timeStamp = System.currentTimeMillis();
			
			// Construct Column
			Column idColumnTimes = new Column();
			idColumnTimes.setName(this.cassandraUtil.toByteBuffer(TIMES_TITLE));
			idColumnTimes.setValue(this.cassandraUtil.toByteBuffer(TIMES_VALUE));
			idColumnTimes.setTimestamp(timeStamp);

			Column idColumnPage = new Column();
			idColumnPage.setName(this.cassandraUtil.toByteBuffer(PAGE_TITLE));
			idColumnPage.setValue(this.cassandraUtil.toByteBuffer(PAGE_VALUE));
			idColumnPage.setTimestamp(timeStamp);

			List<Column> cols = new ArrayList<Column>();
			cols.add(idColumnTimes);
			cols.add(idColumnPage);
			
			// Map Key-super_key-column
			Map<String, List<Mutation>> innerMap = new HashMap<String, List<Mutation>>();

			Mutation columns = new Mutation();
			ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
			SuperColumn superColumn = new SuperColumn();

			superColumn.name = this.cassandraUtil.toByteBuffer(BOOK_SUPER_KEY);
			superColumn.columns = cols;

			columnOrSuperColumn.super_column = superColumn;
			columns.setColumn_or_supercolumn(columnOrSuperColumn);

			columnToAdd.add(columns);
			
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY;
			columnPath.setSuper_column(this.cassandraUtil.toByteBuffer(BOOK_SUPER_KEY));
			columnPath.setSuper_columnIsSet(true);

			innerMap.put(COLUMN_FAMILY, columnToAdd);
			
			// Insert Operator
			outerMap.put(this.cassandraUtil.toByteBuffer(USER_KEY), innerMap);

			client.batch_mutate(outerMap, ConsistencyLevel.QUORUM);
		
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Insert into IpQueryBook CF
	 */
	public boolean InsertIpQueryBook(String ip, String superUuid, String query,String book, String time) {
		System.out.println("------------InsertIpQueryBook--------------");
		String IP_KEY = ip;
		String UUID_SUPER_KEY = superUuid;
		
		String QUERY_TITLE = "query";
		String QUERY_VALUE = query;
		
		String BOOK_TITLE = book;
		String BOOK_VALUE = time;
		
		String COLUMN_FAMILY = "IpQueryBook";
		
		try{
			Map<ByteBuffer, Map<String, List<Mutation>>> outerMap = new HashMap<ByteBuffer, Map<String, List<Mutation>>>();
			List<Mutation> columnToAdd = new ArrayList<Mutation>();

			long timeStamp = System.currentTimeMillis();
			
			// Construct Column
			Column idColumnQuery = new Column();
			idColumnQuery.setName(this.cassandraUtil.toByteBuffer(QUERY_TITLE));
			idColumnQuery.setValue(this.cassandraUtil.toByteBuffer(QUERY_VALUE));
			idColumnQuery.setTimestamp(timeStamp);
			
			Column idColumnBook = new Column();
			idColumnBook.setName(this.cassandraUtil.toByteBuffer(BOOK_TITLE));
			idColumnBook.setValue(this.cassandraUtil.toByteBuffer(BOOK_VALUE));
			idColumnBook.setTimestamp(timeStamp);
			
			List<Column> cols = new ArrayList<Column>();
			cols.add(idColumnQuery);
			cols.add(idColumnBook);
			
			// Map Key-super_key-column
			Map<String, List<Mutation>> innerMap = new HashMap<String, List<Mutation>>();

			Mutation columns = new Mutation();
			ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
			SuperColumn superColumn = new SuperColumn();

			superColumn.name = this.cassandraUtil.toByteBuffer(UUID_SUPER_KEY);
			superColumn.columns = cols;

			columnOrSuperColumn.super_column = superColumn;
			columns.setColumn_or_supercolumn(columnOrSuperColumn);

			columnToAdd.add(columns);
			
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY;
			columnPath.setSuper_column(this.cassandraUtil.toByteBuffer(UUID_SUPER_KEY));
			columnPath.setSuper_columnIsSet(true);

			innerMap.put(COLUMN_FAMILY, columnToAdd);
			
			// Insert Operator
			outerMap.put(this.cassandraUtil.toByteBuffer(IP_KEY), innerMap);

			client.batch_mutate(outerMap, ConsistencyLevel.QUORUM);			
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Insert into UserQueryBook CF
	 */
	public boolean InsertUserQueryBook(String name, String superUuid, String query, String book, String time) {
		System.out.println("------------InsertUserQueryBook--------------");
		String USER_KEY = name;
		String UUID_SUPER_KEY = superUuid;
		
		String QUERY_TITLE = "query";
		String QUERY_VALUE = query;
		
		String BOOK_TITLE = book;
		String BOOK_VALUE = time;
		
		String COLUMN_FAMILY = "UserQueryBook";
		
		try{
			Map<ByteBuffer, Map<String, List<Mutation>>> outerMap = new HashMap<ByteBuffer, Map<String, List<Mutation>>>();
			List<Mutation> columnToAdd = new ArrayList<Mutation>();

			long timeStamp = System.currentTimeMillis();
			
			// Construct Column
			Column idColumnQuery = new Column();
			idColumnQuery.setName(this.cassandraUtil.toByteBuffer(QUERY_TITLE));
			idColumnQuery.setValue(this.cassandraUtil.toByteBuffer(QUERY_VALUE));
			idColumnQuery.setTimestamp(timeStamp);
			
			Column idColumnBook = new Column();
			idColumnBook.setName(this.cassandraUtil.toByteBuffer(BOOK_TITLE));
			idColumnBook.setValue(this.cassandraUtil.toByteBuffer(BOOK_VALUE));
			idColumnBook.setTimestamp(timeStamp);
			
			List<Column> cols = new ArrayList<Column>();
			cols.add(idColumnQuery);
			cols.add(idColumnBook);
			
			// Map Key-super_key-column
			Map<String, List<Mutation>> innerMap = new HashMap<String, List<Mutation>>();

			Mutation columns = new Mutation();
			ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
			SuperColumn superColumn = new SuperColumn();

			superColumn.name = this.cassandraUtil.toByteBuffer(UUID_SUPER_KEY);
			superColumn.columns = cols;

			columnOrSuperColumn.super_column = superColumn;
			columns.setColumn_or_supercolumn(columnOrSuperColumn);

			columnToAdd.add(columns);
			
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = COLUMN_FAMILY;
			columnPath.setSuper_column(this.cassandraUtil.toByteBuffer(UUID_SUPER_KEY));
			columnPath.setSuper_columnIsSet(true);

			innerMap.put(COLUMN_FAMILY, columnToAdd);
			
			// Insert Operator
			outerMap.put(this.cassandraUtil.toByteBuffer(USER_KEY), innerMap);

			client.batch_mutate(outerMap, ConsistencyLevel.QUORUM);			
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Insert into QueryBookKey CF
	 */
	public boolean InsertQueryBookKey(String key, String ip_user, String time) {
		System.out.println("------------InsertQueryBookKey--------------");
		String KEY = key;
		
		String IP_USER_TITLE = ip_user;
		String IP_USER_VALUE = time;
		
		String COLUMN_FAMILY = "QueryBookKey";
		
		try {
			ColumnParent parent = new ColumnParent(COLUMN_FAMILY);

			long timeStamp = System.currentTimeMillis();

			Column idColumnIp = new Column();
			idColumnIp.setName(this.cassandraUtil.toByteBuffer(IP_USER_TITLE));
			idColumnIp.setValue(this.cassandraUtil.toByteBuffer(IP_USER_VALUE));
			idColumnIp.setTimestamp(timeStamp);
			client.insert(this.cassandraUtil.toByteBuffer(KEY), parent, idColumnIp, ConsistencyLevel.QUORUM);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
//			CassandraInsert cassandraInsert = new CassandraInsert();
			
//			boolean returnBool1 = cassandraInsert.InsertIntoBookIp("10.15.62.10", "07018720", "22/Aug/2012:22:22:22", 15);
//			if(returnBool1) {
//				System.out.println("Good");
//			}else{
//				System.out.println("Not fine!");
//			}

//			boolean returnBool2 = cassandraInsert.InsertIntoBookUser("zju", "07018720", "22/Aug/2012:22:22:22", 1000);
//			if(returnBool2) {
//				System.out.println("Good");
//			}else{
//				System.out.println("Not fine!");
//			}
//		

//			boolean returnBool3 = cassandraInsert.InsertUserIp("10.15.62.10", "Yanfei", "2012-12-12", 2);		
//			if(returnBool3) {
//				System.out.println("Good");
//			}else{
//				System.out.println("Not fine!");
//			}
			
//			boolean returnBool4 = cassandraInsert.InsertIntoUserBookPage("Yanfei", "07018721", "12", 25, "12/Aug/2012:23:09:26");
//			if(returnBool4){
//				System.out.println("Good");
//			}else{
//				System.out.println("Not fine!");
//			}
			
//			boolean returnBool5 = cassandraInsert.InsertIntoIpBookPage("10.15.62.10", "07018721", "12", 25, "12/Aug/2012:23:09:26");
//			if(returnBool5){
//				System.out.println("Good");
//			}else{
//				System.out.println("Not fine!");
//			}
			
//			boolean returnBool6 = cassandraInsert.InsertQueryBookKey("201212name", "cadal", "2012-12-12 10:09:46");
//			if(returnBool6) {
//				System.out.println("Good");
//			}else{
//				System.out.println("Not fine!");
//			}

//			boolean returnBool7 = cassandraInsert.InsertIpQueryBook("10.10.10.10", "1-2345-67890-qwe-uio", "%E6%9C%B1%E8%87%AA%E6%B8%85%E6%95%A3%E6%96%87%E9%9B%86", "07018720", "2012-12-12 08:09:23");
//			if(returnBool7) {
//				System.out.println("Good");
//			}else{
//				System.out.println("Not fine!");
//			}

//			boolean returnBool8 = cassandraInsert.InsertUserQueryBook("cadal", "1-2345-67890-qwe-asd", "%E6%9C%B1%E8%87%AA%E6%B8%85%E6%95%A3%E6%96%87%E9%9B%86", "07018720", "2012-12-12 08:09:23");
//			if(returnBool8) {
//				System.out.println("Good");
//			}else{
//				System.out.println("Not fine!");
//			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
