package com.search.analysis.segment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.search.analysis.util.CreateHashMap;

public class SegEnglish extends Segment{

	/**
	 * Construct Function
	 */
	public SegEnglish(String url) {
		this.hashMap = new CreateHashMap(url + "EnglishDict.txt", url + "reuse.txt");
	}
	
	/**
	 * Search Function
	 */
	public boolean searchFun(String str) {
		boolean signal = false;
		
		String reg = "^[a-zA-Z,-]+$";
		
		Pattern pattern = Pattern.compile(reg);
		Matcher ma = pattern.matcher(str);
		
		if(ma.find()){
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
		SegEnglish segEnglish = new SegEnglish("dict\\English\\");
		
		System.out.println(segEnglish.searchFun("A-good-boy"));
		
		/*
		String str = "Goodsjdi-fjdfhaui";
		String reg = "^[a-zA-Z,-]+$";
		
		Pattern pattern = Pattern.compile(reg);
		Matcher ma = pattern.matcher(str);
		
		if(ma.find()){
			System.out.println("Find");
		}else{
			System.out.println("None");
		}
		*/

	}

}
