package cn.cadal;


import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaMessageStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.Message;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



public class MyConsumer {
	static private final Log LOG = LogFactory.getLog(MyConsumer.class);
	/**
	 * @param args
	 */
	public MyConsumer() {
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Properties props = new Properties();
		props.put("zk.connect", "10.15.62.75:2181,10.15.62.76:2181,10.15.62.77:2181");
		props.put("groupid", "sec-group-1");

		ConsumerConfig consumerConfig = new ConsumerConfig(props);
		ConsumerConnector consumer = kafka.consumer.Consumer
				.createJavaConsumerConnector(consumerConfig);

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("sec-stream-one", new Integer(4));

		Map<String, List<KafkaMessageStream>> consumerMap = consumer
				.createMessageStreams(topicCountMap);

		KafkaMessageStream stream = consumerMap.get("sec-stream-one").get(0);
		ConsumerIterator it = stream.iterator();

		Message message;
		String str = "";
		while (it.hasNext()) {
			message = it.next();
			ByteBuffer buffer = message.payload();
			byte[] bytes = new byte[buffer.remaining()];
			buffer.get(bytes);
			
			String tmp = new String(bytes);
			
			if (tmp.equals("#")) {
				System.out.println(str);
				str = "";
			} else
				str += tmp;
		}

	}
}

