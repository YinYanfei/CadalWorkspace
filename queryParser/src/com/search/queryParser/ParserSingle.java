package com.search.queryParser;

public class ParserSingle extends Parser{

	/**
	 * OverLoad
	 */
	@Override
	protected boolean ParserFun(String content) {
		this.resultArray = this.segment.SegStr(content);
		
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
