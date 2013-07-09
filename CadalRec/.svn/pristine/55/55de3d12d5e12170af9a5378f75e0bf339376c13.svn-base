package cn.cadal.rec.algo.itemcf;

import java.util.ArrayList;
import java.util.List;

public class Itemset {
	public List<Integer> val;

	/**
	 * Construct functions
	 */
	public Itemset() {
		this.val = new ArrayList<Integer>();
	}
	
	public Itemset(ArrayList<Integer> val) {
		this.val = val;
	}

	/**
	 * Insert into
	 */
	public void InsertIntoItemset(Integer val){
		this.val.add(val);
	}
	
	/**
	 * Contain or not
	 */
	public boolean ContainOrNot(Integer valSingle) {
		if(this.val.contains(valSingle)){
			return true;
		}
		
		return false;
	}

	/**
	 * Get size
	 */
	public int size(){
		return this.val.size();
	}
	
	/**
	 * get value by index
	 */
	public int getByIndex(int indx){
		return this.val.get(indx);
	}
	
	/**
	 * print
	 */
	public void print(){
		for(int i = 0; i < this.val.size(); ++i) {
			System.out.println(this.val.get(i));
		}
	}

	
	/**
	 * Getter and Setter
	 */
	public List<Integer> getVal() {
		return val;
	}

	public void setVal(List<Integer> val) {
		this.val = val;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test

	}

}
