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

public class SpoutKafka_old implements IRichSpout{

	// Variables
	boolean _isDistributed;
	SpoutOutputCollector _collector;
	
	// Construct functions
	public SpoutKafka_old() {
		this(true);
	}
	public SpoutKafka_old(boolean isDistributed) {
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
			Utils.sleep(500);
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

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test

	}

}
