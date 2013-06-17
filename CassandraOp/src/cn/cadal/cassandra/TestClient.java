package cn.cadal.cassandra;
 
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;  
import org.apache.thrift.transport.TFramedTransport;  
import org.apache.thrift.transport.TSocket;  
import org.apache.thrift.transport.TTransport; 

public class TestClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TTransport tr = new TFramedTransport(new TSocket("10.15.62.77", 9160));
		TProtocol proto = new TBinaryProtocol(tr);
		Cassandra.Client client = new Cassandra.Client(proto);
		
		try{
			tr.open();
			
			if(!tr.isOpen()) {
				System.out.println("Failed! try again!");
				return;
			}
			
			long timeTmp = System.currentTimeMillis();
			
			client.set_keyspace("MyKeyspace2");
			ColumnParent parent = new ColumnParent("school");
			
			// insert
/*			for(int i = 420009; i < 420020; ++i) {
				String k = key_user_id + i;
				long timeStamp = System.currentTimeMillis();
				
				Column idColumn = new Column();
				idColumn.setName(toByteBuffer("name"));
				idColumn.setValue(toByteBuffer("Yanfei"));
				idColumn.setTimestamp(timeStamp);
				
				client.insert(toByteBuffer(k), parent, idColumn, ConsistencyLevel.ONE);
				
			}
*/			
			// read
			ColumnPath path = new ColumnPath("school");
			path.setColumn(toByteBuffer("name"));
			String key3 = "中";
			String name = toString(client.get(toByteBuffer(key3), path, ConsistencyLevel.ONE).column.value);
			if(name.length() > 1) {
				System.out.println(name);
			}else{
				System.out.println("None");
			}
			
			// read a column
			SlicePredicate predicate = new SlicePredicate();
			SliceRange sliceRange = new SliceRange(toByteBuffer(""), toByteBuffer(""), false, 10);
			predicate.setSlice_range(sliceRange);
			List<ColumnOrSuperColumn> results = 
				client.get_slice(toByteBuffer(key3), parent, predicate, ConsistencyLevel.ONE);
			
			for(ColumnOrSuperColumn result:results){
				Column column = result.column;
				System.out.println(toString(column.name) + "-> " + toString(column.value));
			}
			
			long timeTmp2 = System.currentTimeMillis();
			
			System.out.println("Time: " + (timeTmp2 - timeTmp) + "ms");
			
			tr.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    /* 
     * 将String转换为bytebuffer，以便插入cassandra 
     */  
	public static ByteBuffer toByteBuffer(String value) {
		try {
			return ByteBuffer.wrap(value.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /* 
     * 将bytebuffer转换为String 
     */ 
    public static String toString(ByteBuffer buffer)
        throws UnsupportedEncodingException {
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return new String(bytes, "UTF-8");
    }  

}
