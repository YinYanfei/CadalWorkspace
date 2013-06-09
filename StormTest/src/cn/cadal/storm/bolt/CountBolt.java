package cn.cadal.storm.bolt;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class CountBolt extends BaseRichBolt{

	private static final long serialVersionUID = 1L;
	Map<String, Integer> counts = new HashMap();
	OutputCollector _collector;
	
	@Override
	public void execute(Tuple input) {
		String word = input.getString(0);
		Integer intTmp = counts.get(word);
		if(intTmp == null) {
			intTmp = 0;
		}else{
			intTmp++;
		}
		counts.put(word, intTmp);
		this._collector.emit(new Values(word, intTmp));
		this._collector.ack(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word", "count"));
		
	}

}
