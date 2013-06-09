package cn.cadal.storm.test;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;

import java.nio.ByteBuffer;
import java.util.List;

public class TestStandard {

	java.sql.Connection con = null;

	/**
	 * Store into cassandra
	 */
	public void Store(String str) {
		String[] strList = str.split(" ");

		String dateStr = "2012-12-10 13:27:07:832";    // key_name
		
		TTransport tr = new TFramedTransport(new TSocket("10.15.61.111", 9160));
		TProtocol proto = new TBinaryProtocol(tr);
		Cassandra.Client client = new Cassandra.Client(proto);

		try {
			tr.open();

			if (!tr.isOpen()) {
				System.out.println("Failed! try again!");
				return;
			}

			client.set_keyspace("Keyspace2");
			ColumnParent parent = new ColumnParent("Standard");

			long timeStamp = System.currentTimeMillis();

			Column idColumnIp = new Column();
			idColumnIp.setName(toByteBuffer("ip"));
			idColumnIp.setValue(toByteBuffer(strList[0]));
			idColumnIp.setTimestamp(timeStamp);
			client.insert(toByteBuffer(dateStr), parent, idColumnIp,
					ConsistencyLevel.ONE);

			Column idColumnBookid = new Column();
			idColumnBookid.setName(toByteBuffer("bookid"));
			idColumnBookid.setValue(toByteBuffer(strList[1]));
			idColumnBookid.setTimestamp(timeStamp);
			client.insert(toByteBuffer(dateStr), parent, idColumnBookid,
					ConsistencyLevel.ONE);

			Column idColumnPageid = new Column();
			idColumnPageid.setName(toByteBuffer("pageid"));
			idColumnPageid.setValue(toByteBuffer(strList[2]));
			idColumnPageid.setTimestamp(timeStamp);
			client.insert(toByteBuffer(dateStr), parent, idColumnPageid,
					ConsistencyLevel.ONE);

		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			tr.close();
		}

	}

	/**
	 * Standard Query Test
	 */
	public void standardQuery() {
		TTransport tr = new TFramedTransport(new TSocket("10.15.61.111", 9160));
		TProtocol proto = new TBinaryProtocol(tr);
		Cassandra.Client client = new Cassandra.Client(proto);
	
		try {
			tr.open();
			client.set_keyspace("Keyspace2");
			
			// read single column
			System.out.println("------------Single---------------");
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = "Standard";
			columnPath.column = toByteBuffer("ip");
			
			ColumnOrSuperColumn columnOrSuperColumn = client.get(toByteBuffer("2012-12-10 13:27:07:832"), columnPath, ConsistencyLevel.ONE);
	
			Column column = columnOrSuperColumn.getColumn();
			
			System.out.println(new String(column.getName(), "UTF-8") + " --> " + new String(column.getValue(), "UTF-8"));
			
			// read entire row
			System.out.println("------------Entire---------------");
			SlicePredicate predicate = new SlicePredicate();
			SliceRange range = new SliceRange();
			range.start = toByteBuffer("bookid");
			range.finish = toByteBuffer("pageid");
			predicate.setSlice_range(range);
			
			ColumnParent parent = new ColumnParent();
			parent.column_family = "Standard";
			
			List<ColumnOrSuperColumn> results = client.get_slice(toByteBuffer("2012-12-10 13:27:07:832"), parent, predicate, ConsistencyLevel.ONE);
			for (ColumnOrSuperColumn result : results) {
				Column column1 = result.column;
				System.out.println(new String(column1.getName(), "UTF-8") + " -> "+ new String(column1.getValue(), "UTF-8"));
			}
			
			tr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * 将String转换为bytebuffer，以便插入cassandra
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
	public static void main(String[] args) {
		// Test
		TestStandard cassandraOp = new TestStandard();
		
//		cassandraOp.Store("10.15.62.130 37018720 00000037");
		cassandraOp.standardQuery();
		
	}
}
