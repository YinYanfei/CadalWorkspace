package cn.edu.zju.cadal.storm.read.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cn.edu.zju.cadal.hbase.read.ReadCommentOp;

/**
 * 处理用户评论书页的信息
 * 字段：ip user bookid pageno comment time
 */
public class ReadCommentBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private ReadCommentOp readCommentOp = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.readCommentOp = new ReadCommentOp();
	}

	@Override
	public void execute(Tuple input) {
		try {
			String info = (String) input.getValue(0);

			String[] infoArr = info.split("#\\$#");
			if (infoArr.length == 6) {
				this.readCommentOp.insertSingleRow(infoArr[0], infoArr[1],
						infoArr[2], infoArr[3],infoArr[4],
						Long.valueOf(infoArr[5])); //insert into hbase
			}

			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("insert_hbase_comment"));
	}

}
