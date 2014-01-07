package cn.edu.zju.cadal.storm.personal;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.topology.TopologyBuilder;
import cn.edu.zju.cadal.storm.personal.bolt.PersonalReplyBolt;
import cn.edu.zju.cadal.storm.personal.spout.PersonalReplySpout;

public class PersonalReplyTopology {

	/**
	 * @param args
	 * @throws Exception
	 * @throws AlreadyAliveException
	 */
	public static void main(String[] args) throws AlreadyAliveException,
			Exception {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("PersonalReply", new PersonalReplySpout(), 1);
		builder.setBolt("PersonalReply_ins", new PersonalReplyBolt(), 2)
				.shuffleGrouping("PersonalReply");

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
