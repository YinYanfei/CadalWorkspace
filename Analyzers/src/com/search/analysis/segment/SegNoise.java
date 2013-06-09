package com.search.analysis.segment;

import com.search.analysis.util.CreateHashMap;

public class SegNoise extends Segment{
	
	/**
	 * Construct Function
	 */
	public SegNoise(String url) {
		this.hashMap = new CreateHashMap(url + "NoiseDict.txt", url + "reuse.txt");
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
		SegNoise segNoise = new SegNoise("dict\\Noise\\");
		
		segNoise.searchFun(" ");

	}

}
