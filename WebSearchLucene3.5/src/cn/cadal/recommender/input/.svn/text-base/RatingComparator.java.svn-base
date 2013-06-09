/*
 * Created on 2004-12-22
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.cadal.recommender.input;

import java.util.Comparator;

import org.apache.log4j.Logger;

/**
 * @author zhangyin
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class RatingComparator implements Comparator{
	public static final int UIR = 1,URI=2;
	
	private static Logger logger = Logger
			.getLogger(RatingComparator.class);
	

	public abstract int compare(Object lo, Object ro);

	public int cmp(double[] cmpArray, int startIdx) {
		if (cmpArray[startIdx] < cmpArray[startIdx + 1])
			return -1;
		else if (cmpArray[startIdx] == cmpArray[startIdx + 1]){
			if( ( startIdx+2)== cmpArray.length ) return 0;
			return cmp(cmpArray, startIdx + 2);
		}
		else
			return 1;
	}
}
