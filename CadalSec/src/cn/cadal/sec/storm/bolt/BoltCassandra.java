package cn.cadal.sec.storm.bolt;

import java.util.Map;

import org.apache.log4j.Logger;

import cn.cadal.sec.storm.bolt.cassandra.CassandraOp;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class BoltCassandra extends BaseRichBolt  {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	Logger logger = null;
	
	// Variable
	OutputCollector _collector;
	public CassandraOp cassandraOp = null;
	
	@Override
	public void execute(Tuple input) {
		// deal input
		String strTmp = input.getString(0);
		
		// 这个地方可能会因为与kafka接口而有所变化
		if (this.cassandraOp.InsertRecordMinute(strTmp) != 0) {
			this.logger.debug("BoltCassandra -- Insert into RecordMinute Failed");
		} else {
			this.logger.debug("BoltCassandra -- Successed ! ");
		}
		
		// ack
		this._collector.ack(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.cassandraOp = new CassandraOp();
		this.logger = Logger.getLogger(BoltCassandra.class.getClass().getName());
		
		this.logger.debug("BoltCassandra -- prepare function fine");
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("bolt-cassandra-insert"));
		
	}

}
