package com.search.analysis.util;

import java.util.ArrayList;

public class ManageNumber {

	/**
	 * @param args
	 */
	private int curInteger;
	private ArrayList<IntegerMy> reuseInteger;
	
	/**
	 * Construct function.
	 */
	ManageNumber(){
		curInteger = 0;
		reuseInteger = new ArrayList<IntegerMy>();
	}
	
	/**
	 * Acquire a integer, which can be used, from 'reuseInteger', and 
	 * will delete the integer in the end.
	 * @return
	 *        -1: reuseInteger has no number. 
	 * (Integer): the integer can be used.
	 */
	public int acquireNumber(){
		IntegerMy tmpReturn = new IntegerMy(-1);
		
		if(0 != reuseInteger.size()){
			tmpReturn = reuseInteger.get(reuseInteger.size() - 1);
			reuseInteger.remove(reuseInteger.size() - 1);
		}else{
			tmpReturn.setNumber(-1);
		}
		
		return tmpReturn.getNumber();
	}
	
	/**
	 * Add a integer into reuseInteger.
	 * @param
	 *  number: the value will be added into reuseNumber.
	 * @return
	 *   true: success.
	 *  false: failed.
	 */
	public boolean add(int number){
		boolean signal = false;
		
		IntegerMy tmp = new IntegerMy(number);
		reuseInteger.add(tmp);
		
		return signal;
	}
	
	/**
	 * Main Function, only used for testing.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Test 1: Construct");
		ManageNumber manNumber = new ManageNumber();
		System.out.println(manNumber.getCurInteger());
		System.out.println(manNumber.acquireNumber());
		
		System.out.println("Test 3: setCurInteger");
		manNumber.setCurInteger(2);
		System.out.println(manNumber.getCurInteger());
		
		System.out.println("Test 2: Add");
		manNumber.add(23);
		System.out.println(manNumber.getReuseInteger().size());
		System.out.println(manNumber.acquireNumber());
		System.out.println(manNumber.getReuseInteger().size());
		System.out.println(manNumber.acquireNumber());
		
		System.out.println("Test 3: Add more than once");
		for(int i = 0; i < 8; i++) {
			manNumber.add(i);
		}
		System.out.println(manNumber.getReuseInteger().size());
		System.out.println(manNumber.acquireNumber());
		System.out.println(manNumber.getReuseInteger().size());
		
		System.out.println("Print all values of reuseInteger:");
		for(int i = 0; i < manNumber.getReuseInteger().size(); i++){
			System.out.println(manNumber.getReuseInteger().get(i).getNumber());
		}
	}

	/**
	 * Set and Get Functions of variables.
	 */
	public int getCurInteger() {
		return curInteger;
	}

	public void setCurInteger(int curInteger) {
		this.curInteger = curInteger;
	}

	public ArrayList<IntegerMy> getReuseInteger() {
		return reuseInteger;
	}

	public void setReuseInteger(ArrayList<IntegerMy> reuseInteger) {
		this.reuseInteger = reuseInteger;
	}
}
