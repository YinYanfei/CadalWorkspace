package cn.cadal.storm.exp.cassandra;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cadal.storm.exp.cassandraUtil.CassandraUtil;
import cn.cadal.storm.exp.cassandraUtil.Connector;

public class QueryCadalRec {
	
	static private final Log LOG = LogFactory.getLog(QueryCadalRec.class);
	
	private Connector connector = null;
	private Cassandra.Client client = null;
	private CassandraUtil cassandraUtil = null;
	
	public QueryCadalRec() {
		try{
			this.connector = new Connector();
			this.client = this.connector.connect();
			this.cassandraUtil = new CassandraUtil();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() {
		this.connector.finalize();
	}

	/**
	 * This function is used to get bookid + chapter, just like "07018720_1.2.0.0.0"
	 * 
	 * @param signal: just like 241
	 * @return
	 */
	public String QueryFromSignalChapterMap(int signal){

		String content = "";
		
		try {
			ColumnPath columnPath = new ColumnPath();
			columnPath.column_family = "SignalChapterMap";
			columnPath.column = this.cassandraUtil.toByteBuffer("content");
			
			ColumnOrSuperColumn columnOrSuperColumn = client.get(this.cassandraUtil.toByteBuffer(String.valueOf(signal)), columnPath, ConsistencyLevel.ONE);
			Column column = columnOrSuperColumn.getColumn();
			
			content = new String(column.getValue(), "UTF-8");
			
			return content;
			
		} catch (Exception e) {
			LOG.warn("Signal: " + signal + ", Can not find bookid+chapter from Cassandra CadalRec.");
		}
		
		return content;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		QueryCadalRec qcr = new QueryCadalRec();
//		
//		System.out.println(qcr.QueryFromSignalChapterMap(900000003));
	}
}
