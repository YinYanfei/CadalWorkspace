package com.search.analysis.segdoc;

import java.util.ArrayList;

import com.search.analysis.analyze.initial.ParagraphFile;
import com.search.analysis.analyze.initial.Sentence;
import com.search.analysis.util.ReadFromFile;
import com.search.analysis.util.WriteIntoFile;

public abstract class Analysis {

	/**
	 * Variables: 
	 */
	protected String fileSource;
	protected String fileDest;
	protected ReadFromFile readFile;
	protected WriteIntoFile writeFile;
	
	protected ParagraphFile paragraphFile;
	protected Sentence sentence;
	
	protected ArrayList<String> arrEnd;
	
	/**
	 * Construct Function
	 */
	public Analysis(){
		this.fileSource = "";
		this.fileDest = "";
		this.readFile = new ReadFromFile();
		this.writeFile = new WriteIntoFile();
		this.paragraphFile = new ParagraphFile();
		this.sentence = new Sentence();
		this.arrEnd = new ArrayList<String>();
	}
	
	/**
	 * Analysis -- abstract function
	 */
	public abstract boolean AnalysisFun();
	
	/**
	 * Evaluate ArrayList
	 */
	public boolean EvaluateArr(ArrayList<String> arrDest, ArrayList<String> arrSou) {
		boolean signal = false;
		
		try{
			for(String str:arrSou) {
				arrDest.add(str);
			}
			
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Print function -- using to test the result of analysis.
	 */
	public void print(){
		WriteIntoFile writeInto = new WriteIntoFile("E:\\debugAnalysis.txt");
		
		// Write information into file.
		writeInto.OpenFile();
		writeInto.WriteInto("----arrEnd:");
		writeInto.WriteInto(this.arrEnd);
		writeInto.CloseFile();
		
		// Print on the console.
		ReadFromFile readFrom = new ReadFromFile("E:\\debugAnalysis.txt");
		readFrom.ReadPrint();
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
	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

	public String getFileDest() {
		return fileDest;
	}

	public void setFileDest(String fileDest) {
		this.fileDest = fileDest;
	}

	public ReadFromFile getReadFile() {
		return readFile;
	}

	public void setReadFile(ReadFromFile readFile) {
		this.readFile = readFile;
	}

	public WriteIntoFile getWriteFile() {
		return writeFile;
	}

	public void setWriteFile(WriteIntoFile writeFile) {
		this.writeFile = writeFile;
	}

	public ParagraphFile getParagraphFile() {
		return paragraphFile;
	}

	public void setParagraphFile(ParagraphFile paragraphFile) {
		this.paragraphFile = paragraphFile;
	}

	public Sentence getSentence() {
		return sentence;
	}

	public void setSentence(Sentence sentence) {
		this.sentence = sentence;
	}

	public ArrayList<String> getArrEnd() {
		return arrEnd;
	}

	public void setArrEnd(ArrayList<String> arrEnd) {
		this.arrEnd = arrEnd;
	}
	
}
