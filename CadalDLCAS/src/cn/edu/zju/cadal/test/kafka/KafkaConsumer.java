package cn.edu.zju.cadal.test.kafka;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaMessageStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.Message;

import org.apache.log4j.Logger;

//high level consumer api, 只要连接到zookeeper,指定到某一个topic去pull数据即可。kafka内部会自动balance
public class KafkaConsumer {
	public static void main(String[] args) {
		Logger logger = null;
		logger = Logger.getLogger(KafkaConsumer.class.getClass().getName());
		Properties props = new Properties();
		props.put("zk.connect", "10.15.62.76:2181");
		props.put("groupid", "groupbookview");		// 
		props.put("auto.commit.enable","true");
		props.put("autooffset.reset","smallest");
		props.put("auto.commit.interval.ms","3000");
		
		ConsumerConfig consumerConfig = new ConsumerConfig(props);
		ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("bookView", new Integer(1));		// 第二个参数是指用几个流，多个流是为了并行处理。

		Map<String, List<KafkaMessageStream>> consumerMap = consumer.createMessageStreams(topicCountMap);

		KafkaMessageStream stream = consumerMap.get("bookView").get(0);		// 这里只有一个流，所以用get(0)就可以了。
		/*
		 * 
		 * This API is centered around iterators, implemented by the KafkaStream class.
		 * Each KafkaStream represents the stream of messages from one or more partitions on one or more servers.
		 * Each stream is used for single threaded processing, so the client can provide the number of
		 * desired streams in the create call. Thus a stream may represent the merging of multiple 
		 * server partitions (to correspond to the number of processing threads), 
		 * but each partition only goes to one stream.
		 */
		ConsumerIterator it = stream.iterator();
		
		Message message;
		int count = 0;
		while (it.hasNext()) {
			count++;
			message = it.next();
			ByteBuffer buffer = message.payload();
			byte[] bytes = new byte[buffer.remaining()];
			buffer.get(bytes);
			String tmp = new String(bytes);
			System.out.println(count + "  " + tmp);
		}
		System.out.println("count = " + count);
	}
	
}
