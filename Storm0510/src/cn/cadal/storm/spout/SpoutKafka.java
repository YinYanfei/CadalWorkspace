package cn.cadal.storm.spout;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;
import backtype.storm.spout.Scheme;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaMessageStream;
import kafka.javaapi.consumer.ConsumerConnector;
import java.util.List;
import java.util.Properties;

public class SpoutKafka implements IRichSpout {

	/**
	 * Version nouse
	 */
	private static final long serialVersionUID = 1L;
	private final ConsumerConnector consumer;
	private final String topic;
	private final Scheme scheme;
	// Variables
	boolean _isDistributed;
	SpoutOutputCollector _collector;

	// Construct functions
	public SpoutKafka() {
		this(true);
	}
	public SpoutKafka(boolean isDistributed) {
		this._isDistributed = isDistributed;
		this.scheme = null;
		Properties props = new Properties();
		props.put("zk.connect", "10.15.62.104:2181");
		props.put("groupid", "group1");
		props.put("zk.sessiontimeout.ms", "8000");
		props.put("zk.synctime.ms", "2000");
		props.put("autocommit.interval.ms", "1000");
		ConsumerConfig cc = new ConsumerConfig(props);
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(cc);
		this.topic = "accesslog";
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
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(topic, new Integer(1));
		Map<String, List<KafkaMessageStream>> consumerMap = consumer
				.createMessageStreams(topicCountMap);
		KafkaMessageStream stream = consumerMap.get(topic).get(0);
		ConsumerIterator it = stream.iterator();

		String tuple = "";
		String str2 = "";

		while (it.hasNext()) {
			if ((str2 = ExampleUtils.getMessage(it.next())).equals("#")) {
				//this._collector.emit(new Values(tuple));
				this._collector.emit(scheme.deserialize(tuple.getBytes()));

				tuple = "";
			} else {
				tuple += str2;
			}
		}
		tuple = "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this._collector = collector;

	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("ip-bookid-pageid"));
	}

	// configure depend local and distribute
	@Override
	public Map<String, Object> getComponentConfiguration() {
		if (this._isDistributed) {
			return null;
		} else {
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

/*
package cn.cadal.storm.spout;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class SpoutKafka implements IRichSpout{

	*//**
	 * Version nouse
	 *//*
	private static final long serialVersionUID = 1L;

	// Variables
	boolean _isDistributed;
	SpoutOutputCollector _collector;
	
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
		String tuple = "10.15.62.110 07018720 00000037";
		while(true){
			this._collector.emit(new Values(tuple));
			Utils.sleep(1000);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this._collector = collector;
		
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

	*//**
	 * @param args
	 *//*
	public static void main(String[] args) {
		// Test

	}

}
*/