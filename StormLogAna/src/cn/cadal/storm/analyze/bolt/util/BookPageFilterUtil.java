package cn.cadal.storm.analyze.bolt.util;

import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookPageFilterUtil {

	public String BookPage(String line) {
		String book = "";
		String page = "";

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
			page = this.removeHead0(str[2]);
		}else{
			p = Pattern.compile(regBookPage2);
			m = p.matcher(line);
			
			if(m.find()) {
				String tmp = m.group();
				String str[] = (tmp.substring(0, tmp.length() - 1)).split("/");
				book = str[1];
				page = this.removeHead0(str[2]);
			}else{
				p = Pattern.compile(regBookPage3);
				m = p.matcher(line);
				
				if(m.find()) {
					String tmp = m.group();
					String str[] = (tmp.substring(0, tmp.length() - 1)).split("/");
					book = str[1];
					page = this.removeHead0(str[2]);
				}else{
					return "";
				}
			}
		}
		// Match Ip + User + Time
		String ipUserTime = this.UserIpTime(line);
		
		return ipUserTime + "#" + book + "#" + page;
	}
	
	// Match Ip + user + time
	public String UserIpTime(String line){
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
					name = " ";
				} else {
					name = nameTmp.substring(16, nameTmp.length() - 5);
				}
			}else{
				name = " ";
			}
		}
		
		name = name.replaceAll("#", "+");

		return ip + "#" + name + "#" + time;
	}
	
	// delete '0' of page number
	public String removeHead0(String str) {
		if(!str.subSequence(0, 1).equals("0") || str.length() <= 1){
			if(str.equals("0")){
				return "1";
			}
			
			return str;
		}else{
			return removeHead0(str.substring(1, str.length()));
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String FILENAME = "H:/test/access_20110812.log.1";
		BookPageFilterUtil bpfu = new BookPageFilterUtil();
		
		try{
			FileReader in = new FileReader(FILENAME);
			LineNumberReader reader = new LineNumberReader(in);
			String s = "";
			int lineNum = 0;
			while (s != null) {
				s = reader.readLine();
				++lineNum;
				System.out.println(lineNum);
				System.out.println(bpfu.BookPage(s));
			}
			
			reader.close();
			in.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}

			
//		String line = "210.38.204.129 - - [11/Aug/2011:11:36:53 -0700] \"GET /page/07021287/00000000 HTTP/1.1\" 200 363 \"http://www.cadal.zju.edu.cn/flexreader/SinglePageBook.swf\" \"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; .NET CLR 2.0.50727)\" \"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; .NET CLR 2.0.50727)\" \"-\" \"0.036\"\"-\"";
//		BookPageFilterUtil bpfu = new BookPageFilterUtil();
//		
//		System.out.println(bpfu.BookPage(line));

//		String str1 = "/07018720/00000016";
//		String str[] = str1.split("/");
//		System.out.println(str[1] + "  "  + str[2].substring(1, 2));
	
		String str = " ";
		str = str.replaceAll("#", "+");
		
	}
}
