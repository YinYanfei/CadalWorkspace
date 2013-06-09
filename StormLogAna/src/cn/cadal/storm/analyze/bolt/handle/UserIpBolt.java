package cn.cadal.storm.analyze.bolt.handle;

import java.util.Map;

import cn.cadal.storm.analyze.bolt.util.UserIpBoltUtil;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
//import backtype.storm.tuple.Values;

public class UserIpBolt extends BaseRichBolt{

	private static final long serialVersionUID = 1L;
	OutputCollector _collector;
	UserIpBoltUtil uibu = null;

	@Override
	public void execute(Tuple input) {
		String ipNameTime = input.getString(0);
		
		if(this.uibu.DealLog(ipNameTime)) {
			this._collector.ack(input);
		}
		
//		this._collector.emit(new Values(ipNameTime));
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.uibu = new UserIpBoltUtil();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("UserIpBoltField"));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test

	}
}
