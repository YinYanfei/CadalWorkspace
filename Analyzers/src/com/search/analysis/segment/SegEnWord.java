package com.search.analysis.segment;

import com.search.analysis.segment.Segment;
import com.search.analysis.util.CreateHashMap;

public class SegEnWord extends Segment {

	/**
	 * Construct Function
	 */
	public SegEnWord(String url){
		this.hashMap = new CreateHashMap(url + "EnWordDict.txt", url + "reuseEnWord.txt");
	}
	
	/**
	 * Search Function
	 */
	public boolean SearchFun(String str){
		boolean signal = false;
		
		if(this.hashMap.search(str)){
			signal = true;
		}
		
		return signal;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SegEnWord segEnWord = new SegEnWord("dict\\English\\");

		System.out.println(segEnWord.SearchFun("is"));
	}

}
