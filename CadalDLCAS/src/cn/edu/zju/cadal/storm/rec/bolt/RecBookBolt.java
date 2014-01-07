package cn.edu.zju.cadal.storm.rec.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cn.edu.zju.cadal.hbase.rec.RecBookOp;

/**
 * 处理用户对推荐图书的点击行为，并写入数据库
 * 
 * @author CADAL
 */
public class RecBookBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private RecBookOp recBookOp = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.recBookOp = new RecBookOp();
	}

	@Override
	public void execute(Tuple input) {
		try {
			String info = (String) input.getValue(0);

			String[] infoArr = info.split("#\\$#");				// ip#$#user#$#bookid#$#time#$#source
			if (infoArr.length == 5) {
				this.recBookOp.insertSingleRow(infoArr[0], infoArr[1],
						infoArr[2], infoArr[4], Long.valueOf(infoArr[3]));
			}

			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("insert_hbase_recBook"));
	}

}
