package cn.cadal.quicksearch.search;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.RAMDirectory;

import cn.cadal.quicksearch.QuickSearchConfig;
import cn.cadal.quicksearch.analysis.ChineseAnalyzer;
import cn.cadal.user.CheckCookie;
import cn.cadal.util.TStransformer;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.StackTraceUtil;

public class SearchAPI extends ActionSupport  implements SessionAware {

	protected String queryword;

	private List searchResult;
	
	protected final String[] fields = {"Title", "Creator", "Subject"};
	
	protected final BooleanClause.Occur[] flags = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};

	protected int pdis = 1;

	protected int pmod = 1;

	protected int pmin = 1;

	protected int panc = 1;

	protected int pjou = 1;

	protected int ppai = 1;

	protected int pvid = 1;

	protected int pfig = 1;
	
	protected int startNo = 0;
	
	protected int resultNumber = 0;
	
	protected int pageNo=1;
	
	private int pageNum;

	protected int recordNum = 0;

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
//				String path = QuickSearchConfig.getIndexDir() + "/" +types[i];
				String path  = "D:/quicksearch/index" + "/" + types[i];
				RAMDirectory ramd = new RAMDirectory(path);
				dMap.put(types[i], ramd);
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

	/*
	public void setCurrentParams (String currentParams) {
		this.currentParams = currentParams;
	}
	*/

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
	/*
	public final void setPageNo(final int argPageNo) {
		this.pageNo = argPageNo;
	}
	*/

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
		searchResult = search();
		return SUCCESS;
	}

	protected List search()
	{
		int recNumPerPage = 20;
		makePageNo();
		Query query = getQuery();
		if(query==null)
			return new ArrayList();
		Hits hits = null;
		try{
			RAMDirectory ramd = dMap.get(type);
			IndexSearcher indexSearcher = new IndexSearcher(ramd);
			hits = indexSearcher.search(query);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		/*
		List searchResultAll = new ArrayList();
		try
		{
			for(int i=0; i<hits.length(); i++)
			{
				SearchResult res = new SearchResult();
				if(hits.doc(i).get("BookNo")==null || hits.doc(i).get("BookNo").equalsIgnoreCase(""))
					continue;
				res.setBookNo(hits.doc(i).get("BookNo"));
				
				if(hits.doc(i).get("Title")==null || hits.doc(i).get("Title").equalsIgnoreCase(""))
					res.setTitle("暂时无法获的书名信息");
				else
					res.setTitle(hits.doc(i).get("Title"));
				
				if(hits.doc(i).get("Creator")==null || hits.doc(i).get("Creator").equalsIgnoreCase(""))
					res.setCreator("暂时无法获的作者信息");
				else
					res.setCreator(hits.doc(i).get("Creator"));
				
				String strRank = hits.doc(i).get("BookRank");
				float fRank = 0.0f;
				try
				{
					fRank = Float.valueOf(strRank);
				}
				catch(Exception exf)
				{
					exf.printStackTrace();
				}
				res.setBookRank(hits.score(i)+fRank);
				searchResultAll.add(res);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		SearchResultComparator comp = new SearchResultComparator();
		Collections.sort(searchResultAll,comp);
		*/
		
		recordNum = hits.length();
		pageNum = (recordNum - 1) / recNumPerPage;
		pageNum++;

		int firstRow = startNo; //(pageNo - 1) * recNumPerPage;
		int lastRow = startNo + resultNumber; //firstRow + recNumPerPage;
		lastRow = lastRow>recordNum?recordNum:lastRow;
		
		searchResult = new ArrayList();
		
		try
		{
			for(int i=firstRow; i<hits.length() && i<lastRow; i++)
			//for(int i=firstRow; i<searchResultAll.size() && i<lastRow; i++)
			{
				SearchResult res = new SearchResult();
				if(hits.doc(i).get("BookNo")==null || hits.doc(i).get("BookNo").equalsIgnoreCase(""))
					continue;
				res.setBookNo(hits.doc(i).get("BookNo"));
				
				if(hits.doc(i).get("Title")==null || hits.doc(i).get("Title").equalsIgnoreCase(""))
					res.setTitle("暂时无法获得书名信息");
				else
					res.setTitle(hits.doc(i).get("Title"));
				
				if(hits.doc(i).get("Creator")==null || hits.doc(i).get("Creator").equalsIgnoreCase(""))
					res.setCreator("暂时无法获得作者信息");
				else
					res.setCreator(hits.doc(i).get("Creator"));
				
				searchResult.add(res);
				
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return searchResult;
		
	}
	protected Query getQuery()
	{
		String[] strs = queryword.split("AND");
		ArrayList words = new ArrayList();
		for(int i=0; i<strs.length; i++)
			if(!strs[i].trim().equalsIgnoreCase(""))
			{
				//LOG.warn("re search:" + strs[i].trim());	
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
			query = MultiFieldQueryParser.parse(queryStr, fields, flags, analyzer);
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return query;
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

	public int getResultNumber() {
		return resultNumber;
	}

	public void setResultNumber(int resultNumber) {
		this.resultNumber = resultNumber;
	}

	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRecordNum() {
		return recordNum;
	}

}