package cn.cadal.quicksearch.search; // Generated package name

import java.rmi.RemoteException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.Query;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Restrictions;

import cn.cadal.entity.Cenglish;
import cn.cadal.quicksearch.analysis.ChineseAnalyzer;
import cn.cadal.util.TStransformer;
import cn.cadal.webservice.translation.TranslationSoap;

import common.utils.StackTraceUtil;


public class QuickEnglishSearch extends Search {

	private TranslationSoap remoteTranslation;
	
    public QuickEnglishSearch (){
    	repository = "english";
    	type = "english";
    }
    
	protected void makePageNo() {
		setPageNo (pfig);
	}
	
	public TranslationSoap getRemoteTranslation() {
		return remoteTranslation;
	}

	public void setRemoteTranslation(TranslationSoap remoteTranslation) {
		this.remoteTranslation = remoteTranslation;
	}
	
	protected Query getQuery()
	{
		String[] strs = queryword.split("AND");
		ArrayList words = new ArrayList();
		for(int i=0; i<strs.length; i++)
			if(!strs[i].trim().equalsIgnoreCase(""))
			{
				String english="";
		    	try {
		    		english =remoteTranslation.Sentence("ce", strs[i].trim());
		    	}catch (RemoteException reExc) {
		    		LOG.warn("remote exception:"+StackTraceUtil.getStackTrace(reExc));
		    	}
				words.add(strs[i].trim() + " " + english);
			}
		String queryStr = null;
		if(words.size()<1)
			return null;
		else
		{
			queryStr = "(" + (String)words.get(0) + ")";
			for(int i=1; i<words.size(); i++)
				queryStr += " AND " + "(" + (String)words.get(i) + ")";
		}
		Analyzer analyzer = new ChineseAnalyzer();
		Query query = null;
		try
		{
			query = MultiFieldQueryParser.parse(queryStr, fields, flags, analyzer);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return query;
	}
}
