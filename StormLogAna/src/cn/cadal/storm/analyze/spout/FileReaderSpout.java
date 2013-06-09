package cn.cadal.storm.analyze.spout;

import java.util.Map;

import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class FileReaderSpout extends BaseRichSpout{
	
	private static final long serialVersionUID = 1L;
	SpoutOutputCollector _collector;
	private ReadAllocatedLine ral = null;
		
	@Override
	public void nextTuple() {
		String strTmp = "";
		System.out.println("----------------------------------FileReaderSpout-----------------------------------");
		strTmp = this.ral.ReadAppointedLineNumber();     // Get line of file
		Utils.sleep(2);
		if(strTmp.length() > 10) {
			this._collector.emit(new Values(strTmp));        // This line is not tested.
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this._collector = collector;
		this.ral = new ReadAllocatedLine();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("line"));
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		FileReaderSpout frs = new FileReaderSpout();
//		frs.nextTuple();
	}
}
