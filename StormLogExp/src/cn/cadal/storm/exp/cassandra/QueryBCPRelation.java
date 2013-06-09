package cn.cadal.storm.exp.cassandra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import cn.cadal.storm.exp.cassandraUtil.CassandraUtil;
import cn.cadal.storm.exp.cassandraUtil.Connector;

public class QueryBCPRelation {

	static private final Log LOG = LogFactory.getLog(QueryBCPRelation.class);
	
	private Connector connector = null;
	private Cassandra.Client client = null;
	private CassandraUtil cassandraUtil = null;
	
	public QueryBCPRelation() {
		this.connector = new Connector("Keyspace2");
		this.client = this.connector.connect();
		this.cassandraUtil = new CassandraUtil();
	}
	
	public void finalize() {
		this.connector.finalize();
	}

	private Map<String, List<String>> Preprocess(List<String> bookChapterList){
		Map<String, List<String>> mapBookChapter = new HashMap<String, List<String>>();
		
		for(int i = 1; i < bookChapterList.size(); ++i) {
			String[] strArr = bookChapterList.get(i).split("_");
			
			if(mapBookChapter.containsKey(strArr[0])) {
				List<String> listTmp = mapBookChapter.get(strArr[0]);
				listTmp.add(strArr[1]);
				mapBookChapter.put(strArr[0], listTmp);
			}else{
				List<String> listVal = new ArrayList<String>();
				listVal.add(strArr[1]);
				mapBookChapter.put(strArr[0], listVal);
			}
		}
		
		return mapBookChapter;
	}
	
	/**
	 * To query from 'BCPRelation'
	 * @param bookChapterList
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> QueryFromBCPRelation (List<String> bookChapterList){
		List<String> chapterList = new ArrayList<String>();
		
		SlicePredicate predicate = new SlicePredicate();
		SliceRange range = new SliceRange();
		
		range.setStart(new byte[0]);
		range.setFinish(new byte[0]); 
		
		predicate.setSlice_range(range);
		
		ColumnParent parent = new ColumnParent();
		parent.column_family = "BCPRelation";

		try{
			Map<String, List<String>> mapBookChapter = this.Preprocess(bookChapterList);

			for (Iterator iter = mapBookChapter.keySet().iterator(); iter.hasNext();) {  
			    Object key = iter.next();
			    Object val = mapBookChapter.get(key);
			    
			    List<ColumnOrSuperColumn> results = client.get_slice(this.cassandraUtil.toByteBuffer(key.toString()), parent, predicate,ConsistencyLevel.ONE);
			    
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
					
					// Iterator PageNoList insert into map
					List<String> listStr = (List<String>) val;
					for(int j = 0; j < listStr.size(); ++j){
						if(chapterLevel.equals(listStr.get(j))) {
							chapterList.add(chapterTitle);
						}
					}
				}
			}  
			
			return chapterList;
		}catch(Exception e) {
			LOG.warn("Error when dealing bookChapterList");
			e.printStackTrace();
		}
		
		return chapterList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		QueryBCPRelation qbr = new QueryBCPRelation();
//		
//		List<String> listStr = new ArrayList<String>();
//		
//		listStr.add("07018720_2.0.0.0.0");
//		listStr.add("07018720_1.0.0.0.0");
//		listStr.add("07010720_1.2.0.0.0");
//		
//		List<String> resultList = qbr.QueryFromBCPRelation(listStr);
//		
//		for(int i = 0 ; i < resultList.size(); ++i) {
//			System.out.println(resultList.get(i));
//		}
		
	}

}
