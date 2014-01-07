package cn.edu.zju.cadal.storm.personal.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cn.edu.zju.cadal.hbase.personal.PersonalVisit;

/**
 * 处理对其他用户的访问信息，并插入到HBase数据库
 * 
 * @author CADAL
 */
public class PersonalVisitBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private PersonalVisit personalVisit = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.personalVisit = new PersonalVisit();
	}

	@Override
	public void execute(Tuple input) {
		try {
			String info = (String) input.getValue(0);

			String[] infoArr = info.split("#\\$#");
			if (infoArr.length == 4) {
				this.personalVisit.insertSingleRow(infoArr[0], infoArr[1],
						infoArr[2],Long.valueOf(infoArr[3]));
			}
			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("insert_hbase_visit"));
	}
}
