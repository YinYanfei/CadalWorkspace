package cn.cadal.storm.analyze.cassandraOp;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Connector {
	
	private TTransport tr = new TSocket("10.15.61.118", 9160);
	private String KEYSPACE = "CadalDB";
	
	/**
	 * Connect cassandra and open Transport variable(tr)
	 */
	public Cassandra.Client connect() throws Exception{
		TFramedTransport tf = new TFramedTransport(tr);
		TProtocol proto = new TBinaryProtocol(tf);
		Cassandra.Client client = new Cassandra.Client(proto);
		tr.open();

		client.set_keyspace(KEYSPACE);

		return client;
	}
	
	/**
	 * Close Transport variable -- tr
	 */
	public void finalize(){
		this.tr.close();
	}
	
//	public void close() {
//		this.tr.close();
//	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connector connector = new Connector();

		try {
			connector.connect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
