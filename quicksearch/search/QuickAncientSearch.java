package cn.cadal.quicksearch.search; // Generated package name

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import cn.cadal.entity.Cancient;
import cn.cadal.util.TStransformer;


public class QuickAncientSearch extends Search {

    public QuickAncientSearch () {
    	repository = "ancient";
    	type = "ancient";
	}

	protected void makePageNo() {
		setPageNo (panc);
	}  
}

