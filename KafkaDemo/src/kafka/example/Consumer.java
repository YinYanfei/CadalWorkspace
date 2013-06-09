package kafka.example;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaMessageStream;
import kafka.example.ExampleUtils;
import kafka.example.KafkaProperties;
import kafka.javaapi.consumer.ConsumerConnector;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class Consumer extends Thread {

	private final ConsumerConnector consumer;
	private final String topic;

	public Consumer(String topic) {
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(createConsumerConfig());
		this.topic = topic;
	}

	private static ConsumerConfig createConsumerConfig() {
		Properties props = new Properties();
		props.put("zk.connect", KafkaProperties.zkConnect);
		props.put("groupid", KafkaProperties.groupId);
		props.put("zk.sessiontimeout.ms", "5000");
		props.put("zk.synctime.ms", "2000");
		props.put("autocommit.interval.ms", "1000");

		return new ConsumerConfig(props);
	}

	public void run() {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaMessageStream>> consumerMap = consumer.createMessageStreams(topicCountMap);
		KafkaMessageStream stream = consumerMap.get(topic).get(0);
		ConsumerIterator it = stream.iterator();
		String strLine = "";
		String strCh = "";
		while (it.hasNext()){
			if((strCh = ExampleUtils.getMessage(it.next())).equals("#")){
				System.out.println(strLine);
				strLine = "";
			}else{
				strLine += strCh;
			}
		}
	}
}


