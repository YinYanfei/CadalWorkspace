package cn.edu.zju.cadal.kafka.api;

import java.util.ArrayList;
import java.util.List;

import kafka.javaapi.producer.ProducerData;

import org.apache.thrift.TException;

import cn.edu.zju.cadal.kafka.producer.BookViewProducer;
import cn.edu.zju.cadal.kafka.message.BookView;

public class KafkaProducerThriftImpl implements KafkaProducerThrift.Iface {
	private BookViewProducer bookViewProducer;
	//other producer
	
	public KafkaProducerThriftImpl(){
		this.bookViewProducer = new BookViewProducer( "cn.edu.zju.cadal.kafka.message.BookViewMessage");
	}
	@Override
	public void SendMessage(int type, String message) throws TException {
		//将接收到的数据通过producer发送给broker
		try{
		System.out.println(type + " - " + message);
		System.out.println("aaaaaaaaaaaa");
		if(type==1){ //bookViewMessage
			BookView bookView = new BookView(message);
			System.out.println("receive message from python client: " + message);
			List<BookView> msg = new ArrayList<BookView>();
			msg.add(bookView);

			ProducerData<String, BookView> data = new ProducerData<String, BookView>("bookView", msg);
			bookViewProducer.producer.send(data);
			System.out.println("send message to broker client: " + message);
		}
		else if(type==2){
			System.out.println("bbbbbbbbbbb");
		}
		else{
			System.out.println("in else...");
		}
	}catch(Exception e){
		e.printStackTrace();
	}
	}
}
