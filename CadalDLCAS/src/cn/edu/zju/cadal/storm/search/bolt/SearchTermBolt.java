package cn.edu.zju.cadal.storm.search.bolt;

import java.util.Map;

import cn.edu.zju.cadal.hbase.search.SearchTermOp;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;

/**
 * 处理用户搜索词消息
 * 字段为：ip   user    queryMessage    time
 *
 */
public class SearchTermBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	private SearchTermOp searchTermOp = null;

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.searchTermOp = new SearchTermOp();
	}

	@Override
	public void execute(Tuple input) {
		try {
			String info = (String) input.getValue(0);

			String[] infoArr = info.split("#\\$#");
			if (infoArr.length == 4) {
				this.searchTermOp.insertSingleRow(infoArr[0], infoArr[1],
						infoArr[2], Long.valueOf(infoArr[3]));  //insert into hbase
			}

			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("insert_hbase_term"));
	}

}
