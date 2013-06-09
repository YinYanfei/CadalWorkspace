package com.search.analysis.util;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class WriteIntoFile {
	
	/**
	 * Variables;
	 * 		destFile: the address of file which we will use it
	 * 				  to store something.
	 * 		  writer: FileWriter object.
	 */
	private String destFile;
	private FileWriter writer = null;
	
	/**
	 * Construct Functions
	 */
	public WriteIntoFile(){
		
	}
	public WriteIntoFile(String destFile){
		this.setDestFile(destFile);
	}
	public WriteIntoFile(FileWriter writer){
		this.writer = writer;
	}
	
	/**
	 * Open file and initial writer.
	 */
	public boolean OpenFile(){
		boolean signal = false;
		try{
			// Create directory
			String str = this.destFile.substring(0, this.destFile.lastIndexOf('\\') + 1);
			File fileTmp = new File(str);
			fileTmp.mkdirs();
			
			File file = new File(this.destFile);
			writer = new FileWriter(file);
			
			signal = true;
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}finally{
			if(!signal && writer != null){
				try{
					writer.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		return signal;
	}
	
	/**
	 * Overload OpenFile Function.
	 */
	public boolean OpenFile(String str){
		this.destFile = str;
		
		return this.OpenFile();
	}
	
	/**
	 * Close file ---- Very important, when finish writing, please remember use this function
	 * 				   to end the operator.
	 */
	public boolean CloseFile(){
		boolean signal = false;

		if(this.writer != null) {
			try{
				this.writer.close();
				
				signal = true;
			}catch(Exception e){
				signal = false;
				e.printStackTrace();
			}
		}
		
		return signal;
	}
	
	
	/**
	 * Write into file:
	 * 		WriteInto(Overload functions): write into file.
	 */
	public boolean WriteInto(String str) {
		boolean signal = false;
		
		try{
			this.writer.write(str + "\r\n");
			
			signal = true;
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	public boolean WriteInto(ArrayList<String> arrStr){
		boolean signal = false;
		
		try{
			for(String str:arrStr){
				this.writer.write(str + "\r\n");
			}
			
			signal = true;
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	public boolean WriteInto(String str, String separator){
		boolean signal = false;
		
		try{
			this.writer.write(str + separator);
			
			signal = true;
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	public boolean WriteInto(ArrayList<String> arrStr, String separator){
		boolean signal = false;
		
		try{
			for(String str:arrStr){
				this.writer.write(str + separator);
			}
			
			signal = true;
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Read from file and print content on console.
	 */
	public void print(){
		if(!this.destFile.equals("")){
			ReadFromFile reader = new ReadFromFile(this.destFile);
			// Read
			reader.ReadPrint();
		}else{
			System.out.println("File address is illegal!");
			return;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Write String Test
		WriteIntoFile writeIntoFile = new WriteIntoFile("");
		writeIntoFile.OpenFile("E:\\rr\\rr\\tt\\1.txt");
		writeIntoFile.OpenFile();
		writeIntoFile.WriteInto("one");
		writeIntoFile.WriteInto("two");
		writeIntoFile.CloseFile();
		writeIntoFile.print();
		
		// Write Array Test
		WriteIntoFile writeIntoFile2 = new WriteIntoFile("E:\\rr\\rr\\tt\\2.txt");
		ArrayList<String> arrStr = new ArrayList<String>();
		arrStr.add("Yan");
		arrStr.add("Fei");
		arrStr.add("YIN");
		writeIntoFile2.OpenFile();
		writeIntoFile2.WriteInto(arrStr);
		writeIntoFile2.CloseFile();
		writeIntoFile2.print();
	}
	
	/**
	 * Set and get functions.
	 */
	public void setDestFile(String destFile) {
		this.destFile = destFile;
	}
	public String getDestFile() {
		return destFile;
	}
	public void setWriter(FileWriter writer) {
		this.writer = writer;
	}
	public FileWriter getWriter() {
		return writer;
	}

}
