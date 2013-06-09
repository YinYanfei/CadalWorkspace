package cn.cadal.quicksearch.search; // Generated package name

import org.hibernate.Criteria;
import org.hibernate.Session;

import cn.cadal.entity.Cminguo;

public class QuickMinguoSearch extends Search {
    
    public QuickMinguoSearch (){
    	repository = "minguo";
    	type = "minguo";
    }
    
	protected void makePageNo() {
		setPageNo (pmin);
	}
}

