package cn.edu.zju.cadal.storm.personal.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cn.edu.zju.cadal.hbase.personal.PersonalFollow;

/**
 * 处理用户对其他用户的follow行为，插入HBase数据库
 * 
 * @author CADAL
 */
public class PersonalFollowBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private PersonalFollow personalFollow = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.personalFollow = new PersonalFollow();
	}

	@Override
	public void execute(Tuple input) {
		try {
			String info = (String) input.getValue(0);

			String[] infoArr = info.split("#\\$#");			// ip#$#user#$#user_f#$#time
			if (infoArr.length == 4) {
				this.personalFollow.insertSingleRow(infoArr[0], infoArr[1],
						infoArr[2],Long.valueOf(infoArr[3]));
			}
			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("insert_hbase_follow"));
	}
}
