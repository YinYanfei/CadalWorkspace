package shan.data.fourOp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FileReaderOpFour {

	public Map<String, String> CompNew= null;
	public Map<String, Map<String,String>> GarchMap = null;
	
	public FileReaderOpFour() {
		CompNew = new HashMap<String, String>();
		GarchMap = new HashMap<String, Map<String, String>>();
	}
	
	/**
	 * Read new comp-file
	 * 
	 * @param fileName
	 */
	public void readCompNew(String fileName) {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(new File(fileName)));

			String line = "";
			while((line = reader.readLine()) != null){
				String [] lineArr = line.split(",");
				
				this.CompNew.put(lineArr[0]+(lineArr[2].replaceAll("\\/", "-")), line);
			}

			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(reader != null)
					reader.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Read directory dir
	 * 
	 * @param dir
	 */
	public void readGarchDir(String dir) {
		File dirScan = new File(dir);
		File [] fileList = dirScan.listFiles();
		
		for(int i = 0; i < fileList.length; i++) {
			String fileName = dir + fileList[i].getName();
			BufferedReader reader = null;
			
			try{
				reader = new BufferedReader(new FileReader(new File(fileName)));
						
				String line = "";
				while((line = reader.readLine()) != null) {
					String [] lineArr = line.split(",");
					
//					if(lineArr.length == 13) {
//						if(this.GarchMap.containsKey(lineArr[2])) {
//							Map<String, String> map_tmp = this.GarchMap.get(lineArr[2]);
//							map_tmp.put(lineArr[8], lineArr[9] + "," + lineArr[10] + "," + lineArr[11] + "," + lineArr[12]);
//						}else{
//							Map<String, String> map_new = new HashMap<String, String>();
//							map_new.put(lineArr[8], lineArr[9] + "," + lineArr[10] + "," + lineArr[11] + "," + lineArr[12]);
//							this.GarchMap.put(lineArr[2], map_new);
//						}
//					}else{
//						System.out.println(line);
//					}
					
					if(lineArr.length == 12) {
						if(this.GarchMap.containsKey(lineArr[2])) {
							Map<String, String> map_tmp = this.GarchMap.get(lineArr[2]);
							map_tmp.put(lineArr[7], lineArr[8] + "," + lineArr[9] + "," + lineArr[10] + "," + lineArr[11]);
						}else{
							Map<String, String> map_new = new HashMap<String, String>();
							map_new.put(lineArr[7], lineArr[8] + "," + lineArr[9] + "," + lineArr[10] + "," + lineArr[11]);
							this.GarchMap.put(lineArr[2], map_new);
						}
					}else{
						System.out.println(line);
					}
					
				}
				
				reader.close();
			}catch(Exception e) {
				e.printStackTrace();
			}finally{
				try{
					if(reader != null) {
						reader.close();
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TEST
		FileReaderOpFour fileReaderOpFour = new FileReaderOpFour();
		String fileName = "E:/baiduyundownload/2013-12-27 数据处理_V2/fourOp/CompNew.csv";
		String dir = "E:/baiduyundownload/2013-12-27 数据处理_V2/fourOp/GARCH-csv/";
		
		// readCompNew
//		fileReaderOpFour.readCompNew(fileName);
//		Iterator iter = fileReaderOpFour.CompNew.entrySet().iterator();
//		int count = 0;
//		while(iter.hasNext()) {
//			count++;
//			Entry entry = (Entry) iter.next();
//			System.out.println(entry.getKey() + " == " + entry.getValue());
//		}
//		System.out.println(count);
		
		// readGarch
//		fileReaderOpFour.readGarchDir(dir);
//		Iterator iter = fileReaderOpFour.GarchMap.entrySet().iterator();
//		
//		while(iter.hasNext()) {
//			Entry entry = (Entry) iter.next();
//			Map<String, String> val = (Map<String, String>) entry.getValue();
//			System.out.println("----------------");
//			System.out.println(entry.getKey());
//			
//			Iterator iter_inner = val.entrySet().iterator();
//			while(iter_inner.hasNext()) {
//				Entry entry_inner = (Entry) iter_inner.next();
//				System.out.println(entry_inner.getKey() + " -- " + entry_inner.getValue());
//			}
//		}
//		
	}

}
