package cn.cadal;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.thrift.SuperColumn;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class PageChapterMatch {
	String bookNo;
	int pageNo;
	static final Cassandra.Client client;
	static final TTransport tr;

	public PageChapterMatch(String bookno, int pageno) {
		this.bookNo = bookno;
		this.pageNo = pageno;
	}
	
	public void setBookNo(String bookNo){
		this.bookNo = bookNo;
	}
	public void setPageNo(int pageNo){
		this.pageNo = pageNo;
	}
	
	static {
		tr = new TFramedTransport(new TSocket("10.15.61.111", 9160));
		TProtocol proto = new TBinaryProtocol(tr);
		client = new Cassandra.Client(proto);
		try {
			tr.open();
		} catch (TTransportException e) { 
			e.printStackTrace();
		}
		try {
			client.set_keyspace("Keyspace2");
		} catch (InvalidRequestException e) { 
			e.printStackTrace();
		} catch (TException e) { 
			e.printStackTrace();
		}
 
	}
	
	public void getChapterInfo() {
			SlicePredicate predicate = new SlicePredicate();// null, new
			SliceRange range = new SliceRange();
			
			range.setStart(new byte[0]);
			range.setFinish(new byte[0]); 
			
			predicate.setSlice_range(range);

			ColumnParent parent = new ColumnParent();
			parent.column_family = "BCPRelation";

			try {
				List<ColumnOrSuperColumn> results = client.get_slice(toByteBuffer(bookNo), parent, predicate,ConsistencyLevel.ONE);

				for (ColumnOrSuperColumn result : results) {
					SuperColumn superColumn2 = result.super_column;
					
					System.out.println(new String(superColumn2.getName(), "UTF-8"));
					
					List<Column> columns2 = superColumn2.getColumns();

					String chapterName = null;
					int startPage = 0;
					int endPage = 0;
					String chapterLevel = "";
					
					for (Column column : columns2) {
						String columnName = new String(column.getName(), "UTF-8");
												
						if (columnName.equalsIgnoreCase("ChapterLevel")) {
							chapterLevel = new String(column.getValue(), "UTF-8");
						} else if (columnName.equalsIgnoreCase("StartPage")) {
							startPage = Integer.parseInt(new String(column.getValue(), "UTF-8"));
						} else if (columnName.equalsIgnoreCase("EndPage")) {
							endPage = Integer.parseInt(new String(column.getValue(), "UTF-8"));
						} else if (columnName.equalsIgnoreCase("ChapterLabel")) {
							chapterName = new String(column.getValue(), "UTF-8");
						}
					}
					
					if (MatchRange(pageNo, startPage, endPage)) { 
						   System.out.println("BookNo:" + bookNo + "   PageNo:" + pageNo + "  in[" + chapterName + "]:" 
								   + " StartPage:" + startPage + "  EndPage:" + endPage);
						   System.out.println(chapterLevel);
					}
				}
			} catch (Exception e) {
				 e.printStackTrace();
			}

//			tr.close();
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

	public boolean MatchRange(int pageNo, int start, int end) {
		if (pageNo >= start && pageNo <= end) {
			return true;
		} else
			return false;
	}
}
