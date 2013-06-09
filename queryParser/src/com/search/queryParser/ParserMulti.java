package com.search.queryParser;

import java.util.ArrayList;

public class ParserMulti extends Parser{
	
	/**
	 * Variables
	 */
	private ArrayList<Integer> resultLogic;		// 存放每一部分之间的逻辑关系
	private ArrayList<String> initialStr;		// 将原始内容切分成每一部分斌存放在initialStr
	
	/**
	 * Construct Function
	 */
	public ParserMulti() {
		this.resultLogic = new ArrayList<Integer>();		// 没一个部分之间的逻辑(-1,0,1)
		this.initialStr = new ArrayList<String>();			// 按逻辑块存放，获得待分析的各部分
	}
	
	/**
	 * Initial segment query-string
	 *  Store into this.initialStr.
	 */
	private boolean InitialSeg() {
		boolean signal = true;
		
		// deal with 'content'
		String tmp = "";
		try{
			for(int i = 0; i < this.content.length(); ++i) {
				if(this.content.charAt(i) == 'A') {
					if((i < this.content.length() - 2) 
					&& (this.content.charAt(i + 1) == 'N') 
					&& (this.content.charAt(i + 2) == 'D')) {
						this.initialStr.add(tmp);
						this.resultLogic.add(1);
						i += 2;
						tmp = "";
					}else{
						tmp += "A";
						continue;
					}
				}else if(this.content.charAt(i) == 'N') {
					if((i < this.content.length() - 2) 
					&& (this.content.charAt(i + 1) == 'O') 
					&& (this.content.charAt(i + 2) == 'T')) {
						this.initialStr.add(tmp);
						this.resultLogic.add(-1);
						i += 2;
						tmp = "";
					}else {
						tmp += "N";
						continue;
					}
				}else if(this.content.charAt(i) == 'O') {
					if((i < this.content.length() - 1) && (this.content.charAt(i + 1) == 'R')) {
						this.initialStr.add(tmp);
						this.resultLogic.add(0);
						i += 1;
						tmp = "";
					}else {
						tmp += "O";
						continue;
					}
				}else {
					tmp += this.content.charAt(i);
				}
			}
			this.initialStr.add(tmp);
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("InitialSeg Function <-- ParserMulti.java");
			signal = false;
		}
		
		return signal;
	}
	
	/**
	 * OverLoad Function
	 */
	@Override
	protected boolean ParserFun(String content) {
		boolean signal = this.InitialSeg();  // content --> this.initialStr
		
		if(signal){
			for(int i = 0; i < this.initialStr.size(); ++i) {
				this.resultArray.addAll(this.segment.SegStr(this.initialStr.get(i)));
			}
		}else {
			System.out.println("ParserFun Functionn <-- ParserMulti.java");
		}
		
		return signal;
	}

	/**
	 * Main Function
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		ParserMulti parserMulti = new ParserMulti();
		parserMulti.setContent("goodORdsANDdgORjiajORsdo");
		parserMulti.InitialSeg();

	}

	/**
	 * Get and Set Functions
	 */
	public ArrayList<Integer> getResultLogic() {
		return resultLogic;
	}

	public void setResultLogic(ArrayList<Integer> resultLogic) {
		this.resultLogic = resultLogic;
	}

	public void setInitialStr(ArrayList<String> initialStr) {
		this.initialStr = initialStr;
	}

	public ArrayList<String> getInitialStr() {
		return initialStr;
	}

}
