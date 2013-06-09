package cn.cadal.audio.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class BuildIndex {
	private String metadata = "";
	private String indexfile = "";
	private Analyzer analyzer = new CJKAnalyzer(Version.LUCENE_35);
	
	/**
	 * Constructer
	 */
	public BuildIndex() {
		this.metadata = "E:/audio/data/audio-20120806.txt";
		this.indexfile = "E:/audio/index";
	}
	
	/**
	 * Index builder
	 */
	public boolean IndexBuilder() {
		try{
			File file = new File(this.indexfile);
			Directory directory = FSDirectory.open(file);
			IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35, analyzer);
			IndexWriter indexWriter = new IndexWriter(directory, conf);
			
			// deal with file
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.metadata)));
			String line = null;
			
			while((line  = reader.readLine()) != null) {
				String[] strArr = line.split("####");
				Document doc = new Document();
				Field field = null;
				
				// field 1
				if(strArr[0] != null && !strArr[0].trim().equalsIgnoreCase("")) {
					field = new Field("AutioNO", strArr[0].trim(), Field.Store.YES, Field.Index.NO);
					doc.add(field);
				}
				// field 2
				if(strArr[1] != null && !strArr[1].trim().equalsIgnoreCase("")) {
					field = new Field("Title", strArr[1].trim(), Field.Store.YES, Field.Index.ANALYZED);
					field.setBoost(2.4f);
					doc.add(field);
				}
				// field 3
				if(strArr[2] != null && !strArr[2].trim().equalsIgnoreCase("")) {
					field = new Field("Creator", strArr[2].trim(), Field.Store.YES, Field.Index.ANALYZED);
					field.setBoost(1.5f);
					doc.add(field);
				}
				// field 4
				if(strArr[3] != null && !strArr[3].trim().equalsIgnoreCase("")) {
					field = new Field("Subject", strArr[3].trim(), Field.Store.YES, Field.Index.ANALYZED);
					field.setBoost(1.5f);
					doc.add(field);
				}
				// field 5
				if(strArr[4] != null && !strArr[4].trim().equalsIgnoreCase("")) {
					field = new Field("Publisher", strArr[4].trim(), Field.Store.YES, Field.Index.ANALYZED);
					field.setBoost(1.0f);
					doc.add(field);
				}
				
				indexWriter.addDocument(doc);
			}
			reader.close();
			
			indexWriter.commit();
			indexWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BuildIndex bi = new BuildIndex();
		boolean bool = bi.IndexBuilder();
		
		if(bool){
			System.out.println("Completed! Congritulations!!");
		}else{
			System.out.println("Sorry! Failed!!");
		}
	}

	/**
	 * Get and Set functions
	 * @return
	 */
	public String getMetadata() {
		return metadata;
	}

	public void setMetadata(String metadata) {
		this.metadata = metadata;
	}

	public String getIndexfile() {
		return indexfile;
	}

	public void setIndexfile(String indexfile) {
		this.indexfile = indexfile;
	}

}
