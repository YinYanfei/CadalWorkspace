package cn.cadal.np;

import java.util.Comparator;

public class RecommendedBookComparator implements Comparator {
	
	public int compare(Object o1, Object o2) {
		
		RecommendedBook c1 = (RecommendedBook) o1;
		RecommendedBook c2 = (RecommendedBook) o2;
		if (c1.getWeight() < c2.getWeight()) {
			return 1;
		} else if ( c1.getWeight() == c2.getWeight() ) {
		    return 0;
		} else {
			return -1;
		}
	}
}
