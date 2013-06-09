package cn.cadal.storm.bolt;

import java.util.Map;

import cn.cadal.storm.bolt.cassandra.CassandraOp;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class BoltStore extends BaseRichBolt {

	// Variable
	OutputCollector _collector;
	public CassandraOp cassandraOp = null;
		
	@Override
	public void execute(Tuple input) {
		// deal input
		String strTmp = "";
		
		if(input.getString(0).equals("#")) {
			this.cassandraOp.Store(strTmp);
			strTmp = "";
		}else{
			strTmp += input.getString(0);
		}
		
		// this.cassandraOp.Store(input.getString(0));
		
		// ack
		this._collector.ack(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;	
		this.cassandraOp = new CassandraOp();	
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("cassandra-store"));
	}

}
