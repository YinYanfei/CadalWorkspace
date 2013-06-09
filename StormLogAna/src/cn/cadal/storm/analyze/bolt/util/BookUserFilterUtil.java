package cn.cadal.storm.analyze.bolt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookUserFilterUtil {
	
	private BookPageFilterUtil bpfu = null;
	
	public BookUserFilterUtil(){
		this.bpfu = new BookPageFilterUtil();
	}

	public String BookUser(String line) {
		String book = "";
		
		Pattern p = null;
		Matcher m = null;
		
		// Match Book + Page
		String regBookPage1 = "/\\d{8}/\\d{8}";
		String regBookPage2 = "/[0-9]{8}/[0-9]{1,7}\"";
		String regBookPage3 = "/[0-9]{8}/[0-9]{1,7} ";
		
		p = Pattern.compile(regBookPage1);
		m = p.matcher(line);
		
		if (m.find()) {
			String tmp = m.group();
			String str[] = tmp.split("/");
			book = str[1];

		}else{
			p = Pattern.compile(regBookPage2);
			m = p.matcher(line);
			
			if(m.find()) {
				String tmp = m.group();
				String str[] = (tmp.substring(0, tmp.length() - 1)).split("/");
				book = str[1];
			}else{
				p = Pattern.compile(regBookPage3);
				m = p.matcher(line);
				
				if(m.find()) {
					String tmp = m.group();
					String str[] = (tmp.substring(0, tmp.length() - 1)).split("/");
					book = str[1];
				}else{
					return "";
				}
			}
		}
		
		// Match Ip + User + Time
		String ipUserTime = this.bpfu.UserIpTime(line);
		
		return ipUserTime + "#" + book;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String line = "28.29.58.165 - - [03/Aug/2012:05:44:01 -0700] \\x22Yanfei\\x22 [03/Aug/2012:05:44:01 -0700]\"GET /pageTwo/00061041/00000089\" HTTP/1.1\" 200 3100 \"http://www.cadal.zju.edu.cn/account/signincheck//\" \"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; 360SE)\" \"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; 360SE)\" \"-\" \"0.066\"\"-\"";
		BookUserFilterUtil bpfu = new BookUserFilterUtil();
		
		System.out.println(bpfu.BookUser(line));
	}

}
