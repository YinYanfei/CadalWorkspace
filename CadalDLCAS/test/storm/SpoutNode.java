package cn.edu.zju.cadal.test.storm;

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
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class SpoutNode extends BaseRichSpout {

	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector _collector = null;
	private ConsumerIterator it = null;

	
	public void KafkaInit(){
		Properties props = new Properties();
		props.put("zk.connect", "10.15.62.75:2181,10.15.62.76:2181,10.15.62.77:2181");
		props.put("groupid", "group1114");
		props.put("autooffset.reset","smallest");
		
		ConsumerConfig consumerConfig = new ConsumerConfig(props);
		ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("topic1114", new Integer(1));// 第二个参数是指用几个流，多个流是为了并行处理。

		Map<String, List<KafkaMessageStream>> consumerMap = consumer.createMessageStreams(topicCountMap);

		KafkaMessageStream stream = consumerMap.get("topic1114").get(0);// 这里只有一个流，所以得get(0)就可以了。
		this.it = stream.iterator();
	}
	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this._collector = collector;
		KafkaInit();
	}

	@Override
	public void nextTuple() {
		Message message;
		if (it.hasNext()) {
			message = it.next();
			ByteBuffer buffer = message.payload();
			byte[] bytes = new byte[buffer.remaining()];
			buffer.get(bytes);
			String emitvalue = new String(bytes);
			
			_collector.emit(new Values(emitvalue));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sentence"));
	}
}
