package cn.cadal.storm.demo.thriftClient;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class Client {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TTransport transport;
		try {
			transport=new TSocket("10.15.62.107",8585);
			TProtocol protocol = new TBinaryProtocol(transport);
			RecServer.Client clients=new RecServer.Client(protocol);
			transport.open();

			System.out.println(clients.GetRecInfo("1", "10"));
			
			transport.close();
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}


	}

}
