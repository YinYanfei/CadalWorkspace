package com.search.analysis.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ReadFromFile {

	/**
	 * Variables:
	 * 
	 */
	private String fileName = "";
	private BufferedReader reader = null;
	
	/**
	 * Construct Function.
	 */
	public ReadFromFile(){
		
	}
	public ReadFromFile(String fileName){
		this.fileName = fileName;
	}
	public ReadFromFile(BufferedReader reader){
		this.reader = reader;
	}
	
	/**
	 * Open file.
	 */
	private boolean OpenFile(){
		boolean signal = false;
		if(!this.fileName.equals("") && this.reader == null){
			try{
				File file = new File(this.fileName);
				FileReader fileReader = new FileReader(file);
				this.reader = new BufferedReader(fileReader);
							
				signal = true;
			}catch(Exception e){
				signal = false;
				e.printStackTrace();
			}
		}else if(this.reader == null && this.fileName.equals("")){
			System.out.println("Can not open!");
			signal = false;
		}
		
		return signal;
	}
	
	/**
	 * Close File. ---- Very important file, and when finish reading, please use this
	 * 					function to end the operator.
	 */
	private boolean CloseFile(){
		boolean signal = false;
		
		if(this.reader != null){
			try{
				this.reader.close();
				
				signal = true;
			}catch(Exception e){
				signal = false;
				e.printStackTrace();
			}
		}
		return signal;
	}
	
	/**
	 * Read and print.
	 */
	public void ReadPrint(){
		String strTmp = "";
		
		try{
			this.OpenFile();
			while((strTmp = this.reader.readLine()) != null){
				System.out.println(strTmp);
				strTmp = "";
			}
			this.CloseFile();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(this.reader != null){
				try{
					this.reader.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	public void ReadPrint(String separator){
		String strTmp = "";
		
		try{
			this.OpenFile();
			while((strTmp = this.reader.readLine())!= null){
				System.out.print(strTmp + separator);
				
				strTmp = "";
			}
			this.CloseFile();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(this.reader != null){
				try{
					this.reader.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReadFromFile readFile = new ReadFromFile("tt\\test.txt");
		
		readFile.OpenFile();
		readFile.ReadPrint();
		readFile.CloseFile();		// Important
		System.out.println("------------");
	}
	
	/**
	 * Set and get functions.
	 */
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public BufferedReader getReader() {
		return reader;
	}
	public void setReader(BufferedReader reader) {
		this.reader = reader;
	}

}
