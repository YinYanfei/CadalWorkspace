package thrift.demo.client;

import org.apache.thrift.TApplicationException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import thrift.demo.gen.UserNotFound;
import thrift.demo.gen.UserService;

public class UserClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String address = "127.0.0.1";
		int port = 9633;
		
		TTransport transport = new TSocket(address, port);
		TProtocol protocol = new TBinaryProtocol(transport);
		UserService.Client client = new UserService.Client(protocol);
		
		try{
			transport.open();
			
			System.out.println(client.getUserByName("user1"));
		}catch(TApplicationException e) {
			System.out.println(e.getMessage() + " " + e.getType());
		}catch(TTransportException e) {
			e.printStackTrace();
		}catch(UserNotFound e) {
			e.printStackTrace();
		}catch(TException e) {
			e.printStackTrace();
		}finally{
			transport.close();
		}

	}

}
