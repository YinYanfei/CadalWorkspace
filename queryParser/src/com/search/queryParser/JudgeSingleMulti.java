package com.search.queryParser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JudgeSingleMulti {
	
	/**
	 * Variables
	 *       content: String object will be judged.
	 * SINGLEORMULTI: false -- single
	 *   	          true  -- multiply
	 */
	private boolean SINGLEORMULTI;
	private String content;
	
	private String[] logicStr = {"AND", "OR", "NOT",
								 "&&", "||", "!",
								 " ", "　", "	"};
	
	/**
	 * Construct functions
	 */
	public JudgeSingleMulti() {
		this.content = "";
		this.SINGLEORMULTI = false;
	}
	public JudgeSingleMulti(String content) {
		this.content = content;
		this.SINGLEORMULTI = false;
	}
	
	/**
	 * Judge Function, to determine query-string is single or multiple.
	 */
	public boolean Judge() {
		boolean re = true;
		
		this.DealInitial();
		
		String regex = "";
		
		try{
			int i = 0;
			for(i = 0; i < this.logicStr.length - 1; ++i) {
				regex += this.logicStr[i] + ",";
			}
			regex += logicStr[i];
			
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(this.content);
			
			if(matcher.find()) {
				this.SINGLEORMULTI = true;
				re = true;
			}else{
				this.SINGLEORMULTI = false;
				re = false;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return re;
	}
	
	/**
	 * Judge Overload
	 */
	public boolean Judge(String str) {
		this.setContent(str);
		
		return this.Judge();
	}
	
	/**
	 * Initial operator
	 *  1.remove spaces at being and end of 'content';
	 *  2.replace 'tab' and more than one space into single space.
	 */
	private boolean DealInitial() {
		boolean su = false;
		String strTmp = "";
		String str = "";
		
		System.out.println(this.content.length());
		
		try{
			if(this.content.length() != 0) {
				strTmp = this.content;
				
				// Deal String
				for(int i = 0; i < strTmp.length(); i++) {
					if(strTmp.charAt(i) == '	' || strTmp.charAt(i) == '	'
					   ||strTmp.charAt(i) == '\n' || strTmp.charAt(i) == '　') {
						str += ' ';
					}else{
						str += strTmp.charAt(i);
					}
				}
				
				this.content = str.trim();
				
				// content --> standard logic content
				this.content = this.content.replaceAll("&&", "AND");
				this.content = this.content.replaceAll("\\|\\|", "OR");
				this.content = this.content.replaceAll("!", "NOT");
				
				strTmp = this.content;

				// 此处的代码不够强健，有待改进!!
				this.content = "";
				for(int i = 0; i < strTmp.length(); ++i) {
					if(strTmp.charAt(i) == ' '){
						//
						int j = 1;
						while(strTmp.charAt(i + j) == ' ') {
							++j;
						}
						i += (j - 1);
						//
						
						if(((i > 2) && ((strTmp.substring(i - 3, i).equals("AND"))||
								(strTmp.substring(i - 3, i).equals("NOT")))) 
							|| ((i > 1) && (strTmp.substring(i - 2, i).equals("OR")))){
							continue;
						}else{
							//
							int j2 = 1;
							while(strTmp.charAt(i + j2) == ' ') {
								++j2;
							}
							i += (j2 - 1);
							
							// System.out.println(strTmp.substring(i + 1, i + 4).equals("AND") + "===" + strTmp.substring(i + 1, i + 4));
							if(((i < strTmp.length() - 3)&&(strTmp.substring(i + 1, i + 4).equals("AND") || 
									strTmp.substring(i + 1, i + 4).equals("NOT"))) 
								|| ((i < strTmp.length() - 2) && strTmp.substring(i + 1, i + 3).equals("OR"))) {
								continue;
							}else{
								this.content += "OR";
							}
						}
						
					}else{
						this.content += strTmp.charAt(i);
					}
				}
				
			}else{
				su = false;
				
				System.out.println("!!检索内容不能为空!!--JudgeSingleMulti Function--");
			}
			
			su = true;
		}catch(Exception e) {
			su = false;
			e.printStackTrace();
		}
		
		return su;
	}
	
	/**
	 * No-Useful
	 * Remove all spaces of sides of String.
	 * 	We have used String.trim function to replace this function
	 */
	@SuppressWarnings("unused")
	private String RemoveSpaces(String str) {
		int begin = 0;
		int end = str.length();
		
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == ' ') {
				++begin;
			}else{
				break;
			}
		}
		for(int i = str.length() - 1; i >= 0; --i) {
			if(str.charAt(i) == ' ') {
				--end;
			}else{
				break;
			}
		}
		
		if(end > begin) {
			return str.substring(begin, end);
		}else{
			return "";
		}
	}
	
	/**
	 * Print Function
	 */
	private void print() {
		System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-");
		System.out.println("Judge Single or Multiple");
		System.out.println("content: " + this.content);
		System.out.println(" signal: " + this.SINGLEORMULTI);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		JudgeSingleMulti judge = new JudgeSingleMulti("   good 	ds  && dg\tjiaj\nsdo  ");
		judge.print();
		judge.DealInitial();
		judge.print();
		
	}
	
	/**
	 * Set and Get functions
	 */
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String[] getLogicStr() {
		return logicStr;
	}
	public void setLogicStr(String[] logicStr) {
		this.logicStr = logicStr;
	}
	public boolean isSINGLEORMULTI() {
		return SINGLEORMULTI;
	}
	public void setSINGLEORMULTI(boolean sINGLEORMULTI) {
		SINGLEORMULTI = sINGLEORMULTI;
	}

}
