package cn.cadal.storm.exp.readSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cadal.storm.exp.cassandra.QueryCadalRec;

public class ReadSourceFile {

	static private final Log LOG = LogFactory.getLog(ReadSourceFile.class);
	
//	private String ADDRESS = "H:/Exp/cluster_v1_test.txt";
	private String ADDRESS = "H:/Exp/cluster_v1.txt";
	
	private QueryCadalRec qci = null;
	
	public List<List<String>> bookChapterList= null;
	
	/**
	 * Construct Function
	 */
	public ReadSourceFile() {
		this.qci = new QueryCadalRec();
		this.bookChapterList = new ArrayList<List<String>>();
	}
	
	/**
	 * This function is used to deal with a single line
	 * @param line
	 */
	private void DealSingleLine(String line) {
		List<String> singleLine = new ArrayList<String>();
		
		try{
			String[] lineStrArr = line.split(",");
			
			System.out.println("Read file: " + lineStrArr[0]);
			
			singleLine.add(lineStrArr[0]);
			
			for(int i = 1 ; i < lineStrArr.length; ++i) {
				singleLine.add(this.qci.QueryFromSignalChapterMap(Integer.valueOf(lineStrArr[i]).intValue()));
			}
			
			this.bookChapterList.add(singleLine);
		}catch(Exception e){
			LOG.warn("Deal with a single line: " + line.substring(0, line.indexOf(",")));
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This file is used to read content from "cluster_v1.txt", and store content in 'bookChapterList'
	 */
	public void ReadFile(){
		File file = new File(this.ADDRESS);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			String strTmp = "";
			
			while((strTmp = reader.readLine()) != null) {
				this.DealSingleLine(strTmp);
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
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		ReadSourceFile rsf = new ReadSourceFile();
//		
//		rsf.ReadFile();
//		
//		for(int i = 0 ; i < rsf.bookChapterList.size(); ++i) {
//			System.out.println(rsf.bookChapterList.get(i));
//		}
	}

}
