package log.cadal.ipmatch;

import java.util.HashMap;
import java.util.Map;

public class Match {
	public String fileName = "E:/H/logAna/parament/ip2school.txt"; // ip���ӦѧУ��ŵĹ����ļ�
	public Map ipMapMatch = null;
	public IpStore ipStore = null;
	
	// construct function
	public Match() {
		this.ipMapMatch = new HashMap();
		this.ipStore = new IpStore();
		
		this.ipStore.readFile(this.fileName);
		
		this.ipMapMatch = this.ipStore.ipMap;
	}
	
	// match ip to confirm the school of the ip-addr
	public String match(String ip) {
		String str[] = ip.split("\\.");	
		
		if(this.ipMapMatch.containsKey(ip)) {
			return (String)this.ipMapMatch.get(ip);
		}
		
		if(this.ipMapMatch.containsKey(str[0] + "." + str[1] + "." + str[2])){
			return (String) this.ipMapMatch.get(str[0] + "." + str[1] + "." + str[2]);
		}
		
		if(this.ipMapMatch.containsKey(str[0] + "." + str[1])){
			return (String) this.ipMapMatch.get(str[0] + "." + str[1]);
		}
		
		return "0";
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Match match = new Match();
		
		System.out.println(match.match("210.33.44.2"));
		System.out.println(match.match("210.33.44.2"));
		System.out.println(match.match("210.33.44.2"));
		System.out.println(match.match("210.33.44.2"));
		System.out.println(match.match("210.33.44.2"));
		System.out.println(match.match("210.33.44.2"));
		System.out.println(match.match("210.33.44.2"));

	}

}
