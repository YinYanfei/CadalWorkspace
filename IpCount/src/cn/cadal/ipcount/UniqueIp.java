package cn.cadal.ipcount;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UniqueIp {

	private List<String> filenameList;
	private Map<String,Integer> ipMap;
	
	private String FILENAME = "H:/ipcount/filename.txt";
	private String SOURCE = "H:/ipcount/source/";     // 需要补全文件名称
	private String DEST = "H:/ipcount/dest/ipNum.txt";
	
	/**
	 * Construct function
	 */
	public UniqueIp() {
		this.filenameList = new ArrayList<String>();
		this.ipMap = new HashMap<String, Integer>();
	}
	
	/**
	 * Read filename.txt
	 */
	public void readFileName() {
		File file = new File(FILENAME);
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			int num = 0;
			while((line=reader.readLine()) != null){
				this.filenameList.add(line);
				++num;
			}
			
			System.out.println("Total number of lines: " + num);
			
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Writer into file
	 */
	@SuppressWarnings("unchecked")
	public void writeIntoFile() {
		FileWriter writer = null;
		
		try{
			writer = new FileWriter(this.DEST, true);
			
			Iterator iter = this.ipMap.entrySet().iterator();
			Object obj = null;
			String key = "";
			while(iter.hasNext()) {
				obj = iter.next();
				key = obj.toString();
				writer.write(key + "\n");  // key.subSequence(0, key.indexOf("="))
			}
			
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(writer != null) {
				try{
					writer.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Core programming
	 */
	public void Deal() {
		File file = null;
		BufferedReader reader = null;
		try{
			String line = "";
			
			for(int i = 0; i < this.filenameList.size(); ++i) {
				file = new File(SOURCE + this.filenameList.get(i));
				reader = new BufferedReader(new FileReader(file));
				while((line = reader.readLine()) != null) {
					if(!this.ipMap.containsKey(line)) {
						this.ipMap.put(line, 1);
					}else{
						this.ipMap.put(line, this.ipMap.get(line) + 1);
					}
				}
				
				System.out.println(this.filenameList.get(i));
			}
			
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UniqueIp ui = new UniqueIp();
		ui.readFileName();

		ui.Deal();
		
		ui.writeIntoFile();
		
		System.out.println("Done");
	}

	/**
	 * set and get functions
	 */
	public List<String> getFilenameList() {
		return filenameList;
	}

	public void setFilenameList(List<String> filenameList) {
		this.filenameList = filenameList;
	}

	public Map<String, Integer> getIpMap() {
		return ipMap;
	}

	public void setIpMap(Map<String, Integer> ipMap) {
		this.ipMap = ipMap;
	}

}
