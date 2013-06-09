package com.search.analysis.segdoc;

import com.search.analysis.analyze.delicate.MaxMatchImprove;

public class AnalysisMMI extends Analysis{

	/**
	 * Variable
	 */
	private MaxMatchImprove maxMatchImp;
	
	/**
	 * Construct functions
	 */
	public AnalysisMMI(String fileSource, String fileDest){
		this.fileSource = fileSource;
		this.fileDest = fileDest;
		maxMatchImp = new MaxMatchImprove();
	}
	public AnalysisMMI(String fileSource) {
		this.fileSource = fileSource;
		this.fileDest = fileSource.substring(0, fileSource.length() - 4) + "Dest.txt";
		maxMatchImp = new MaxMatchImprove();
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
			this.maxMatchImp.setSentence(str);
			this.maxMatchImp.Division();
		}
		
		// result --> arrEnd
		this.EvaluateArr(arrEnd, this.maxMatchImp.getArrWord());
		
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
		AnalysisMMI anaMMI = new AnalysisMMI("E:\\test.txt");
		anaMMI.AnalysisFun();

		System.out.println("2");
		AnalysisMMI anaMMI2 = new AnalysisMMI("E:\\test.txt", "E:\\testDestTwo.txt");
		anaMMI2.AnalysisFun();
	}

}
