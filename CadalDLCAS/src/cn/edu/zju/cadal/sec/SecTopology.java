package cn.edu.zju.cadal.sec;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

public class SecTopology {

	/**
	 * @param args
	 * @throws InvalidTopologyException
	 * @throws AlreadyAliveException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws AlreadyAliveException,
			InvalidTopologyException, InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();
		
		// Spout
		builder.setSpout("SecSpout", new SecSpout(), 1);

		// Bolt topology for estimating unfriendly ip
		builder.setBolt("SecEstBolt", new SecEstBolt(), 1).allGrouping("SecSpout");
		builder.setBolt("SecEstCountBolt", new SecEstCountBolt(), 1)
				.allGrouping("SecEstBolt");
		builder.setBolt("SecEstRateBolt", new SecEstRateBolt(), 1)
				.allGrouping("SecEstBolt");
		
		builder.setBolt("SecEstInsertBolt_1", new SecEstInsertBolt(), 2)
				.shuffleGrouping("SecEstCountBolt");
		builder.setBolt("SecEstInsertBolt_2", new SecEstInsertBolt(), 2)
				.shuffleGrouping("SecEstRateBolt");

		// Bolt topology for settling MySQL DB
		builder.setBolt("SecSettleBolt", new SecSettleBolt(), 1).allGrouping("SecSpout");
		builder.setBolt("SecSettleDBBolt", new SecSettleDBBolt(), 1)
				.shuffleGrouping("SecSettleBolt");
		
		Config conf = new Config();
		conf.setDebug(true);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(2);

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
