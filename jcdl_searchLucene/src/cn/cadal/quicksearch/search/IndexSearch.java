package cn.cadal.quicksearch.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class IndexSearch {
	
	/**
	 * Variables
	 */
	private static int QUERYWORDNUM = 10;			// 设定每一个topic中要取出的queryword的个数
	private ArrayList<String> arrTopic; 			// --> data/topic.txt
	private ArrayList<String> arrQueryIndex;		// --> query/queryindex.txt
	private ArrayList<Integer> arrQueryTopic;		// --> topic/querytopic*.txt
	private ArrayList<String> arrQueryWord;			// --> arrQueryTopic and arrQueryIndex 
	private ArrayList<String> arrTimes;				// --> query/times.txt
	private ArrayList<String> arrQueryWordTimes;	// --> arrQueryTopic and arrTimes
	
	private TopicSearchResult[] listResult;			// 存放queryword
	
	public int countTmp = 0;
	
	/**
	 * Construct Function
	 */
	@SuppressWarnings("static-access")
	public IndexSearch(){
		this.setArrTopic(new ArrayList<String>());
		this.setArrQueryIndex(new ArrayList<String>());
		this.setArrQueryTopic(new ArrayList<Integer>());
		this.setArrQueryWord(new ArrayList<String>());
		this.setArrTimes(new ArrayList<String>());
		this.setArrQueryWordTimes(new ArrayList<String>());
		
		this.listResult = new TopicSearchResult[this.QUERYWORDNUM];
	}

	/**
	 * Read file --> this.arrTopic
	 */
	public boolean readFileArrTopic() {
		boolean signal = false;
		String str;
		//
		System.out.println("--------readFileArrTopic--------");
		//
		try{
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("E:/data/topic.txt"),"UTF-8"));
			
			while((str = input.readLine()) != null) {
				this.arrTopic.add(str);
			}
						
			input.close();
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Read file --> this.arrQueryIndex
	 */
	public boolean readFileArrQueryIndex() {
		boolean signal = false;
		String str;
		//
		System.out.println("--------readFileArrQueryIndex--------");
		//
		try{
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("E:/query/queryindex.txt"),"UTF-8"));
			
			while((str = input.readLine()) != null) {
				this.arrQueryIndex.add(str.substring(0, str.length() - 4));
			}
						
			input.close();
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Read file --> this.arrQueryTopic
	 */
	public boolean readFileArrQueryTopic(int num) {
		boolean signal = false;
		this.arrQueryTopic.clear();
		String str;
		//
		System.out.println("--------readFileArrQueryTopic--------");
		//
		try{
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("E:/topic/querytopic"+ num +".txt"),"UTF-8"));
			
			while((str = input.readLine()) != null) {
				this.arrQueryTopic.add(Integer.parseInt(str));
			}
			
			input.close();
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Read file --> this.arrTimes
	 */
	public boolean readFileArrTimes() {
		boolean signal = false;
		this.arrQueryWordTimes.clear();
		String str;
		//
		System.out.println("--------readFileArrTimes--------");
		//
		try{
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("E:/query/times.txt"),"UTF-8"));
			
			while((str = input.readLine()) != null) {
				this.arrTimes.add(str);
			}
			
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}
				
		return signal;
	}
	
	/**
	 * arrQueryTopic and arrQueryIndex to get this.arrQueryWord
	 */
	public boolean evaluateArrQueryWord() {
		boolean signal = false;
		this.arrQueryWord.clear();
		//
		System.out.println("--------evaluateArrQueryWord--------");
		//
		try{
			for(int i = 0; i < this.arrQueryTopic.size(); ++i) {
				this.arrQueryWord.add(this.arrQueryIndex.get(this.arrQueryTopic.get(i) - 1));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * arrTimes and arrQueryTopic to get this.arrQueryWordTimes
	 */
	public boolean evaluateArrQueryWordTimes() {
		boolean signal = false;
		this.arrQueryWordTimes.clear();
		
		//
		System.out.println("--------evaluateArrQueryWordTimes--------");
		//
		
		try{
			for(int i = 0; i < this.arrQueryTopic.size(); ++i) {
				this.arrQueryWordTimes.add(this.arrTimes.get(this.arrQueryTopic.get(i) - 1));
			}
			
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Create index
	 */
	@SuppressWarnings("deprecation")
	public boolean createIndex(int num) {
		boolean signal = false;
		Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_35);
		String path = "E:/topicIndex/querytopic" + num;
		//
		System.out.println("--------createIndex--------");
		//
		File file = new File(path);
		if(file.exists()){
			file.delete();
		}
		file.mkdir();
		
		try{
			Directory dir = FSDirectory.open(file);
			
			IndexWriter TextIndex = new IndexWriter(dir, analyzer, true, IndexWriter.MaxFieldLength.UNLIMITED);
			Document doc;
			
			for(int i = 0; i < this.arrQueryTopic.size(); ++i) {
				doc = new Document();
				doc.add(new Field("ID", this.arrQueryTopic.get(i).toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));		// 注意这里ID是String类型的
				doc.add(new Field("QueryWord", this.arrQueryWord.get(i), Field.Store.YES, Field.Index.ANALYZED));
				doc.add(new Field("Times", this.arrQueryWordTimes.get(i), Field.Store.YES, Field.Index.ANALYZED));
				TextIndex.addDocument(doc);
			}
			TextIndex.optimize();
			TextIndex.close();
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Search
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	public boolean search(int num, String str) {
		boolean signal = false;
		Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_35);
	
		String path = "E:/topicIndex/querytopic" + num;
		File file = new File(path);

		try{
			FSDirectory dir = FSDirectory.open(file);
			
			IndexSearcher indexSearcher = new IndexSearcher(dir);
			
			QueryParser parse = new QueryParser(Version.LUCENE_35, "QueryWord", analyzer);
			Query query = parse.parse(str);
			
			TopDocs topDocs = indexSearcher.search(query, this.QUERYWORDNUM);
			ScoreDoc[] docs = topDocs.scoreDocs;
			
			//
			for(int i = 0; i < docs.length; ++i) {
				System.out.println(indexSearcher.doc(docs[i].doc).get("ID") + "   " 
								 + indexSearcher.doc(docs[i].doc).get("QueryWord") + "   "
								 + indexSearcher.doc(docs[i].doc).get("Times"));
			}
			//
			
			indexSearcher.close();
			signal = true;
		}catch(Exception e) {
			e.printStackTrace();
			signal = false;
		}
		
		return signal;
	}
	
	/**
	 * Search -->根据给定的一个数字，从而获得对应的topic，再然后到相应的索引文件中查询，获取前10个结果
	 */
	@SuppressWarnings({ "deprecation", "static-access" })
	public boolean search(int num) {
		boolean signal = false;
		Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_35);
		
		String path = "E:/topicIndex/querytopic" + (num + 1);
		File file = new File(path);

		try{
			String topic = this.arrTopic.get(num);
			//
			System.out.println("----------" + topic + "--------" + num);
			//
			FSDirectory dir = FSDirectory.open(file);
			
			IndexSearcher indexSearcher = new IndexSearcher(dir);
			
			QueryParser parse = new QueryParser(Version.LUCENE_35, "QueryWord", analyzer);
			Query query = parse.parse(topic);
			
			TopDocs topDocs = indexSearcher.search(query, this.QUERYWORDNUM);
			ScoreDoc[] docs = topDocs.scoreDocs;

			System.out.println(docs.length);
			
			this.countTmp = docs.length;
			
			System.out.println(countTmp);
			
			// 将结果放到this.listResult中去
			for(int i = 0; i < this.countTmp; ++i) {
				TopicSearchResult resultTmp = new TopicSearchResult();
				
				resultTmp.setID(Integer.parseInt(indexSearcher.doc(docs[i].doc).get("ID")));
				resultTmp.setQueryWord(indexSearcher.doc(docs[i].doc).get("QueryWord"));
				resultTmp.setTimes(indexSearcher.doc(docs[i].doc).get("Times"));
				
				this.listResult[i] = resultTmp;
			}
			
			System.out.println("---------------" + this.listResult.length + "----------------------");
			
			signal = true;
		}catch(Exception e)  {
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		IndexSearch indexSearch = new IndexSearch();
		// this.readFileArrTopic
		indexSearch.readFileArrTopic();
		
		// this.readFileArrQueryIndex
		indexSearch.readFileArrQueryIndex();
		
		indexSearch.readFileArrTimes();
		/*
		int n = 0;
		
		for(int i = 0; i < indexSearch.arrTopic.size(); ++i) {
			if(!indexSearch.arrTopic.get(i).equalsIgnoreCase("0")){
				indexSearch.readFileArrQueryTopic(i + 1);
				indexSearch.evaluateArrQueryWord();
				indexSearch.evaluateArrQueryWordTimes();
				indexSearch.createIndex(i + 1);
				++n;
			}
		}
		System.out.println(n);
		*/
		/*
		// this.readFileArrQueryTopic
		indexSearch.readFileArrQueryTopic(18);
		
		// this.evaluateArrQueryWord
		indexSearch.evaluateArrQueryWord();
		
		// createIndex
		indexSearch.createIndex(18);
		*/
		
		// search
		indexSearch.search(18, "教育");
		
	}

	/**
	 * Set and Get Functions
	 */
	public void setArrTopic(ArrayList<String> arrTopic) {
		this.arrTopic = arrTopic;
	}

	public ArrayList<String> getArrTopic() {
		return arrTopic;
	}

	public void setArrQueryIndex(ArrayList<String> arrQueryIndex) {
		this.arrQueryIndex = arrQueryIndex;
	}

	public ArrayList<String> getArrQueryIndex() {
		return arrQueryIndex;
	}

	public void setArrQueryTopic(ArrayList<Integer> arrQueryTopic) {
		this.arrQueryTopic = arrQueryTopic;
	}

	public ArrayList<Integer> getArrQueryTopic() {
		return arrQueryTopic;
	}

	public void setArrQueryWord(ArrayList<String> arrQueryWord) {
		this.arrQueryWord = arrQueryWord;
	}

	public ArrayList<String> getArrQueryWord() {
		return arrQueryWord;
	}

	public void setQUERYWORDNUM(int qUERYWORDNUM) {
		QUERYWORDNUM = qUERYWORDNUM;
	}

	public int getQUERYWORDNUM() {
		return QUERYWORDNUM;
	}

	public void setListResult(TopicSearchResult[] listResult) {
		this.listResult = listResult;
	}

	public TopicSearchResult[] getListResult() {
		return listResult;
	}

	public ArrayList<String> getArrTimes() {
		return arrTimes;
	}

	public void setArrTimes(ArrayList<String> arrTimes) {
		this.arrTimes = arrTimes;
	}

	public ArrayList<String> getArrQueryWordTimes() {
		return arrQueryWordTimes;
	}

	public void setArrQueryWordTimes(ArrayList<String> arrQueryWordTimes) {
		this.arrQueryWordTimes = arrQueryWordTimes;
	}

}
