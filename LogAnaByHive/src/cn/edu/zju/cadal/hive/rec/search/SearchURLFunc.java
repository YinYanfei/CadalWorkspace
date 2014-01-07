package cn.edu.zju.cadal.hive.rec.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.hive.ql.exec.UDF;

public class SearchURLFunc extends UDF {

	private static String reg = "[query|queryword]=(.*?)&";
	private Pattern pattern = null;
	
	public SearchURLFunc() {
		this.pattern = Pattern.compile(reg);
	}
	
	/**
	 * To judge whether url include query-word
	 * 
	 * @param url
	 * @return
	 * 		- true for include
	 * 		- false for none
	 */
	public boolean evaluate(final String url) {
		Matcher mat = this.pattern.matcher(url);
		
		if(mat.find()) {
			return true;
		}
		return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		SearchURLFunc suf = new SearchURLFunc();
		String url = "/QuickModernSearch.action?queryword=%E9%9B%86%E5%90%88%E8%AE%BA&dojo.preventCache=1357037823842";
		
		System.out.println(suf.evaluate(url));
	}

}
