package cn.paoding.ana;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Date;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class MainTest {
    public static void testIndex() throws Exception{
		// Files will be indexed.
		File fileDir = new File("D:\\luceneweb\\docs");
		
		// Files will store the index of 'fileDir'.
		File indexDir = new File("D:\\luceneweb\\index");

		// Create Index writer object
		Analyzer paodingAnalyzer = new PaodingAnalyzer();

		Directory FSDir = FSDirectory.open(indexDir);
		IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35, paodingAnalyzer); // new StandardAnalyzer(Version.LUCENE_35)
		IndexWriter indexWriter = new IndexWriter(FSDir, conf);
		
		File[] textFiles = fileDir.listFiles();
		long startTime = new Date().getTime();
		
        try {
            System.out.println("Indexing to directory '" + indexDir.getName() + "'...");
            
    		// Add document to index
    		for(int i = 0; i < textFiles.length; i++){
    			if(textFiles[i].isFile() && textFiles[i].getName().endsWith(".txt")){
    				System.out.println("File " + textFiles[i].getCanonicalPath().substring(18) + " 正在被索引...");
    				String tmp = FileReaderAll(textFiles[i].getCanonicalPath(), "GBK");
    		
    				Document document = new Document();
    				System.out.println("--" + (i + 1) + "-- " + textFiles[i].getPath().substring(18));
    				Field FieldPath = new Field("path", textFiles[i].getPath(), 
    											Field.Store.YES, Field.Index.NO);
    				
    				// System.out.println("--" + (i + 1) + "-- " + tmp);
    				Field FieldBody = new Field("body", tmp, Field.Store.YES, 
    											Field.Index.ANALYZED, Field.TermVector.WITH_POSITIONS_OFFSETS);
    				
    				// Construct document object
    				document.add(FieldPath);
    				document.add(FieldBody);
    				
    				// Add into the index dir
    				indexWriter.addDocument(document);
    			} // if		
    		} // for

    		// Optimize of the index
    		indexWriter.optimize();
    		indexWriter.close();
    		
    		// Test the time
    		long endTime = new Date().getTime();
    		System.out.println("这花费了 " + (endTime - startTime) 
    							+ " 毫秒来把文件增加到索引中去! " + fileDir.getPath());    		
        }catch(Exception e) {
        	e.printStackTrace();
        }
    }
    
    public static void testSearch() throws Exception{
    	File file = new File("D:\\luceneweb\\index");
    	String queryStr = "中华人民共和国";
    	ScoreDoc[] scoreDoc = null;
    	
    	Analyzer paodingAnalyzer = new PaodingAnalyzer();
		FSDirectory fsd = FSDirectory.open(file);
		IndexSearcher indexSearcher = new IndexSearcher(fsd);

		try {
			// "body" is as same as the index created in 'TestFileIndex.java' 
			// QueryParser queryParser = new QueryParser(Version.LUCENE_35, queryStr, paodingAnalyzer);
			// Query query = queryParser.parse("");
			
			
			Term term = new Term("body", "中华");
			Query query= new TermQuery(term);
			
			
			TopDocs docs = indexSearcher.search(query, 1000);
			
			System.out.println("Searching " + query.toString() + " ...");
			System.out.println("===========" + docs.totalHits);
			
			scoreDoc = docs.scoreDocs;
			
			for(int i = 0; i < docs.totalHits; ++i){
				System.out.println("-----" + i);
				
				System.out.println(indexSearcher.doc(scoreDoc[i].doc).get("path"));
				System.out.println(indexSearcher.doc(scoreDoc[i].doc).get("body"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
	// Read a file and return the content of the file(type -- String).
	private static String FileReaderAll(String FileName, String charset) throws Exception{
		BufferedReader reader = new BufferedReader(
								new InputStreamReader(
								new FileInputStream(FileName), charset));
		
		String line = new String();
		String temp = new String();
		
		while((line = reader.readLine()) != null){
			temp += line;
		}
		
		reader.close();
		return temp;
	}// FileReaderAll Function

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //testIndex();
        testSearch();
    }
}
