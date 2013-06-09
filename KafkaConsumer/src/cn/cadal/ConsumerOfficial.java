package cn.cadal;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.Message;
import java.util.List;
import java.util.Map;


public class ConsumerOfficial {

	
	
	public void consume(){
		// specify some consumer properties
		Properties props = new Properties();
		props.put("zk.connect", "localhost:2181");
		props.put("zk.connectiontimeout.ms", "1000000");
		props.put("groupid", "test_group");

		// Create the connection to the cluster
		ConsumerConfig consumerConfig = new ConsumerConfig(props);
		ConsumerConnector consumerConnector = Consumer.createJavaConsumerConnector(consumerConfig);

		// create 4 partitions of the stream for topic ¡°test¡±, to allow 4 threads to consume
		Map<String, List<KafkaStream<Message>>> topicMessageStreams = 
		    consumerConnector.createMessageStreams(ImmutableMap.of("test", 4));
		List<KafkaStream<Message>> streams = topicMessageStreams.get("test");

		// create list of 4 threads to consume from each of the partitions 
		ExecutorService executor = Executors.newFixedThreadPool(4);

		// consume the messages in the threads
		for(final KafkaStream<Message> stream: streams) {
		  executor.submit(new Runnable() {
		    public void run() {
		      for(MessageAndMetadata msgAndMetadata: stream) {
		        // process message (msgAndMetadata.message())
		      }	
		    }
		  });
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
