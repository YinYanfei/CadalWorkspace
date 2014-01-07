package cn.edu.zju.cadal.storm.read;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;
import cn.edu.zju.cadal.storm.read.bolt.ReadInfoBolt;
import cn.edu.zju.cadal.storm.read.bolt.ReadInfoBookBolt;
import cn.edu.zju.cadal.storm.read.bolt.ReadInfoIpBolt;
import cn.edu.zju.cadal.storm.read.bolt.ReadInfoUserBolt;
import cn.edu.zju.cadal.storm.read.spout.ReadInfoSpout;

public class ReadInfoTopology {

	/**
	 * Build topology for ReadInfo region
	 * 
	 * @param args
	 * @throws InvalidTopologyException
	 * @throws AlreadyAliveException
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws AlreadyAliveException,
			InvalidTopologyException, InterruptedException {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("ReadInfo", new ReadInfoSpout(), 1);
		builder.setBolt("ReadInfo_ins", new ReadInfoBolt(), 6)
				.shuffleGrouping("ReadInfo");
		builder.setBolt("ReadInfoIp", new ReadInfoIpBolt(), 6)
				.shuffleGrouping("ReadInfo");
		builder.setBolt("ReadInfoUser", new ReadInfoUserBolt(), 6)
				.shuffleGrouping("ReadInfo");
		builder.setBolt("ReadInfoBook", new ReadInfoBookBolt(), 6)
				.shuffleGrouping("ReadInfo");

		Config conf = new Config();
		conf.setDebug(true);

		if (args != null && args.length > 0) {
			conf.setNumWorkers(6);

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
