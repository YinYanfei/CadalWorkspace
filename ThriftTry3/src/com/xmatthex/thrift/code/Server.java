package com.xmatthex.thrift.code;

import java.nio.ByteBuffer;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TFramedTransport.Factory;

import com.sleepycat.je.rep.elections.Protocol.Value;
import com.xmatthew.thrift.demo.DemoService;
import com.xmatthew.thrift.demo.DemoService.Iface;
import com.xmatthew.thrift.method.FunClass;

public class Server implements Iface{

	private final int port;
	private final TThreadPoolServer tr_server;
	
	//
	private FunClass fc = new FunClass();
	//
	
	public Server(int _port) throws Exception {
		this.port = _port;
		org.apache.thrift.protocol.TBinaryProtocol.Factory protoFactory = new TBinaryProtocol.Factory(true, true);
		TServerTransport serverTransport = new TServerSocket(port);
		DemoService.Processor processor = new DemoService.Processor<Iface>(this);
		tr_server = new TThreadPoolServer(new Args(serverTransport).processor(processor)
				.protocolFactory(protoFactory));
	}
	
	public void run(){ 
		tr_server.serve();
	}
	
	public synchronized void close() {
		tr_server.stop();
	}
	
	//
	public void add(String key, ByteBuffer value) throws TException {
		fc.add(key, value);
		// System.out.println("invoke 'add'(" + key + ", " + new String(value.array()) + ")");
	}

	public ByteBuffer get(String key) throws TException {
		/*
		System.out.println("invoke 'set'(" + key + ")");
		ByteBuffer bb = ByteBuffer.wrap("get success".getBytes());
		return bb;
		*/
		return fc.get(key);
	}
	//
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		Server server = new Server(8900);
		server.run();

	}

}
