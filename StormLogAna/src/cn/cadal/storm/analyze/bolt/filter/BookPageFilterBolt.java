package cn.cadal.storm.analyze.bolt.filter;

import java.util.Map;

import cn.cadal.storm.analyze.bolt.util.BookPageFilterUtil;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class BookPageFilterBolt extends BaseRichBolt{

	private static final long serialVersionUID = 1L;
	OutputCollector _collector;
	public BookPageFilterUtil bpfu = null;
	
	@Override
	public void execute(Tuple input) {
		String line = this.bpfu.BookPage(input.getString(0));
		
		if(line.length() > 0){
			this._collector.emit(new Values(line));
		}
		
		this._collector.ack(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.bpfu = new BookPageFilterUtil();
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("UserBookPage"));
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test

	}
}
