package com.search.analysis.analyze.delicate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaxAndReMaxComp extends Word{
	
	/**
	 * Variables
	 */
	private ArrayList<String> arrWord1;
	private ArrayList<String> arrWord2;

	/**
	 * Construct Function
	 */
	public MaxAndReMaxComp(){
		this.arrWord1 = new ArrayList<String>();
		this.arrWord2 = new ArrayList<String>();
	}
	
	/**
	 * Get the most completed split.
	 */
	public boolean Division(){
		boolean signal = false;
		String str1 = "";
		String str2 = "";
		
		// Devision maxMatch and reMaxMatch
		this.arrWord1.clear();
		this.arrWord2.clear();
		signal = ((this.DivisionMaxMatch()) && (this.DivisionReMaxMatch()));
				
		// Deal
		Iterator<String> iter1 = arrWord1.iterator();
		Iterator<String> iter2 = arrWord2.iterator();
		
		while(iter1.hasNext() && iter2.hasNext()) {			
			str1 = iter1.next();
			str2 = iter2.next();
			
			if(str1.equals(str2)){
				this.arrWord.add(str1);
			}else{
				this.arrWord.add(str1);
				this.arrWord.add(str2);
			}
		}
		
		// Deal with the last of MaxMatch or ReMaxMatch
		while(iter1.hasNext()) {
			str1 = iter1.next();
			this.arrWord.add(str1);
		}
		
		while(iter2.hasNext()) {
			str2 = iter2.next();
			this.arrWord.add(str2);
		}

		return signal;
	}
	
	/**
	 * DevisionComplete Help Function
	 */
	@SuppressWarnings("unused")
	private boolean judge(String strTmp) {
		boolean signal = true;
		
		for(String str:this.getArrWord()){
			if(str.equals(strTmp)){
				signal = false;
			}
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
		// Test
		String str = "当一个隐士不能通过直接观察天气" +
				"状态来预测天气时但他有一些水藻民间的传说告诉我们水" +
				"藻的状态与天气有一定的概率关系也就是说水藻的状态与天" +
				"气时紧密相关的此时我们就有两组状态观察状态水藻的" +
				"状态和隐含状态天气状态因此我们希望得到一个算法可以为" +
				"隐士通过水藻和马尔科夫过程在没有直接观察天气的情况" +
				"下得到天气的变化情况更容易理解的一个应用就是语音识别" +
				"我们的问题定义就是如何通过给出的语音信号预测出原来" +
				"的文字信息在这里语音信号就是观察状态识别出的文字" +
				"就是隐含状态这里需要注意的是在任何一" +
				"种应用中观察状态的个数与隐含状态的个数有可能不" +
				"一样的下面我们就用隐马尔科夫模型HMM来解决这类问题";
		MaxAndReMax maxAndReMax = new MaxAndReMax();
		
		maxAndReMax.setSentence(str);
		
		maxAndReMax.Division();
		maxAndReMax.print();
	}
	
	/**
	 * Set and Get Functions
	 */
	public ArrayList<String> getArrWord1() {
		return arrWord1;
	}
	
	public void setArrWord1(ArrayList<String> arrWord1) {
		this.arrWord1 = arrWord1;
	}

	public ArrayList<String> getArrWord2() {
		return arrWord2;
	}

	public void setArrWord2(ArrayList<String> arrWord2) {
		this.arrWord2 = arrWord2;
	}

}
