package com.search.queryParser.parserAnalysis;

import java.util.ArrayList;

/**
 * 
 * 这个类需要换乘Analysis中segstr文件夹下的AnalysisStr.java文件,直接
 * 在StrSegment文件中更改引用的地址即可.
 *
 */
public class AnalysisStr {
	public ArrayList<String> resultArr = null;
	
	public boolean AnalysisFun(String sentence, int signal){
		this.resultArr = null;
		
		return true;
	};
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 
	}

}
