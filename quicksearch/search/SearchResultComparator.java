package cn.cadal.quicksearch.search;

import java.util.Comparator;

public class SearchResultComparator implements Comparator {
	
	public int compare(Object o1, Object o2) {
		
		SearchResult c1 = (SearchResult) o1;
		SearchResult c2 = (SearchResult) o2;
		if (c1.getBookRank() < c2.getBookRank()) {
			return 1;
		} else if ( c1.getBookRank() == c2.getBookRank() ) {
		    return 0;
		} else {
			return -1;
		}
	}
}
