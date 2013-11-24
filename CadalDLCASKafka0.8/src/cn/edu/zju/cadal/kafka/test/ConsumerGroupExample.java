package cn.edu.zju.cadal.kafka.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class ConsumerGroupExample {
	private final ConsumerConnector consumer;
	private final String topic;
	private ExecutorService executor;
	
	public ConsumerGroupExample(String a_zookeeper, String a_groupId,String a_topic){
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig(a_zookeeper,a_groupId));
		this.topic = a_topic;
	}
	
	private static ConsumerConfig createConsumerConfig(String a_zookeeper,
			String a_groupId) {
		Properties props = new Properties();
		props.put("zookeeper.connect",a_zookeeper);
		props.put("group.id",a_groupId);
		props.put("zookeeper.session.timeout.ms","10000");
		props.put("zookeeper.sync.time.ms","200");
		props.put("auto.commit.interval.ms","1000");
		return new ConsumerConfig(props);
	}

	public void run(int a_numThreads){
		Map<String,Integer> topicCountMap = new HashMap<String,Integer>();
		topicCountMap.put(topic,new Integer(a_numThreads));
		Map<String,List<KafkaStream<byte[],byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
		
		executor = Executors.newFixedThreadPool(a_numThreads);
		
		int threadNumber = 0;
		
		for(final KafkaStream stream : streams){
			executor.submit(new ConsumerTest(stream,threadNumber));
			threadNumber ++;
		}
	}

	public static void main(String[] args) {
		String zookeeper = "10.15.62.75:2181,10.15.62.76:2181,10.15.62.77:2181";
		String groupId = "group10";
		String topic = "my-topic-10";
		int threads = 4;
		
		ConsumerGroupExample example = new ConsumerGroupExample(zookeeper,groupId,topic);
		example.run(threads);
	}
}
