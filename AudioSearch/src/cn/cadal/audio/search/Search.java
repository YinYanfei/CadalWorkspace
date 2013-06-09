package cn.cadal.audio.search;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cn.cadal.audio.entity.SearchResult;

public class Search {
	private String indexFile = "D:/quicksearch/index/audio";
	private List<SearchResult> searchResultList = null;
	private final String[] fields = {"Title","Creator","Subject","Publisher"};
	private final BooleanClause.Occur[] flags = {BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD, 
			BooleanClause.Occur.SHOULD, BooleanClause.Occur.SHOULD};
	private final Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_35);
	
	/**
	 * Constructer
	 */
	public Search() {
		this.searchResultList = new ArrayList<SearchResult>();
	}
	
	/**
	 * Search Function
	 */
	@SuppressWarnings("deprecation")
	public boolean Searcher(String queryWord) {
		try{
			this.searchResultList.clear();
			File file = new File(this.indexFile);
			FSDirectory fsd = FSDirectory.open(file);
			IndexSearcher indexSearcher = new IndexSearcher(fsd);			
			Query query = MultiFieldQueryParser.parse(Version.LUCENE_35, queryWord, fields, flags, analyzer);
			ScoreDoc[] scoreDoc = indexSearcher.search(query, 1000).scoreDocs;
			
			// deal result
			SearchResult sr;
			
			for(int i = 0; i < scoreDoc.length; ++i) {
				sr = new SearchResult();
				
				if(indexSearcher.doc(scoreDoc[i].doc).get("AutioNO") != null) {
					sr.setAutioNO(indexSearcher.doc(scoreDoc[i].doc).get("AutioNO"));
				}
				if(indexSearcher.doc(scoreDoc[i].doc).get("Title") != null) {
					sr.setTitle(indexSearcher.doc(scoreDoc[i].doc).get("Title"));
				}
				if(indexSearcher.doc(scoreDoc[i].doc).get("Creator") != null) {
					sr.setCreator(indexSearcher.doc(scoreDoc[i].doc).get("Creator"));
				}
				if(indexSearcher.doc(scoreDoc[i].doc).get("Subject") != null) {
					sr.setSubject(indexSearcher.doc(scoreDoc[i].doc).get("Subject"));
				}
				if(indexSearcher.doc(scoreDoc[i].doc).get("Publisher") != null) {
					sr.setPublisher(indexSearcher.doc(scoreDoc[i].doc).get("Publisher"));
				}
				
				this.searchResultList.add(sr);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * print result
	 */
	public void printResult() {
		
		System.out.println("===========" + this.searchResultList.size() + "============");
		
		for(int i = 0; i < this.searchResultList.size(); ++i) {
			System.out.println("========-----------========");
			System.out.println(this.searchResultList.get(i).getAutioNO());
			System.out.println(this.searchResultList.get(i).getTitle());
			System.out.println(this.searchResultList.get(i).getCreator());
			System.out.println(this.searchResultList.get(i).getSubject());
			System.out.println(this.searchResultList.get(i).getPublisher());
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Search search = new Search();
		
		search.Searcher("静雅思听");
		
		search.printResult();
	}

	/**
	 * get and set functions
	 */
	public String getIndexFile() {
		return indexFile;
	}

	public void setIndexFile(String indexFile) {
		this.indexFile = indexFile;
	}

	@SuppressWarnings("unchecked")
	public List getSearchResultList() {
		return searchResultList;
	}

	@SuppressWarnings("unchecked")
	public void setSearchResultList(List searchResultList) {
		this.searchResultList = searchResultList;
	}

}
