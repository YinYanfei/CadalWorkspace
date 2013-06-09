package cn.cadal.dis.java.cassandra;

import java.util.ArrayList;
import java.util.List;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.thrift.SuperColumn;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import cn.cadal.dis.java.utils.Utils;

public class CasTimeBook {
	public Cassandra.Client client;
	public SlicePredicate predicate;
	public ColumnParent columnParent;
	public KeyRange keyRange;

	public CasTimeBook() {
		try {
			TTransport tr = new TFramedTransport(new TSocket("10.15.61.111",
					9160));
			TProtocol proto = new TBinaryProtocol(tr);
			client = new Cassandra.Client(proto);
			tr.open();

			client.set_keyspace("CadalSecTest");

			predicate = new SlicePredicate();
			SliceRange range = new SliceRange();
			range.setStart(new byte[0]);
			range.setFinish(new byte[0]);
			range.setCount(10000);
			predicate.setSlice_range(range);

			columnParent = new ColumnParent();
			columnParent.setColumn_family("RecordMinute");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public List<String> QueryOneMinute(String min){
		List<String> bookList = new ArrayList<String>();
		try {
			List<ColumnOrSuperColumn> results = client.get_slice(Utils
					.toByteBuffer(min), columnParent, predicate,
					ConsistencyLevel.ONE);
			for (ColumnOrSuperColumn cc : results) {
				SuperColumn superColumn = cc.getSuper_column();
				List<Column> list = superColumn.getColumns();
				for (Column c : list) {
					String columnName = new String(c.getName(), "UTF-8");
					if (columnName.equals("bookno")) {
						String value = new String(c.getValue(), "UTF-8");
						if (!bookList.contains(value)) {
							bookList.add(value);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return bookList;
	}
	
	public static void main(String[] args){
		CasTimeBook ctb = new CasTimeBook();
		List<String> list = new ArrayList<String>();
		list = ctb.QueryOneMinute("2013-04-10 14:34");
		for(String str:list){
			System.out.println(str);
		}
	}
}
