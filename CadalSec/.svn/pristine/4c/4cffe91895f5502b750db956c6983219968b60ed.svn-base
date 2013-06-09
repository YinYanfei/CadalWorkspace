package cn.cadal.sec.storm.topology;

import org.apache.log4j.Logger;

import cn.cadal.sec.storm.bolt.BoltCassandra;
import cn.cadal.sec.storm.spout.SpoutKafka;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class TopologyBuild {

	static Logger logger = Logger.getLogger(TopologyBuild.class.getClass().getName());
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Build Topology
		// topology builder
		TopologyBuilder builder = new TopologyBuilder();

		// spout
		builder.setSpout("spout-source", new SpoutKafka(), 4);
		
		// bolt -- Insert data into cassandra db
		builder.setBolt("bolt-cassandra", new BoltCassandra(), 4).shuffleGrouping("spout-source");

		// Config
		Config conf = new Config();
		conf.setDebug(true);
		
		// create topology
		if(args != null && args.length > 0) {
			logger.info("TopologyBuild -- Run in real station modal");
			
			conf.setNumWorkers(4);
			
			// submit topology
			try {
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			logger.info("TopologyBuild -- run in local model");
			
			// local model
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("topoloty", conf, builder.createTopology());  // submit
			
			// kill and exit
			Utils.sleep(100000);
			cluster.killTopology("topology");
			cluster.shutdown();			
		}
	}

}
