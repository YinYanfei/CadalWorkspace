package cn.cadal.storm.rec.chapterInfoOp;

import java.util.ArrayList;
import java.util.List;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;

import cn.cadal.storm.rec.cassandra.CassandraUtil;
import cn.cadal.storm.rec.cassandra.Connector;

public class QueryChapterInfo {
	
	private Connector connector = null;
	private Cassandra.Client client = null;
	private CassandraUtil cassandraUtil = null;
	
	public QueryChapterInfo() {
		try{
			this.connector = new Connector();
			this.client = this.connector.connect();
			this.cassandraUtil = new CassandraUtil();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() {
		this.connector.finalize();
	}

	/**
	 * Query From CF -- "UserChapter"
	 * @param int userid: 'userid' is column key of this CF, like '119115'
	 */
	public List<String> QueryFromUserChapter(int userid){
//		System.out.println("------------QueryFromUserChapter--------------");

		List<String> listStr = new ArrayList<String>();
		
		try {
			SlicePredicate predicate = new SlicePredicate();
			SliceRange range = new SliceRange();
			range.start = this.cassandraUtil.toByteBuffer("");
			range.finish = this.cassandraUtil.toByteBuffer("");
			range.setCount(10000000);
			predicate.setSlice_range(range);

			ColumnParent parent = new ColumnParent();
			parent.column_family = "UserChapter";    // CF name

			List<ColumnOrSuperColumn> results = client.get_slice(this.cassandraUtil.toByteBuffer(String.valueOf(userid)), parent, predicate,ConsistencyLevel.ONE);
			
			for (ColumnOrSuperColumn result : results) {
				Column column1 = result.column;
				listStr.add(new String(column1.getName(), "UTF-8"));
			}
			
			return listStr;
			
		} catch (Exception e) {
			return listStr;
		}
	}

	/**
	 * Query From CF -- "ChapterSignalMap"
	 * @param String content: content like '"07018720_1.2.0.0.0"'
	 * 
	 */
	public List<String> QueryFromChapterSignalMap(String content){
//		System.out.println("------------QueryFromChapterSignalMap--------------");

		List<String> listMaxidSignal = new ArrayList<String>();
		
		try {
			// get 'maxid'
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = "ChapterSignalMap";
			columnPath.column = this.cassandraUtil.toByteBuffer("maxid");
			
			ColumnOrSuperColumn columnOrSuperColumn = client.get(this.cassandraUtil.toByteBuffer("maxid"), columnPath, ConsistencyLevel.ONE);
			Column column = columnOrSuperColumn.getColumn();
			
			listMaxidSignal.add(new String(column.getValue(), "UTF-8"));
			
			// get 'signal'
			columnPath.column = this.cassandraUtil.toByteBuffer("signal");
			
			columnOrSuperColumn = client.get(this.cassandraUtil.toByteBuffer(content), columnPath, ConsistencyLevel.ONE);
			column = columnOrSuperColumn.getColumn();
			
			listMaxidSignal.add(new String(column.getValue(), "UTF-8"));
			
			return listMaxidSignal;
			
		} catch (Exception e) {
			return listMaxidSignal;
		}
	}

	/**
	 * Query From CF -- "SignalChapterMap"
	 * @param int signal: signal like '23'
	 */
	public String QueryFromSignalChapterMap(int signal){
//		System.out.println("------------QueryFromSignalChapterMap--------------");

		String content = "";
		
		try {
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = "SignalChapterMap";
			columnPath.column = this.cassandraUtil.toByteBuffer("content");
			
			ColumnOrSuperColumn columnOrSuperColumn = client.get(this.cassandraUtil.toByteBuffer(String.valueOf(signal)), columnPath, ConsistencyLevel.ONE);
			Column column = columnOrSuperColumn.getColumn();
			
			content = new String(column.getValue(), "UTF-8");
			
			return content;
			
		} catch (Exception e) {
			return content;
		}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		QueryChapterInfo qci = new QueryChapterInfo();
//		
//		System.out.println(qci.QueryFromSignalChapterMap(122));
		
//		List<String> listTmp = qci.QueryFromUserChapter(119115);
//		
//		for(int i = 0; i < listTmp.size(); ++i) {
//			System.out.println(listTmp.get(i));
//		}
		
//		List<String> listStr = qci.QueryFromChapterSignalMap("07018720_1.0.0.0.0");
//		
//		System.out.println(listStr.size() + "   " + listStr.get(0) + "  " + listStr.get(1));
		
//		System.out.println(qci.QueryFromSignalChapterMap(23));
	}

}
