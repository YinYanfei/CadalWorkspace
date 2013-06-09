package project.cadal;

import java.io.File;
import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;
public class BerkeleyDB {
	private static Environment env;
	private static Database profileDB;
	private static Database bookDB;
	private static Database userinfoDB;
	private static Database recommendByInterestDB;
	private static Database recommendByBookDB;
	private static Database recommendByTopicDB;
	public BerkeleyDB() {
		
	}
	public static Database getRecommendByBookDB(){
		return recommendByBookDB;
	}
	public static Database getRecommendByTopicDB(){
		return recommendByTopicDB;
	}
	public static Database getProfileDB(){
		return profileDB;
	}
	public static Database getUserInfoDB(){
		return userinfoDB;
	}
	public static Database getBookDB(){
		return bookDB;
	}
	public static Database getRecommendByInterestDB(){
		return recommendByInterestDB;
	}
	
	public static void setUp(String path, long cacheSize) {
		EnvironmentConfig envConfig = new EnvironmentConfig();
		envConfig.setAllowCreate(true);
		envConfig.setCacheSize(cacheSize);
		try {
			env = new Environment(new File(path),envConfig);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	public static void open() {
		DatabaseConfig dbConfig = new DatabaseConfig();
		dbConfig.setAllowCreate(true);
		try {
			profileDB = env.openDatabase(null, "profile", dbConfig);
			bookDB = env.openDatabase(null, "book", dbConfig);
			userinfoDB = env.openDatabase(null, "user", dbConfig);
			recommendByInterestDB = env.openDatabase(null, "re_interest", dbConfig);
			recommendByBookDB = env.openDatabase(null, "re_book", dbConfig);
			recommendByTopicDB = env.openDatabase(null, "re_topic", dbConfig);
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	public static void close() {
		try {
			if(profileDB != null) {
				profileDB.close();
			}
			if(bookDB != null) {
				bookDB.close();
			}
			if(userinfoDB != null) {
				userinfoDB.close();
			}
			if(recommendByInterestDB != null) {
				recommendByInterestDB.close();
			}
			if(recommendByBookDB != null) {
				recommendByBookDB.close();
			}
			if(recommendByTopicDB != null) {
				recommendByTopicDB.close();
			}
			if(env != null) {
				env.close();
			}
		} catch (DatabaseException e) {
			e.printStackTrace();
		}
	}
	public static String getProfile(String username) throws Exception {
		DatabaseEntry queryKey = new DatabaseEntry();
		DatabaseEntry value = new DatabaseEntry();
		queryKey.setData(username.getBytes("UTF-8"));
		OperationStatus status = profileDB.get(null, queryKey, value,
				LockMode.DEFAULT);
		if (status == OperationStatus.SUCCESS) {
			return new String(value.getData());
		}
		return null;
	}
	public static boolean putProfile(String key, String value) throws Exception {
		byte[] theKey = key.getBytes("UTF-8");
		byte[] theValue = value.getBytes("UTF-8");
		OperationStatus status = profileDB.put(null, new DatabaseEntry(theKey),
				new DatabaseEntry(theValue));
		if(status == OperationStatus.SUCCESS) {
			return true;
		}
		return false;
	}
	public static String getUserInfo(String username) throws Exception {
		DatabaseEntry queryKey = new DatabaseEntry();
		DatabaseEntry value = new DatabaseEntry();
		queryKey.setData(username.getBytes("UTF-8"));
		OperationStatus status = userinfoDB.get(null, queryKey, value,
				LockMode.DEFAULT);
		if (status == OperationStatus.SUCCESS) {
			return new String(value.getData());
		}
		return null;
	}
	public static boolean putUserInfo(String key, String value) throws Exception {
		byte[] theKey = key.getBytes("UTF-8");
		byte[] theValue = value.getBytes("UTF-8");
		OperationStatus status = userinfoDB.put(null, new DatabaseEntry(theKey),
				new DatabaseEntry(theValue));
		if(status == OperationStatus.SUCCESS) {
			return true;
		}
		return false;
	}
	public static String getBook(String bookname) throws Exception {
		DatabaseEntry queryKey = new DatabaseEntry();
		DatabaseEntry value = new DatabaseEntry();
		queryKey.setData(bookname.getBytes("UTF-8"));
		OperationStatus status = bookDB.get(null, queryKey, value,
				LockMode.DEFAULT);
		if (status == OperationStatus.SUCCESS) {
			return new String(value.getData());
		}
		return null;
	}
	public static boolean putBook(String key, String value) throws Exception {
		byte[] theKey = key.getBytes("UTF-8");
		byte[] theValue = value.getBytes("UTF-8");
		OperationStatus status = bookDB.put(null, new DatabaseEntry(theKey),
				new DatabaseEntry(theValue));
		if(status == OperationStatus.SUCCESS) {
			return true;
		}
		return false;
	}
	
	public static String getRec(String db,String user) throws Exception {
		Database database=null;
		if(db.equals("interest"))
			database=recommendByInterestDB;
		else if(db.equals("book"))
			database=recommendByBookDB;
		else if(db.equals("topic"))
			database=recommendByTopicDB;
		DatabaseEntry queryKey = new DatabaseEntry();
		DatabaseEntry value = new DatabaseEntry();
		queryKey.setData(user.getBytes("UTF-8"));
		OperationStatus status = database.get(null, queryKey, value,
				LockMode.DEFAULT);
		if (status == OperationStatus.SUCCESS) {
			return new String(value.getData());
		}
		return null;
	}
	public static boolean putRec(String db,String key, String value) throws Exception {
		Database database=null;
		if(db.equals("interest"))
			database=recommendByInterestDB;
		else if(db.equals("book"))
			database=recommendByBookDB;
		else if(db.equals("topic"))
			database=recommendByTopicDB;
		byte[] theKey = key.getBytes("UTF-8");
		byte[] theValue = value.getBytes("UTF-8");
		OperationStatus status = database.put(null, new DatabaseEntry(theKey),
				new DatabaseEntry(theValue));
		if(status == OperationStatus.SUCCESS) {
			return true;
		}
		return false;
	}
}

