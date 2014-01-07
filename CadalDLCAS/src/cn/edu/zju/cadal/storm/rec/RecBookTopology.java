package cn.edu.zju.cadal.storm.rec;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.topology.TopologyBuilder;
import cn.edu.zju.cadal.storm.rec.bolt.RecBookBolt;
import cn.edu.zju.cadal.storm.rec.spout.RecBookHomePageSpout;
import cn.edu.zju.cadal.storm.rec.spout.RecBookPersonalPageSpout;
import cn.edu.zju.cadal.storm.rec.spout.RecBookRecPageSpout;

public class RecBookTopology {

	/**
	 * @param args
	 * @throws Exception
	 * @throws AlreadyAliveException
	 */
	public static void main(String[] args) throws AlreadyAliveException,
			Exception {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("recBookHomePage", new RecBookHomePageSpout(), 1);
		builder.setSpout("recBookPersonalPage", new RecBookPersonalPageSpout(),1);
		builder.setSpout("recBookRecPage", new RecBookRecPageSpout(), 1);
		
		builder.setBolt("recBook_ins_1", new RecBookBolt(), 2)
			.shuffleGrouping("recBookHomePage");
		builder.setBolt("recBook_ins_2", new RecBookBolt(), 2)
			.shuffleGrouping("recBookPersonalPage");
		builder.setBolt("recBook_ins_3", new RecBookBolt(), 2)
			.shuffleGrouping("recBookRecPage");

		Config conf = new Config();
		conf.setDebug(true);

		if (args != null && args.length > 0) {
			conf.setNumAckers(4);

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
