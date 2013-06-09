package com.search.analysis.segdoc;

import com.search.analysis.analyze.delicate.ReMaxMatchImprove;

public class AnalysisRMMI extends Analysis{

	/**
	 * Variable
	 */
	private ReMaxMatchImprove reMaxMatchImp;
	
	/**
	 * Construct functions
	 */
	public AnalysisRMMI(String fileSource, String fileDest) {
		this.fileSource = fileSource;
		this.fileDest = fileDest;
		reMaxMatchImp = new ReMaxMatchImprove();
	}
	public AnalysisRMMI(String fileSource) {
		this.fileSource = fileSource;
		this.fileDest = fileSource.substring(0, fileSource.length() - 4) + "Dest.txt";
		reMaxMatchImp = new ReMaxMatchImprove();
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
			this.reMaxMatchImp.setSentence(str);
			this.reMaxMatchImp.Division();
		}
		
		// result --> arrEnd
		this.EvaluateArr(arrEnd, this.reMaxMatchImp.getArrWord());
		
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
		AnalysisRMMI anaRMMI = new AnalysisRMMI("E:\\test.txt");
		anaRMMI.AnalysisFun();

		System.out.println("2");
		AnalysisRMMI anaRMMI2 = new AnalysisRMMI("E:\\test.txt", "E:\\testDestTwo.txt");
		anaRMMI2.AnalysisFun();

	}

}
