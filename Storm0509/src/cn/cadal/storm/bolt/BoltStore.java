package cn.cadal.storm.bolt;

import java.util.Map;

import cn.cadal.storm.bolt.cassandra.InitDataLog;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class BoltStore extends BaseRichBolt {

	/**
	 * SerialVersion nouse
	 */
	private static final long serialVersionUID = 1L;
	
	// Variable
	OutputCollector _collector;
	public InitDataLog initDataLog = null;
	
	@Override
	public void execute(Tuple input) {
		// deal input
		this.initDataLog.Store(input.getString(0));
		
		// ack
		this._collector.ack(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;	
		this.initDataLog = new InitDataLog();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("cassandra-store"));
	}

}
