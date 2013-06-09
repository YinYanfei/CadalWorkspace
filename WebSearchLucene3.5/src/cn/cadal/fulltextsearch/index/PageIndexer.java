package cn.cadal.fulltextsearch.index;

import java.io.File;
import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;

import cn.cadal.entity.BookType;
import cn.cadal.entity.Cbook;
import cn.cadal.fulltextsearch.content.FileContentGenerator;
import cn.cadal.fulltextsearch.content.FileContentGeneratorBuilder;

import common.utils.HibernateUtil;

public class PageIndexer {
	private static final Log LOG = LogFactory.getLog (PageIndexer.class);

	public static void parse(File file,IndexWriter iw,IndexBuilder indexBuilder,
							 String bookNo, String title, String bookType)throws IOException{
			
		String fileName = file.getName();
		String pageNo = fileName.substring(0,fileName.indexOf("."));
		
		int intPageNo = 0;
		try{
			intPageNo = Integer.parseInt(pageNo);
		}catch(NumberFormatException nfe){
			return;
		}
		if(intPageNo<=2)
			return;
		
		FileContentGenerator fileContentGenerator = FileContentGeneratorBuilder.buildFileContentGenerator(file);
		String content = fileContentGenerator.getFileContent(file);
		
		LOG.info(" pageNo:"+pageNo);
		
		Document doc = new Document();
		
		doc.add(new Field("BookNo", bookNo, Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("PageNo", pageNo, Field.Store.YES, Field.Index.NO));
		doc.add(new Field("Title", title, Field.Store.YES, Field.Index.NO));
		doc.add(new Field("Content", content, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("BookType", bookType, Field.Store.YES, Field.Index.NO));
		
		iw.addDocument(doc);
	}
}
