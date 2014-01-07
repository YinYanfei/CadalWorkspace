package cn.edu.zju.cadal.storm.search.bolt;

import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cn.edu.zju.cadal.hbase.search.SearchClickOp;

/**
 * 处理用户搜索后点击图书的消息
 * 字段为：ip   user    queryMessage  bookid  time
 *
 */
public class SearchClickBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private SearchClickOp searchClickOp = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.searchClickOp = new SearchClickOp();
	}

	@Override
	public void execute(Tuple input) {
		try {
			String info = (String) input.getValue(0);

			String[] infoArr = info.split("#\\$#");
			if (infoArr.length == 5) {
				this.searchClickOp.insertSingleRow(infoArr[0], infoArr[1],
						infoArr[2], infoArr[3], Long.valueOf(infoArr[4]));
			}

			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("insert_hbase_click"));
	}

}
