package com.search.analysis.util;
/**
 * Integer construct an object to store a number, the object
 * will be used in class HashMap in file 'CreateHashMap.java'.
 * @author Yanfei
 */

public class IntegerMy {

	/**
	 * @param args
	 * number: store a integer.
	 */
	private int number;
	
	/**
	 * Construct function.
	 */
	IntegerMy(int tmpInt){
		this.number = tmpInt;
	}
	
	// Main function 
	public static void main(String[] args) {
		IntegerMy integer = new IntegerMy(1);
		System.out.println("Test getNumber function:");
		System.out.println(integer.getNumber());
		
		integer.setNumber(2);
		System.out.println("Test setNumber function:");
		System.out.println(integer.getNumber());
	}
	
	/**
	 * Get and Set functions
	 * @return
	 */
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}

}
