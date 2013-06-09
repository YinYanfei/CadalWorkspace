package cn.cadal.storm.demo.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNum {

	public List<Integer> randomList = null;
	
	public RandomNum() {
		this.randomList = new ArrayList<Integer>();
	}
	
	/**
	 * To get special random
	 * @param num   : The top number of random
	 * @param reNum : The number want to get
	 */
	public void GetRandom(int num, int reNum) {
		Random random = new Random();
		boolean[] bool = new boolean[num];
		int randInt = 0;
		
		for (int j = 0; j < reNum; j++) {
			
			do {
				randInt = random.nextInt(num);
			} while (bool[randInt]);
			
			this.randomList.add(randInt);
			
			bool[randInt] = true;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		RandomNum rn = new RandomNum();
//		
//		rn.GetRandom(10, 9);
//		
//		System.out.println(rn.randomList.size());
//		
//		for(int i = 0; i < rn.randomList.size(); ++i) {
//			System.out.println(rn.randomList.get(i));
//		}

	}

}
