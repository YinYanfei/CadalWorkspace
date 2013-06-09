package cn.cadal.quicksearch.index;


import java.io.*;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NRTCachingDirectory;
import org.apache.lucene.util.Version;

import cn.cadal.quicksearch.QuickSearchConfig;
// import cn.cadal.quicksearch.analysis.ChineseAnalyzer;

public class IndexBuilder {

	public IndexBuilder(){
	}
	
	private final String[] types = {"ancient", "dissertation", "english", "journal", "minguo", "modern"};
	
	//private final String[] types = {"journal"};
	
	private Analyzer analyzer = new CJKAnalyzer (Version.LUCENE_35);
	private BookRankClient bookRankClient = new BookRankClient();
	
	public void build(){
		
		for(int i=0; i<types.length; i++)
		{
			try{
				File f = new File("E:/index/" + types[i]);
//				File f = new File(QuickSearchConfig.getIndexDir() + "/" + types[i]);
				if(f.exists())
					f.delete();
				f.mkdir();				
				
				/**
				 * MY
				 */
 				Directory FSDir = FSDirectory.open(f);
 				IndexWriterConfig conf = new IndexWriterConfig(Version.LUCENE_35, analyzer); // new StandardAnalyzer(Version.LUCENE_35)
				IndexWriter writer = new IndexWriter(FSDir, conf);
				
				parse(writer, types[i]);
				
				writer.commit();
				writer.close();
				
				System.out.println(types[i] + "\t\t\tbuild completely.");
				
				/**
				 * MY
				 */
				/*
				IndexWriter indexWriter = new IndexWriter(FSDirectory.getDirectory(f.getPath()), analyzer, true);
				parse(indexWriter, types[i]);
				indexWriter.optimize();
				indexWriter.close();
				System.out.println(types[i] + "\t\t\tbuild completely.");
				*/
			}catch(IOException ex){
				ex.printStackTrace();
			}
		}
	}
	
	private void parse(IndexWriter indexWriter, String type)
	{
		int count = 0;
		try{
			File f = new File("E:/data/" + type + ".txt");
//			File f = new File(QuickSearchConfig.getTxtDir() + "/" + type + ".txt");
			if(!f.exists())
				return;
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("E:/data/" + type + ".txt"),"UTF-8") );
			
//			BufferedReader input = new BufferedReader(new FileReader(f));
			String line = null;
			while((line = input.readLine()) != null)
			{
				
				// System.out.println(line);
				
				String[] strs = line.split("####");
				Document doc = new Document();
				Field fd = null;
				
				if(strs[0]!=null && !strs[0].trim().equalsIgnoreCase(""))
				{
					/**
					 * MY
					 */
					fd = new Field("BookNo", strs[0].trim(), Field.Store.YES, Field.Index.NO);
					doc.add(fd);
					fd = new Field("BookRank", bookRankClient.getBookRank(strs[0].trim()), Field.Store.YES, Field.Index.NO);
					doc.add(fd);
					/**
					 * MY
					 */
					/*
					fd = new Field("BookNo", strs[0].trim(), Field.Store.COMPRESS, Field.Index.NO);
					doc.add(fd);
					fd = new Field("BookRank", bookRankClient.getBookRank(strs[0].trim()), Field.Store.COMPRESS, Field.Index.NO);
					doc.add(fd);
					*/
					//System.out.println(strs[0].trim() + " " + bookRankClient.getBookRank(strs[0].trim()));
				}
				
				if(strs[1]!=null && !strs[1].trim().equalsIgnoreCase(""))
				{
					/**
					 * MY
					 */
					fd = new Field("Title", strs[1].trim(), Field.Store.YES, Field.Index.ANALYZED);
					/**
					 * MY
					 */
					/*
					fd = new Field("Title", strs[1].trim(), Field.Store.COMPRESS, Field.Index.TOKENIZED);
					*/
					fd.setBoost(2.4f);
					doc.add(fd);
				}
				
				if(strs[2]!=null && !strs[2].trim().equalsIgnoreCase(""))
				{	
					/**
					 * MY
					 */
					fd = new Field("Creator", strs[2].trim(), Field.Store.YES, Field.Index.ANALYZED);
					/**
					 * MY
					 */
					/*
					fd = new Field("Creator", strs[2].trim(), Field.Store.COMPRESS, Field.Index.TOKENIZED);
					*/
					fd.setBoost(1.2f);
					doc.add(fd);
				}
				
				if(strs[3]!=null && !strs[3].trim().equalsIgnoreCase(""))
				{
					/**
					 * MY
					 */
					fd = new Field("Subject", strs[3].trim(), Field.Store.YES, Field.Index.ANALYZED);
					/**
					 * MY
					 */
					/*
					fd = new Field("Subject", strs[3].trim(), Field.Store.COMPRESS, Field.Index.TOKENIZED);
					*/
					fd.setBoost(0.8f);
					doc.add(fd);
				}
				
				/**
				 * MY
				 */
				if(strs[4] != null && !strs[4].trim().equalsIgnoreCase("")) {
					fd = new Field("Press", strs[4].trim(), Field.Store.YES, Field.Index.NO);
					fd.setBoost(1.0f);
					doc.add(fd);
				}
				if(strs[5] != null && !strs[5].trim().equalsIgnoreCase("")) {
					fd = new Field("QueryWord", strs[5].trim(), Field.Store.YES, Field.Index.ANALYZED);
					fd.setBoost(1.0f);
					doc.add(fd);
				}
				if(strs[6] != null && !strs[6].trim().equalsIgnoreCase("")) {
					fd = new Field("Matrix", strs[6].trim(), Field.Store.YES, Field.Index.NO);
					fd.setBoost(1.0f);
					doc.add(fd);
				}
				if(strs[7] != null && !strs[7].trim().equalsIgnoreCase("")) {
					fd = new Field("Times", strs[7].trim(), Field.Store.YES, Field.Index.NO);
					fd.setBoost(1.0f);
					doc.add(fd);
				}
				/**
				 * MY
				 */
				
				float fRank = 0.0f;
				try
				{
					fRank = Float.valueOf(bookRankClient.getBookRank(strs[0].trim()));
				}
				catch(Exception exf)
				{
					exf.printStackTrace();
				}
				doc.setBoost(fRank);
				indexWriter.addDocument(doc);
				count++;
			}

			input.close();

		}catch(IOException ex){
			ex.printStackTrace();
		}
		System.out.println(type + "\t\t\t: " + count);
	}

	public static void main (String[] args){
		
		long startTime = new Date().getTime();
		
		IndexBuilder iBuilder = new IndexBuilder();
		iBuilder.build();
		
		long endTime = new Date().getTime();
		System.out.println("Time costs: " + (endTime - startTime)/1000 + "s");
	}
}