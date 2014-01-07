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
 * Settle IP information in MYSQL DB
 * 
 * -- 判断当前是否应该进行数据库的清理工作
 * 		- 短期清理：settle_each_minute
 * 		- 长期清理：settle_each_day
 * 
 * @author CADAL
 */
public class SecSettleBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	
	private long TIMESTAMP = 0L;
	private long TIMESTAMP_24 = 0L;
	
	private static long EACH_MINUTE = 60000;
	private static long EACH_DAY = 86400000;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.TIMESTAMP = System.currentTimeMillis();		// 启动时设定初始时间戳
		this.TIMESTAMP_24 = System.currentTimeMillis();
	}

	@SuppressWarnings({ "static-access", "unused" })
	@Override
	public void execute(Tuple input) {
		try{
			String msg = input.getString(0);
			long curTimeStamp = System.currentTimeMillis();
			
			if(curTimeStamp - this.TIMESTAMP >= this.EACH_MINUTE) {
				this.TIMESTAMP = curTimeStamp;
				this._collector.emit(new Values("settle_each_minute"));
			}else if(curTimeStamp - this.TIMESTAMP_24 >= this.EACH_DAY) {
				this.TIMESTAMP_24 = curTimeStamp;
				this._collector.emit(new Values("settle_each_day"));
			}
			
			this._collector.ack(input);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("settleMsg"));
	}
}
