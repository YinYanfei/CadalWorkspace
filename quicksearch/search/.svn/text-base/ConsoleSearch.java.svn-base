package cn.cadal.quicksearch.search;

import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanClause.Occur;

import cn.cadal.fulltextsearch.search.PageSearcher;
import cn.cadal.quicksearch.QuickSearchConfig;
import cn.cadal.quicksearch.analysis.ChineseAnalyzer;
import cn.cadal.util.TStransformer;

public class ConsoleSearch {

	private final String[] types = {"ancient", "dissertation", "english", "journal", "minguo", "modern"};
	private final String[] fields = {"Title", "Creator", "Subject"};
	private final BooleanClause.Occur[] flags = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
	private final Analyzer analyzer = new ChineseAnalyzer();
	
	
	public Hits search(String type, String qstr)
	{
		
		String queryStr = "(" + qstr + ") OR ("+TStransformer.STtransform(qstr)+")";
		
		Hits hits = null;
		try{
			FSDirectory fsd = FSDirectory.getDirectory(QuickSearchConfig.getIndexDir() + "/" + type);
			//RAMDirectory ramd = new RAMDirectory(QuickSearchConfig.getIndexDir() + "/" + type);
			IndexSearcher indexSearcher = new IndexSearcher(fsd);
			Query query = MultiFieldQueryParser.parse(queryStr, fields, flags, analyzer);
			hits = indexSearcher.search(query);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return hits;
	}
	public static void main(String[] args) {
		
		try{
			ConsoleSearch csearch = new ConsoleSearch();
			long startTime = new Date().getTime();
			Hits hits = csearch.search("minguo", "Êµ¼Ê");
			for(int i=0; i<hits.length(); i++)
				System.out.println(hits.doc(i).get("Title") + " " + hits.score(i) + " " + i);
			long endTime = new Date().getTime();
			System.out.println("Time costs: " + (endTime - startTime) + "ms");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

}
