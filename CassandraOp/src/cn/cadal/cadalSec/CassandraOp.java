package cn.cadal.cadalSec;

import java.io.FileWriter;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

import cn.cadal.util.TimeOp;

public class CassandraOp {

	public int InsertRecordMinute(String str) {
		String[] strList = str.split("\\$");
//		System.out.println(strList[0]);
//		System.out.println(strList[1]);
//		System.out.println(strList[2]);
//		System.out.println(strList[3]);
//		System.out.println(strList[4]);
		
		String IP = strList[0];			// 10.15.62.237
		String USERNAME = strList[1];	// Yanfei
		String BOOKNO = strList[2];		// 07018720
		String PAGENO = strList[3];		// 00000028
		String TIME = strList[4];		// 2013-03-13 08:51:13.123456
		
		String key_name = TIME.substring(0, 16);  // 2013-03-13 08:51
		
		String super_key_name = String.valueOf(UUID.randomUUID());
		String ColumnFamilyName = "RecordMinute";
		
		try {
			TTransport tr = new TFramedTransport(new TSocket("10.15.61.118", 9160));

			TProtocol proto = new TBinaryProtocol(tr);
			Cassandra.Client client = new Cassandra.Client(proto);
			
			tr.open();
			
//			client.set_keyspace("CadalSecTest");
			client.set_keyspace("CadalSec");
			
			long timeStamp = System.currentTimeMillis();

			Map<ByteBuffer, Map<String, List<Mutation>>> outerMap = new HashMap<ByteBuffer, Map<String, List<Mutation>>>();
			List<Mutation> columnToAdd = new ArrayList<Mutation>();

			// 
			Column idColumnIp = new Column();
			idColumnIp.setName(toByteBuffer("ip"));
			idColumnIp.setValue(toByteBuffer(IP));
			idColumnIp.setTimestamp(timeStamp);

			Column idColumnUsername = new Column();
			idColumnUsername.setName(toByteBuffer("username"));
			idColumnUsername.setValue(toByteBuffer(USERNAME));
			idColumnUsername.setTimestamp(timeStamp);

			Column idColumnBookno = new Column();
			idColumnBookno.setName(toByteBuffer("bookno"));
			idColumnBookno.setValue(toByteBuffer(BOOKNO));
			idColumnBookno.setTimestamp(timeStamp);

			Column idColumnPageno = new Column();
			idColumnPageno.setName(toByteBuffer("pageno"));
			idColumnPageno.setValue(toByteBuffer(PAGENO));
			idColumnPageno.setTimestamp(timeStamp);

			Column idColumnTime = new Column();
			idColumnTime.setName(toByteBuffer("time"));
			idColumnTime.setValue(toByteBuffer(TIME));
			idColumnTime.setTimestamp(timeStamp);
			
			List<Column> cols = new ArrayList<Column>();
			cols.add(idColumnIp);
			cols.add(idColumnUsername);
			cols.add(idColumnBookno);
			cols.add(idColumnPageno);
			cols.add(idColumnTime);

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

			// Insert
			outerMap.put(toByteBuffer(key_name), innerMap);

			client.batch_mutate(outerMap, ConsistencyLevel.ONE);
			
			tr.close();
			
			return 0;
		} catch (Exception e) {
			e.printStackTrace();
			
			return 1;
		}
	}
	
	/**
	 * Query from cf [RecordMinute]
	 * @param queryWord
	 */
	public void QueryRecordMinute(String queryWord) {
		TTransport tr = new TFramedTransport(new TSocket("10.15.61.111", 9160));
		TProtocol proto = new TBinaryProtocol(tr);
		Cassandra.Client client = new Cassandra.Client(proto);

		try {
			tr.open();
			client.set_keyspace("CadalSecTest");
//			client.set_keyspace("CadalSec");
			
			// read single column
//			System.out.println("------------Single---------------");
//			ColumnPath columnPath = new ColumnPath();
//			columnPath.column_family = "RecordMinute";
//			columnPath.super_column = toByteBuffer("e140d64d-cc0f-423b-8ccc-312ed508f563");
//			
//			ColumnOrSuperColumn columnOrSuperColumn = client.get(toByteBuffer(queryWord), columnPath, ConsistencyLevel.ONE);
//
//			SuperColumn superColumn = columnOrSuperColumn.getSuper_column();
//			
//			List<Column> columns = superColumn.getColumns();
//			
//			for(Column col : columns) {
//				System.out.println(new String(col.getName(), "UTF-8") + " --> " + new String(col.getValue(), "UTF-8"));
//			}
			
			// read entire row
			System.out.println("------------Entire---------------");
			SlicePredicate predicate = new SlicePredicate();//null, new SliceRange(new byte[0], new byte[0], false, 10)
			SliceRange range = new SliceRange();
			range.start = toByteBuffer("");
			range.finish = toByteBuffer("");
			predicate.setSlice_range(range);
			
			ColumnParent parent = new ColumnParent();
			parent.column_family = "RecordMinute";
			
			List<ColumnOrSuperColumn> results = client.get_slice(toByteBuffer(queryWord), parent, predicate, ConsistencyLevel.ONE);
			for (ColumnOrSuperColumn result : results) {
				SuperColumn superColumn2 = result.super_column;
				
				List<Column> columns2 = superColumn2.getColumns();
				
				System.out.println(new String(superColumn2.getName(), "UTF-8"));
				
				for (Column column : columns2) {
					System.out.println(new String(column.getName(), "UTF-8") + " -> "+ new String(column.getValue(), "UTF-8"));
				}
				System.out.println("----------------------------------------");
			}
			
			tr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Query from cf [RecordMinute]
	 * 	to GetCassandraInfo function
	 * @param queryWord
	 */
	public void QueryRecordMinuteTwo(String queryWord, FileWriter writer) {
		TTransport tr = new TFramedTransport(new TSocket("10.15.61.118", 9160));
		TProtocol proto = new TBinaryProtocol(tr);
		Cassandra.Client client = new Cassandra.Client(proto);

		try {
			tr.open();
			client.set_keyspace("CadalSecTest");
			
			// read entire row
			SlicePredicate predicate = new SlicePredicate();//null, new SliceRange(new byte[0], new byte[0], false, 10)
			SliceRange range = new SliceRange();
			range.start = toByteBuffer("");
			range.finish = toByteBuffer("");
			predicate.setSlice_range(range);
			
			ColumnParent parent = new ColumnParent();
			parent.column_family = "RecordMinute";
			
			List<ColumnOrSuperColumn> results = client.get_slice(toByteBuffer(queryWord), parent, predicate, ConsistencyLevel.ONE);
			String strTmp = "";
			for (ColumnOrSuperColumn result : results) {
				SuperColumn superColumn2 = result.super_column;
				
				List<Column> columns2 = superColumn2.getColumns();

				for (Column column : columns2) {
					if(new String(column.getName(), "UTF-8").equals("username") && new String(column.getValue(), "UTF-8").equals("None")) {
						strTmp = "";
						break;
					}
					strTmp += new String(column.getValue(), "UTF-8");
					strTmp += "####";
				}

				if(strTmp.length() < 1) {
					continue;
				}
				writer.write(strTmp);
				writer.write("\n");
				strTmp = "";
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
	 * 获得Cassandra收集的日记的信息
	 * @throws Exception 
	 */
	public void GetCassandraInfo(String startDatePar, String endDatePar, String fileName) throws Exception {
		String startDate = startDatePar;
		String   endDate = endDatePar;
		String   curDate = startDate;
		
		FileWriter writer = new FileWriter(fileName, true);  

		TimeOp to = new TimeOp();
		CassandraOp co = new CassandraOp();
		
		Date curDateFormat = to.getFormatDate(curDate);
		Date endDateFormat = to.getFormatDate(endDate);
		while(curDateFormat.compareTo(endDateFormat) < 0) {
			System.out.println(curDate);
			co.QueryRecordMinuteTwo(curDate, writer);
			
			curDate = to.getPreDate(curDateFormat, "m", 1);
			curDateFormat = to.getFormatDate(curDate);
		}
		
		writer.close();
		
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		CassandraOp co = new CassandraOp();
		
//		co.GetCassandraInfo("2013-03-19 12:00", "2013-06-05 12:00", "cassandraInfoWin.txt"); // "2013-03-19 12:00", "2013-06-05 12:00"
		co.QueryRecordMinute("2013-06-21 15:52");
	}
}