package cn.cadal.storm.demo.deepOp;

import java.util.ArrayList;
import java.util.List;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.thrift.SuperColumn;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cadal.storm.demo.util.CassandraUtil;
import cn.cadal.storm.demo.util.Connector;

public class QueryBCPRelation {

	static private final Log LOG = LogFactory.getLog(QueryBCPRelation.class);
	
	public List<List<String>> finalResult = null;
	
	private Connector connector = null;
	private Cassandra.Client client = null;
	private CassandraUtil cassandraUtil = null;
	
	public QueryBCPRelation() {
		this.finalResult = new ArrayList<List<String>>();
		
		this.connector = new Connector("Keyspace2");
		this.client = this.connector.connect();
		this.cassandraUtil = new CassandraUtil();
	}
	
	public void finalize() {
		this.connector.finalize();
	}
	
	/**
	 * To query from 'BCPRelation'
	 * @param bookChapterList
	 */
	public void QueryFromBCPRelation (List<List<String>> bookChapterList){		
		SlicePredicate predicate = new SlicePredicate();
		SliceRange range = new SliceRange();
		
		range.setStart(new byte[0]);
		range.setFinish(new byte[0]); 
		
		predicate.setSlice_range(range);
		
		ColumnParent parent = new ColumnParent();
		parent.column_family = "BCPRelation";

		try{
			for (int i = 0; i < bookChapterList.size(); ++i) {
				List<String> innerResult = new ArrayList<String>();
				
				List<String> innerList = bookChapterList.get(i);
				
				// Deal with a single book info list
				String bookId = innerList.get(0);
				List<String> chapterList = innerList.subList(1, innerList.size());
				
				innerResult.add(bookId);
				
				// Query from cassandra
				List<ColumnOrSuperColumn> results = client.get_slice(this.cassandraUtil.toByteBuffer(bookId), parent, predicate,ConsistencyLevel.ONE);
				
				// Iterator SuperColumn List
				for (ColumnOrSuperColumn result : results) {
					SuperColumn superColumn2 = result.super_column;
					List<Column> columns2 = superColumn2.getColumns();

					// Get detail information of a single chapter
					String chapterLevel = "";
					String chapterTitle = "";
					
					for (Column column : columns2) {
						String columnName = new String(column.getName(), "UTF-8");
						
						if (columnName.equalsIgnoreCase("ChapterLevel")) {
							chapterLevel = new String(column.getValue(), "UTF-8");
						} else if (columnName.equalsIgnoreCase("ChapterLabel")) {
							chapterTitle = new String(column.getValue(), "UTF-8");
						}
					}
					
					// Iterator chapterList insert into map
					for(int j = 0; j < chapterList.size(); ++j){
						if(chapterLevel.equals(chapterList.get(j))) {
							innerResult.add(chapterTitle);
						}
					}
				}
				
				this.finalResult.add(innerResult);
			}

		}catch(Exception e) {
			LOG.warn("Error when dealing bookChapterList");
			e.printStackTrace();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		QueryBCPRelation qbr = new QueryBCPRelation();
//		
//		List<List<String>> listStr = new ArrayList<List<String>>();
//		
//		List<String> inner1 = new ArrayList<String>();
//		inner1.add("06332861");
//		inner1.add("6.8.0.0.0");
//		inner1.add("6.0.0.0.0");
//
//		List<String> inner2 = new ArrayList<String>();
//		inner2.add("06332860");
//		inner2.add("1.1.2.0.0");
//		inner2.add("1.1.3.0.0");
//		inner2.add("3.4.0.0.0");
//		inner2.add("1.1.0.0.0");
//		inner2.add("3.0.0.0.0");
//		inner2.add("1.1.1.0.0");
//		
//		listStr.add(inner1);
//		listStr.add(inner2);
//		
//		qbr.QueryFromBCPRelation(listStr);
//		
//		for(int i = 0 ; i < qbr.finalResult.size(); ++i) {
//			System.out.println(qbr.finalResult.get(i));
//		}
	}
}
