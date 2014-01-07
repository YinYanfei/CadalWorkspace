package shan.data.fiveOp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FileReaderOpFive {
	public Map<String, String> CompNew= null;
	public Map<String, Map<String,String>> StockPriceMap = null;
	
	public FileReaderOpFive() {
		CompNew = new HashMap<String, String>();
		StockPriceMap = new HashMap<String, Map<String, String>>();
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
				
				this.CompNew.put(lineArr[0] + lineArr[2], line);
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

					if(lineArr.length == 17) {
						if(this.StockPriceMap.containsKey(lineArr[0])) {
							Map<String, String> map_tmp = this.StockPriceMap.get(lineArr[0]);
							map_tmp.put(lineArr[1], lineArr[2] + "," + lineArr[3] + "," + lineArr[4] + "," + lineArr[5] + "," + lineArr[6] + "," + 
													lineArr[7] + "," + lineArr[8] + "," + lineArr[9] + "," + lineArr[10] + "," + lineArr[11] + "," + 
													lineArr[12] + "," + lineArr[13] + "," +lineArr[14] + "," + lineArr[15] + "," + lineArr[16]);
						}else{
							Map<String, String> map_new = new HashMap<String, String>();
							map_new.put(lineArr[1], lineArr[2] + "," + lineArr[3] + "," + lineArr[4] + "," + lineArr[5] + "," + lineArr[6] + "," + 
									lineArr[7] + "," + lineArr[8] + "," + lineArr[9] + "," + lineArr[10] + "," + lineArr[11] + "," + 
									lineArr[12] + "," + lineArr[13] + "," +lineArr[14] + "," + lineArr[15] + "," + lineArr[16]);
							this.StockPriceMap.put(lineArr[0], map_new);
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
		FileReaderOpFive fileReaderOpFive = new FileReaderOpFive();
		String fileName = "E:/baiduyundownload/2014-01-01 数据处理_V4/股价综合/08-13 stock.csv";
		String dir = "E:/baiduyundownload/2014-01-01 数据处理_V4/股价综合/股价数据/";
		
		// readCompNew
//		fileReaderOpFive.readCompNew(fileName);
//		Iterator iter = fileReaderOpFive.CompNew.entrySet().iterator();
//		int count = 0;
//		while(iter.hasNext()) {
//			count++;
//			Entry entry = (Entry) iter.next();
//			System.out.println(entry.getKey() + " == " + entry.getValue());
//		}
//		System.out.println(count);
		
		// readGarch
		fileReaderOpFive.readGarchDir(dir);
		Iterator iter = fileReaderOpFive.StockPriceMap.entrySet().iterator();
		
		while(iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			Map<String, String> val = (Map<String, String>) entry.getValue();
			System.out.println("----------------");
			System.out.println(entry.getKey());
			
			Iterator iter_inner = val.entrySet().iterator();
			while(iter_inner.hasNext()) {
				Entry entry_inner = (Entry) iter_inner.next();
				System.out.println(entry_inner.getKey() + " -- " + entry_inner.getValue());
			}
		}
		
	}

}
