package cn.edu.zju.cadal.storm.rec.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cn.edu.zju.cadal.hbase.rec.RecTagOp;

/**
 * 收集推荐页面上用户对标签-图书的点击信息
 * 
 * 消息：
 * 		ip#$#user#$#tag#$#bookid#$#time
 * 
 * @author CADAL
 */
public class RecTagBookBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private RecTagOp recTagOp = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.recTagOp = new RecTagOp();
	}

	@Override
	public void execute(Tuple input) {
		try {
			String info = (String) input.getValue(0);

			String[] infoArr = info.split("#\\$#");
			if (infoArr.length == 5) {
				this.recTagOp.insertSingleRow(infoArr[0], infoArr[1],
						infoArr[2], infoArr[3], Long.valueOf(infoArr[4]));
			}
			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("insert_hbase_rec_tag_book"));
	}
}
