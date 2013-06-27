package cn.cadal.rec.java;

import java.util.ArrayList;
import java.util.HashMap;

public class HashMapTry {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HashMap<String, ArrayList<Integer>> hashMap = new HashMap<String, ArrayList<Integer>>(); 

		ArrayList<Integer> arrListTmp = new ArrayList<Integer>();
		arrListTmp.add(1);
		arrListTmp.add(10);
		
		hashMap.put("one", arrListTmp);
		System.out.println(hashMap.get("one"));
		
		hashMap.get("one").add(100);
		System.out.println(hashMap.get("one"));		
	}
}
