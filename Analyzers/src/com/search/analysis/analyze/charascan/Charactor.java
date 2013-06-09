package com.search.analysis.analyze.charascan;

import java.util.ArrayList;

import com.search.analysis.segment.SegCustom;
import com.search.analysis.segment.SegEnWord;
import com.search.analysis.segment.SegEnglish;
import com.search.analysis.segment.SegName;
import com.search.analysis.segment.SegNoise;
import com.search.analysis.segment.SegNumber;
import com.search.analysis.segment.SegPlace;
import com.search.analysis.segment.SegSpecial;
import com.search.analysis.util.ReadFromFile;
import com.search.analysis.util.WriteIntoFile;

public abstract class Charactor {

	/**
	 * Variables 
	 */
	protected static int LENGTH = 8;
	protected static int SPECIAL = 3;			// һ������ֵ�� ����
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
	protected SegSpecial segSpecial;
	
	/**
	 * Construct Function
	 */
	public Charactor(){
		this.sentence = "";						// ͳһʹ��set/get����������sentence,��ʼ��ʱ����ֵ.
		this.arrWord = new ArrayList<String>(); // ��ŷִ���ɺ�Ľ��.
		
		this.segCustom = new SegCustom(URL + "Custom\\");
		this.segEnglish = new SegEnglish(URL + "English\\");	// Ӣ������ֵ
		this.segEnWord = new SegEnWord(URL + "English\\");
		this.segName = new SegName(URL + "Name\\");
		this.segNoise = new SegNoise(URL + "Noise\\");
		this.segNumber = new SegNumber(URL + "Number\\");		// ��������ֵ
		this.segPlace = new SegPlace(URL + "Place\\");
		this.segSpecial = new SegSpecial(URL + "Special\\");	// һ��������(�������)
	}
	
	/**
	 * Division
	 */
	public abstract boolean Division();
	
	/**
	 * Print Function
	 */
	public void print(){
		WriteIntoFile writeInto = new WriteIntoFile("E:\\debugCharactor.txt");
		
		// Write information into file.
		writeInto.OpenFile();
		writeInto.WriteInto("----Sentence:");
		writeInto.WriteInto(this.sentence);
		writeInto.WriteInto("----arrWord:");
		writeInto.WriteInto(this.arrWord);
		writeInto.CloseFile();
		
		// Print on the console.
		ReadFromFile readFrom = new ReadFromFile("E:\\debugCharactor.txt");
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

	public SegSpecial getSegSpecial() {
		return segSpecial;
	}

	public void setSegSpecial(SegSpecial segSpecial) {
		this.segSpecial = segSpecial;
	}

	public void setArrWord(ArrayList<String> arrWord) {
		this.arrWord = arrWord;
	}

	public ArrayList<String> getArrWord() {
		return arrWord;
	}

}
