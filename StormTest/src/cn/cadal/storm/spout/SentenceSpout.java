package cn.cadal.storm.spout;

import java.util.Map;
import java.util.Random;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

public class SentenceSpout extends BaseRichSpout{
	private static final long serialVersionUID = 1L;
	SpoutOutputCollector _collector;
	Random _random;
	
	@Override
	public void nextTuple() {
        Utils.sleep(100);
        String[] sentences = new String[] {
            "the cow jumped over the moon",
            "an apple a day keeps the doctor away",
            "four score and seven years ago",
            "snow white and the seven dwarfs",
            "i am at two with nature"};
        String sentence = sentences[_random.nextInt(sentences.length)];
        _collector.emit(new Values(sentence));
    }

	@SuppressWarnings("unchecked")
	@Override
	public void open(Map conf, TopologyContext context,
			SpoutOutputCollector collector) {
		this._collector = collector;
		this._random = new Random();
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SentenceSpout frs = new SentenceSpout();
		frs.nextTuple();

	}

}
