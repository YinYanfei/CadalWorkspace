package cn.cadal.storm.analyze.bolt.filter;

import java.util.Map;

import cn.cadal.storm.analyze.bolt.util.QueryBookFilterUtil;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class QueryBookFilterBolt extends BaseRichBolt{

	private static final long serialVersionUID = 1L;
	OutputCollector _collector;
	public QueryBookFilterUtil qbfu = null;
	
	@Override
	public void execute(Tuple input) {
		String line = this.qbfu.QueryBook(input.getString(0));
		
		if(line.length() > 0) {
			this._collector.emit(new Values(line));
		}
		
		this._collector.ack(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.qbfu = new QueryBookFilterUtil();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("QueryBook"));
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
