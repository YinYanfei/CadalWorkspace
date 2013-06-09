package cn.cadal.quicksearch.search; // Generated package name

import org.hibernate.Criteria;
import org.hibernate.Session;

import cn.cadal.entity.CjournalMetadata;


public class QuickJournalSearch extends Search {

    public QuickJournalSearch (){
    	repository = "journal";
    	type = "journal";
	}
    
	protected void makePageNo() {
		setPageNo (pjou);
	}
}
