package cn.edu.zju.cadal.sec;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * To estimate trouble ip or user
 * 
 * - 之后的Bolt节点根据不同的判定恶意IP的方法而分类
 * 
 * @author CADAL
 */
public class SecEstBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
	}

	@Override
	public void execute(Tuple input) {
		try {
			String info = (String) input.getValue(0);
			
			this._collector.emit(new Values(info));   // ip#$#user#$#bookid#$#pageno#$#time

			this._collector.ack(input);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("message"));
	}

}
