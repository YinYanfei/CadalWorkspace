package cn.edu.zju.cadal.test.storm;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.topology.TopologyBuilder;

public class TopologyBuilderNode {

	/**
	 * To build topology.
	 * 
	 * @param args
	 * @throws Exception 
	 * @throws AlreadyAliveException 
	 */
	public static void main(String [] args) throws AlreadyAliveException, Exception {
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("spout-sentence", new SpoutNode(), 1);
		builder.setBolt("bolt-insertDB", new BoltNode(), 12).shuffleGrouping("spout-sentence");
		
		Config conf = new Config();
		conf.setDebug(true);
		
		if(args!=null && args.length > 0) {
            conf.setNumWorkers(6);
            
            StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
        } else {
            conf.setMaxTaskParallelism(3);

            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("storm-test", conf, builder.createTopology());
        
            Thread.sleep(100000);

            cluster.shutdown();
        }
	}
	
}
