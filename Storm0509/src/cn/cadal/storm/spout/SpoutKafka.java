package cn.cadal.storm.spout;

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

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

public class SpoutKafka implements IRichSpout{

	/**
	 * SerialVersion nouse
	 */
	private static final long serialVersionUID = 1L;

	// Variables
	boolean _isDistributed;
	SpoutOutputCollector _collector;
	ConsumerIterator it = null;
	
	// Construct functions
	public SpoutKafka() {
		this(true);
	}
	public SpoutKafka(boolean isDistributed) {
		this._isDistributed = isDistributed;
	}
	
	@Override
	public void ack(Object msgId) {
		// Nothing to do		
	}

	@Override
	public void close() {
		// Nothing to do		
	}

	@Override
	public void fail(Object msgId) {
		// Nothing to do
		
	}

	@Override
	public void nextTuple() {
		// Importent
		Message message;
		String str = "";
		while (it.hasNext()) {
			message = it.next();
			ByteBuffer buffer = message.payload();
			byte[] bytes = new byte[buffer.remaining()];
			buffer.get(bytes);
			
			String tmp = new String(bytes);
			
			if (tmp.equals("#")) {
				this._collector.emit(new Values(str));
				str = "";
				break;
			} else {
				str += tmp;
			}
		}

		// 05-12
		// String tuple = "10.15.62.110 07018720 00000037 Yanfei";
		// tell me the ip of this machine
		// this._collector.emit(new Values(tuple));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this._collector = collector;
		
		
		Properties props = new Properties();
		props.put("zk.connect", "10.15.62.104:2181");
		props.put("groupid", "group1");

		ConsumerConfig consumerConfig = new ConsumerConfig(props);
		ConsumerConnector consumer = kafka.consumer.Consumer
				.createJavaConsumerConnector(consumerConfig);

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("newtopic", new Integer(1));

		Map<String, List<KafkaMessageStream>> consumerMap = consumer
				.createMessageStreams(topicCountMap);

		KafkaMessageStream stream = consumerMap.get("newtopic").get(0);
		
		this.it = stream.iterator();		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("ip-bookid-pageid"));		
	}
	
	// configure depend local and distribute
	@Override
	public Map<String, Object> getComponentConfiguration() {
		if(this._isDistributed){
			return null;
		}else{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put(Config.TOPOLOGY_MAX_TASK_PARALLELISM, 1);
			return map;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
	}
}