package cn.edu.zju.cadal.storm.rec.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cn.edu.zju.cadal.hbase.rec.RecUserOp;

/**
 * 处理用户推荐的点击结果，并插入到HBase数据库
 * 
 * @author CADAL
 */
public class RecUserBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private RecUserOp recUserOp = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.recUserOp = new RecUserOp();
	}

	@Override
	public void execute(Tuple input) {
		try {
			String info = (String) input.getValue(0);

			String[] infoArr = info.split("#\\$#");				// ip#$#user#$#user_r#$#source#$#time
			if (infoArr.length == 5) {
				this.recUserOp.insertSingleRow(infoArr[0], infoArr[1],
						infoArr[2], infoArr[3], Long.valueOf(infoArr[4]));
			}
			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("insert_hbase_rec_user"));
	}
}
