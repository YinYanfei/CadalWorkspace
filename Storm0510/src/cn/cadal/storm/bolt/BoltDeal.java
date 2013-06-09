package cn.cadal.storm.bolt;

import java.util.Map;

import cn.cadal.storm.bolt.deal.Deal;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class BoltDeal extends BaseRichBolt{
	
	// Variable
	OutputCollector _collector;
	public Deal deal = null;

	@Override
	public void execute(Tuple input) {
		// deal
		this.deal.DealFun(input.getString(0));
		
		// ack
		this._collector.ack(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.deal = new Deal();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("source-deal"));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
