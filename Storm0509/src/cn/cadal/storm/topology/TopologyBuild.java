package cn.cadal.storm.topology;

import cn.cadal.storm.bolt.BoltDeal;
import cn.cadal.storm.bolt.BoltStore;
import cn.cadal.storm.spout.SpoutKafka;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class TopologyBuild {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// topology builder
		TopologyBuilder builder = new TopologyBuilder();

		// spout
		builder.setSpout("source", new SpoutKafka(), 4);
		
		// bolt 
		   // -- store
		builder.setBolt("source-store", new BoltStore(), 4).shuffleGrouping("source");
		   // -- deal
		builder.setBolt("source-deal", new BoltDeal(), 8).shuffleGrouping("source");
		
		// Config
		Config conf = new Config();
		conf.setDebug(true);
		
		// create topology
		if(args != null && args.length > 0) {
			conf.setNumWorkers(4);
			
			// submit topology
			try {
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			// local modle
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("topoloty", conf, builder.createTopology());  // submit
			
			// kill and exit
			Utils.sleep(10000);
			cluster.killTopology("topology");
			cluster.shutdown();			
		}
	}
}
