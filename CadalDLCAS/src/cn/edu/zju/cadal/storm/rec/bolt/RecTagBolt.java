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
 * 处理用户对推荐标签的点击行为，并存入HBase数据库
 * 
 * @author CADAL
 */
public class RecTagBolt extends BaseRichBolt {

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

			String[] infoArr = info.split("#\\$#");				// ip#$#user#$#tag#$#time#$#source
			if (infoArr.length == 5) {
				this.recTagOp.insertSingleRow(infoArr[0], infoArr[1],
						infoArr[2], infoArr[4], Long.valueOf(infoArr[3]));
			}

			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("insert_hbase_tag"));
	}

}
