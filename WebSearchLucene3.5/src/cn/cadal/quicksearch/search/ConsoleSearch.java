package cn.cadal.quicksearch.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.util.Version;

// import cn.cadal.quicksearch.analysis.ChineseAnalyzer;
import cn.cadal.util.TStransformer;

public class ConsoleSearch {

	private final String[] types = {"ancient", "dissertation", "english", "journal", "minguo", "modern"};
	private final String[] fields = {"QueryWord"}; //
	private final BooleanClause.Occur[] flags = {BooleanClause.Occur.SHOULD};//
	private final Analyzer analyzer = new CJKAnalyzer (Version.LUCENE_35);
	
	//
	private static int LARGE = 3;
	private List<SearchResult> resultSearch;
	private HashMap<Integer, Integer> resultMap;
	private int totalTimes;
	private ArrayList<Integer> matrixArr;
	private int matricLineNum;
	private double[] topicArr;
	private int []topTopic;
	private String []topTopicName;
	private double []topTopicTmp;
	private int []topic;
	
	private QueryIndex QueryIndex;
	private IndexSearch IndexSearch;
	//
	
	/**
	 * Construct Function
	 */
	@SuppressWarnings("static-access")
	public ConsoleSearch() {
		this.resultSearch = new ArrayList<SearchResult>();
		this.resultMap = new HashMap<Integer, Integer>();
		this.totalTimes = 0;
		this.matrixArr = new ArrayList<Integer>();
		this.matricLineNum = 0;
		this.topicArr = new double[500];
		this.topic = new int[500];
		this.topTopic = new int[this.LARGE];
		this.topTopicName = new String[this.LARGE];
		this.topTopicTmp = new double[this.LARGE];
		
		this.QueryIndex = new QueryIndex();
		this.IndexSearch = new IndexSearch();
	}
	
	/**
	 * Search all file
	 * @param type
	 * @param qstr
	 * @return
	 * @throws IOException 
	 * @throws CorruptIndexException 
	 */
	public void search(String qstr) throws CorruptIndexException, IOException {
		this.resultMap.clear();
		this.resultSearch.clear();
		
		// Search in six different files
		for(int i = 0; i < types.length; ++i) {
			this.search(types[i], qstr);
		}
		System.out.println("----------" + this.totalTimes + "----------" + this.resultMap.size());
/*		for(Iterator iter = this.resultMap.entrySet().iterator(); iter.hasNext(); ){
			Map.Entry<Integer, Integer> element = (Map.Entry<Integer, Integer>)iter.next();
			Object intKey = element.getKey();
			Object intVal = element.getValue();
			System.out.println(intKey + "<-->" + intVal);
		}
*/		
		for(int i = 0; i < resultSearch.size(); ++i) {
			System.out.println(this.resultSearch.get(i).getTitle());
		}

	}
	
	@SuppressWarnings("deprecation")
	public ScoreDoc[] search(String type, String qstr)
	{
		File file = new File("C:/data/index/" + type);
		String queryStr = "(" + qstr + ") OR ("+TStransformer.STtransform(qstr)+")";
		
		ScoreDoc[] scoreDoc = null;
		
		String[] strTmp1;
		String[] strTmp2;

		try{
			/**
			 * MY
			 */
			FSDirectory fsd = FSDirectory.open(file);
			IndexSearcher indexSearcher = new IndexSearcher(fsd);			
			Query query = MultiFieldQueryParser.parse(Version.LUCENE_35, queryStr, fields, flags, analyzer);
			scoreDoc = indexSearcher.search(query, 1000).scoreDocs;
			SearchResult resultTmp;

			// HashMap
			for(int i = 0; i < scoreDoc.length; ++i) {
				resultTmp = new SearchResult();
				// 将结果放到searchResult中
				if(indexSearcher.doc(scoreDoc[i].doc).get("BookNo") != null) {
					resultTmp.setBookNo(indexSearcher.doc(scoreDoc[i].doc).get("BookNo"));
				}
				
				if(indexSearcher.doc(scoreDoc[i].doc).get("BookRank") != null) {
					resultTmp.setBookRank(Float.parseFloat(indexSearcher.doc(scoreDoc[i].doc).get("BookRank")));
				}
			
				if(indexSearcher.doc(scoreDoc[i].doc).get("Title") != null) {
					resultTmp.setTitle(indexSearcher.doc(scoreDoc[i].doc).get("Title"));
				}
				
				if(indexSearcher.doc(scoreDoc[i].doc).get("Creater") != null) {
					resultTmp.setCreator(indexSearcher.doc(scoreDoc[i].doc).get("Creator"));
				}
				
				if(indexSearcher.doc(scoreDoc[i].doc).get("Subject") != null) {
					resultTmp.setSubject(indexSearcher.doc(scoreDoc[i].doc).get("Subject"));
				}
				
				if(indexSearcher.doc(scoreDoc[i].doc).get("Press") != null) {
					resultTmp.setPress(indexSearcher.doc(scoreDoc[i].doc).get("Press"));
				}
				
				if(indexSearcher.doc(scoreDoc[i].doc).get("QueryWord") != null) {
					resultTmp.setQueryWord(indexSearcher.doc(scoreDoc[i].doc).get("QueryWord"));
				}
				
				if(indexSearcher.doc(scoreDoc[i].doc).get("Matrix") != null) {
					resultTmp.setMatrix(indexSearcher.doc(scoreDoc[i].doc).get("Matrix"));
				}
				
				if(indexSearcher.doc(scoreDoc[i].doc).get("Times") != null) {
					resultTmp.setTimes(indexSearcher.doc(scoreDoc[i].doc).get("Times"));
				}
				
				this.resultSearch.add(resultTmp);

				// 处理Map
				if(indexSearcher.doc(scoreDoc[i].doc).get("Matrix") != null 
				&& !indexSearcher.doc(scoreDoc[i].doc).get("Matrix").equalsIgnoreCase("")){
					strTmp1 = indexSearcher.doc(scoreDoc[i].doc).get("Matrix").split(",");
					strTmp2 = indexSearcher.doc(scoreDoc[i].doc).get("Times").split(",");
					
					for(int j = 0; j < strTmp1.length; ++j) {
						if(this.resultMap.containsKey(Integer.parseInt(strTmp1[j]))) {
							int value = this.resultMap.get(Integer.parseInt(strTmp1[j]));
							this.resultMap.remove(Integer.parseInt(strTmp1[j]));
							this.resultMap.put(Integer.parseInt(strTmp1[j]), Integer.parseInt(strTmp2[j]) + value);
						}else{
							this.resultMap.put(Integer.parseInt(strTmp1[j]), Integer.parseInt(strTmp2[j]));
						}
						this.totalTimes += Integer.parseInt(strTmp2[j]) ;
					}
				}
			}
			/**
			 * MY
			 */
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return scoreDoc;
	}
	
	/**
	 * Read file to get matrix
	 */
	public boolean readMatrix() {
		boolean signal = false;
		
		try {
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("C:/data/data/matrix.txt"),"UTF-8"));
			
			String strLine = "";
			String []strArr;
			while((strLine = input.readLine()) != null){
				strArr = strLine.split(" ");
				for(int i = 0; i < strArr.length; ++i){
					this.matrixArr.add(Integer.parseInt(strArr[i]));
				}
				++this.matricLineNum;
			}
			
			signal = true;
		} catch (Exception e) {
			signal = false;
			e.printStackTrace();
		} 
		
		return signal;
	}
	
	/**
	 * Read file to get topic
	 */
	public boolean topicGet(){
		boolean signal = false;
		
		try{
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("C:/data/data/topic.txt"),"UTF-8"));
			
			String strLine = "";
			int num = 0;
			while((strLine = input.readLine()) != null) {
				if(strLine.equalsIgnoreCase("0")){
					this.topic[num++] = 0;
				}else{
					this.topic[num++] = 1;
				}
			}
			
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Deal topicArr and matrixArr
	 */
	public boolean dealMatrix() {
		boolean signal = false;
		double d;
		try{
			for(int i = 0; i < this.matricLineNum; ++i) {
				if(this.resultMap.containsKey(i)){
					double dTmp = 1.0;
					for(int j = 0; j < 3; ++j) {
						d = (double)this.resultMap.get(i) / (double)this.totalTimes;
						this.topicArr[this.matrixArr.get(i * 20 + j) - 1] += d * dTmp;
						dTmp -= 0.2;
					}
				}else{
					continue;
				}
			}
			
			for(int i = 0; i < 500; ++i) {
				if(0 == this.topic[i])
					this.topicArr[i] = 0.0;
			}
						
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Get top topic
	 */
	@SuppressWarnings("static-access")
	public void topTopicGet() {
		for(int i = 0; i < this.LARGE; ++i) {
			this.topTopicTmp[i] = this.topicArr[i];
			this.topTopic[i] = i;
		}
		
		for(int j = this.LARGE; j < 500; ++j) {
			for(int t = 0; t < this.LARGE; ++t) {
				if(this.topicArr[j] > this.topTopicTmp[t]){
					this.topTopic[t] = j;
					this.topTopicTmp[t] = this.topicArr[j];
					break;
				}
			}
		}
		
		this.QueryIndex.readFileIndex();
		this.IndexSearch.readFileArrTopic();
		
		for(int i = 0; i < this.LARGE; ++i) {
			this.topTopicName[i] = this.IndexSearch.getArrTopic().get(this.topTopic[i]);
		}
		
		//
		this.IndexSearch.readFileArrTopic();
		this.IndexSearch.readFileArrQueryIndex();
		//
		
		System.out.println("+++++++++++++++++++++++++++++");
	}
	
	/**
	 * get QueryIndex and QueryTopic
	 */
	public void queryGet(){
		
		// get from file
		this.QueryIndex.readFileTopic();
		this.QueryIndex.readFileIndex();
		
		System.out.println(this.topTopic.length);
		
		//

		for(int i = 0; i < this.topTopic.length; ++i) {
			this.QueryIndex.queryTopicGet(this.topTopic[i]);
			this.QueryIndex.queryIndexGet();
		}
		
		//
	}
	
	// Main Function
	public static void main(String[] args) {
		try{
			ConsoleSearch csearch = new ConsoleSearch();
			long startTime = new Date().getTime();
			
			csearch.readMatrix();		// matrix
			
			csearch.topicGet();			// topic
			
			csearch.search("春秋");		// resultMap
			System.out.println("------------------");
			csearch.dealMatrix();		// topicArr
			
			csearch.topTopicGet();		// topTopic
			
			csearch.queryGet();			// query
			
			System.out.println("------End------");
			long endTime = new Date().getTime();
			System.out.println("Time costs: " + (endTime - startTime) + "ms");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	/**
	 * Get and Set Functions
	 */
	public List<SearchResult> getResultSearch() {
		return resultSearch;
	}

	public void setResultSearch(ArrayList<SearchResult> resultSearch) {
		this.resultSearch = resultSearch;
	}

	public int getTotalTimes() {
		return totalTimes;
	}

	public void setTotalTimes(int totalTimes) {
		this.totalTimes = totalTimes;
	}

	public ArrayList<Integer> getMatrixArr() {
		return matrixArr;
	}

	public void setMatrixArr(ArrayList<Integer> matrixArr) {
		this.matrixArr = matrixArr;
	}

	public String[] getTypes() {
		return types;
	}

	public String[] getFields() {
		return fields;
	}

	public BooleanClause.Occur[] getFlags() {
		return flags;
	}

	public Analyzer getAnalyzer() {
		return analyzer;
	}

	public void setResultMap(HashMap<Integer, Integer> resultMap) {
		this.resultMap = resultMap;
	}

	public HashMap<Integer, Integer> getResultMap() {
		return resultMap;
	}

	public int getMatricLineNum() {
		return matricLineNum;
	}

	public void setMatricLineNum(int matricLineNum) {
		this.matricLineNum = matricLineNum;
	}

	public double[] getTopicArr() {
		return topicArr;
	}

	public void setTopicArr(double[] topicArr) {
		this.topicArr = topicArr;
	}

	public void setTopTopic(int [] topTopic) {
		this.topTopic = topTopic;
	}

	public int [] getTopTopic() {
		return topTopic;
	}

	public void setTopTopicTmp(double [] topTopicTmp) {
		this.topTopicTmp = topTopicTmp;
	}

	public double [] getTopTopicTmp() {
		return topTopicTmp;
	}

	public void setTopic(int [] topic) {
		this.topic = topic;
	}

	public int [] getTopic() {
		return topic;
	}

	public void setIndexSearch(IndexSearch indexSearch) {
		IndexSearch = indexSearch;
	}

	public IndexSearch getIndexSearch() {
		return IndexSearch;
	}

	public void setTopTopicName(String [] topTopicName) {
		this.topTopicName = topTopicName;
	}

	public String [] getTopTopicName() {
		return topTopicName;
	}

}
