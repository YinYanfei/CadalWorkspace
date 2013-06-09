package com.search.analysis.util;

public class CollectDict {
	private CreateHashMap createHashMap;
	
	/**
	 * Construct Function.
	 */
	CollectDict(String fileURL, String reuseFile){ 
		createHashMap = new CreateHashMap(fileURL, reuseFile);		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Collect word.txt dictionary
		/*
		CollectDict c1 = new CollectDict("F:\\dic\\create.txt", "F:\\dic\\reuseFile.txt");
		System.out.println(c1.getCreateHashMap().getCurInteger().getCurInteger());
		c1.getCreateHashMap().addAccordFile("F:\\dic\\dict0_0.txt");
		c1.getCreateHashMap().addAccordFile("F:\\dic\\dict1_0.txt");
		c1.getCreateHashMap().addAccordFile("F:\\dic\\dict2_0.txt");
		c1.getCreateHashMap().addAccordFile("F:\\dic\\dict3_0.txt");
		c1.getCreateHashMap().addAccordFile("F:\\dic\\dict4_0.txt");
		c1.getCreateHashMap().addAccordFile("F:\\dic\\dict5_0.txt");
		c1.getCreateHashMap().restore();
		*/
		
		// Collect interpuction.txt dictionary
		CollectDict c1 = new CollectDict("F:\\dic\\interpunction.txt", "F:\\dic\\reuseFile.txt");
		c1.getCreateHashMap().addAccordFile("F:\\dic\\Stopword.txt");
		c1.getCreateHashMap().restore();
		
	}

	/**
	 * Get and Set Functions.
	 */
	public CreateHashMap getCreateHashMap() {
		return createHashMap;
	}

	public void setCreateHashMap(CreateHashMap createHashMap) {
		this.createHashMap = createHashMap;
	}

}
