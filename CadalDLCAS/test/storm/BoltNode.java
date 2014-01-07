package cn.edu.zju.cadal.test.storm;

import java.util.HashMap;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

public class BoltNode extends BaseRichBolt {
	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private InsertDB insertDB = null;
	
	private String tableName = "user_info_2";
	
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.insertDB = new InsertDB(this.tableName);
	}

	@Override
	public void execute(Tuple input) {
		String userInfo = (String) input.getValue(0);
		
		String [] userInfoArr = userInfo.split(" ");
		
		String key = userInfoArr[0] + userInfoArr[1];
		String cfs = "info";
		HashMap<String, String> columnInfo = new HashMap<String, String>();
		
		columnInfo.put("age", userInfoArr[2]);
		columnInfo.put("hometown", userInfoArr[2]);
		
		this.insertDB.WriteRow(this.tableName, cfs, key, columnInfo);
		
		this._collector.ack(input);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));		
	}

}
