package com.search.analysis.segdir;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetFileName {
	
	/**
	 * Variable
	 */
	protected ArrayList<String> arrFileName;
	protected ArrayList<String> desFileName;
	
	/**
	 * Construct function
	 */
	protected GetFileName() {
		this.arrFileName = new ArrayList<String>();
		this.desFileName = new ArrayList<String>();
	}
	
	/**
	 * Get filename of the directory including child-directory.
	 */
	protected void GetFile(String dir, String des) {		
		File file = new File(dir);
		
		String regex = ".(.txt)$";
		Pattern pattern = Pattern.compile(regex);
		Matcher ma = null;
		
		File[] fs = file.listFiles();
		
		String strTmp = "";
		String strRus = "";
		
		// Get filename of directory
	    for (int i = 0; i < fs.length; i++) {
		    if(".svn".equals(fs[i].getName())) {
			    continue;
		    }
		    ma = pattern.matcher(fs[i].getName());
		    if(fs[i].isFile() && !fs[i].isHidden() && ma.find()) {
		    	strRus = "";
		    	strTmp = dir + "\\" + fs[i].getName();
		    	for(int i1 = 0; i1 < strTmp.length(); ++i1) {
		    		if('\\' == strTmp.charAt(i1)) {
		    			strRus += "\\\\";
		    		}else{
		    			strRus += strTmp.charAt(i1);
		    		}
		    	}
			    this.arrFileName.add(strRus);
			    
			    strRus = "";
			    strTmp = des + "\\" + fs[i].getName();
		    	for(int i1 = 0; i1 < strTmp.length(); ++i1) {
		    		if('\\' == strTmp.charAt(i1)) {
		    			strRus += "\\\\";
		    		}else{
		    			strRus += strTmp.charAt(i1);
		    		}
		    	}
		    	this.desFileName.add(strRus);
		    }else if(fs[i].isDirectory() && !fs[i].isHidden()){
		    	this.GetFile(dir + fs[i].getPath().substring(dir.length()), 
		    				 des + fs[i].getPath().substring(dir.length()));
		    }
	    }
	    	    
	}
	
	/**
	 * Print function
	 */
	public void print() {
		for(String str:this.arrFileName) {
			System.out.println(str);
		}
		System.out.println("--------------------------");
		for(String str:this.desFileName){
			System.out.println(str);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		
		String str = "tt";
		
		GetFileName getFile = new GetFileName();
		getFile.GetFile(str, str);
		getFile.print();
	}

	/**
	 * Get and Set Function.
	 */
	public ArrayList<String> getArrFileName() {
		return arrFileName;
	}

	public void setArrFileName(ArrayList<String> arrFileName) {
		this.arrFileName = arrFileName;
	}

}
