package cn.cadal.storm.analyze.cassandra.query;

public class BookIpQuery {

	/**
	 * Query using parameter like: (07018720, 10.15.62.10, last) or (07018720, 10.15.62.10, times)
	 */
	public String QueryBookIpColum(String book, String ip, String column){
		
		return "";
	}
	
	/**
	 * Query using parameter like: (07018720, 10.15.62.10)
	 */
	public void QueryBookIp(String book, String ip) {
		
	}
	
	/**
	 * Query using only bookNo like: (07018720)
	 */
	public void QueryBook(String book) {
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test

	}

}
