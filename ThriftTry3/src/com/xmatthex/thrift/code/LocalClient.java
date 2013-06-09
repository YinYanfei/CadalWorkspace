package com.xmatthex.thrift.code;

import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.xmatthew.thrift.demo.DemoService.Client;

public class LocalClient extends Client{

	private TTransport transport;
	
	public LocalClient(TProtocol prot, int port, TTransport ftransport) 
		throws TTransportException {
		super(prot);
		this.transport = ftransport;
	}
	
	public void close() {
		this.transport.close();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
