package cn.cadal.quicksearch.search;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import cn.cadal.quicksearch.analysis.ChineseAnalyzer;
import cn.cadal.user.CheckCookie;
import cn.cadal.util.TStransformer;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.StackTraceUtil;

public class Search extends ActionSupport  implements SessionAware {

	protected String queryword;

	private List searchResult;
	
	private final Analyzer analyzer = new CJKAnalyzer (Version.LUCENE_35);
	
	protected final String[] fields = {"Subject", "QueryWord"};
	
	protected final BooleanClause.Occur[] flags = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};

	protected int pdis = 1;

	protected int pmod = 1;

	protected int pmin = 1;

	protected int panc = 1;

	protected int pjou = 1;

	protected int ppai = 1;

	protected int pvid = 1;

	protected int pfig = 1;
	
	
	protected int pageNo=1;
	
	private int pageNum;

	private int recordNum = 0;

	protected String repository;
	
	protected String type;

	private Map servletSession;
	
	private static String[] types = {"ancient", "dissertation", "english", "journal", "minguo", "modern"};
	private static HashMap<String, RAMDirectory> dMap = null;
	
	static
	{
		dMap = new HashMap<String, RAMDirectory>();
		for(int i=0; i<types.length; i++)
		{
			try{
				/**
				 * MY
				 */
				File path  = new File("E:/index" + "/" + types[i]);
				Directory dir = FSDirectory.open(path);
				// RAMDirectory ramd = new RAMDirectory(dir);
				dMap.put(types[i], null);
				/**
				 * MY
				 */
			}catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
	}
	
	public void setSession(Map arg0) {
		servletSession = arg0;
	}

	private String currentParams;

	public String getCurrentParams () {
		return currentParams;
	}

	public void setCurrentParams (String currentParams) {
		this.currentParams = currentParams;
	}

	/**
	 * Gets the value of searchResult
	 *
	 * @return the value of searchResult
	 */
	public final List getSearchResult() {
		return this.searchResult;
	}


	/**
	 * Sets the value of pageNo
	 *
	 * @param argPageNo Value to assign to this.pageNo
	 */
	public final void setPageNo(final int argPageNo) {
		this.pageNo = argPageNo;
	}

	/**
	 * Gets the value of pageNum
	 *
	 * @return the value of pageNum
	 */
	public final int getPageNum() {
		return this.pageNum;
	}

	/**
	 * Sets the value of pageNum
	 *
	 * @param argPageNum Value to assign to this.pageNum
	 */
	public final void setPageNum(final int argPageNum) {
		this.pageNum = argPageNum;
	}

	/**
	 * Gets the value of recordNum
	 *
	 * @return the value of recordNum
	 */
	public final int getRecordNum() {
		return this.recordNum;
	}

	/**
	 * Sets the value of recordNum
	 *
	 * @param argRecordNum Value to assign to this.recordNum
	 */
	public final void setRecordNum(final int argRecordNum) {
		this.recordNum = argRecordNum;
	}

	/**
	 * Gets the value of repository
	 *
	 * @return the value of repository
	 */
	public final String getRepository() {
		return this.repository;
	}

	/**
	 * Sets the value of repository
	 *
	 * @param argRepository Value to assign to this.repository
	 */
	public final void setRepository(final String argRepository) {
		this.repository = argRepository;
	}

	/**
	 * Gets the value of query
	 *
	 * @return the value of query
	 */
	public final String getQueryword() {
		return this.queryword;
	}

	/**
	 * Sets the value of query
	 *
	 * @param argQuery Value to assign to this.query
	 */
	public final void setQueryword(final String argQuery) {
		this.queryword = argQuery;
	}
	
	public String execute() throws Exception {
	
		CheckCookie checkCookie = new CheckCookie(servletSession);
		checkCookie.checkCookieLogin();

		searchResult = search();

		currentParams = "";
		if( pdis > 1 ){
			currentParams +="&pdis="+pdis;
		}if( pmod > 1 ){
			currentParams +="&pmod="+pmod;
		}if( pmin > 1 ){
			currentParams +="&pmin="+pmin;
		}if( panc > 1 ){
			currentParams +="&panc="+panc;
		}if( pjou > 1 ){
			currentParams +="&pjou="+pjou;
		}if( ppai > 1 ){
			currentParams +="&ppai="+ppai;
		}if( pvid > 1 ){
			currentParams +="&pvid="+pvid;
		}if( pfig > 1 ){
			currentParams +="&pfig="+pfig;
		}
		return SUCCESS;
	}

	protected List search()
	{
		int recNumPerPage = 20;
		makePageNo();
		
		Query query = getQuery();
		if(query==null)
			return new ArrayList();
		
		/**
		 * MY
		 */
		searchResult = new ArrayList();
		ScoreDoc[] hits = null;
		IndexSearcher indexSearcher = null;
		/**
		 * MY
		 */
		for(int i = 0; i < this.types.length; ++i) {		
			// Hits hits = null;
			try{
				/**
				 * MY
				 */
				File path  = new File("E:/index/" + types[i]);
				FSDirectory fsd = FSDirectory.open(path);
				indexSearcher = new IndexSearcher(fsd);	
				query = this.getQuery();
				hits = indexSearcher.search(query, 1000).scoreDocs;

				// indexSearcher = new IndexSearcher(IndexReader.open(dMap.get(type)));
				/*
				QueryParser qp = new MultiFieldQueryParser(Version.LUCENE_35, fields, new ChineseAnalyzer());
				Query tq = qp.Query(queryword);
				TopDocs results = indexSearcher.search(query, 100);			// 100是将要返回的结果数
				hits = results.scoreDocs;
				*/
				/**
				 * MY
				 */
				// RAMDirectory ramd = dMap.get(type);
				// IndexSearcher indexSearcher = new IndexSearcher(ramd);
				// hits = indexSearcher.search(query);
			}catch(Exception ex){
				ex.printStackTrace();
			}
			recordNum = hits.length;
			
			// recordNum = hits.length();
			pageNum = (recordNum - 1) / recNumPerPage;
			pageNum++;
			
			int firstRow = (pageNo - 1) * recNumPerPage;
			int lastRow = firstRow + recNumPerPage;
			
			/**
			 * MY
			 */
			Document doc = null;
			try
			{
				for(int i1=firstRow; i1<hits.length && i1<lastRow; i1++){
					SearchResult res = new SearchResult();
					doc = indexSearcher.doc(hits[i1].doc);
					if(doc.get("BookNo")==null || doc.get("BookNo").equalsIgnoreCase(""))
						continue;
					res.setBookNo(doc.get("BookNo"));
					
					if(doc.get("Title")==null || doc.get("Title").equalsIgnoreCase(""))
						res.setTitle("暂时无法获得书名信息");
					else
						res.setTitle(doc.get("Title"));
					
					if(doc.get("Creator")==null || doc.get("Creator").equalsIgnoreCase(""))
						res.setCreator("暂时无法获得作者信息");
					else
						res.setCreator(doc.get("Creator"));
					
					//
					if(doc.get("Press") == null || doc.get("Press").equalsIgnoreCase("")) {
						res.setPress("暂时无法获得出版社信息");
					}else{
						res.setPress(doc.get("Press"));
					}
					
					if(doc.get("QueryWord") == null || doc.get("QueryWord").equalsIgnoreCase("")) {
						res.setQueryWord("暂时无法获取查询词信息");
					}else{
						res.setQueryWord(doc.get("QueryWord"));
					}
					
					if(doc.get("Matrix") == null || doc.get("Matrix").equalsIgnoreCase("")){
						res.setMatrix("暂时无法获得标号信息");
					}else{
						res.setMatrix(doc.get("Matrix"));
					}
					
					if(doc.get("Times") == null || doc.get("Times").equalsIgnoreCase("")) {
						res.setTimes("暂时无法获得次数信息");
					}else{
						res.setTimes(doc.get("Times"));
					}
					
					//
					
					searchResult.add(res);
					
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		//
		
		
		SearchResult res2 = new SearchResult();
		for(int i = 0; i < this.searchResult.size(); ++i) {
			res2 = (SearchResult) this.searchResult.get(i);
			System.out.println(res2.getTitle());
		}
		
		//

		/**
		 * MY
		 */

		return searchResult;
		
	}
	
	/**
	 * GetQuery Function
	 */
	protected Query getQuery()
	{
		String[] strs = queryword.split("AND");
		ArrayList words = new ArrayList();
		for(int i=0; i<strs.length; i++)
			if(!strs[i].trim().equalsIgnoreCase(""))
			{
				words.add(strs[i].trim() + " " + TStransformer.STtransform(strs[i].trim()));
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
			query = MultiFieldQueryParser.parse(Version.LUCENE_35, queryStr, fields, flags, analyzer);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return query;
	}
	
	/**
	 * Main Function
	 */
	public static void main(String[] args) {
		Search search = new Search();
		
		List listResult = new ArrayList();
		
		search.queryword = "red";
		
		listResult = search.search();
	}
	
	protected void makePageNo() {
	}

	public int getPanc() {
		return panc;
	}

	public void setPanc(int panc) {
		this.panc = panc;
	}

	public int getPdis() {
		return pdis;
	}

	public void setPdis(int pdis) {
		this.pdis = pdis;
	}

	public int getPfig() {
		return pfig;
	}

	public void setPfig(int pfig) {
		this.pfig = pfig;
	}

	public int getPjou() {
		return pjou;
	}

	public void setPjou(int pjou) {
		this.pjou = pjou;
	}

	public int getPmin() {
		return pmin;
	}

	public void setPmin(int pmin) {
		this.pmin = pmin;
	}

	public int getPmod() {
		return pmod;
	}

	public void setPmod(int pmod) {
		this.pmod = pmod;
	}

	public int getPpai() {
		return ppai;
	}

	public void setPpai(int ppai) {
		this.ppai = ppai;
	}

	public int getPvid() {
		return pvid;
	}

	public void setPvid(int pvid) {
		this.pvid = pvid;
	}

	public int getPageNo() {
		return pageNo;
	}
}