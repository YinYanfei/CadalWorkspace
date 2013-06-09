package com.search.analysis.segment;

import com.search.analysis.util.CreateHashMap;

public class SegInterval extends Segment{

	/**
	 * Construct Function
	 */
	public SegInterval(String url) {
		this.hashMap = new CreateHashMap(url + "IntervalDict.txt", url + "reuse.txt");
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
	 * 这个函数是专门为了使中文标点能够写入到hash-map而专门的函数，常规方法不能够进行.
	 */
	public boolean specialForInterval(){
		boolean signal = false;
		
		try{
			hashMap.addAccordWord(" ");	
			hashMap.addAccordWord("　");	
			hashMap.addAccordWord("，");
			hashMap.addAccordWord("；");
			hashMap.addAccordWord("‘");
			hashMap.addAccordWord("’");
			hashMap.addAccordWord("“");
			hashMap.addAccordWord("”");
			hashMap.addAccordWord("/");
			hashMap.addAccordWord("？");
			hashMap.addAccordWord("～");
			hashMap.addAccordWord("！");
			hashMap.addAccordWord("＠");
			hashMap.addAccordWord("＃");
			hashMap.addAccordWord("￥");
			hashMap.addAccordWord("％");
			hashMap.addAccordWord("……");
			hashMap.addAccordWord("…");
			hashMap.addAccordWord("＆");
			hashMap.addAccordWord("×");
			hashMap.addAccordWord("。");
			hashMap.addAccordWord("【");
			hashMap.addAccordWord("】");
			hashMap.addAccordWord("｛");
			hashMap.addAccordWord("｝");
			hashMap.addAccordWord("｜");
			hashMap.addAccordWord("、");
			hashMap.addAccordWord("《");
			hashMap.addAccordWord("》");	
			hashMap.addAccordWord("（");
			hashMap.addAccordWord("）");
			hashMap.addAccordWord("—");
			hashMap.addAccordWord("：");	
			
			signal = true;
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SegInterval segInterval = new SegInterval("dict\\Interval\\");
		
		// Special for interval.
		segInterval.specialForInterval();
		
		System.out.println(segInterval.searchFun("【"));
	}
}
