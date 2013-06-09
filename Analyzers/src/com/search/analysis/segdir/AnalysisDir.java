package com.search.analysis.segdir;

import com.search.analysis.segdoc.Analysis;
import com.search.analysis.segdoc.AnalysisMM;

public class AnalysisDir extends GetFileName{
	
	/**
	 * Variable
	 */
	private String dir;
	private String des;
	private Analysis analysis;
	
	/**
	 * Construct Functions
	 */
	public AnalysisDir(String dir){
		super();
		this.dir = dir;
		this.des = dir + "\\Segment";
		this.analysis = null;
	}
	public AnalysisDir(String dir, String des) {
		super();
		this.dir = dir;
		this.des = des;
		this.analysis = null;
	}
	
	/**
	 * Analysis Function
	 */
	public boolean AnalysisFun() {
		boolean signal = false;
		
		// Get filenames
		this.GetFile(dir, des);
	
		// Analysis
		for(int i = 0; i < this.arrFileName.size(); ++i) {
			this.analysis = this.Create(this.arrFileName.get(i), this.desFileName.get(i));
			this.analysis.AnalysisFun();
		}
		
		return signal;
	}
	
	/**
	 * Create object of analysis
	 */
	private Analysis Create(String sou, String des) {
		/**
		 * AnalysisMM
		 */
		return new AnalysisMM(sou, des);
	
		/**
		 * AnalysisMMI
		 */
		// return new AnalysisMMI(sou, des);
	
		/**
		 * AnalysisMR
		 */
		// return new AnalysisMR(sou, des);
	
		/**
		 * AnalysisMRC
		 */
		// return new AnalysisMRC(sou, des);
	
		/**
		 * AnalysisRMM
		 */
		// return new AnalysisRMM(sou, des);
	
		/**
		 * AnalysisRMMI
		 */
		// return new AnalysisRMMI(sou, des);
	
		/**
		 * AnalysisRSC
		 */
		// return new AnalysisRSC(sou, des);
	
		/**
		 * AnalysisSC
		 */
		// return new AnalysisSC(sou, des);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		AnalysisDir anaDir = new AnalysisDir("E:\\");
		
		anaDir.AnalysisFun();
	}
	
	/**
	 * Get and Set Functions
	 */
	public Analysis getAnalysis() {
		return analysis;
	}

	public void setAnalysis(Analysis analysis) {
		this.analysis = analysis;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public String getDir() {
		return dir;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getDes() {
		return des;
	}

}
