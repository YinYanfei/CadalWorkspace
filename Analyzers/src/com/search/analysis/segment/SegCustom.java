package com.search.analysis.segment;

import com.search.analysis.util.CreateHashMap;

public class SegCustom extends Segment{
	
	/**
	 * Construct Function
	 */
	public SegCustom(String url) {
		this.hashMap = new CreateHashMap(url + "CustomDict.txt", url + "reuse.txt");
	}

	/**
	 * Search Functions
	 */
	public boolean searchFun(String str){
		boolean signal = false;
		
		if(this.hashMap.search(str)){
			signal = true;
		}
		
		return signal;
	}
	
	/**
	 * Just to be used testing.
	 * @param args
	 */
	public static void main(String[] args) {
		SegCustom segCustom = new SegCustom("dict\\Custom\\");
		
		System.out.println(segCustom.hashMap.search("¹ú´óÖÐ"));
	}
}
