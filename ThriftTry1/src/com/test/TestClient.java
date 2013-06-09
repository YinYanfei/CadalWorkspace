package com.test;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.test.ThriftCase.Client;

public class TestClient {
	private void start() {
        TTransport transport;
        try {
            transport = new TSocket("localhost", 8899);
            TProtocol protocol = new TBinaryProtocol(transport);
            Client client = new Client(protocol);
            
            UserRequest request=new UserRequest();
            request.setIdentitycard("32010619881231103X");
            
            transport.open();
            
            com.test.UserResponse urp=client.integralService(request);
            if(urp.code!=null&&!urp.code.equals("")){
            	System.out.println("返回代码："+urp.code+"; 参数是："+urp.params.get("integral"));
            }
            transport.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TestClient c = new TestClient();
            c.start();
    }

}
