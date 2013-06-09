package cn.cadal.storm.analyze.cassandra.query;

import org.apache.cassandra.thrift.Cassandra;

import cn.cadal.storm.analyze.cassandra.Util.CassandraUtil;
import cn.cadal.storm.analyze.cassandra.Util.Connector;

public class ParentQuery {
	
	protected Connector connector = null;
	protected CassandraUtil cassandraUtil = null;
	protected Cassandra.Client client = null;

	public ParentQuery() {
		this.connector = new Connector();
		this.client = this.connector.connect();
		this.cassandraUtil = new CassandraUtil();
	}
	
	/**
	 * Finalize function
	 */
	protected void finalize() {
		this.connector.finalize();
	}
}
