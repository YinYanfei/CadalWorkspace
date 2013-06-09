package com.search.analysis.segdoc;

import com.search.analysis.analyze.charascan.ReScan;

public class AnalysisRSC extends Analysis{

	/**
	 * Variable
	 */
	private ReScan reScan;
	
	/**
	 * Construct Functions
	 */
	public AnalysisRSC(String fileSource, String fileDest){
		this.fileSource = fileSource;
		this.fileDest = fileDest;
		reScan = new ReScan();
	}
	public AnalysisRSC(String fileSource) {
		this.fileSource = fileSource;
		this.fileDest = fileSource.substring(0, fileSource.length() - 4) + "Dest.txt";
		reScan = new ReScan();
	}
	
	@Override
	public boolean AnalysisFun() {
		boolean signal = false;
		
		// file --> paragraph
		this.paragraphFile.setFileName(fileSource);
		this.paragraphFile.SegInitial();
		
		// paragraph --> sentence
		for(String str:this.paragraphFile.getArrStr()) {
			this.sentence.setParagraph(str);
			this.sentence.SegSentence();
			this.sentence.getArrSentence().add("\n");
		}
		
		// sentence --> result
		for(String str:this.sentence.getArrSentence()){
			this.reScan.setSentence(str);
			this.reScan.Division();
		}
		
		// result --> arrEnd
		this.EvaluateArr(arrEnd, this.reScan.getArrWord());
		
		// arrEnd --> file
		this.writeFile.setDestFile(fileDest);
		this.writeFile.OpenFile();
		signal = 
			this.writeFile.WriteInto(this.arrEnd, "\\");
		this.writeFile.CloseFile();
		
		return signal;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		System.out.println("1");
		AnalysisRSC anaRSC = new AnalysisRSC("E:\\test.txt");
		anaRSC.AnalysisFun();

		System.out.println("2");
		AnalysisRSC anaRSC2 = new AnalysisRSC("E:\\test.txt", "E:\\testDestTwo.txt");
		anaRSC2.AnalysisFun();

	}

}
