package cn.edu.zju.cadal.hive.logAna;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.hive.ql.exec.UDF;

public class RequestURLFunc extends UDF {
//	private final String regexReadURL = "\\/([0-9]{8})";
	private final String regexReadURL = "\\/([0-9]{8})\\/([0-9]{8})";
	private Pattern pattern = null;
	
	public RequestURLFunc() {
		this.pattern = Pattern.compile(this.regexReadURL);
	}
	
	/**
	 * To judge whether URL is reading or not
	 * 
	 * @param url
	 * @return
	 * 		- true : reading
	 * 		- false: others operation
	 */
	public boolean evaluate(final String url) {
		Matcher matcher = pattern.matcher(url);
		
		if(matcher.find()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * To get bookid or pageno for matched url
	 * 
	 * @param url
	 * @param idx
	 * @return
	 * 		- bookid [goal]
	 * 		- pageno
	 */
	public String evaluate(final String url, final int idx) {
		Matcher matcher = pattern.matcher(url);
		
		try{
			if(matcher.find()) {
				return matcher.group(idx);
			}
			return null;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Test Main Function
	 * 
	 * @param args
	 */
	public static void main(final String [] args) {
		RequestURLFunc requestURlFunc = new RequestURLFunc();
		String url = "/pageTwo/09008184/1";
		
		// fisrt
		System.out.println(requestURlFunc.evaluate(url));
		
		// second
		System.out.println(requestURlFunc.evaluate(url, 1));
	}
}
