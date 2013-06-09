package cn.cadal.storm.analyze.bolt.basis;

import java.util.Map;

import cn.cadal.storm.analyze.bolt.util.JudgeStr;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class BasisBolt extends BaseRichBolt{

	private static final long serialVersionUID = 1L;
	OutputCollector _collector;
	JudgeStr judge = null;
	
	@Override
	public void execute(Tuple input) {
		String line = input.getString(0);
		
		if(this.judge.JudgeStrEmit(line)) {
			this._collector.emit(new Values(line));
		}
		
		this._collector.ack(input);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.judge = new JudgeStr();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("filter"));
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test

	}

}
