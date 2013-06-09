package project.cadal;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import project.cadal.Recommend.Processor;

public class Server {
	public void startServer(){
		
		try {
/*			serverTransport = new TServerSocket(8585);
			Args args = new Args(serverTransport);
			Factory portFactory = new TBinaryProtocol.Factory(true, true);
			args.protocolFactory(portFactory);	
			Recommend.Processor process=new Processor(new RecommendServer());
			args.processor(process);
			
			TServer server = new TThreadPoolServer(args);
*/
			TServerSocket serverTransport = new TServerSocket(8585);
			Recommend.Processor processor = new Recommend.Processor(new RecommendServer());
			Factory protFactory = new TBinaryProtocol.Factory(true, true);
			
			Args args = new Args(serverTransport);
			args.processor(processor);
			args.protocolFactory(protFactory);

			TServer server = new TThreadPoolServer(args);

			server.serve();
		} catch (TTransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
