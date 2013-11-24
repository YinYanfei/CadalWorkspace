package cn.edu.zju.cadal.kafka.api;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class KafkaProducerServer {

	private void start() {
		try {
			TServerSocket serverTransport = new TServerSocket(7911);
			KafkaProducerThrift.Processor processor = new KafkaProducerThrift.Processor(new KafkaProducerThriftImpl());
			Factory factory = new TBinaryProtocol.Factory(true, true);
			
			Args args = new Args(serverTransport);
			args.processor(processor);
			args.protocolFactory(factory);
			TServer server = new TThreadPoolServer(args);
			server.serve();
			
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		KafkaProducerServer srv = new KafkaProducerServer();
		System.out.println("Thrift server start...");
		srv.start();
		
	}
}
