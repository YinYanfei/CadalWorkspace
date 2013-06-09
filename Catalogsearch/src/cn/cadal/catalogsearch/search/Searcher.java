package cn.cadal.catalogsearch.search;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.analysis.cn.ChineseAnalyzer;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Searcher {
	private static final String INDEX_PATH = "C:\\cadal.indices";
	private static Directory indexDir = null;
	private static IndexSearcher indexSearcher;
	private static final String OUTPUT_FILE = "C:\\Users\\Chenxing\\Desktop\\books.txt";
	private static String[] queries = {"二次优化", "支持向量机", "线性学习", "核函数", "线性分类"};
	private static final String DB_URL = "jdbc:mysql://10.15.62.226:3306/cadal_metadata_full_dbo";
	private static final String DB_USERNAME = "root";
	private static final String DB_PASSWORD = "31415";
	private static Connection connection;
	private static Statement statement;

	public static void init() {
		try {
			indexDir = FSDirectory.open(new File(INDEX_PATH));
			indexSearcher = new IndexSearcher(indexDir);
			Class.forName( "org.gjt.mm.mysql.Driver" );
			connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
			statement = connection.createStatement();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String[] args) throws Exception {
		init();
		PrintWriter out = new PrintWriter(new FileWriter(OUTPUT_FILE));
		Set<String> all = new HashSet<String>();
		for (String cmd : queries) {
			System.out.printf("Query: %s...", cmd);
			Query query = new QueryParser(Version.LUCENE_30, "Chapter", new ChineseAnalyzer()).parse(cmd.trim());
			ScoreDoc[] scoreDoc = indexSearcher.search(query, 10000).scoreDocs;
			System.out.printf("%d hits\n", scoreDoc.length);
			Set<String> books = new HashSet<String>();
			for (int i = 0; i < scoreDoc.length; i++) {
				System.out.printf("%s %s %s\r\n", indexSearcher.doc(scoreDoc[i].doc).get("Chapter"), indexSearcher.doc(scoreDoc[i].doc).get("BookNo"), indexSearcher.doc(scoreDoc[i].doc).get("FileID"));
				books.add(indexSearcher.doc(scoreDoc[i].doc).get("BookNo"));
				all.add(indexSearcher.doc(scoreDoc[i].doc).get("BookNo"));
			}
			out.printf("Result for %s, totally %d books\r\n--------------------\r\n", cmd, books.size());
			for (String bookNo : books) {
				ResultSet result = statement.executeQuery(String.format("SELECT Title FROM Cbook WHERE BookNo = '%s'", bookNo));
				result.first();
				out.printf("%s %s\r\n", bookNo, result.getString("Title"));
			}
			out.printf("--------------------\r\n\r\n");
		}
		out.printf("Result for ALL, totally %d books\r\n--------------------\r\n", all.size());
		for (String bookNo : all) {
			ResultSet result = statement.executeQuery(String.format("SELECT Title FROM Cbook WHERE BookNo = '%s'", bookNo));
			result.first();
			out.printf("%s %s\r\n", bookNo, result.getString("Title"));
		}
		out.printf("--------------------\r\n\r\n");
		out.close();
	}
}
