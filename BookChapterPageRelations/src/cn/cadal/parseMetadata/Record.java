package cn.cadal.parseMetadata;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.Mutation;
import org.apache.cassandra.thrift.SuperColumn;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import cn.cadal.util.StackTraceUtil;


/**
 * 代表每条要插入数据库的记录
 * @author zhangpeng
 *
 */
public class Record {
	static private final Log LOG = LogFactory.getLog(Record.class);
	static final Cassandra.Client CLIENT;
	//获取cassandra连接
	static{
		TTransport tr = new TFramedTransport(new TSocket("10.15.61.112",9160));;
		TProtocol proto = new TBinaryProtocol(tr);
		CLIENT = new Cassandra.Client(proto);
		boolean flag=true;
		while(flag){
			try{
				tr.open();
				flag=false;
			}catch (Exception e) {
				LOG.warn(StackTraceUtil.getStackTrace(e));
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
					LOG.warn(StackTraceUtil.getStackTrace(e1));
				}
			}
		}
		try {
			CLIENT.set_keyspace("Keyspace2");
		} catch (Exception e) {
			e.printStackTrace();
			LOG.warn(StackTraceUtil.getStackTrace(e));
		}
	}
	
	
	String bookId;
	String chapterLevel;
	String chapterLabel;
	int startPage;
	int endPage;
	
	public Record(String BookId,String ChapterLevel,String ChapterLabel,int StartPage,int EndPage){
		this.bookId=BookId;
		this.chapterLevel=ChapterLevel;
		this.chapterLabel=ChapterLabel;
		this.startPage=StartPage;
		this.endPage=EndPage;
	}

	public void setEndPage(int endPage){
		this.endPage = endPage;
	}
	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("BookId = " + bookId + " ");
		sb.append("ChapterLevel = " + chapterLevel + " ");
		sb.append("ChapterLabel = "+ chapterLabel + " ");
		sb.append("StartPage = " + startPage + " ");
		sb.append("EndPage = " + endPage );
		return sb.toString();
	}
	/**
	 * Cassandra Column Structure Example:
	 * 
	 * ColumnFamily: test
	 * Key        |||  SuperColumn
	 *                 super key
	 * 10101011   |||  1.1.1.1    ||   ChapterLabel:总则     |   ChapterLevel：1.1.1.1 | StartPage:1 | EndPage:101
	 * 07018720   |||  1.0.0.0    ||   ChapterLabel:绪论     |   ChapterLevel：1.0.0.0 | StartPage:1 | EndPage:95
	 * @throws TException 
	 * @throws TimedOutException 
	 * @throws UnavailableException 
	 * @throws InvalidRequestException 
	 * 
	 */
	
	public void insertToDB() {
		String key_name = this.bookId; 
		String super_key_name = this.chapterLevel;
		String ColumnFamilyName = "BCPRelation";
/*		TTransport tr = new TFramedTransport(new TSocket("10.15.61.111",9160));;
		
		try{
			//Connect Cassandra via Thrift 
			TProtocol proto = new TBinaryProtocol(tr);
			Cassandra.Client client = new Cassandra.Client(proto);
			boolean flag=true;
			while(flag){
				try{
					tr.open();
					flag=false;
				}catch (Exception e) {
					LOG.warn(StackTraceUtil.getStackTrace(e));
					Thread.sleep(3000);
				}
			}
			client.set_keyspace("Keyspace2");//要操作的test 在 Keyspace2下面
*/			
			Map<ByteBuffer,Map<String,List<Mutation>>> outerMap = 
				new HashMap<ByteBuffer, Map<String,List<Mutation>>>();
			List<Mutation> columnToAdd = new ArrayList<Mutation>();
			
			List<Column> cols = new ArrayList<Column>();
			cols.add(createColumn( "ChapterLabel",this.chapterLabel));
			cols.add(createColumn("ChapterLevel",this.chapterLevel));
			cols.add(createColumn("StartPage",Integer.toString(this.startPage)));
			cols.add(createColumn("EndPage",Integer.toString(this.endPage)));
			
			Map<String, List<Mutation>> innerMap = new HashMap<String, List<Mutation>>();
			
			SuperColumn superColumn = new SuperColumn();
			superColumn.name = toByteBuffer(super_key_name);
			superColumn.columns = cols;

			ColumnOrSuperColumn columnOrSuperColumn = new ColumnOrSuperColumn();
			columnOrSuperColumn.super_column = superColumn;
			
			Mutation columns = new Mutation();
			columns.setColumn_or_supercolumn(columnOrSuperColumn);
			columnToAdd.add(columns);

			innerMap.put(ColumnFamilyName, columnToAdd);
			outerMap.put(toByteBuffer(key_name), innerMap);
			synchronized (CLIENT) {
				try {
					CLIENT.batch_mutate(outerMap, ConsistencyLevel.ONE);
				} catch (Exception e) {
					LOG.warn("Error in insert into Cassandra DB, BookNo is: "+key_name+" Chapter Level is: "+super_key_name);
					LOG.warn(StackTraceUtil.getStackTrace(e));
					System.out.println("Error in insert into Cassandra DB, BookNo is: "+key_name+" Chapter Level is："+super_key_name);
				}
			}
	}
	
	//Create a column. A column consists of a columnName, its corresponding columnValue and timeStamp 
	private Column createColumn(String name,String value){
		long timeStamp = System.currentTimeMillis();
		Column clm  = new Column();
		clm.setName(toByteBuffer(name));
		clm.setValue(toByteBuffer(value));
		clm.setTimestamp(timeStamp);
		return clm;
	}
	
	//Convert String to ByteBuffer in order in order to insert into Cassandra
	public ByteBuffer toByteBuffer(String value) {
		try {
			return ByteBuffer.wrap(value.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
