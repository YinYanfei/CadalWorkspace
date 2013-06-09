package com.search.queryParser.parserAnalysis;

import java.util.ArrayList;

public class StrSegment {

	/**
	 * Variable
	 */
	private AnalysisStr analysis;
	
	/**
	 * Construct Function
	 */
	public StrSegment(){
		this.analysis = new AnalysisStr();
	}
	
	/**
	 * Segment
	 */
	public ArrayList<String> SegStr(String str){
		this.analysis.AnalysisFun(str, 0);
		// this.analysis.AnalysisFun(str, 1);     可根据不同的情况选择
		
		return this.analysis.resultArr;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		
	}

	/**
	 * Set and Get Functions
	 */
	public AnalysisStr getAnalysis() {
		return analysis;
	}

	public void setAnalysis(AnalysisStr analysis) {
		this.analysis = analysis;
	}

}
