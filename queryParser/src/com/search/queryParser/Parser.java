package com.search.queryParser;

import java.util.ArrayList;

import com.search.queryParser.parserAnalysis.StrSegment;

public abstract class Parser {

	/**
	 * Variables
	 */
	protected static int NOT = -1;
	protected static int OR = 0;
	protected static int AND = 1;
	
	protected String content;
	protected StrSegment segment;
	protected ArrayList<String> resultArray;	// 存放最终的结果
	
	/**
	 * Construct Functions
	 */
	public Parser() {
		this.setSegment(new StrSegment());
		this.resultArray = new ArrayList<String>();
	}
	public Parser(String content) {
		this.content = content;
		this.segment = new StrSegment();
		this.resultArray = new ArrayList<String>();
	}
	
	/**
	 * Abstract Function
	 */
	protected abstract boolean ParserFun(String content);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test

	}

	/**
	 * Get and Set functions
	 */
	public ArrayList<String> getResultArray() {
		return resultArray;
	}

	public void setResultArray(ArrayList<String> resultArray) {
		this.resultArray = resultArray;
	}

	public void setSegment(StrSegment segment) {
		this.segment = segment;
	}

	public StrSegment getSegment() {
		return segment;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return content;
	}

}
