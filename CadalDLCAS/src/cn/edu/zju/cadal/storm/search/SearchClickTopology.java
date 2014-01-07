package cn.edu.zju.cadal.storm.search;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.topology.TopologyBuilder;
import cn.edu.zju.cadal.storm.search.bolt.SearchClickBolt;
import cn.edu.zju.cadal.storm.search.spout.SearchClickSpout;

public class SearchClickTopology {

	/**
	 * @param args
	 * @throws Exception 
	 * @throws AlreadyAliveException 
	 */
	public static void main(String[] args) throws AlreadyAliveException, Exception {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("SearchClick", new SearchClickSpout(), 1);
		builder.setBolt("SearchClick_ins", new SearchClickBolt(), 6)
			.shuffleGrouping("SearchClick");

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