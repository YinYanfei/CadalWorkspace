package cn.cadal.storm.bolt;

import java.util.Map;

import cn.cadal.storm.bolt.statistics.CadalSta;
import cn.cadal.storm.bolt.statistics.SchoolSta;
import cn.cadal.storm.bolt.statistics.SchoolTypeSta;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class BoltDeal extends BaseRichBolt{
	
	/**
	 * SerialVersion nouse
	 */
	private static final long serialVersionUID = 1L;
	
	// Variable
	OutputCollector _collector;
	
	public CadalSta cadalSta = null;			// cadal -- time
	public SchoolSta schoolSta = null;          // school -- time
	public SchoolTypeSta schoolTypeSta = null;  // school -- type -- time
	
	@Override
	public void execute(Tuple input) {
		// deal
		this.schoolTypeSta.deal(input.getString(0));	// school -- type -- time
		this.cadalSta.deal(input.getString(0));			// cadal -- time
		this.schoolSta.deal(input.getString(0));		// school -- time
		
		// ack
		this._collector.ack(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;

		this.cadalSta = new CadalSta();
		this.schoolSta = new SchoolSta();
		this.schoolTypeSta = new SchoolTypeSta();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("source-deal"));
	}
}
