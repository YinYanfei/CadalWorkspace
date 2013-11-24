package cn.edu.zju.cadal.kafka.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class myConsumer {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("zookeeper.connect","10.15.62.76:2181");
		props.put("group.id","mygroup001");
		props.put("zookeeper.session.timeout.ms","40000");
		props.put("zookeeper.sync.time.ms","200");
		props.put("auto.commit.interval.ms","1000");
		
		ConsumerConfig consumerConfig = new ConsumerConfig(props);
		
		ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);
		
		Map<String,Integer> topicCountMap = new HashMap<String,Integer>();
		topicCountMap.put("my-topic",new Integer(1));
		System.out.println("zzzzzzzzzzzzz");
		Map<String,List<KafkaStream<byte[],byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get("my-topic");
		
		KafkaStream<byte[], byte[]> stream = streams.get(0);
		
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		System.out.println("before while...");
		while(it.hasNext()){
			System.out.println(new String(it.next().message()));
		}
	}
}
