package cn.cadal.catalogsearch.indexBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class IndexBuilder {
	
	private static String catalogDir = "C:/cadal.indices";
	private static Directory indexDir = null;
	private static Analyzer analyzer = null;
	
	public IndexBuilder() {
		try{
			this.indexDir = FSDirectory.open(new File(this.catalogDir));
			this.analyzer = new ChineseAnalyzer();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		IndexWriter indexWriter = new IndexWriter(indexDir, analyzer, true, null);
		File catalogRoot = new File("C:\\catalog_txt");
		for (File catalog : catalogRoot.listFiles()) {
			System.out.println(catalog.getName());
			LineNumberReader reader = new LineNumberReader(new InputStreamReader(new FileInputStream(catalog), "utf-8"));
			String ln = reader.readLine();
			while (ln != null) {
				//if (ln.length() < 18)
				//	System.out.println(ln);
				try {
					int sp1;
					sp1 = ln.indexOf(' '); 
					String bookNo = ln.substring(0, sp1);
					int sp2 = ln.indexOf(' ', sp1 + 1);
					String fileId = ln.substring(sp1 + 1, sp2);
					String label = ln.substring(sp2 + 1);
					Document doc = new Document();
					doc.add(new Field("Chapter", label, Field.Store.YES, Field.Index.ANALYZED));
					doc.add(new Field("BookNo", bookNo, Field.Store.YES, Field.Index.NO));
					doc.add(new Field("FileID", fileId, Field.Store.YES, Field.Index.NO));
					indexWriter.addDocument(doc);
				}
				catch (Exception e) {
					e.printStackTrace();
					System.out.println(reader.getLineNumber());
					System.out.println(ln);
				}
				finally {
					ln = reader.readLine();
				}
			}
		}
		System.out.println("Optimizing...");
		indexWriter.optimize();
		indexWriter.close();
	}
}
