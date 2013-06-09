package com.search.analysis.analyze.delicate;

import java.util.ArrayList;

import com.search.analysis.segment.SegCustom;
import com.search.analysis.segment.SegEnWord;
import com.search.analysis.segment.SegEnglish;
import com.search.analysis.segment.SegName;
import com.search.analysis.segment.SegNoise;
import com.search.analysis.segment.SegNumber;
import com.search.analysis.segment.SegPlace;
import com.search.analysis.util.ReadFromFile;
import com.search.analysis.util.WriteIntoFile;

public abstract class Word {

	/**
	 * Variables:
	 * 		  LENGTH: the length we analyze each time.
	 * 		     URL: root address of dictionary.
	 *  	sentence: the content will be analyzed.
	 *  	    seg*: objects to analyze sentence.
	 */
	protected static int LENGTH = 8;
	protected static String URL = "dict\\";
	
	protected String sentence;
	protected ArrayList<String> arrWord = null;
	
	protected SegCustom segCustom;
	protected SegEnglish segEnglish;
	protected SegEnWord segEnWord;
	protected SegName segName;
	protected SegNoise segNoise;
	protected SegNumber segNumber;
	protected SegPlace segPlace;
	
	/**
	 * Construct Functions
	 */
	public Word() {
		this.sentence = "";						// 统一使用set/get方法来操作sentence,初始化时不赋值.
		this.arrWord = new ArrayList<String>(); // 存放分词完成后的结果.
		
		this.segCustom = new SegCustom(URL + "Custom\\");
		this.segEnglish = new SegEnglish(URL + "English\\");
		this.segEnWord = new SegEnWord(URL + "English\\");
		this.segName = new SegName(URL + "Name\\");
		this.segNoise = new SegNoise(URL + "Noise\\");
		this.segNumber = new SegNumber(URL + "Number\\");
		this.segPlace = new SegPlace(URL + "Place\\");
	}
	
	/**
	 * Function will be override in Child-classes.
	 */
	abstract public boolean Division();
	
	/**
	 * Print sentence and arrWord
	 */
	public void print() {
		WriteIntoFile writeInto = new WriteIntoFile("E:\\debugWord.txt");
		
		// Write information into file.
		writeInto.OpenFile();
		writeInto.WriteInto("----Sentence:");
		writeInto.WriteInto(this.sentence);
		writeInto.WriteInto("----arrWord:");
		writeInto.WriteInto(this.arrWord);
		writeInto.CloseFile();
		
		// Print on the console.
		ReadFromFile readFrom = new ReadFromFile("E:\\debugWord.txt");
		readFrom.ReadPrint();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		/*
		Word word = new Word();
		word.setSentence("中华人民共和国");
		
		System.out.println(word.getArrWord().size());
		System.out.println(word.getSegCustom());
		*/
	}

	/**
	 * Set and Get Functions
	 */	
	public String getSentence() {
		return sentence;
	}

	public void setSentence(String sentence) {
		this.sentence = sentence;
	}

	public SegCustom getSegCustom() {
		return segCustom;
	}

	public void setSegCustom(SegCustom segCustom) {
		this.segCustom = segCustom;
	}

	public SegEnglish getSegEnglish() {
		return segEnglish;
	}

	public void setSegEnglish(SegEnglish segEnglish) {
		this.segEnglish = segEnglish;
	}

	public SegEnWord getSegEnWord() {
		return segEnWord;
	}

	public void setSegEnWord(SegEnWord segEnWord) {
		this.segEnWord = segEnWord;
	}

	public SegName getSegName() {
		return segName;
	}

	public void setSegName(SegName segName) {
		this.segName = segName;
	}

	public SegNoise getSegNoise() {
		return segNoise;
	}

	public void setSegNoise(SegNoise segNoise) {
		this.segNoise = segNoise;
	}

	public SegNumber getSegNumber() {
		return segNumber;
	}

	public void setSegNumber(SegNumber segNumber) {
		this.segNumber = segNumber;
	}

	public SegPlace getSegPlace() {
		return segPlace;
	}

	public void setSegPlace(SegPlace segPlace) {
		this.segPlace = segPlace;
	}

	public void setArrWord(ArrayList<String> arrWord) {
		this.arrWord = arrWord;
	}

	public ArrayList<String> getArrWord() {
		return arrWord;
	}

}
