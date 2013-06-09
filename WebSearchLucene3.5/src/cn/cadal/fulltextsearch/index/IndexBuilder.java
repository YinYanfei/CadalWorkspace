package cn.cadal.fulltextsearch.index;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import cn.cadal.fulltextsearch.analysis.cjk.CJKAnalyzer;
import cn.cadal.fulltextsearch.content.IndexBuildType;


public class IndexBuilder implements Runnable{
		
	private static final Log LOG = LogFactory.getLog (IndexBuilder.class);
	
	
	public IndexBuilder(){
	}
	
	protected static Analyzer analyzer = new CJKAnalyzer();
	
	public void build(){
	
		File file = new File(IndexBuildConfig.bookDir);
		try{
			/**
			 * MY
			 */
			Directory FSDir = FSDirectory.open(file);
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35, analyzer);
			IndexWriter indexWriter = new IndexWriter(FSDir, conf);
			
			/**
			 * MY
			 */
			// IndexWriter indexWriter = new IndexWriter(IndexBuildConfig.indexDir, analyzer,true);
			DirectoryIndexer.parse(file,indexWriter,this);
			indexWriter.commit();
			// indexWriter.optimize();
			LOG.info("build completely.\r\n");
			indexWriter.close();
			
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public void append(){
		File file = new File(IndexBuildConfig.bookDir);
		IndexWriter indexWriter = null;
		try{
			/**
			 * MY
			 */
			Directory dir = FSDirectory.open(file);
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35, analyzer);
			indexWriter = new IndexWriter(dir, conf);
			/**
			 * MY
			 */
			// Directory dir = FSDirectory.getDirectory(IndexBuildConfig.indexDir, false);
			IndexWriter.unlock(dir);
			// IndexReader.unlock(dir);
			// indexWriter = new IndexWriter(IndexBuildConfig.indexDir, analyzer, false);
			DirectoryIndexer.parse(file,indexWriter,this);
			indexWriter.commit();
			// indexWriter.optimize();
			LOG.info("append completely.\r\n");
			indexWriter.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
	}

	public static int delete(String bookId,String indexPath){
		int deleteCount = 0;
		System.out.println("delelte index: bookId = " + bookId);
		try{
			File file = new File(indexPath);
			/**
			 * MY
			 */
			Directory directory = FSDirectory.open(file);
			/**
			 * MY
			 */
			
			// Directory directory = FSDirectory.getDirectory(file,false);
			Term term  = new Term("BookID",bookId);
			IndexReader reader = IndexReader.open(directory);
			
			/**
			 * MY
			 */
			IndexWriter.unlock(directory);
			/**
			 * MY
			 */
			// IndexReader.unlock(directory);
			deleteCount = reader.deleteDocuments(term);
			
			System.out.println("delete count: " + deleteCount);
			reader.close();
			directory.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return deleteCount;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		int buildType = IndexBuildType.New;
		switch(buildType){
			case IndexBuildType.New:
				build();
				break;
			case IndexBuildType.AppendWithDup:
				append();
				break;
		}
	}
	
	public static void main (String[] args){
		IndexBuilder iBuilder = new IndexBuilder();
		File segFile = new File (IndexBuildConfig.indexDir+"\\segments");
		if (segFile.exists()) {
			iBuilder.append();
		} else {
			iBuilder.build();
		}
	}

}
