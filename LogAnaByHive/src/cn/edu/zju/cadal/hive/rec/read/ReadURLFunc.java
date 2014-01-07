package cn.edu.zju.cadal.hive.rec.read;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.hive.ql.exec.UDF;

public class ReadURLFunc extends UDF {
	
	private final String regexReadURL = "\\/([0-9]{8})\\/([0-9]{8})";
	private Pattern pattern = null;
	
	public ReadURLFunc(){
		this.pattern = Pattern.compile(regexReadURL);
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
	 * To get bookid or pageno
	 * 
	 * @param url
	 * @param sig
	 * @return
	 * 		- if sig is 1, to return bookid
	 * 		- if sig is 2, to return pageno
	 */
	public String evaluate(final String url, final int sig) {
		Matcher matcher = pattern.matcher(url);
		
		if(matcher.find()) {
			if(sig == 1)
				return matcher.group(1);
			else if(sig == 2)
				return matcher.group(2);
		}
		
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		ReadURLFunc readUrlFunc = new ReadURLFunc();
		
		System.out.println(readUrlFunc.evaluate("/pageTwo/09008184/00000154", 1));
		System.out.println(readUrlFunc.evaluate("/pageTwo/09008184/00000154", 2));
	}

}
