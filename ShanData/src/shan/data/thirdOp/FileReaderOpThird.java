package shan.data.thirdOp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class FileReaderOpThird {
	public Map<String, String> CompNew= null;

	public Map<String, Map<String, String>> BETA_MAP = null;
	public Map<String, Map<String, String>> CAR_MAP = null;
	
	public FileReaderOpThird() {
		this.CompNew = new HashMap<String, String>();
		this.BETA_MAP = new HashMap<String, Map<String, String>>();
		this.CAR_MAP = new HashMap<String, Map<String, String>>();
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
				
				this.CompNew.put(lineArr[0]+(lineArr[2].replaceAll("/", "-")), line);
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
	public void readBETADir(String dir) {
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
					
					if(lineArr.length == 15) {
						if(this.BETA_MAP.containsKey(lineArr[2])) {
							Map<String, String> map_tmp = this.BETA_MAP.get(lineArr[2]);
							map_tmp.put(lineArr[6], lineArr[7] + "," + lineArr[8] + "," + lineArr[9] + "," 
									+ lineArr[10] + "," + lineArr[11] + "," + lineArr[12] + "," + lineArr[13] + "," + lineArr[14]);
						}else{
							Map<String, String> map_new = new HashMap<String, String>();
							map_new.put(lineArr[6], lineArr[7] + "," + lineArr[8] + "," + lineArr[9] + "," 
									+ lineArr[10] + "," + lineArr[11] + "," + lineArr[12] + "," + lineArr[13] + "," + lineArr[14]);
							this.BETA_MAP.put(lineArr[2], map_new);
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
	 * Read directory dir
	 * 
	 * @param dir
	 */
	public void readCARDir(String dir) {
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
//					if(lineArr.length == 18) {
//						if(this.CAR_MAP.containsKey(lineArr[2])) {
//							Map<String, String> map_tmp = this.CAR_MAP.get(lineArr[2]);
//							map_tmp.put(lineArr[8], lineArr[9] + "," + lineArr[10] + "," + lineArr[11] + "," + lineArr[12] + "," + lineArr[13] + "," 
//									+ lineArr[14] + "," + lineArr[15] + "," + lineArr[16] + "," + lineArr[17]);
//						}else{
//							Map<String, String> map_new = new HashMap<String, String>();
//							map_new.put(lineArr[8], lineArr[9] + "," + lineArr[10] + "," + lineArr[11] + "," + lineArr[12] + "," + lineArr[13] + "," 
//									+ lineArr[14] + "," + lineArr[15] + "," + lineArr[16] + "," + lineArr[17]);
//							this.CAR_MAP.put(lineArr[2], map_new);
//						}
//					}else{
//						System.out.println(line);
//					}
					
					// 2013-12-29
					if(lineArr.length == 17) {
						if(this.CAR_MAP.containsKey(lineArr[2])) {
							Map<String, String> map_tmp = this.CAR_MAP.get(lineArr[2]);
							map_tmp.put(lineArr[7], lineArr[8] + "," + lineArr[9] + "," + lineArr[10] + "," + lineArr[11] + "," + lineArr[12] + "," 
									+ lineArr[13] + "," + lineArr[14] + "," + lineArr[15] + "," + lineArr[16]);
						}else{
							Map<String, String> map_new = new HashMap<String, String>();
							map_new.put(lineArr[7], lineArr[8] + "," + lineArr[9] + "," + lineArr[10] + "," + lineArr[11] + "," + lineArr[12] + "," 
									+ lineArr[13] + "," + lineArr[14] + "," + lineArr[15] + "," + lineArr[16]);
							this.CAR_MAP.put(lineArr[2], map_new);
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
		FileReaderOpThird fileReaderOpThird = new FileReaderOpThird();
		
		String COMP_FILE = "E:/baiduyundownload/2013-12-27 数据处理_V2/fourOp/CompNew.csv";
		String BETA_DIR = "E:/baiduyundownload/2013-12-27 数据处理_V2/fourOp/BETA-csv/";
		String CAR_DIR = "E:/baiduyundownload/2013-12-27 数据处理_V2/fourOp/CAR-csv/";
		
		// 
		fileReaderOpThird.readCompNew(COMP_FILE);
		
		// 
		fileReaderOpThird.readBETADir(BETA_DIR);
		
		//
		fileReaderOpThird.readCARDir(CAR_DIR);
	}

}
