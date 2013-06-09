package cn.cadal.storm.topology;

import cn.cadal.storm.bolt.CountBolt;
import cn.cadal.storm.bolt.SplitBolt;
import cn.cadal.storm.spout.SentenceSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;

public class WordCountTopology {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("spout", new SentenceSpout(), 4);
		builder.setBolt("split", new SplitBolt(), 4).shuffleGrouping("spout");
		builder.setBolt("count", new CountBolt(), 8).fieldsGrouping("split", new Fields("word"));
		
		Config conf = new Config();
		conf.setDebug(true);
		
		try{
			if(args != null && args.length > 0) {
				conf.setNumWorkers(3);
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			}else{
				conf.setMaxTaskParallelism(3);
				LocalCluster cluster = new LocalCluster();
				cluster.submitTopology("word-count", conf, builder.createTopology());
				
				Thread.sleep(100000);
				cluster.shutdown();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
