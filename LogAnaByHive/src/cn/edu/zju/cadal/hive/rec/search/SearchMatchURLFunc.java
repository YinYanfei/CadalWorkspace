package cn.edu.zju.cadal.hive.rec.search;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.hadoop.hive.ql.exec.UDF;

public class SearchMatchURLFunc extends UDF {
	
	private static String reg = "[query|queryword]=(.*?)&";
	private Pattern pattern = null;
	
	public SearchMatchURLFunc() {
		this.pattern = Pattern.compile(reg);
	}
	
	/**
	 * To match query-word from url
	 * 
	 * @param url
	 * @return
	 */
	public String evaluate(final String url) {
		Matcher mat = this.pattern.matcher(url);
		
		if(mat.find()) {
			try {
				return URLDecoder.decode(mat.group(1),"UTF-8");
			} catch (Exception e) {
				return "NULL";
			}
		}
		return "NULL";
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		SearchMatchURLFunc suf = new SearchMatchURLFunc();
		String url = "/QuickModernSearch.action?queryword=%E9%9B%86%E5%90%88%E8%AE%BA&dojo.prevent&Cache=1357037823842";
		
		System.out.println(suf.evaluate(url));

	}

}
