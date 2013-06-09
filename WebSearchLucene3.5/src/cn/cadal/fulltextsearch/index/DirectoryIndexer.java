package cn.cadal.fulltextsearch.index;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import cn.cadal.entity.BookType;
import cn.cadal.entity.Cbook;

import common.utils.HibernateUtil;

public class DirectoryIndexer {
	private static final Log LOG = LogFactory.getLog(DirectoryIndexer.class);
	
	static String bookNo="";
	static String bookType="";
	static String title="";
	
	public static void parse(File dirFile,IndexWriter iw,IndexBuilder indexBuilder)throws IOException
	{	
		File[]file = dirFile.listFiles();
		for(int i= 0;i<file.length;i++){
			File f = file[i];
			if(f.isDirectory()){
				String fileName = f.getName();
				if(fileName.equalsIgnoreCase("ptiff")
						||fileName.equalsIgnoreCase("text")
						||fileName.equalsIgnoreCase("txt")){
					LOG.info("parsing book: " + f.getAbsolutePath());
				
					bookNo = f.getParentFile().getName();
					
					HibernateUtil.getSessionFactory().getCurrentSession().beginTransaction();
					Cbook bookMeta = (Cbook) HibernateUtil.getSessionFactory().getCurrentSession().get(Cbook.class, bookNo);					
					BookType bType = new BookType();
					bookMeta.accept(bType);
					title = bookMeta.getTitle();
					bookType = bType.getBookType();
					bookMeta.setFullIndexed(true);
					HibernateUtil.getSessionFactory().getCurrentSession().getTransaction().commit();
					
					LOG.info ("bookNo:"+bookNo);
					LOG.info ("title:"+title);
										
					DirectoryIndexer.parse(f,iw,indexBuilder);
					/*try{
						iw.close();
					}catch(Exception e){
						Directory dir = FSDirectory.getDirectory(IndexBuildConfig.indexDir, false);
						IndexReader.unlock(dir);
					    //dir.makeLock(IndexWriter.WRITE_LOCK_NAME).release();
					    //dir.makeLock(IndexWriter.COMMIT_LOCK_NAME).release();
						iw = new IndexWriter(IndexBuildConfig.indexDir, IndexBuilder.analyzer,false);
					}finally{
						
					}*/
				}
				else if(fileName.equals("meta")){
					continue;
				}else
				{
					DirectoryIndexer.parse(f,iw,indexBuilder);
				}
			} else if(f.isFile()) {
				String dirName = f.getParentFile().getParentFile().getName();
				// heuristic hint to anti non-book files
				if (dirName.length() != 8 ) {
					continue;
				}
				PageIndexer.parse(f,iw,indexBuilder,bookNo,title,bookType);
			}
		}
	}
}

