package cn.edu.zju.cadal.storm.read;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.topology.TopologyBuilder;
import cn.edu.zju.cadal.storm.read.bolt.ReadCommentBolt;
import cn.edu.zju.cadal.storm.read.spout.ReadCommentSpout;

public class ReadCommentTopology {

	/**
	 * @param args
	 * @throws Exception
	 * @throws AlreadyAliveException
	 */
	public static void main(String[] args) throws AlreadyAliveException,
			Exception {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("ReadComment", new ReadCommentSpout(), 1);
		builder.setBolt("ReadComment_ins", new ReadCommentBolt(), 3)
				.shuffleGrouping("ReadComment");

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
