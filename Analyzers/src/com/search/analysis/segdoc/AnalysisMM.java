package com.search.analysis.segdoc;

import com.search.analysis.analyze.delicate.MaxMatch;

public class AnalysisMM extends Analysis{

	/**
	 * Variable
	 */
	private MaxMatch maxMatch;
	
	/**
	 * Construct Function
	 * 		1) Users define destination file
	 * 		2) Program create destination file auto.
	 */
	public AnalysisMM(String fileSource, String fileDest) {
		this.fileSource = fileSource;
		this.fileDest = fileDest;
		maxMatch = new MaxMatch();
	}
	public AnalysisMM(String fileSource) {
		this.fileSource = fileSource;
		this.fileDest = fileSource.substring(0, fileSource.length() - 4) + "Dest.txt";
		maxMatch = new MaxMatch();
	}
	
	/**
	 * Analysis function -- override
	 */
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
			this.maxMatch.setSentence(str);
			this.maxMatch.Division();
		}
		
		// result --> arrEnd
		this.EvaluateArr(arrEnd, this.maxMatch.getArrWord());
		
		// arrEnd --> file
		this.writeFile.setDestFile(fileDest);
		this.writeFile.OpenFile();
		if(this.writeFile != null)
			signal = this.writeFile.WriteInto(this.arrEnd, "\\");
		else
			System.out.println("AnalysisMM Cannot open!");
		this.writeFile.CloseFile();
		
		return signal;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		System.out.println("1");
		AnalysisMM anaMM = new AnalysisMM("tt\\test - ¸±±¾.txt");
		anaMM.AnalysisFun();

		System.out.println("2");
		AnalysisMM anaMM2 = new AnalysisMM("E:\\test.txt", "E:\\testDestTwo.txt");
		anaMM2.AnalysisFun();
	}

}
