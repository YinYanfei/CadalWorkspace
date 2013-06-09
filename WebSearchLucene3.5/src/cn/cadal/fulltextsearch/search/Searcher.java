/*
 * Created on 2006-3-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.cadal.fulltextsearch.search;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.cadal.entity.Cbook;
import cn.cadal.fulltextsearch.analysis.HighLighter;
import cn.cadal.fulltextsearch.analysis.cjk.CJKAnalyzer;
import cn.cadal.fulltextsearch.cache.CacheManager;
import cn.cadal.fulltextsearch.cache.SearchResultCache;
import cn.cadal.fulltextsearch.configure.Configure;
import cn.cadal.fulltextsearch.configure.ConfigureManager;
import cn.cadal.fulltextsearch.content.TxtFileContentGenerator;
import cn.cadal.util.TStransformer;

import common.utils.HibernateUtil;

/**
 * @author lwm
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Searcher {
	
	private static final Log LOG = LogFactory.getLog (Searcher.class);
	
	public ArrayList<SearchResult> search(Query query)throws IOException,ParseException{
		ArrayList<SearchResult> results = new ArrayList(0);
		Configure conf = ConfigureManager.getConfigure();
		
		if(query.getQ().trim().equalsIgnoreCase(""))
			return results;
		
		String queryStr = "("+query.getQ()+") OR ( "+TStransformer.STtransform(query.getQ())+")";
		Hits hits = PageSearcher.search(conf.getIndexPath(), queryStr, "Content");
		
		int length = hits.length();
		query.hitSize = length;
		int start = query.start;
		int end = query.start + query.pageSize;
		if(end>length)
			end = length;
		for(int i = start;i<end;i++){
			SearchResult r = new SearchResult();
			Document doc = hits.doc(i);
			r.setBookID(doc.getField("BookNo").stringValue());
			r.setPageNo(doc.getField("PageNo").stringValue());
			r.setContent(doc.getField("Content").stringValue());
			r.setTitle(doc.getField("Title").stringValue());
			/**
			 * MY
			 */
			
			/**
			 * MY
			 */
			results.add(r);
		}
		return results;
	}
	
	public ArrayList<SearchResult> searchInBook(Query query,String bookId)throws IOException,ParseException{
		ArrayList<SearchResult> results = new ArrayList(0);
		Configure conf = ConfigureManager.getConfigure();
		String queryStr = "("+query.getQ()+") OR ( "+TStransformer.STtransform(query.getQ())+")";
		
		LOG.info("query string :"+queryStr+" book no:"+bookId );
		Hits hits = PageSearcher.multiSearch(conf.getIndexPath(),queryStr,bookId);
		int length = hits.length();
		query.hitSize = length;
		int start = query.start;
		int end = query.start + query.pageSize;
		if(end>length)
			end = length;
		for(int i = start;i<end;i++){
			SearchResult r = new SearchResult();
			Document doc = hits.doc(i);
			r.setBookID(doc.getField("BookNo").stringValue());
			r.setPageNo(doc.getField("PageNo").stringValue());
			r.setContent (doc.getField("Content").stringValue());
			r.setTitle(doc.getField("Title").stringValue());
			results.add(r);
		}
		return results;
	}
	
	private ArrayList<ViewResult> doHighlight (ArrayList<SearchResult> searchResultList, Query query)throws IOException{
		ArrayList<ViewResult> viewResults = new ArrayList<ViewResult>(0);
		Configure conf = ConfigureManager.getConfigure();
		
		TxtFileContentGenerator g = new TxtFileContentGenerator();
		
		String queryStr = query.getQ()+" "+TStransformer.STtransform(query.getQ());
		StringReader stringReader = new StringReader(queryStr);
		CJKAnalyzer analyzer = new CJKAnalyzer();
		TokenStream tokenStream = analyzer.tokenStream("", stringReader);
		
		ArrayList tokens = new ArrayList();
		
		/**
		 * MY
		 */
		for(Token token = tokenStream.getAttribute(Token.class); tokenStream.incrementToken(); token = tokenStream.getAttribute(Token.class)){
			tokens.add(token.term());
		}
		/**
		 * MY
		 */
		/*
		for (Token token = tokenStream.next(); token != null; token = tokenStream.next()) {
			tokens.add(token.termText());
		}
		*/
		HighLighter h = new HighLighter(tokens);	     
		
		for(int i = 0; i<searchResultList.size(); i++){
			ViewResult v = new ViewResult();
           	SearchResult r = (SearchResult)searchResultList.get(i);
           	String bookID = r.getBookID();
           	v.setBookNo(bookID);
           	v.setPageNo(Integer.parseInt(r.getPageNo()));
           	String content = r.getContent();
           	String summary = h.highLight(content);
           	v.setSummary(summary);
           	v.setTitle(r.getTitle());
         	v.setPageUrl("Reader.action?bookNo="+v.getBookNo()+"&pageNo="+v.getPageNo());
          	viewResults.add(v);
        }    
		
		return viewResults;
	}
	
	public ArrayList<ViewResult> getViewResultList(Query query)throws IOException,ParseException{
		long start = System.currentTimeMillis();
		ArrayList<SearchResult> searchResultList = search(query);
		ArrayList<ViewResult> viewResults = doHighlight(searchResultList, query);
		query.timeUsed = System.currentTimeMillis() - start;
		return viewResults;
	}
	
	public ArrayList getViewResultListInBooks(Query query,String bookId)throws IOException,ParseException{
		long start = System.currentTimeMillis();
		ArrayList searchResultList = searchInBook(query,bookId);		
		ArrayList<ViewResult> viewResults = doHighlight(searchResultList, query);
		query.timeUsed = System.currentTimeMillis() - start;
		return viewResults;
	}
	
}
