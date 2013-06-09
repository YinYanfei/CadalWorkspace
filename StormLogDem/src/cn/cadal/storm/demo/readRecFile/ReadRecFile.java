package cn.cadal.storm.demo.readRecFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cadal.storm.demo.util.Constants;
import cn.cadal.storm.demo.util.RandomNum;

public class ReadRecFile {
	
	static private final Log LOG = LogFactory.getLog(ReadRecFile.class);
	
	public List<String> ReadBook = null;
	public List<String> ReadChapter = null;
	public List<List<String>> ReadResult = null;
	
	private RandomNum randomNum = null;
	
	/**
	 * Construnct
	 */
	public ReadRecFile() {
		this.ReadBook = new ArrayList<String>();
		this.ReadChapter = new ArrayList<String>();
		this.ReadResult = new ArrayList<List<String>>();
		this.randomNum = new RandomNum();
	}
	
	/**
	 * To read cluster file, finish this function, parameter 'ReadBook' and 'ReadChapter' will be filled.
	 * @param clusterNum
	 */
	public void ProcessReadFile(String clusterNum) {
		File file = new File(Constants.RECOMMEND + clusterNum + ".txt");
		BufferedReader reader = null;
		
		this.ReadBook.clear();
		this.ReadChapter.clear();
		this.ReadResult.clear();
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			String strTmp = "";
			boolean signal = false;
			
			while((strTmp = reader.readLine()) != null) {
				if(signal && !strTmp.equals("---------------------------------------")) {
					this.ReadChapter.add(strTmp);
					
				}else if(strTmp.equals("---------------------------------------")){
					signal = true;
					reader.readLine();
					
				}else{
					this.ReadBook.add(strTmp);
					
				}
			}
			
			reader.close();
		}catch(Exception e) {
			LOG.warn("Read file error.");
			e.printStackTrace();
		}finally{
	        if (reader != null) {  
	            try {  
	                reader.close();  
	            } catch (Exception e1) {
	            	LOG.warn("Error in 'Finally'.");
	            }  
	        }  	
		}
	}
	
	/**
	 * To get special number result, store into parameter 'ReadResult'.
	 * @param numberOfResult
	 */
	public void ProcessGetResult(int numberOfResult) {
		if(numberOfResult >= this.ReadBook.size()) {
			this.DealReadResult(this.ReadBook);
		}else{
			this.randomNum.GetRandom(this.ReadBook.size(), numberOfResult);
			
			List<String> listTmpBook = new ArrayList<String>();
			
			for(int i = 0; i < this.randomNum.randomList.size(); ++i) {
				listTmpBook.add(this.ReadBook.get(this.randomNum.randomList.get(i)));
			}
		
			this.DealReadResult(listTmpBook);
		}
	}
	
	/**
	 * Deal to get ReadResult
	 * @param listBook
	 */
	private void DealReadResult(List<String> listBook) {
		for(int i = 0; i < listBook.size(); ++i) {
			List<String> innerList = new ArrayList<String>();
			
			innerList.add(listBook.get(i));
			
			for(int j = 0; j < this.ReadChapter.size(); ++j) {
				if(listBook.get(i).equals(this.ReadChapter.get(j).substring(0, this.ReadChapter.get(j).indexOf("_")))) {
					innerList.add(this.ReadChapter.get(j).substring(this.ReadChapter.get(j).indexOf("_") + 1));
				}
			}
			
			this.ReadResult.add(innerList);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		ReadRecFile rrf = new ReadRecFile();
//		
//		rrf.ProcessReadFile("20");
//		
//		System.out.println(rrf.ReadBook.size());
//		System.out.println(rrf.ReadChapter.size());
//
//		rrf.ProcessGetResult(20);
//		
//		System.out.println(rrf.ReadResult.size());
//		
//		for(int i = 0; i < rrf.ReadResult.size(); ++i) {
//			System.out.println(rrf.ReadResult.get(i));
//		}
	}

}
