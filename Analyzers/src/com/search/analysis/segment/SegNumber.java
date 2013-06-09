package com.search.analysis.segment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.search.analysis.util.CreateHashMap;

public class SegNumber extends Segment{

	/**
	 * Construct Function
	 */
	public SegNumber(String url) {
		this.hashMap = new CreateHashMap(url + "NumberDict.txt", url + "reuse.txt");
	}
	
	/**
	 * Search Function
	 * 对于数字的识别在这个函数中做的比较的粗略，有待改进 ---- 可以以小数点作为分界来分类进行识别.(待定)
	 */
	public boolean searchFun(String number) {
		boolean signal = true;
		
		String reg = "^[0-9,.,０－９,．]+$";			// 西文数字的匹配
		
		Pattern pattern = Pattern.compile(reg);
		Matcher ma = pattern.matcher(number);
		
		if(ma.find()){
			signal = true;
		}else{
			// 中文数字以及中西结合型数字的匹配
			for(int i = 0; signal && i < number.length(); i++) {
				if(this.hashMap.search(number.substring(i, i + 1))){
					continue;
				}else{
					signal = false;
					break;
				}
			}
		}
		
		return signal;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SegNumber segNumber = new SegNumber("dict\\Number\\");
		
		System.out.println(segNumber.searchFun("９"));
		/*
		String reg = "^[0-9,.,０－９,．]+$";
		String number = "90.９０";
		
		Pattern pattern = Pattern.compile(reg);
		Matcher ma = pattern.matcher(number);
		
		System.out.println(ma.find());
		*/
	}

}
