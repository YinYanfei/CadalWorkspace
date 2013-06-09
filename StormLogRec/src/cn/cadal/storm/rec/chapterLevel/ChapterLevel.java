package cn.cadal.storm.rec.chapterLevel;

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

import cn.cadal.storm.rec.cassandra.CassandraUtil;
import cn.cadal.storm.rec.cassandra.Connector;

public class ChapterLevel {
	
	private Connector connector = null;
	private Cassandra.Client client = null;
	private CassandraUtil cassandraUtil = null;
	
	public ChapterLevel() {
		this.connector = new Connector("Keyspace2");
		this.client = this.connector.connect();
		this.cassandraUtil = new CassandraUtil();
	}
	
	public void finalize() {
		this.connector.finalize();
	}

	/**
	 * This function is used to deal many pages of a book, to find all chapter_level info of pages.
	 * @param bookNo like: 07018720
	 * @param pageNoList just like: <<23>, <45>, <2>>
	 * @return List of book_page:<<07018720_1.1.0.0.0>, <07018720_2.0.0.0.0>, <07018720_1.2.0.0.0>>
	 */
	@SuppressWarnings("unchecked")
	public List<String> GetChapterLevel(String bookNo, List<Integer> pageNoList){
		
		SlicePredicate predicate = new SlicePredicate();
		SliceRange range = new SliceRange();
		
		range.setStart(new byte[0]);
		range.setFinish(new byte[0]); 
		
		predicate.setSlice_range(range);

		ColumnParent parent = new ColumnParent();
		parent.column_family = "BCPRelation";

		Map<String, String> tmpMap = new HashMap<String, String>();
		List<String> returnStrList = new ArrayList<String>();
			
		try {
			List<ColumnOrSuperColumn> results = client.get_slice(this.cassandraUtil.toByteBuffer(bookNo), parent, predicate,ConsistencyLevel.ONE);

			// Iterator SuperColumn List
			for (ColumnOrSuperColumn result : results) {
				SuperColumn superColumn2 = result.super_column;
				List<Column> columns2 = superColumn2.getColumns();

				// Get detail information of a single chapter
				int startPage = 0;
				int endPage = 0;
				String chapterLevel = "";
				
				for (Column column : columns2) {
					String columnName = new String(column.getName(), "UTF-8");
					
					if (columnName.equalsIgnoreCase("ChapterLevel")) {
						chapterLevel = new String(column.getValue(), "UTF-8");
					} else if (columnName.equalsIgnoreCase("StartPage")) {
						startPage = Integer.valueOf(new String(column.getValue(), "UTF-8")).intValue();
					} else if (columnName.equalsIgnoreCase("EndPage")) {
						endPage = Integer.valueOf(new String(column.getValue(), "UTF-8")).intValue();
					} 
				}
				
				// Iterator PageNoList insert into map
				for(int j = 0; j < pageNoList.size(); ++j){
					if(MatchRange(pageNoList.get(j), startPage, endPage)) {
						tmpMap.put(bookNo + "_" + chapterLevel, "");
					}
				}

			}
			
			// Convert Map to List
			Iterator iter = tmpMap.entrySet().iterator(); 
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry)iter.next(); 
			    Object key = entry.getKey(); 
			    
			    returnStrList.add(key.toString());			    
			}
			
			return returnStrList;
			
		} catch (Exception e) {
			 e.printStackTrace();
		}
		
		return returnStrList;
	}
	
	/**
	 * To ensure whether the 'pageNo' located between 'start' and 'end' or not.  
	 */
	public boolean MatchRange(int pageNo, int start, int end) {
		if (pageNo >= start && pageNo <= end) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Main function
	 */
	public static void main(String[] args) {
		// Test
		String bookno = "01060137";
		List<Integer> listPageInt = new ArrayList<Integer>();
		
		listPageInt.add(1);
		
		ChapterLevel cl = new ChapterLevel();
		
		List<String> listChapterLevel = cl.GetChapterLevel(bookno, listPageInt);
		
		// Print information
		for (int i = 0; i < listChapterLevel.size(); ++i) {
			System.out.println(listChapterLevel.get(i));
		}
		
	}

}
