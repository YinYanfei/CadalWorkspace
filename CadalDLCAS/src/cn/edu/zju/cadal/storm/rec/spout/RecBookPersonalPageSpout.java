package cn.edu.zju.cadal.storm.rec.spout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

/**
 * 收集个人页面上的图书推荐点击行为
 * 
 * 消息：
 * 		ip#$#user#$#bookid#$#time
 * 
 * 向bolt发送的消息：
 * 		ip#$#user#$#bookid#$#time#$#source
 * 
 * @author CADAL
 */
public class RecBookPersonalPageSpout extends BaseRichSpout{
	
	private static final long serialVersionUID = 1L;
	private SpoutOutputCollector _collector = null;
	private ConsumerIterator<byte[], byte[]> it = null;
	
	private void KafkaInit(){
		Properties props = new Properties();
		props.put("zookeeper.connect", "10.15.62.75:2181,10.15.62.76:2181,10.15.62.77:2181");
		props.put("group.id", "RecPersonalPage");
		
		ConsumerConfig consumerConfig = new ConsumerConfig(props);
		ConsumerConnector consumer = kafka.consumer.Consumer.createJavaConsumerConnector(consumerConfig);

		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put("Rec-personalPage", new Integer(1));// 第二个参数是指用几个流，多个流是为了并行处理。

		Map<String, List<KafkaStream<byte[],byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);

		KafkaStream<byte[], byte[]> stream = consumerMap.get("Rec-personalPage").get(0);// 这里只有一个流，所以得get(0)就可以了。
		this.it = stream.iterator();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this._collector = collector;
		this.KafkaInit();
	}

	@Override
	public void nextTuple() {
		if(it.hasNext()) {
			String emitvalue = (new String(it.next().message())) + "#$#" + "personalPage";
			
			this._collector.emit(new Values(emitvalue));
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("recBookPersonalPage"));
	}

}
