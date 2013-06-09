package com.search.analysis.segdoc;

import com.search.analysis.analyze.delicate.MaxAndReMax;

public class AnalysisMR extends Analysis{

	/**
	 * Variable
	 */
	private MaxAndReMax maxAndReMax;
	
	/**
	 * Construct Functions
	 */
	public AnalysisMR(String fileSource, String fileDest) {
		this.fileSource = fileSource;
		this.fileDest = fileDest;
		maxAndReMax = new MaxAndReMax();
	}
	public AnalysisMR(String fileSource) {
		this.fileSource = fileSource;
		this.fileDest = fileSource.substring(0, fileSource.length() - 4) + "Dest.txt";
		maxAndReMax = new MaxAndReMax();
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
			this.maxAndReMax.setSentence(str);
			this.maxAndReMax.Division();
		}
		
		// result --> arrEnd
		this.EvaluateArr(arrEnd, this.maxAndReMax.getArrWord());
		
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
		AnalysisMR anaMR = new AnalysisMR("E:\\test.txt");
		anaMR.AnalysisFun();

		System.out.println("2");
		AnalysisMR anaMR2 = new AnalysisMR("E:\\test.txt", "E:\\testDestTwo.txt");
		anaMR2.AnalysisFun();

	}

}
