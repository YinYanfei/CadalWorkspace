package com.search.analysis.segdoc;

import com.search.analysis.analyze.charascan.Scan;

public class AnalysisSC extends Analysis{

	/**
	 * Variable
	 */
	private Scan scan;
	
	/**
	 * Construct Functions
	 */
	public AnalysisSC(String fileSource, String fileDest) {
		this.fileSource = fileSource;
		this.fileDest = fileDest;
		scan = new Scan();
	}
	public AnalysisSC(String fileSource) {
		this.fileSource = fileSource;
		this.fileDest = fileSource.substring(0, fileSource.length() - 4) + "Dest.txt";
		scan = new Scan();
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
			this.scan.setSentence(str);
			this.scan.Division();
		}
		
		// result --> arrEnd
		this.EvaluateArr(arrEnd, this.scan.getArrWord());
		
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
		AnalysisSC anaSC = new AnalysisSC("E:\\test.txt");
		anaSC.AnalysisFun();

		System.out.println("2");
		AnalysisSC anaSC2 = new AnalysisSC("E:\\test.txt", "E:\\testDestTwo.txt");
		anaSC2.AnalysisFun();

	}

}
