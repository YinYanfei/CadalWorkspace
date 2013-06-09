package cn.cadal.storm.rec.cassandra;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Connector {
	
	private TTransport tr = new TSocket("10.15.61.118", 9160);
	private String KEYSPACE = "CadalRec";
	
	/**
	 * Construct
	 */
	public Connector() {
	}
	
	public Connector(String keyspace) {
		this.KEYSPACE = keyspace;
	}
	
	/**
	 * Connect cassandra and open Transport variable(tr)
	 */
	public Cassandra.Client connect(){
		try{
			TFramedTransport tf = new TFramedTransport(tr);
			TProtocol proto = new TBinaryProtocol(tf);
			Cassandra.Client client = new Cassandra.Client(proto);
			tr.open();
	
			client.set_keyspace(KEYSPACE);
			return client;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Close Transport variable -- tr
	 */
	public void finalize(){
		this.tr.close();
	}
	
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
