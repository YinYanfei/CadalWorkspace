package cn.cadal.dis.java.cassandra;

import java.util.Date;
import java.util.List;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import cn.cadal.dis.java.utils.TimeOp;
import cn.cadal.dis.java.utils.Utils;

public class CasTimePV {
	
	private TimeOp timeOp = null;
	
	public CasTimePV() {
		this.timeOp = new TimeOp();
	}

	/**
	 * Query from cf [RecordMinute]
	 * @param queryWord
	 * @return
	 */
	public int QueryRecordMinute(String queryWord) {
		TTransport tr = new TFramedTransport(new TSocket("10.15.61.118", 9160));
		TProtocol proto = new TBinaryProtocol(tr);
		Cassandra.Client client = new Cassandra.Client(proto);

		try {
			tr.open();
			client.set_keyspace("CadalSecTest");

			// read entire row
			SlicePredicate predicate = new SlicePredicate();// new SliceRange(new byte[0], new byte[0], false, 10)
			SliceRange range = new SliceRange();
			range.start = Utils.toByteBuffer("");
			range.finish = Utils.toByteBuffer("");
			predicate.setSlice_range(range);

			ColumnParent parent = new ColumnParent();
			parent.column_family = "RecordMinute";

			List<ColumnOrSuperColumn> results = client.get_slice(Utils.toByteBuffer(queryWord), parent, predicate, ConsistencyLevel.ONE);
			
			tr.close();
			
			return results.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * Query from cf [RecordMinute]
	 * @param queryTimeStart
	 * @param queryTimeEnd
	 * @return
	 */
	public int QueryRecordTimeSlide(String queryTimeStart, String queryTimeEnd) {
		TTransport tr = new TFramedTransport(new TSocket("10.15.61.118", 9160));
		TProtocol proto = new TBinaryProtocol(tr);
		Cassandra.Client client = new Cassandra.Client(proto);

		Date dateQuery = this.timeOp.getFormatDate(queryTimeStart);
		Date dateEnd   = this.timeOp.getFormatDate(queryTimeEnd);
		
		int count = 0;
		
		try {
			tr.open();
			client.set_keyspace("CadalSecTest");

			// read entire row
			SlicePredicate predicate = new SlicePredicate();// new SliceRange(new byte[0], new byte[0], false, 10)
			SliceRange range = new SliceRange();
			range.start = Utils.toByteBuffer("");
			range.finish = Utils.toByteBuffer("");
			predicate.setSlice_range(range);

			ColumnParent parent = new ColumnParent();
			parent.column_family = "RecordMinute";
			
			String queryWord = this.timeOp.getFormatDate(dateQuery);
			
			while(dateQuery.before(dateEnd)) {
				List<ColumnOrSuperColumn> results = client.get_slice(Utils.toByteBuffer(queryWord), parent, predicate, ConsistencyLevel.ONE);
				count += results.size();
				dateQuery = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateQuery, "m", 1));
				queryWord = this.timeOp.getFormatDate(dateQuery);
			}
			
			tr.close();
			
			return count;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		// String strTmp =
		// "290.15.6.200$Yanfei$07018720$00000180$2013-03-13 22:07:02.909009";

		CasTimePV co = new CasTimePV();

		// Insert into cf [RecordMinute]
		// co.InsertRecordMinute(strTmp);

		// Query from cf [RecordMinute]
		//System.out.println(co.QueryRecordMinute("2013-04-01 10:00"));
		
		System.out.println(co.QueryRecordTimeSlide("2013-04-09 09:00", "2013-04-09 09:10"));
	}

}
