package cn.edu.zju.cadal.storm.personal.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cn.edu.zju.cadal.hbase.personal.PersonalButton;

/**
 * 处理用户对个人页面大标题的点击行为，插入HBase数据库
 * 
 * 大标题包括： 
 * 		personalHome,personalBorrow,personalTag,personalComment,personalMessage,personalRec
 * 
 * @author CADAL
 */
public class PersonalButtonBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private PersonalButton personalButton = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.personalButton = new PersonalButton();
	}

	@Override
	public void execute(Tuple input) {
		try {
			String info = (String) input.getValue(0);

			String[] infoArr = info.split("#\\$#");			// ip#$#user#$#button#$#time
			if (infoArr.length == 4) {
				this.personalButton.insertSingleRow(infoArr[0], infoArr[1],
						infoArr[2],Long.valueOf(infoArr[3]));
			}

			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("insert_hbase_button"));
	}
}
