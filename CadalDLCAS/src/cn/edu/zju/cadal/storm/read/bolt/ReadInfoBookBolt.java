package cn.edu.zju.cadal.storm.read.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cn.edu.zju.cadal.hbase.read.ReadInfoBookOp;

/**
 * 处理用户阅读书页的消息，插入到相应的HBase Table(read_info_book)
 * 字段为：ip user bookid pageno time
 */
public class ReadInfoBookBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private ReadInfoBookOp readInfoBookOp = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.readInfoBookOp = new ReadInfoBookOp();
	}

	@Override
	public void execute(Tuple input) {
		try {
			String info = (String) input.getValue(0);

			String[] infoArr = info.split("#\\$#");
			if (infoArr.length == 5) {
				this.readInfoBookOp.insertSingleRow(infoArr[0], infoArr[1],
						infoArr[2], infoArr[3],
						Long.valueOf(infoArr[4])); // insert into hbase
			}

			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("insert_hbase"));
	}

}
