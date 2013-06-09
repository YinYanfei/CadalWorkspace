package com.search.analysis.analyze.initial;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

public class Paragraph {
	
	/**
	 * Variables:
	 * 		  arrStr: To store the result.
	 *   fileContent: The content will be segment.
	 */
	protected ArrayList<String> arrStr;
	protected String fileContent;				// 注意:这里的fileContent其换行符是'\n'.
	
	/**
	 * Construct Function
	 */
	Paragraph(String fileContent) {
		this.fileContent = fileContent;
		arrStr = new ArrayList<String>();
	}
	
	/**
	 * Segment according "\n" in window OS.
	 * @return:
	 *  true: success.
	 * false: fail. 
	 */
	public boolean SegInitial(){
		boolean signal = false;		
		char chTmp;
		String strTmp = "";
		try{
			for(int i = 0; i < this.fileContent.length(); ++i) {		
				chTmp = this.fileContent.charAt(i);
				
				if(chTmp == '\n'){
					if(strTmp.length() != 0)
						this.arrStr.add(strTmp);
					strTmp = "";
				}else{
					strTmp += String.valueOf(chTmp);
				}
			}
			
			signal = true;
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Print all content of "fileContent"
	 */
	public void print(){
		System.out.println("-------Print------");
		for(String tmp:this.arrStr){
			System.out.println("----" + tmp);
		}
		System.out.println("-------Print------");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "H:\\test.txt";
		String content = "";
		
		File file = new File(str);
		Reader reader = null;
		
		try{
			int chTmp;
			FileInputStream fileInput = new FileInputStream(file);
			reader = new InputStreamReader(fileInput);
			while((chTmp = reader.read()) != -1){
				if((char)(chTmp) != '\r'){
					content += String.valueOf((char)chTmp);
				}
			}
			content += String.valueOf('\n');
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		
		Paragraph pa = new Paragraph(content);		
		pa.SegInitial();
		pa.print();
	}

	/**
	 * Get and Set Functions.
	 * @return
	 */
	public ArrayList<String> getArrStr() {
		return arrStr;
	}

	public void setArrStr(ArrayList<String> arrStr) {
		this.arrStr = arrStr;
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

}
