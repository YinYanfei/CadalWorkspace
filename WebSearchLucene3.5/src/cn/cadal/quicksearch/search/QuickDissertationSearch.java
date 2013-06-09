package cn.cadal.quicksearch.search; // Generated package name

import org.hibernate.Criteria;
import org.hibernate.Session;

import cn.cadal.entity.Cdissertation;


public class QuickDissertationSearch extends Search {
    
    public QuickDissertationSearch () {
    	repository = "dissertation";
    	type = "dissertation";
    }

	protected void makePageNo() {
		setPageNo (pdis);
	}
}
