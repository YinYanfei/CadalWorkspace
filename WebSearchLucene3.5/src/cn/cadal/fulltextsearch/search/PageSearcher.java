package cn.cadal.fulltextsearch.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cn.cadal.fulltextsearch.analysis.cjk.CJKAnalyzer;

public class PageSearcher {
	protected static Analyzer analyzer = new CJKAnalyzer();
	//search from all book
	public static ArrayList search(String indexPath,String queryString,String searchField,String sortField,boolean reversed)throws IOException,ParseException{
		/**
		 * MY
		 */
		File file = new File(indexPath);
		Directory dir = FSDirectory.open(file);
		IndexSearcher indexSearcher = new IndexSearcher(IndexReader.open(dir));
		// IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		Query query = new QueryParser(Version.LUCENE_35, searchField,analyzer).parse(queryString);
		Sort sort = new Sort(new SortField(sortField, SortField.STRING));
		ScoreDoc[] docs = indexSearcher.search(query, null, 100, sort).scoreDocs;
		ArrayList arrDoc = new ArrayList();
		Document doc = null;
		
		for(int i = 0; i < docs.length; ++i) {
			doc = indexSearcher.doc(docs[i].doc);
			arrDoc.add(doc);
		}
		//Sort sort = new Sort(new SortField(sortField,reversed));
		//Hits hits = indexSearcher.search(query,sort);
		
		/**
		 * MY
		 */
		return arrDoc;
	}
	public static Hits search(String indexPath,String[] queryStrings,String[]searchFields,String[] sortFields,boolean[]reversed)throws IOException,ParseException{
		Hits hits = null;
		IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		SortField fields[] = new SortField[sortFields.length];
		for(int i = 0;i<sortFields.length;i++){
			fields[i] = new SortField(sortFields[i],reversed[i]);
		}
		Sort sort = new Sort(fields);
		Query[] queries = new Query[queryStrings.length];
		for(int i = 0;i<searchFields.length;i++){
			queries[i] = new QueryParser(Version.LUCENE_35, searchFields[i],analyzer).parse(queryStrings[i]);
		}
		Query query =  Query.mergeBooleanQueries(queries);
		hits = indexSearcher.search(query,sort);
		return hits;
	}
	public static ArrayList search(String indexPath,String queryString,String sortField,boolean reversed)throws IOException,ParseException{
		String searchField = "Content";
		return search(indexPath,queryString,searchField,sortField,reversed);
	}
	public static Hits search(String indexPath,String queryString,String searchField)throws IOException,ParseException{
		IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		Query query = new QueryParser(Version.LUCENE_35, searchField,analyzer).parse(queryString);
		Hits hits = indexSearcher.search(query);
		return hits;
	}
	public static Hits search(String indexPath,String[] queryStrings,String[]searchFields)throws IOException,ParseException{
		Hits hits = null;
		IndexSearcher indexSearcher = new IndexSearcher(indexPath);
		Query[] queries = new Query[queryStrings.length];
		BooleanQuery query = new BooleanQuery();
		for(int i = 0;i<searchFields.length;i++){
			queries[i] = new QueryParser(Version.LUCENE_35, searchFields[i],analyzer).parse(queryStrings[i]);
			query.add(queries[i], Occur.MUST);
		}
		hits = indexSearcher.search(query);
		return hits;
	}
	public static Hits multiSearch(String indexPath,String queryString,String bookID)throws IOException,ParseException{
		if(queryString.equals("") && bookID.equals(""))
			return null;
		if(queryString.equals(""))
			return search(indexPath,bookID,"BookID");
		else if(bookID.equals(""))
			return search(indexPath,queryString,"Content");
		String []queryStrings = new String[]{queryString,bookID};
		String []searchFields = new String[]{"Content","BookNo"};
		return search(indexPath,queryStrings,searchFields);
	}
	
}
