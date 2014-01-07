package shan.data.factors.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shan.data.factors.FileInfo;

public class FileReadOp {

	private FileReaderHandler fileReaderHandler = null;
	
	public FileReadOp(){
		this.fileReaderHandler = new FileReaderHandler();
	}
	
	/**
	 * To read file 1.
	 * 
	 * @return
	 * 		["000062,深圳华强,2008-10-20,S90",...]
	 */
	public List<String> readFile_1() {
		BufferedReader reader = null;
		List<String> infoFile_1 = new ArrayList<String>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_1);
			String str = "";
			while((str = reader.readLine()) != null){
				infoFile_1.add(str);
			}
			
			return infoFile_1;
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}
	
	/**
	 * To read file 2.
	 * 
	 * @return
	 * 		<0000012013-01-05, [2.34,34.123,....]>,<...>,...
	 */
	public Map<String, double[]> readFile_2() {
		String Clsprc = "5-Clsprc";
		
		BufferedReader reader = null;
		Map<String, double[]> infoFile_2 = new HashMap<String, double[]>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_2);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strList = str.split(",");
				
				if(strList[3].equals(Clsprc)) {
					String key = strList[0] + strList[2];
					double [] val = new double[281];
					
					for(int idx = 5; idx < strList.length; ++idx) {
						val[idx - 5] = Double.valueOf(strList[idx]);
					}
					
					infoFile_2.put(key, val);
				}
			}
			
			return infoFile_2;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}
	
	/**
	 * To read file 3.
	 * 
	 * @return
	 * 		<0000012013-01-05,"...">,<...>,...
	 * 
	 * 0  --  2-个股日收益率
	 * 1  --  5-流通市值加权平均市场日收益率
	 * 2  --  6-总市值加权平均市场收益率
	 * 3  --  15-Alpha_总市值加权_Alpha_mc
	 * 4  --  16-风险因子_总市值加权_Beta_mc
	 */
	public Map<String, String[]> readFile_3() {
		String idx_0 = "2-个股日收益率";
		String idx_1 = "5-流通市值加权平均市场日收益率";
		String idx_2 = "6-总市值加权平均市场收益率";
		String idx_3 = "15-Alpha_总市值加权_Alpha_mc";
		String idx_4 = "16-风险因子_总市值加权_Beta_mc";
		
		BufferedReader reader = null;
		Map<String, String[]> infoFile_3 = new HashMap<String, String[]>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_3);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strList = str.split(",");
				String key = strList[0] + strList[2];
				
				if(infoFile_3.containsKey(key)) {
					String [] strArr = infoFile_3.get(key);
					
					if(strList[3].equals(idx_0)) {
						strArr[0] = str;
					}else if(strList[3].equals(idx_1)) {
						strArr[1] = str;
					}else if(strList[3].equals(idx_2)) {
						strArr[2] = str;
					}else if(strList[3].equals(idx_3)) {
						strArr[3] = str;
					}else if(strList[3].equals(idx_4)) {
						strArr[4] = str;
					}else{
						continue;
					}
					
					infoFile_3.put(key, strArr);
				}else{
					String [] strArr = new String[5];
					if(strList[3].equals(idx_0)) {
						strArr[0] = str;
					}else if(strList[3].equals(idx_1)) {
						strArr[1] = str;
					}else if(strList[3].equals(idx_2)) {
						strArr[2] = str;
					}else if(strList[3].equals(idx_3)) {
						strArr[3] = str;
					}else if(strList[3].equals(idx_4)) {
						strArr[4] = str;
					}else{
						continue;
					}
					
					infoFile_3.put(key, strArr);
				}
			}
			
			return infoFile_3;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}
	
	/**
	 * To read file 4.
	 * 
	 * @return
	 * 		<0000012009-12-31, "0.017078,0.020111,0.014566,1">,<...>,...
	 */
	public Map<String, String> readFile_4() {
		BufferedReader reader = null;
		Map<String, String> infoFile_4 = new HashMap<String, String>();

		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_4);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strArr = str.split(",");
				String key = strArr[0] + strArr[1];
				String val_tmp = "1";
				if(strArr[4].equals("0") || strArr[4].equals(" ")) {
					val_tmp = "0";
				}
				String val = strArr[2] + "," + strArr[3] + "," + strArr[4] + "," + val_tmp;
				
				infoFile_4.put(key, val);
			}
			
			return infoFile_4;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}
	
	/**
	 * To read file 5.
	 * 
	 * @return
	 * 	<0000012013-01-05,["50","33.33"," "," ","31.31","19.03","4.91","1.84","0.54","0.47","0.45","0.36","0.24","0.22"]>,<...>,...
	 */
	public Map<String, String[]> readFile_5() {
		BufferedReader reader = null;
		Map<String, String[]> infoFile_5 = new HashMap<String, String[]>();

		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_5);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strArr = str.split(",");
				
				String key = strArr[0] + strArr[1];
				String [] val = (str.substring(str.indexOf(",", 10) + 1)).split(",");

				infoFile_5.put(key, val);
			}
			
			return infoFile_5;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}
	
	/**
	 * To read file 6.
	 * 
	 * @return
	 * 		<0000012013-06-30,"8197360665,0,8197360665,29782">,<...>,...
	 */
	public Map<String, String> readFile_6() {
		BufferedReader reader = null;
		Map<String, String> infoFile_6 = new HashMap<String, String>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_6);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strArr = str.split(",");
				String key = strArr[0] + strArr[1];
				String val = str.substring(str.indexOf(",", 10) + 1);
				
				infoFile_6.put(key, val);
			}
			
			return infoFile_6;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}

	/**
	 * To read file 7.
	 * 
	 * @return
	 * 		<0000012006-12-31,"19,4,21.05">,<...>,...
	 */
	public Map<String, String> readFile_7() {
		BufferedReader reader = null;
		Map<String, String> infoFile_7 = new HashMap<String, String>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_7);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strArr = str.split(",");
				String key = strArr[0] + strArr[1];
				String val = str.substring(str.indexOf(",", 10) + 1);
				
				infoFile_7.put(key, val);
			}
			
			return infoFile_7;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}
	
	/**
	 * To read file 8.
	 * 
	 * @return
	 * 		<0000012010-12-31,"727610068000.00,33512876000.00">,<...>,...
	 */
	public Map<String, String> readFile_8() {
		BufferedReader reader = null;
		Map<String, String> infoFile_8 = new HashMap<String, String>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_8);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strArr = str.split(",");
				String key = strArr[0] + strArr[1];
				String val = strArr[2] + "," + strArr[3];
				
				infoFile_8.put(key, val);
			}
			
			return infoFile_8;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}

	/**
	 * To read file 9.
	 * 
	 * @return
	 * 		<0000012008-12-31,"0.001294,0.037439">,<...>,...
	 */
	public Map<String, String> readFile_9() {
		BufferedReader reader = null;
		Map<String, String> infoFile_9 = new HashMap<String, String>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_9);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strArr = str.split(",");
				String key = strArr[0] + strArr[1];
				String val = strArr[2] + "," + strArr[3];
				
				infoFile_9.put(key, val);
			}
			
			return infoFile_9;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}

	/**
	 * To read file 10.
	 * 
	 * @return
	 * 		<0000082010-12-31,"0.0121,0.0117,1.0076,-2093.786544,882925123.16 ,11.329385,0.088266">,<...>,...
	 */
	public Map<String, String> readFile_10() {
		BufferedReader reader = null;
		Map<String, String> infoFile_10 = new HashMap<String, String>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_10);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strArr = str.split(",");
				String key = strArr[0] + strArr[1];
				String val = str.substring(str.indexOf(",", 10) + 1);
				
				infoFile_10.put(key, val);
			}
			
			return infoFile_10;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}

	/**
	 * To read file 11.
	 * 
	 * @return
	 * 		<0000012007-12-31,"2649903000,2649903000">,<...>,...
	 */
	public Map<String, String> readFile_11() {
		BufferedReader reader = null;
		Map<String, String> infoFile_11 = new HashMap<String, String>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_11);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strArr = str.split(",");
				String key = strArr[0] + strArr[1];
				String val = strArr[2] + "," + strArr[3];
				
				infoFile_11.put(key, val);
			}
			
			return infoFile_11;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}
	
	/**
	 * To read file 12.
	 * 
	 * @return
	 * 		<0000012006-12-31,"0.975153,39.246775,11.45641,40.246775">,<...>,...
	 */
	public Map<String, String> readFile_12() {
		BufferedReader reader = null;
		Map<String, String> infoFile_12 = new HashMap<String, String>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_12);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strArr = str.split(",");
				String key = strArr[0] + strArr[1];
				String val = strArr[0] + "," + strArr[1] + "," + strArr[0] + "," + strArr[1];

				infoFile_12.put(key, val);
			}
			
			return infoFile_12;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}
	
	/**
	 * To read file 13.
	 * 
	 * @return
	 * 		<0000012006-12-31,"29310000,25,11380000,14150000,595000">,<...>,...
	 */
	public Map<String, String> readFile_13() {
		BufferedReader reader = null;
		Map<String, String> infoFile_13 = new HashMap<String, String>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_13);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strArr = str.split(",");
				String key = strArr[0] + strArr[1];
				String val = str.substring(str.indexOf(",", 10) + 1);
				
				infoFile_13.put(key, val);
			}
			
			return infoFile_13;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}

	/**
	 * To read file 14.
	 * 
	 * @return
	 * 		<0000012010-09-02,"深圳发展银行股份有限公司,有条件通过,2010000001B001,10000,000001,B,2010-09-02,...">,<...>,...
	 */
	public Map<String, String> readFile_14() {
		BufferedReader reader = null;
		Map<String, String> infoFile_14 = new HashMap<String, String>();
		
		try{
			reader = (BufferedReader) this.fileReaderHandler.createReaderHandler(FileInfo.FILE_14);
			String str = "";
			
			while((str = reader.readLine()) != null) {
				String [] strArr = str.split(",");
				String key = strArr[4] + strArr[6];
				
				infoFile_14.put(key, str);
			}
			
			return infoFile_14;
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			this.fileReaderHandler.closeReaderHandler(reader);
		}
		
		return null;
	}

}
