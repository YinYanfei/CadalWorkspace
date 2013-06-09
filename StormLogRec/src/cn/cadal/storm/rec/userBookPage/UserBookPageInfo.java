package cn.cadal.storm.rec.userBookPage;

import java.util.ArrayList;
import java.util.HashMap;
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

public class UserBookPageInfo {
	
	private Connector connector = null;
	private Cassandra.Client client = null;
	private CassandraUtil cassandraUtil = null;
	
	public UserBookPageInfo() {
		this.connector = new Connector("CadalDB");
		this.client = this.connector.connect();
		this.cassandraUtil = new CassandraUtil();
	}
	
	public void finalize() {
		this.connector.finalize();
	}

	/**
	 * To get all book-page information about an allocated 'username'
	 * @param String username: like 'zju' 'Yanfei'
	 */
	public Map<String, List<String>> GetAllInfoOfUser(String username) {
		
		Map<String, List<String>> infoMap = new HashMap<String, List<String>>();
		
		SlicePredicate predicate = new SlicePredicate();// null, new
		SliceRange range = new SliceRange();
		
		range.setStart(new byte[0]);
		range.setFinish(new byte[0]); 
		range.setCount(200000);
		
		predicate.setSlice_range(range);

		ColumnParent parent = new ColumnParent();
		parent.column_family = "UserBookPage";

		String bookid;
		List<String> listPage = null;
		
		try {
			List<ColumnOrSuperColumn> results = client.get_slice(this.cassandraUtil.toByteBuffer(username), parent, predicate, ConsistencyLevel.ONE);
			
			for (ColumnOrSuperColumn result : results) {
				listPage = new ArrayList<String>();
				
				SuperColumn superColumn2 = result.super_column;
				
				bookid = "";
				bookid = new String(superColumn2.getName(), "UTF-8");           // bookid
				
				List<Column> columns2 = superColumn2.getColumns();
				
				for (Column column : columns2) {
					String columnName = new String(column.getName(), "UTF-8");
					
					if(columnName.equalsIgnoreCase("times")){
						continue;
					}else{
						listPage.add(new String(column.getName(), "UTF-8"));     // page-number
					}
				}
				
				infoMap.put(bookid, listPage);
			}
			
			return infoMap;
		} catch (Exception e) {
			 e.printStackTrace();
			 return infoMap;
		}

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		UserBookPageInfo ubpi = new UserBookPageInfo();
//		
//		Map<String, List<String>> results = ubpi.GetAllInfoOfUser("Yanfei");
//		
//		Iterator iter = results.keySet().iterator(); 
//		int n = 0;
//		while (iter.hasNext()) { 
//		    Object key = iter.next(); 
//		    System.out.println(key.toString());
//		    Object val = results.get(key); 
//		    
//		    List<Integer> listRe = (List<Integer>) val;
//		    
//		    for(int i= 0; i < listRe.size(); ++i) {
//		    	System.out.println(listRe.get(i));
//		    }
//		    
//		    System.out.println(val.toString());
//		    
//		    System.out.println("=============");
//		    
//		    n++;
//		}
//		
//		System.out.println(n);
	}

}
