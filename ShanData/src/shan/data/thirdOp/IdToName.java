package shan.data.thirdOp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IdToName {
	public Map<String, String> AAT_MAP = null;
	public List<String> idList = null;

	public IdToName() {
		this.AAT_MAP = new HashMap<String, String>();
		this.idList = new ArrayList<String>();
	}

	/**
	 * Read AAT Directory
	 * 
	 * @param dir
	 */
	public void fileReadOp(String dir) {
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
//						if(!this.AAT_MAP.containsKey(lineArr[0])) {
//							this.AAT_MAP.put(lineArr[0],lineArr[1]);
//						}
//					}else{
//						System.out.println(line);
//					}
					
					// 2013-12-29
					if(lineArr.length == 11) {
						if(!this.AAT_MAP.containsKey(lineArr[2])) {
							this.AAT_MAP.put(lineArr[2],lineArr[3]);
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
	 * Read id file
	 * 
	 * @param fileName
	 */
	public void readIdFile(String fileName) {
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(new File(fileName)));

			String line = "";
			while((line = reader.readLine()) != null){
				this.idList.add(line.split(",")[0]);
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
	 * Deal
	 * 
	 * @return
	 */
	public List<String> getNameListByIdList() {
		List<String> nameList = new ArrayList<String>();
		
		for(int i = 0; i < this.idList.size(); i++) {
			try{
				nameList.add(this.AAT_MAP.get(this.idList.get(i)));
			}catch(Exception e) {
				System.out.println(this.idList.get(i));
			}
		}
		
		return nameList;
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// OP
		IdToName idToName = new IdToName();
		// 2013-12-27
//		String dir = "E:/baiduyundownload/2013-12-26 数据处理/初步处理/AAT/";
//		String fileName = "E:/baiduyundownload/2013-12-27 数据处理_V2/id_for_name.csv";

		// 2013-12-29
		String dir = "E:/baiduyundownload/2013-12-29 数据处理_V3/Deal_2/AAT/";
		String fileName = "E:/baiduyundownload/2013-12-29 数据处理_V3/id_for_name_1229.csv";
		
		idToName.fileReadOp(dir);
		idToName.readIdFile(fileName);
		
		List<String> listStringTmp = idToName.getNameListByIdList();
		
		System.out.println(idToName.idList.size() + "   " + listStringTmp.size());
		
		String res = "";
		
		// 2013-12-27
//		FileWriter writer = new FileWriter("E:/baiduyundownload/2013-12-27 数据处理_V2/id_for_name_res_3.csv", true);
		
		// 2013-12-29
		FileWriter writer = new FileWriter("E:/baiduyundownload/2013-12-29 数据处理_V3/id_for_name_res_3_1229.csv", true);
				
		for(int i = 0; i < listStringTmp.size(); i++) {
			writer.write(idToName.idList.get(i) + "," + listStringTmp.get(i)+"\n");
		}
		
		writer.close();
	}

}
