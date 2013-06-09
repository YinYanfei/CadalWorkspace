package cn.cadal.dis.java.cassandra;

import java.nio.ByteBuffer;
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
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class CassandraOp {
	
	public Map<String, ArrayList<String>> MapUserBook;
	
	public CassandraOp() {
		this.MapUserBook = new HashMap<String, ArrayList<String>>();
	}
	
	public void QueryRecordMinute(String queryWord) {
		TTransport tr = new TFramedTransport(new TSocket("10.15.61.111", 9160));
		TProtocol proto = new TBinaryProtocol(tr);
		Cassandra.Client client = new Cassandra.Client(proto);

		try {
			tr.open();
			client.set_keyspace("CadalSecTest");
		
			// read entire row
			//System.out.println("------------Entire---------------");
			SlicePredicate predicate = new SlicePredicate();//null, new SliceRange(new byte[0], new byte[0], false, 10)
			SliceRange range = new SliceRange();
			range.start = toByteBuffer("");
			range.finish = toByteBuffer("");
			predicate.setSlice_range(range);
			
			ColumnParent parent = new ColumnParent();
			parent.column_family = "RecordMinute";
			
			String username = "";
			String bookno   = "";
			List<ColumnOrSuperColumn> results = client.get_slice(toByteBuffer(queryWord), parent, predicate, ConsistencyLevel.ONE);
			for (ColumnOrSuperColumn result : results) {
				SuperColumn superColumn2 = result.super_column;
				List<Column> columns2 = superColumn2.getColumns();
				
				// To get map<username, list[bookno]>
				for (Column column : columns2) {
					//System.out.println(new String(column.getName(), "UTF-8") + " -> "+ new String(column.getValue(), "UTF-8"));
					
					String columnName = new String(column.getName(), "UTF-8");
					
					if(columnName.equals("username")) {	
						username  = new String(column.getValue(), "UTF-8");	
					}else if(columnName.equals("bookno")) {
						bookno = new String(column.getValue(), "UTF-8");
					}
				}
				
				if(this.MapUserBook.containsKey(username)) {
					if(!this.MapUserBook.get(username).contains(bookno)) {
						this.MapUserBook.get(username).add(bookno);
					}
				}else{
					ArrayList<String> bookList = new ArrayList<String>();
					bookList.add(bookno);
					this.MapUserBook.put(username, bookList);
				}
				
				//System.out.println("-------------------------------------------------");
			}
			
			tr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * String => bytebuffer
	 */
	public ByteBuffer toByteBuffer(String value) {
		try {
			return ByteBuffer.wrap(value.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		CassandraOp co = new CassandraOp();
		
		co.QueryRecordMinute("2013-03-29 19:18");
		
		Iterator iter = co.MapUserBook.keySet().iterator(); 
		while (iter.hasNext()) {
		    Object key = iter.next(); 
		    Object val = co.MapUserBook.get(key);
		    
		    System.out.println("-------------------------------");
		    
		    System.out.println(key);
		    ArrayList<String> alTmp = (ArrayList<String>) val;
		    System.out.println("========");
		    for(int i = 0; i < alTmp.size(); ++i) {
		    	System.out.println(alTmp.get(i));
		    }
		}
	}
}
