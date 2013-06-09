/*****************************************
 * Copyright 漏 2011-2011, programerhelper.com, All Rights Reserved
 *
 * @file   com.hikvision.lucene.chineseword.paoding.MainTest.java
 * @filecomment
 *
 * @author keith.
 * @create 2011-9-11 涓嬪崍09:52:42
 * @e-mail hanyicai1988@qq.com or keith@programerhelper.com
 * @tel 15158119791
 * WWW: www.programerhelper.com
 */
package com.hikvision.lucene.chineseword.paoding;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.paoding.analysis.analyzer.PaodingAnalyzer;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.NumericField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class MainTest {

	// 索引保存目录
    public static String indexFilePath = "luceneDemoTestIndex";

    public static void testIndex() throws Exception{
    	// 待搜索目录文件
        String itemFilePath = "TestDocs/luceneDemoTest.txt";
        boolean isCreate = true;
        Date start = new Date();
        try {
            System.out.println("Indexing to directory '" + indexFilePath + "'...");

            Directory dir = FSDirectory.open(new File(indexFilePath));
            //Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_33);
            //Analyzer analyzer = new PaodingAnalyzer("etc/paoding-analysis-default.properties");
            //Analyzer analyzer = new PaodingAnalyzer("F:/paodingetc/paoding-analysis-default.properties");
            //Analyzer analyzer = new PaodingAnalyzer("ifexists:paoding-dic-home.properties");
            Analyzer analyzer = new PaodingAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_33,
                    analyzer);

            if (isCreate) {
                // 未建立索引的情况下建立索引
                iwc.setOpenMode(OpenMode.CREATE);
            } else {
                // 如果索引已经建立则添加和更新Document
                iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
            }
            IndexWriter writer = new IndexWriter(dir, iwc);
            FileInputStream fis;
            try {
                fis = new FileInputStream(itemFilePath);
            } catch (FileNotFoundException fnfe) {
                return;
            }
            try {
                //new BufferedReader();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
                String item = "";
                String[] fields;
                item = bufferedReader.readLine();
                Integer id = 0;
                while(!"".equals(item) && item != null) {
                    id++;
                    fields = item.split("::");

                    // Create Document Object
                    Document doc = new Document();

                    doc.add(new Field("ID", id.toString(),Field.Store.YES, Field.Index.NO));
                    doc.add(new Field("IDSTR", id.toString(),Field.Store.YES, Field.Index.NOT_ANALYZED));
                    
                    NumericField idField = new NumericField("IDNUM", Field.Store.YES, true);
                    idField.setIntValue(id);
                    doc.add(idField);//鐢ㄤ簬娴嬭瘯鏁板瓧

                    doc.add(new Field("PCIP", fields[0],Field.Store.YES, Field.Index.ANALYZED));

                    doc.add(new Field("DeviceIP", fields[1],Field.Store.YES, Field.Index.ANALYZED));

                    doc.add(new Field("DeviceSerialNum", fields[2],Field.Store.YES, Field.Index.ANALYZED));

                    doc.add(new Field("AlarmType", fields[3],Field.Store.YES, Field.Index.ANALYZED));

                    //涓�釜鍩熷彲浠ユ湁瀛愰泦
                    doc.add(new Field("MultiFields", fields[2],Field.Store.YES, Field.Index.ANALYZED));
                    doc.add(new Field("MultiFields", fields[3],Field.Store.YES, Field.Index.ANALYZED));

                    NumericField alarmDatetime = new NumericField("alarmDatetime", Field.Store.YES, true);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    alarmDatetime.setLongValue(simpleDateFormat.parse(fields[4]).getTime());
                    doc.add(alarmDatetime);

                    if (id == 5) {
                        doc.setBoost(1.2f);
                    }

                    if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
                        writer.addDocument(doc);
                    } else {
                        writer.updateDocument(new Term("ID" , id.toString()), doc);
                    }
                    item = bufferedReader.readLine();
                }

            } finally {
                fis.close();
            }

            System.out.println("Finished Index!!");
            
            writer.optimize();

            writer.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime()
                    + " total milliseconds");

        } catch (IOException e) {
            System.out.println(" caught a " + e.getClass()
                    + "\n with message: " + e.getMessage());
        }
    }
    public static void testSearch() throws Exception{
        String field = "AlarmType";
        int hitsPerPage = 10;

        IndexSearcher searcher = new IndexSearcher(FSDirectory.open(new File(indexFilePath)));
 
        Query query = new TermQuery(new Term(field,"移动"));
        System.out.println("Searching for: " + query.toString(field));
        // Collect enough docs to show 5 pages
        TopDocs results = searcher.search(query, 5 * hitsPerPage, Sort.RELEVANCE);
        ScoreDoc[] hits = results.scoreDocs;
        int numTotalHits = results.totalHits;
        System.out.println(numTotalHits + " total matching documents");
        for (int i = 0; i < hits.length; i++) {
            Document doc = searcher.doc(hits[i].doc);
            System.out.println("----------------------------------");
            System.out.println((i + 1) + ": ");
            System.out.println("ID:" + doc.get("ID") + "\nPCIP:" + doc.get("PCIP") + "\nDeviceIP:" + doc.get("DeviceIP")
                    + "\nDeviceSerialNum:" + doc.get("DeviceSerialNum") + "\nAlarmType:" + doc.get("AlarmType")
                    + "\nAlarmDatetime:" + new Date(Long.parseLong(doc.get("alarmDatetime"))).toString());
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception{
        testIndex();
        testSearch();

    }

}
