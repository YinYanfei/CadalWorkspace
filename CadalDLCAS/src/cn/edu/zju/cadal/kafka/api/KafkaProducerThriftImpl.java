package cn.edu.zju.cadal.kafka.api;

import org.apache.thrift.TException;

public class KafkaProducerThriftImpl implements KafkaProducerThrift.Iface {
	
	@Override
	public void SendMessage(int type, String message) throws TException {
		System.out.println(type + " -- " + message);
		
	}

}
