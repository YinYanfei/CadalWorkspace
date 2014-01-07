package cn.edu.zju.cadal.storm.personal;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.topology.TopologyBuilder;
import cn.edu.zju.cadal.storm.personal.bolt.PersonalVisitBolt;
import cn.edu.zju.cadal.storm.personal.spout.PersonalVisitSpout;

public class PersonalVisitTopology {

	/**
	 * @param args
	 * @throws Exception
	 * @throws AlreadyAliveException
	 */
	public static void main(String[] args) throws AlreadyAliveException,
			Exception {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("PersonalVisit", new PersonalVisitSpout(), 1);
		builder.setBolt("PersonalVisit_ins", new PersonalVisitBolt(), 2)
				.shuffleGrouping("PersonalVisit");

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
