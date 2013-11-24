package cn.edu.zju.cadal.test.kafka;

import java.nio.ByteBuffer;
import java.util.Properties;

import kafka.api.FetchRequest;
import kafka.javaapi.consumer.SimpleConsumer;
import kafka.javaapi.message.ByteBufferMessageSet;
import kafka.message.MessageAndOffset;

public class mySimpleConsumer {
	//low level api, ���Ե�ָ����broker�ϵ�ָ����topic��ָ����partition��ȥpull���� ����ָ��offset
	public static void main(String[] args) {
		Properties props = new Properties();
//	props.put("zk.connect","10.15.62.76:2181");
//		props.put("groupid","testgroup");
		
		SimpleConsumer consumer = new SimpleConsumer("10.15.62.70",9092,10000,1024000);
		long offset =  0; 
		int count = 0;
		String str1 = "";
	//	while(true){
			FetchRequest fetchRequest  = new FetchRequest("topic1114",3,offset,10000000);//���һ��������һ���������ݵ������byte
			ByteBufferMessageSet messages = consumer.fetch(fetchRequest);
			for(MessageAndOffset msg  :messages){
				count++;
				ByteBuffer buffer = msg.message().payload();
				byte[] bytes = new byte[buffer.remaining()];
				buffer.get(bytes);
				String str = new String(bytes);
				System.out.println(str);
				offset = msg.offset();
				System.out.println("offset: " + offset);
			}
			System.out.println("------------ count= " + count);
	//	}
	}
}
