package cn.cadal.rec.ol.thrift.server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TBinaryProtocol.Factory;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;

import cn.cadal.rec.ol.thrift.RecAPI;
import cn.cadal.rec.ol.thrift.RecAPIImp;

public class RecServer {

	/**
	 * Start thrift server
	 */
	@SuppressWarnings("unchecked")
	private void StartServer() {
		try{
			String DBName = "cadalrectest-77";
			
			TServerSocket serverTransport = new TServerSocket(7911);
			RecAPI.Processor processor = new RecAPI.Processor(new RecAPIImp(DBName));
			Factory factory = new TBinaryProtocol.Factory(true, true);
			
			Args args = new Args(serverTransport);
			args.processor(processor);
			args.protocolFactory(factory);
			
			TServer server = new TThreadPoolServer(args);
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			System.out.println("!!! CADAL new recommendation service is started !!!");
			System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
			
			server.serve();
			
		}catch(Exception e){
			e.printStackTrace();
			
			System.out.println("-------------------------------------");
			System.out.println("--- Thrift service can not start! ---");
			System.out.println("-------------------------------------");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Server start
		RecServer recServer = new RecServer();
		recServer.StartServer();
	}

}
