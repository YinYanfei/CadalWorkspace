package com.search.analysis.segment;

import com.search.analysis.util.CreateHashMap;

public class SegPlace extends Segment{

	/**
	 * Construct Function
	 */
	public SegPlace(String url) {
		this.hashMap = new CreateHashMap(url + "PlaceDict.txt", url + "reuse.txt");
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
	 * @param args
	 */
	public static void main(String[] args) {
		SegPlace segPlace = new SegPlace("dict\\Place\\");

		System.out.println(segPlace.searchFun("º£ÄÏ"));
	}

}
