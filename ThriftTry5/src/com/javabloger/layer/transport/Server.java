package com.javabloger.layer.transport;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TCompactProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.server.TThreadPoolServer.Args;

import com.javabloger.layer.business.BusinessImpl;

import  com.javabloger.gen.code.ThriftCase;

public class Server {
	private void start() {
		try {
			TServerSocket serverTransport = new TServerSocket(9632);
			ThriftCase.Processor processor = new ThriftCase.Processor( new BusinessImpl() );
			Factory protFactory = new TCompactProtocol.Factory();
			
			Args arg = new Args(serverTransport);
			arg.processor(processor);
			arg.protocolFactory(protFactory);
			
			TServer server = new TThreadPoolServer(arg);
			
			System.out.println("Starting server on port 9632 ...");
			server.serve();

		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Server srv = new Server();
		srv.start();
	}
}
