package com.test;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

public class TestService {
	private void start(){
		try {
			TServerSocket serverTransport = new TServerSocket(8899);
			ThriftCase.Processor processor = new ThriftCase.Processor(new ThriftCaseImpl());

			// TBinaryProtocol – 二进制编码格式进行数据传输。
			Factory protFactory = new TBinaryProtocol.Factory(true,true);

			//TCompactProtocol 这种协议非常有效的，使用Variable-Length Quantity (VLQ) 编码对数据进行压缩
			//Factory protFactory = new TCompactProtocol.Factory();

			Args args = new Args(serverTransport);
			args.processor(processor);
			args.protocolFactory(protFactory);

			TServer server = new TThreadPoolServer(args);
			System.out.println("Starting server on port 8899 ...");
			server.serve();
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		TestService srv = new TestService();
		srv.start();
	}
}
