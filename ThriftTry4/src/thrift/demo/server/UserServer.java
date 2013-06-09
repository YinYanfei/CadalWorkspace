package thrift.demo.server;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import thrift.demo.gen.UserService;
import thrift.demo.gen.UserService.Iface;

public class UserServer {

	public final static int PORT = 9633;
	/**
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		try{
			/*
			TNonblockingServerSocket socket = new TNonblockingServerSocket(PORT);
			final UserService.Processor processor = 
				new UserService.Processor<Iface>(new UserServerHandler());
			THsHaServer.Args arg = new THsHaServer.Args(socket);
			*/
			/*
			org.apache.thrift.protocol.TBinaryProtocol.Factory protoFactory = new TBinaryProtocol.Factory(true, true);
			TServerTransport serverTransport = new TServerSocket(PORT);
			UserService.Processor processor = new UserService.Processor<Iface>(new UserServerHandler());
			THsHaServer.Args arg = new THsHaServer.Args((TNonblockingServerTransport) serverTransport);
			
			arg.protocolFactory(new TCompactProtocol.Factory());
			arg.transportFactory(new TFramedTransport.Factory());
			arg.processorFactory(new TProcessorFactory(processor));
			*/
			TServerSocket serverTransport = new TServerSocket(PORT);
			UserService.Processor processor = new UserService.Processor(new UserServerHandler());
			Factory protFactory = new TBinaryProtocol.Factory(true, true);
			
			Args arg = new Args(serverTransport);
			arg.processor(processor);
			arg.protocolFactory(protFactory);

			TServer server = new TThreadPoolServer(arg);

			// TServer server = new THsHaServer(arg);
			
			System.out.println("service begin...");
			server.serve();
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println("UserServer.java main function");
		}

	}

}
