package com.xmatthex.thrift.code;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;

public class ClientFactory {

	public static LocalClient getClient(String ip, int port) 
		throws TTransportException, IOException {
		TSocket transport = new TSocket(ip, port);
		TProtocol protocol = new TBinaryProtocol(transport);
		transport.open();
		LocalClient client = new LocalClient(protocol, port, transport);
		return client;
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws TException 
	 */
	public static void main(String[] args) throws IOException, TException {
		LocalClient client = ClientFactory.getClient("localhost", 8900);
		ByteBuffer bb = ByteBuffer.wrap("Hello".getBytes());
		client.add("abc", bb);						// 已定义的方法
		System.out.println("ok");
		System.out.println(new String(client.get("aaa").array()));		// 已定义的方法

	}

}
