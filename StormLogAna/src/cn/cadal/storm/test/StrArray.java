package cn.cadal.storm.test;

import java.util.ArrayList;
import java.util.List;

public class StrArray {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String[] strList = {"0", "1", "2", "3"};
		strList[1] = "test";
		
		List<String[]> arrList = new ArrayList<String[]>();
		
		arrList.add(strList);
		
		System.out.println(arrList.get(arrList.size() - 1)[1]);

	}
}
