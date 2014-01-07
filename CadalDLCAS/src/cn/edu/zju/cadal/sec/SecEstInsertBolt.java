package cn.edu.zju.cadal.sec;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cn.edu.zju.cadal.utils.MySQLUtil;

public class SecEstInsertBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private MySQLUtil mysqlUtil = null;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.mysqlUtil = new MySQLUtil();
	}

	@Override
	public void execute(Tuple input) {
		try{
			String msg = (String)input.getValue(0);			// ip#$#user   [10.15.62.75#$#cadal]
			String [] msgArr = msg.split("#\\$#");
			
			this.mysqlUtil.insert(msgArr[0], msgArr[1]);	// insert into db
			
			this._collector.ack(input);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("dbDone"));
	}
}

