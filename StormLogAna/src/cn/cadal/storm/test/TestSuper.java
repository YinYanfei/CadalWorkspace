package cn.cadal.storm.test;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.thrift.SuperColumn;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class TestSuper {

	/*
	 * Super-Column test insert
	 */
	public void superStore(String str) {
		String key_name = "Yanfei";
		String super_key_name = "100300";
		String ColumnFamilyName = "Super";
		String[] strList = str.split(" ");

		try {

			TTransport tr = new TFramedTransport(new TSocket("10.15.61.111", 9160));
			TProtocol proto = new TBinaryProtocol(tr);
			Cassandra.Client client = new Cassandra.Client(proto);
			
			tr.open();
			
			client.set_keyspace("Keyspace2");
			
			long timeStamp = System.currentTimeMillis();

			Map<ByteBuffer, Map<String, List<Mutation>>> outerMap = new HashMap<ByteBuffer, Map<String, List<Mutation>>>();
			List<Mutation> columnToAdd = new ArrayList<Mutation>();

			// 
			Column idColumnIp = new Column();
			idColumnIp.setName(toByteBuffer("ip"));
			idColumnIp.setValue(toByteBuffer(strList[0]));
			idColumnIp.setTimestamp(timeStamp);

			Column idColumnBookid = new Column();
			idColumnBookid.setName(toByteBuffer("bookid"));
			idColumnBookid.setValue(toByteBuffer(strList[1]));
			idColumnBookid.setTimestamp(timeStamp);

			Column idColumnPageid = new Column();
			idColumnPageid.setName(toByteBuffer("pageid"));
			idColumnPageid.setValue(toByteBuffer(strList[2]));
			idColumnPageid.setTimestamp(timeStamp);

			List<Column> cols = new ArrayList<Column>();
			cols.add(idColumnIp);
			cols.add(idColumnBookid);
			cols.add(idColumnPageid);

			//
			Map<String, List<Mutation>> innerMap = new HashMap<String, List<Mutation>>();

			Mutation columns = new Mutation();
			ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
			SuperColumn superColumn = new SuperColumn();

			superColumn.name = toByteBuffer(super_key_name);
			superColumn.columns = cols;

			columnOrSuperColumn.super_column = superColumn;
			columns.setColumn_or_supercolumn(columnOrSuperColumn);

			columnToAdd.add(columns);

			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = ColumnFamilyName;
			columnPath.setSuper_column(toByteBuffer(super_key_name));
			columnPath.setSuper_columnIsSet(true);

			innerMap.put(ColumnFamilyName, columnToAdd);

			// 
			outerMap.put(toByteBuffer(key_name), innerMap);

			client.batch_mutate(outerMap, ConsistencyLevel.ONE);
			
			tr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Super-Column Query
	 */
	public void superQuery() {
		TTransport tr = new TFramedTransport(new TSocket("10.15.61.111", 9160));
		TProtocol proto = new TBinaryProtocol(tr);
		Cassandra.Client client = new Cassandra.Client(proto);

		try {
			tr.open();
			client.set_keyspace("Keyspace2");
			
			// read single column
			System.out.println("------------Single---------------");
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = "Super";
			columnPath.super_column = toByteBuffer("100900");
			
			ColumnOrSuperColumn columnOrSuperColumn = client.get(toByteBuffer("Yanfei"), columnPath, ConsistencyLevel.ONE);

			SuperColumn superColumn = columnOrSuperColumn.getSuper_column();
			
			List<Column> columns = superColumn.getColumns();
			
			for(Column col : columns) {
				System.out.println(new String(col.getName(), "UTF-8") + " --> " + new String(col.getValue(), "UTF-8"));
			}
			
			// read entire row
			System.out.println("------------Entire---------------");
			SlicePredicate predicate = new SlicePredicate();//null, new SliceRange(new byte[0], new byte[0], false, 10)
			SliceRange range = new SliceRange();
			range.start = toByteBuffer("100100");
			range.finish = toByteBuffer("100300");
			predicate.setSlice_range(range);
			
			ColumnParent parent = new ColumnParent();
			parent.column_family = "Super";
			
			List<ColumnOrSuperColumn> results = client.get_slice(toByteBuffer("Yanfei"), parent, predicate, ConsistencyLevel.ONE);
			for (ColumnOrSuperColumn result : results) {
				SuperColumn superColumn2 = result.super_column;
				
				List<Column> columns2 = superColumn2.getColumns();
				
				System.out.println(new String(superColumn2.getName(), "UTF-8"));
				
				for (Column column : columns2) {
					System.out.println(new String(column.getName(), "UTF-8") + " -> "+ new String(column.getValue(), "UTF-8"));
				}
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
		TestSuper testSuper = new TestSuper();

		testSuper.superQuery();
//		testSuper.superStore("10.15.62.130 37018720 00000037");

	}

}
