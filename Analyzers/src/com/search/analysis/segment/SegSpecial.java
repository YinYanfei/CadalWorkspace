package com.search.analysis.segment;

import com.search.analysis.util.CreateHashMap;

public class SegSpecial extends Segment{

	/**
	 * Construct Function
	 */
	public SegSpecial(String url){
		this.hashMap = new CreateHashMap(url + "SpecialDict.txt", url + "reuse.txt");
	}
	
	/**
	 * Search Function
	 */
	public boolean SearchFun(String str) {
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
		// Test
		SegSpecial segSpecial = new SegSpecial("dict\\Special\\");
		
		System.out.println(segSpecial.SearchFun("“Ú¥À"));
	}

}
