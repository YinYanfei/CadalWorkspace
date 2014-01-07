package shan.data.secondOp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class FileReaderOp {

	public Map<String, String> COMP_MAP = null;

	public Map<String, String> AAT_MA_0B = null;
	public Map<String, String> AAT_MA_1A = null;
	public Map<String, String> AAT_MA_2A = null;
	public Map<String, String> AAT_MA_2AB = null;

	public Map<String, Map<String, String>> AAT_MAP = null;

	public FileReaderOp() {
		this.COMP_MAP = new HashMap<String, String>();

		this.AAT_MA_0B = new HashMap<String, String>();
		this.AAT_MA_1A = new HashMap<String, String>();
		this.AAT_MA_2A = new HashMap<String, String>();
		this.AAT_MA_2AB = new HashMap<String, String>();

		this.AAT_MAP = new HashMap<String, Map<String, String>>();
	}

	/**
	 * Read file - Comp.csv
	 * 
	 * @param fileName
	 */
	public void ReadComp(String fileName) {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(new File(fileName)));

			String line = "";
			while((line = reader.readLine()) != null){
				String [] lineArr = line.split(",");
				
				this.COMP_MAP.put(lineArr[0]+lineArr[1], line);
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
	 * Read file - AAT[dir]
	 * 
	 * @param fileName
	 */
	public void ReadAAT(String dir) {
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
					
					// 2013-12-27
//					if(lineArr.length == 8) {
//						if(this.AAT_MAP.containsKey(lineArr[0])) {
//							Map<String, String> map_tmp = this.AAT_MAP.get(lineArr[0]);
//							map_tmp.put(lineArr[4], lineArr[3] + "," + lineArr[5] + "," + lineArr[6] + "," + lineArr[7]);
//						}else{
//							Map<String, String> map_new = new HashMap<String, String>();
//							map_new.put(lineArr[4], lineArr[3] + "," + lineArr[5] + "," + lineArr[6] + "," + lineArr[7]);
//							this.AAT_MAP.put(lineArr[0], map_new);
//						}
//					}else{
//						System.out.println(line);
//					}
					
					// 2013-12-29
					if(lineArr.length == 11) {
						if(this.AAT_MAP.containsKey(lineArr[2])) {
							Map<String, String> map_tmp = this.AAT_MAP.get(lineArr[2]);
							map_tmp.put(lineArr[7], lineArr[6] + "," + lineArr[8] + "," + lineArr[9] + "," + lineArr[10]);
						}else{
							Map<String, String> map_new = new HashMap<String, String>();
							map_new.put(lineArr[7], lineArr[6] + "," + lineArr[8] + "," + lineArr[9] + "," + lineArr[10]);
							this.AAT_MAP.put(lineArr[2], map_new);
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
	 * Read file - AAT_MA
	 * 
	 * @param fileName
	 */
	public void ReadAATMA(String dir) {
		BufferedReader reader = null;
		
		String [] file = new String[4];
		file[0] = "0B.csv";
		file[1] = "1A.csv";
		file[2] = "2A.csv";
		file[3] = "2AB.csv";
		
		for(int i = 0; i < 4; ++i) {
			String fileName = dir + file[i];
			
			try {
				reader = new BufferedReader(new FileReader(new File(fileName)));
	
				String line = "";
				while((line = reader.readLine()) != null){
					String [] lineArr = line.split(",");
					
					if(i == 0) {
						this.AAT_MA_0B.put(lineArr[0], lineArr[1] + "," + lineArr[2]);
					}else if(i == 1) {
						this.AAT_MA_1A.put(lineArr[0], lineArr[1] + "," + lineArr[2]);
					}else if(i == 2) {
						this.AAT_MA_2A.put(lineArr[0], lineArr[1] + "," + lineArr[2]);
					}else{
						this.AAT_MA_2AB.put(lineArr[0], lineArr[1] + "," + lineArr[2]);
					}
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
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TEST
		FileReaderOp fileReader = new FileReaderOp();
		
		// Test ReadComp
//		fileReader.ReadComp("E:/baiduyundownload/2013-12-29 数据处理_V3/Comp_2_28.csv");
//		Iterator iter = fileReader.COMP_MAP.entrySet().iterator();
//		int count = 0;
//		while(iter.hasNext()) {
//			count++;
//			Entry entry = (Entry) iter.next();
//			System.out.println(entry.getKey() + " == " + entry.getValue());
//		}
//		System.out.println(count);
		
		// Test ReadAAT
		fileReader.ReadAAT("E:/baiduyundownload/2013-12-29 数据处理_V3/Deal_2/AAT/");
		Iterator iter = fileReader.AAT_MAP.entrySet().iterator();
		
		while(iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			Map<String, String> val = (Map<String, String>) entry.getValue();
			
			System.out.println(val.size());
		}
		
		// Test ReadAATMA
//		fileReader.ReadAATMA("E:/baiduyundownload/2013-12-29 数据处理_V3/Deal_2/AAT_MA/");
//		
//		System.out.println(fileReader.AAT_MA_0B.size());
//		System.out.println(fileReader.AAT_MA_1A.size());
//		System.out.println(fileReader.AAT_MA_2A.size());
//		System.out.println(fileReader.AAT_MA_2AB.size());
	}

}
