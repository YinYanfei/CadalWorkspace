package com.search.analysis.segdoc;

import com.search.analysis.analyze.delicate.MaxAndReMaxComp;

public class AnalysisMRC extends Analysis{

	/**
	 * Variable
	 */
	private MaxAndReMaxComp maxAndReMaxComp;
	
	/**
	 * Construct functions
	 */
	public AnalysisMRC(String fileSource, String fileDest) {
		this.fileSource = fileSource;
		this.fileDest = fileDest;
		maxAndReMaxComp = new MaxAndReMaxComp();
	}
	public AnalysisMRC(String fileSource) {
		this.fileSource = fileSource;
		this.fileDest = fileSource.substring(0, fileSource.length() - 4) + "Dest.txt";
		maxAndReMaxComp = new MaxAndReMaxComp();
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
			this.maxAndReMaxComp.setSentence(str);
			this.maxAndReMaxComp.Division();
		}
		
		// result --> arrEnd
		this.EvaluateArr(arrEnd, this.maxAndReMaxComp.getArrWord());
		
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
		AnalysisMRC anaMRC = new AnalysisMRC("E:\\test.txt");
		anaMRC.AnalysisFun();

		System.out.println("2");
		AnalysisMRC anaMRC2 = new AnalysisMRC("E:\\test.txt", "E:\\testDestTwo.txt");
		anaMRC2.AnalysisFun();

	}
}
