package com.search.analysis.segdoc;

import com.search.analysis.analyze.delicate.ReMaxMatch;

public class AnalysisRMM extends Analysis{

	/**
	 * Variable
	 */
	private ReMaxMatch reMaxMatch;
	
	/**
	 * Construct functions
	 */
	public AnalysisRMM(String fileSource, String fileDest) {
		this.fileSource = fileSource;
		this.fileDest = fileDest;
		reMaxMatch = new ReMaxMatch();
	}
	public AnalysisRMM(String fileSource) {
		this.fileSource = fileSource;
		this.fileDest = fileSource.substring(0, fileSource.length() - 4) + "Dest.txt";
		reMaxMatch = new ReMaxMatch();
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
			this.sentence.SegSentenceRe();
			this.sentence.getArrSentence().add(0, "\n");
		}
		
		// sentence --> result
		for(String str:this.sentence.getArrSentence()){
			this.reMaxMatch.setSentence(str);
			this.reMaxMatch.Division();
		}
		
		// result --> arrEnd
		this.EvaluateArr(arrEnd, this.reMaxMatch.getArrWord());
		
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
		AnalysisRMM anaRMM = new AnalysisRMM("E:\\test.txt");
		anaRMM.AnalysisFun();

		System.out.println("2");
		AnalysisRMM anaRMM2 = new AnalysisRMM("E:\\test.txt", "E:\\testDestTwo.txt");
		anaRMM2.AnalysisFun();

	}

}
