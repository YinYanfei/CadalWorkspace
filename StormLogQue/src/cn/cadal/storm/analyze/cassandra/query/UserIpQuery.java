package cn.cadal.storm.analyze.cassandra.query;

public class UserIpQuery {

	/**
	 * Query using parameter like: (Yanfei, 10.15.62.10, times) or (Yanfei, 10.15.62.10, last)
	 */
	public String QueryUserIpColumn(String user, String ip, String column) {
		
		return "";
	}
	
	/**
	 * Query using parameter like: (Yanfei, 10.15.62.10)
	 */
	public void QueryUserIp(String user, String ip){
		
	}
	
	/**
	 * Query using patameter like: (Yanfei)
	 */
	public void QueryUser(String user) {
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		
	}

}
