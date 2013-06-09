package com.search.analysis.analyze.delicate;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaxAndReMax extends Word{

	/**
	 * Variables
	 */
	private ArrayList<String> arrWord1;
	private ArrayList<String> arrWord2;
	
	/**
	 * Construct Function
	 */
	public MaxAndReMax(){
		this.arrWord1 = new ArrayList<String>();
		this.arrWord2 = new ArrayList<String>();
	}

	/**
	 * Accept the shortest split.
	 */
	@Override
	public boolean Division() {
		boolean signal = false;
		
		// Division MaxMatch and ReMaxMatch
		this.arrWord1.clear();
		this.arrWord2.clear();
		signal = ((this.DivisionMaxMatch()) && (this.DivisionReMaxMatch()));
		
		// Judge and Deal
		if(arrWord1.size() > arrWord2.size()) {
			signal = evaluate(arrWord2);
		}else if(arrWord1.size() < arrWord2.size()) {
			signal = evaluate(arrWord1);
		}else{
			// Calculate the number of single word in array-list.
			int num1 = 0;
			int num2 = 0;
			
			for(String str:arrWord1){
				if(1 == str.length()){
					++num1;
				}
			}
			for(String str:arrWord2) {
				if(1 == str.length()) {
					++num2;
				}
			}
			
			// Deal
			if(num1 > num2) {
				signal = evaluate(arrWord2);
			}else if(num1 < num2) {
				signal = evaluate(arrWord1);
			}else{
				signal = evaluate(arrWord2);
			}
		}
		
		return signal;
	}
	
	/**
	 * Devision Help -- evaluate of array-list.
	 */
	private boolean evaluate(ArrayList<String> arrStrTmp) {
		boolean signal = false;
		
		try{
			for(String str: arrStrTmp) {
				this.arrWord.add(str);
			}
			
			signal = true;
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Division
	 */
	private boolean DivisionMaxMatch() {
		boolean signal = false;
		String str = "";
		String regex = "";
		int position = 0;
		int strLen = 0;
		
		while(position < this.sentence.length()){
			// Get a substring of sentence
			if(this.sentence.length() - position <= LENGTH){
				str = this.sentence.substring(position, this.sentence.length());
				strLen = this.sentence.length() - position;
				position = this.sentence.length();
			}else{
				str = this.sentence.substring(position, position + LENGTH);
				position = position + LENGTH;
				strLen = LENGTH;
			}

			// Judge English or Chinese
			// 这是处理的一个英语单词匹配的特殊情况.
			regex = "^[a-zA-Z-]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher ma = pattern.matcher(str);
			
			if(ma.find()){
				while(position < this.sentence.length()){
					if(this.segEnglish.searchFun(this.sentence.substring(position, position + 1))){
						str += this.sentence.substring(position, position + 1);
						++position;
					}else{
						break;
					}
				}
				if(!this.segEnWord.SearchFun(str)) 		// 匹配这个来判断是否为is、am等等的单词
					this.arrWord1.add(str); 			// 完全是英语时直接添加并跳过本次循环

				continue;
			}
			
			// Judge Number ---- 数字和英语字母一样存在边界处理的问题
			if(this.segNumber.searchFun(str)){
				while(position < this.sentence.length()){
					if(this.segNumber.searchFun(this.sentence.substring(position, position + 1))){
						str += this.sentence.substring(position, position + 1);
						++position;
					}else{
						break;
					}
				}
				this.arrWord1.add(str);
				
				continue;
			}
			
			// System.out.println(strLen);
			
			// Deal with each part.
			while(strLen > 1){
				// 判断英语
				ma = pattern.matcher(str);
				if(ma.find()) {
					this.arrWord1.add(str);
					break;
				}
				
				// Custom
				if(this.segCustom.searchFun(str)){
					this.arrWord1.add(str);
					break;
				}
				
				// Name
				if(str.length() >= 2 && str.length() <= 4){
					if(this.segName.searchFun(str)){
						this.arrWord1.add(str);
						break;
					}
				}
				
				// Place
				if(this.segPlace.searchFun(str)){
					this.arrWord1.add(str);
					break;
				}
				
				// Number
				if(this.segNumber.searchFun(str)){
					this.arrWord1.add(str);
					break;
				}
				
				str = str.substring(0, str.length() - 1);
				position--;
				strLen--;
			}
			
			if(strLen == 1) {
				if(!this.segNoise.searchFun(str)){
					this.arrWord1.add(str);
					// position--;
					continue;
				}
			}
		}
		
		// Judge success or not.
		if(this.getSentence().length() == position){
			signal = true;
		}else{
			signal = false;
		}
		
		return signal;
	}
	
	private boolean DivisionReMaxMatch(){
		boolean signal = false;
		String str = "";
		String regex = "";
		int position = this.getSentence().length();
		int strLen = 0;
		
		while(position > 0){
			// Get a substring of sentence
			if(position <= LENGTH){
				str = this.sentence.substring(0, position);
				strLen = position;
				position = 0;
			}else{
				str = this.sentence.substring(position - LENGTH, position);
				position = position - LENGTH;
				strLen = LENGTH;
			}
			
			// Judge English or Chinese
			// 这是处理的一个英语单词匹配的特殊情况.
			regex = "^[a-zA-Z-]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher ma = pattern.matcher(str);
			
			if(ma.find()){
				while(position > 0){
					if(this.segEnglish.searchFun(this.sentence.substring(position - 1, position))){
						str = this.sentence.substring(position - 1, position) + str;
						--position;
					}else{
						break;
					}
				}
				if(!this.segEnWord.SearchFun(str))  	// 匹配这个来判断是否为is、am等等的单词
					this.arrWord2.add(0, str); 			// 完全是英语时直接添加并跳过本次循环

				continue;
			}
			
			// Judge Number ---- 数字和英语字母一样存在边界处理的问题
			if(this.segNumber.searchFun(str)){
				while(position > 0){
					if(this.segNumber.searchFun(this.sentence.substring(position - 1, position))){
						str = this.sentence.substring(position - 1, position) + str;
						--position;
					}else{
						break;
					}
				}
				this.arrWord2.add(0, str);
				
				continue;
			}

			// Deal with each part.
			while(strLen > 1){
				// 判断英语
				ma = pattern.matcher(str);
				if(ma.find()) {
					this.arrWord2.add(0, str);
					break;
				}
				
				// Custom
				if(this.segCustom.searchFun(str)){
					this.arrWord2.add(0, str);
					break;
				}
				
				// Name
				if(str.length() >= 2 && str.length() <= 4){
					if(this.segName.searchFun(str)){
						this.arrWord2.add(0, str);
						break;
					}
				}
				
				// Place
				if(this.segPlace.searchFun(str)){
					this.arrWord2.add(0, str);
					break;
				}
				
				// Number
				if(this.segNumber.searchFun(str)){
					this.arrWord2.add(0, str);
					break;
				}
				
				str = str.substring(1, str.length());
				++position;
				strLen--;
			}

			if(strLen == 1) {
				if(!this.segNoise.searchFun(str)){
					this.arrWord2.add(0, str);
					continue;
				}
			}
			
		}
		
		// Judge success or not.
		if(0 == position){
			signal = true;
		}else{
			signal = false;
		}

		return signal;

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "这样的人才能 is aghish 经受住考验";
		MaxAndReMax maxAndReMax = new MaxAndReMax();
		
		maxAndReMax.setSentence(str);

		maxAndReMax.Division();
		maxAndReMax.print();
	}

	/**
	 * Get and set functions
	 */
	public void setArrWord1(ArrayList<String> arrWord1) {
		this.arrWord1 = arrWord1;
	}

	public ArrayList<String> getArrWord1() {
		return arrWord1;
	}

	public void setArrWord2(ArrayList<String> arrWord2) {
		this.arrWord2 = arrWord2;
	}

	public ArrayList<String> getArrWord2() {
		return arrWord2;
	}

}
