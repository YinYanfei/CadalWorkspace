package com.search.analysis.segment;

import com.search.analysis.util.CreateHashMap;

public class SegName extends Segment{
	
	/**
	 * Variables
	 */
	private CreateHashMap singleName;
	private CreateHashMap doubleName1;
	private CreateHashMap doubleName2;
	
	/**
	 * Construct Function
	 */
	public SegName(String url) {
		// FamilyName hash-map -- 此处的变量名字与其他的不同应注意使用.
		this.hashMap = new CreateHashMap(url + "FamilyName.txt", url + "reuseFamily.txt");
		
		// SingleName hash-map
		this.singleName = new CreateHashMap(url + "SingleName.txt", url + "reuseSingle.txt");
		
		// DoubleName1 hash-map
		this.doubleName1 = new CreateHashMap(url + "DoubleName1.txt", url + "reuseDouble1.txt");
		
		// DoubleName2 hash-map
		this.doubleName2 = new CreateHashMap(url + "DoubleName2.txt", url + "reuseDouble2.txt");
	}
	
	/**
	 * Construct function for test.
	 * We can not use this construct function to construct an object.
	 */
	private SegName(String url, int number){
		if(1 == number) {
			// hashMap 
			this.hashMap = new CreateHashMap(url + "FamilyName.txt", url + "reuseFamily.txt");		
		}else if(2 == number) {
			// singleName
			this.singleName = new CreateHashMap(url + "SingleName.txt", url + "reuseSingle.txt");
		}else if(3 == number) {
			// doubleName1
			this.doubleName1 = new CreateHashMap(url + "DoubleName1.txt", url + "reuseDouble1.txt");
		}else if(4 == number){
			// doubleName2
			this.doubleName2 = new CreateHashMap(url + "DoubleName2.txt", url + "reuseDouble2.txt");
		}else{
			// Exception
			System.out.println("I donnot know how to do it! -- SegName.java");
		}
	}
	
	/**
	 * Search Function
	 */
	public boolean searchFun(String name){
		boolean signal = false;
		
		int len = name.length();
		
		switch(len){
			case 2:
				signal = searchHelp2(name);
				break;
			case 3:
				signal = searchHelp3(name);
				break;
			case 4:
				signal = searchHelp4(name);
				break;
			default:
				signal = false;
				break;
		}
		
		return signal;
	}
	
	/**
	 * SearchHelp2 Function
	 */
	private boolean searchHelp2(String name) {
		boolean signal = false;
		
		String StrfamilyName = name.substring(0, 1);
		if(this.hashMap.search(StrfamilyName)){
			String StrSingleName = name.substring(1);
			
			if((this.getSingleName().search(StrSingleName)) 
				|| (this.getDoubleName1().search(StrSingleName))
				|| (this.getDoubleName2().search(StrSingleName))){
				signal = true;
			}else{
				signal = false;
			}
		}else{
			signal = false;
		}
		
		return signal;
	}
	
	/**
	 * SearchHelp3 Function
	 */
	private boolean searchHelp3(String name) {
		boolean signal = false;
		
		String StrFamily = name.substring(0, 1);
		String StrDouble1 = name.substring(1, 2);
		String StrDouble2 = name.substring(2);
		
		if(this.hashMap.search(StrFamily)){
			if((this.doubleName1.search(StrDouble1) && this.doubleName2.search(StrDouble2))
			    || (this.doubleName1.search(StrDouble1) && this.doubleName1.search(StrDouble2))
			    || (this.doubleName2.search(StrDouble1) && this.doubleName1.search(StrDouble2))
			    || (this.doubleName2.search(StrDouble1) && this.doubleName2.search(StrDouble2))){
				signal = true;
			}else{
				signal = false;
			}
		}else{
			StrFamily = name.substring(0, 2);
			StrDouble1 = name.substring(3);
			
			if(this.hashMap.search(StrFamily)) {
				if(this.doubleName1.search(StrDouble1) || this.doubleName2.search(StrDouble1))
					signal = true;
				else
					signal = false;
			}else{
				signal = false;
			}
		}
		
		return signal;
	}
	
	/**
	 * SearchHelp4 Function
	 */
	private boolean searchHelp4(String name){
		boolean signal = false;
		
		String StrFamily = name.substring(0, 2);
		String StrDouble1 = name.substring(2, 3);
		String StrDouble2 = name.substring(3);
		
		if(this.hashMap.search(StrFamily)){
			if((this.doubleName1.search(StrDouble1) && this.doubleName2.search(StrDouble2))
				|| (this.doubleName1.search(StrDouble1) && this.doubleName1.search(StrDouble2))
				|| (this.doubleName2.search(StrDouble1) && this.doubleName1.search(StrDouble2))
				|| (this.doubleName2.search(StrDouble1) && this.doubleName2.search(StrDouble2))){
				signal = true;
			}else{
				signal = false;
			}
		}else{
			signal = false;
		}
		
		return signal;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SegName segName = null;
		
		segName = new SegName("dict\\Name\\");
		System.out.println(segName.searchFun("闫相杉"));
		
		segName = new SegName("dict\\Name\\", 1);
		System.out.println(segName.getHashMap().search("尹"));
		
		segName = new SegName("dict\\Name\\", 2);
		System.out.println(segName.getSingleName().search("瑞"));
		
		segName = new SegName("dict\\Name\\", 3);
		System.out.println(segName.getDoubleName1().search("志"));
		
		segName = new SegName("dict\\Name\\", 4);
		System.out.println(segName.getDoubleName2().search("飞"));
	}
	
	/**
	 * Get and Set Functions.
	 */
	public CreateHashMap getSingleName() {
		return singleName;
	}

	public void setSingleName(CreateHashMap singleName) {
		this.singleName = singleName;
	}

	public CreateHashMap getDoubleName1() {
		return doubleName1;
	}

	public void setDoubleName1(CreateHashMap doubleName1) {
		this.doubleName1 = doubleName1;
	}

	public CreateHashMap getDoubleName2() {
		return doubleName2;
	}

	public void setDoubleName2(CreateHashMap doubleName2) {
		this.doubleName2 = doubleName2;
	}
	
}