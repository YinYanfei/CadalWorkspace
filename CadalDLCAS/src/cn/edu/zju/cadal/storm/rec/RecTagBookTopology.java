package cn.edu.zju.cadal.storm.rec;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.topology.TopologyBuilder;
import cn.edu.zju.cadal.storm.rec.bolt.RecTagBookBolt;
import cn.edu.zju.cadal.storm.rec.spout.RecTagBookSpout;

public class RecTagBookTopology {

	/**
	 * @param args
	 * @throws Exception
	 * @throws AlreadyAliveException
	 */
	public static void main(String[] args) throws AlreadyAliveException,
			Exception {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("RecTagBook", new RecTagBookSpout(), 1);
		builder.setBolt("RecTagBook_ins", new RecTagBookBolt(), 2)
				.shuffleGrouping("RecTagBook");

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
