package log.cadal.ipmatch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class IpStore {
	public Map ipMap = null;
	
	// construct function
	public IpStore() {
		this.ipMap = new HashMap();
	}
	
	// read ip-addr from file and store then into ipList
	public void readFile(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		String ip = "";
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String[] list = null;
			
			int num = 0;
			
			while((ip = reader.readLine()) != null) {
				++num;
				
				list = ip.split(" ");
				
				this.ipMap.put(list[0], list[1]);
			}
			
			// close file
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		IpStore is = new IpStore();
		
		is.readFile("E:/H/logAna/parament/ip2school.txt");
		
		System.out.println(is.ipMap.get("210.192.97.41"));

	}

}
