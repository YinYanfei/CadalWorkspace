package cn.cadal.storm.analyze.bolt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryBookFilterUtil {

	public BookUserFilterUtil bufu = null;
	public BookPageFilterUtil bpfu = null;
	
	public QueryBookFilterUtil() {
		this.bufu = new BookUserFilterUtil();
		this.bpfu = new BookPageFilterUtil();
	}
	
	public String QueryBook(String line) {
		String regQuery = "\\.action\\?queryword=(.*)&";
		
		Pattern patQuery = null;
		Matcher matQuery = null;
		
		patQuery = Pattern.compile(regQuery);
		matQuery = patQuery.matcher(line);
		
		if(matQuery.find()) {
			String query = matQuery.group(1);
			
			// query for special situation: queryword=\x22C#\x22&type=modern&startNo=0&resultNumber=100
			if(query.indexOf("\\x22") != -1) {
				query = query.substring(4);
				query = query.substring(0, query.indexOf("\\x22"));

				query = query.replaceAll("#", "+");
			}
			
			String ipUserTime = this.bpfu.UserIpTime(line);
			return ipUserTime + "#" + query + "#QUERY";
		}else{
			String strTmp = this.bufu.BookUser(line);
			if(strTmp.length() > 0){
				return strTmp + "#BOOK";
			}else{
				return "";
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String line = "202.201.163.5 - - [06/Dec/2012:01:30:39 -0800] \\x22Yanfei\\x22 [06/Dec/2012:01:30:39 -0800]\"GET /QuickAncientSearch.action?queryword=\\x22C#\\x22&type=modern&startNo=0&resultNumber=100 HTTP/1.1\" 200 1347 \"http://www.cadal.zju.edu.cn/QuickSearch.action/90909090/00000231\" \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)\" \"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0)\" \"-\" \"0.009\"\"-\"";
		
		QueryBookFilterUtil qbfu = new QueryBookFilterUtil();
		
		System.out.println(qbfu.QueryBook(line));
		
	}

}
