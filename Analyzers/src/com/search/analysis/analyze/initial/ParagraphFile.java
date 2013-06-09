package com.search.analysis.analyze.initial;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class ParagraphFile extends Paragraph{
	
	/**
	 * Variables:
	 * fileName: address of the file will read.
	 */
	private String fileName; 
	
	/**
	 * Construct Function
	 */
	public ParagraphFile(){
		super("");
		this.fileName = "";
		this.arrStr = new ArrayList<String>();
	}
	public ParagraphFile(String fileName){
		super("");
		this.fileName = fileName;
		this.arrStr = new ArrayList<String>();
	}
	
	/**
	 * Read and deal with the file.
	 */
	public boolean SegInitial(){
		boolean signal = false;
		
		File file = new File(this.fileName);
		BufferedReader reader = null;
		String str = "";
		
		try{
			FileReader fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);
			
			while((str = reader.readLine()) != null){
				if(str.length() != 0)
					this.arrStr.add(str);
			}
			
			signal = true;			
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		return signal;
	}

	/**
	 * Use for Reverse Scan
	 */
	public boolean SegInitialRe(){
		boolean signal = false;
		
		File file = new File(this.fileName);
		BufferedReader reader = null;
		String str = "";
		
		try{
			FileReader fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);
			
			while((str = reader.readLine()) != null){
				if(str.length() != 0)
					this.arrStr.add(0, str);
			}
			
			signal = true;			
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		return signal;
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ParagraphFile paraFile = new ParagraphFile("E:\\test.txt");
		
		if(paraFile.SegInitial()){
			paraFile.print();
		}else{
			System.out.println("----");
		}

	}
	
	/**
	 * Set and Get Function.
	 */
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
