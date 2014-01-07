package cn.edu.zju.cadal.storm.search;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import cn.edu.zju.cadal.storm.search.bolt.SearchTermBolt;
import cn.edu.zju.cadal.storm.search.spout.SearchTermSpout;

public class SearchTermTopology {

	/**
	 * @param args
	 * @throws InvalidTopologyException 
	 * @throws AlreadyAliveException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws AlreadyAliveException, InvalidTopologyException, InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("SearchTerm", new SearchTermSpout(), 1);
		builder.setBolt("SearchTerm_ins", new SearchTermBolt(), 6)
			.shuffleGrouping("SearchTerm");

		Config conf = new Config();
		conf.setDebug(true);

		if (args != null && args.length > 0) {
			conf.setNumAckers(2);

			StormSubmitter.submitTopology(args[0], conf,
					builder.createTopology());
		} else {
			conf.setMaxTaskParallelism(3);
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("storm-test", conf, builder.createTopology());

			Thread.sleep(100000);

			cluster.shutdown();
		}
	}

}
