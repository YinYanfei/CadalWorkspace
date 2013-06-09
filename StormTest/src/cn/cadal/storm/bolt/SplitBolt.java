package cn.cadal.storm.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class SplitBolt extends BaseRichBolt{

	private static final long serialVersionUID = 1L;
	OutputCollector _colloctor;
	
	@Override
	public void execute(Tuple input) {
		String str = input.getString(0);
		
		String strArray[] = str.split(" ");
		
		for(int i = 0; i < strArray.length; ++i) {
			this._colloctor.emit(new Values(strArray[i]));
		}
		this._colloctor.ack(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._colloctor = collector;
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
		
	}

}
