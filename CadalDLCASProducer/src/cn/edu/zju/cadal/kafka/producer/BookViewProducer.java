package cn.edu.zju.cadal.kafka.producer;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import cn.edu.zju.cadal.kafka.message.BookView;
public class BookViewProducer {
	public Producer<String, BookView> producer;
	public BookViewProducer(String serializerClass){
		Properties props = new Properties();
		props.put("zk.connect","10.15.62.76:2181,10.15.62.75:2181,10.15.62.77:2181");
		props.put("serializer.class", serializerClass); // 选择用哪个类来进行序列化,这里用的是自定义的序列化类BookViewMessage
//		props.put("partitioner.class","cn.edu.zju.cadal.test.kafka.ProducerPartitioner");
		props.put("zk.connectiontimeout.ms", "6000");
		ProducerConfig config = new ProducerConfig(props);
		
		producer = new Producer<String, BookView>(config);
	}
	
	public static void main(String[] args) {
		
	}

}
