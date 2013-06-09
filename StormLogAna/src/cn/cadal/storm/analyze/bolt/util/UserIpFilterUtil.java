package cn.cadal.storm.analyze.bolt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserIpFilterUtil {

	/**
	 * return ""  : is not adjusted
	 * return "*" : nice for "ip-user-time", just like: "28.29.58.65#Yanfei#03/Aug/2012:05:44:01"
	 */
	public String UserIp(String line){
		String ip = "";
		String time = "";
		String name = "";
		
		Pattern p = null;
		Matcher m = null;
		
		// Match IP address
		String regIp = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
		p = Pattern.compile(regIp);
		m = p.matcher(line);
		if (m.find()) {
			ip = m.group();
		}
		
		// Match Time
		String regTime = "\\[.*\\] ";
		p = Pattern.compile(regTime);
		m = p.matcher(line);
		if (m.find()) {
			String timeTmp = m.group();
			time = timeTmp.substring(1, timeTmp.length() - 8);
		}

		// Match Name
		String nameTmp = "";
		String regName1 = " \\\\x22(.*)\\\\x22 ";
		String regName2 = "/personal/image/.* HTTP";
		p = Pattern.compile(regName1);
		m = p.matcher(line);
		if (m.find()) {
			nameTmp = m.group();
			name = nameTmp.substring(5, nameTmp.length() - 5);
		} else {
			p = Pattern.compile(regName2);
			m = p.matcher(line);

			if (m.find()) {
				nameTmp = m.group();
				if (nameTmp.length() > 30 && nameTmp.contains("-")) {
					name = "";
				} else {
					name = nameTmp.substring(16, nameTmp.length() - 5);
				}
			}
		}
		
		name = name.replaceAll("#", "+");

		// Concordance
		if(ip.length() > 0 && name.length() > 0){
			return ip + "#" + name + "#" + time;
		}else{
			return "";			
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

//		String line = "28.29.58.65 - - [03/Aug/2012:05:44:01 -0700] \\x22Yanfei\\x22 [03/Aug/2012:05:44:01 -0700]\"GET /personal/image/Yanfei HTTP/1.1\" 200 3100 \"http://www.cadal.zju.edu.cn/account/signincheck//\" \"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; 360SE)\" \"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 1.1.4322; .NET CLR 2.0.50727; .NET CLR 3.0.04506.30; .NET CLR 3.0.4506.2152; .NET CLR 3.5.30729; 360SE)\" \"-\" \"0.066\"\"-\"";
//		
//		UserIpFilterUtil uifu = new UserIpFilterUtil();
//
//		System.out.println(uifu.UserIp(line));

// Name
//		String line = "[\\x22Yanfei\\x2]\"GET /personal/image/xiaomiliu HTTP/1.1\" 200 1513";		
//		
//		String nameTmp = "";
//		String regName1 = "\\\\x22(.*)\\\\x22";
//		String regName2 = "/personal/image/.* HTTP";
//		Pattern p = Pattern.compile(regName1);
//		Matcher m = p.matcher(line);
//		if (m.find()) {
//			nameTmp = m.group();
//			System.out.println(nameTmp.substring(4, nameTmp.length() - 4));
//		} else {
//			p = Pattern.compile(regName2);
//			m = p.matcher(line);
//
//			if (m.find()) {
//				nameTmp = m.group();
//				System.out.println(nameTmp.substring(16, nameTmp.length() - 5));
//			}
//		}

// Time
//		String str = "[04/Aug/2012:01:26:07]";		
//		String regex = "\\[(.*)\\]";
//		Pattern p = Pattern.compile(regex);
//		Matcher m = p.matcher(str);
//		if (m.find()) {
//			String result = m.group();
//			System.out.println(result.substring(1, result.length() - 1));
//		}

// IP		
//		String str = "192.168.1.1  192.168.1.1";
//		String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
//		Pattern p = Pattern.compile(regex);
//		Matcher m = p.matcher(str);
//		if (m.find()) {
//			System.out.println(m.group());
//		}		
		
	}

}
