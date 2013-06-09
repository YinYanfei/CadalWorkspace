package cn.cadal.storm.topology;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import cn.cadal.storm.bolt.BoltStore;
import cn.cadal.storm.spout.util.KafkaSpout;
import cn.cadal.storm.spout.util.StringScheme;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.IRichSpout;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class KafkaTestTopology {

    public enum COMPONENTS {
        KAFKA_SPOUT;

        public int id() {
            return ordinal() + 1;
        }
    }

    public static void main(final String[] args) {

        TopologyBuilder builder = new TopologyBuilder();

        // ensure that you have the same or more partitions on the Kafka broker
        // if parallelism count is greater than partitions, some spouts/consumers will sit idle
        String strNum = String.valueOf(COMPONENTS.KAFKA_SPOUT.id());
        // spout
        builder.setSpout(strNum, createKafkaSpout(), 5);
        // bolt
        builder.setBolt("source-store", new BoltStore(), 4).shuffleGrouping(strNum);
 
		// Config
		Config conf = new Config();
		conf.setDebug(true);
		
		// create topology
		if(args != null && args.length > 0) {
			conf.setNumWorkers(4);
			
			// submit topology
			try {
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			// local modle
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("topoloty", conf, builder.createTopology());  // submit
			
			// kill and exit
			Utils.sleep(10000);
			cluster.killTopology("topology");
			cluster.shutdown();			
		}

 /*       
        Map<String, Object> conf = new HashMap<String, Object>();
        conf.put(Config.TOPOLOGY_DEBUG, true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("test", conf, builder.createTopology());

        // for testing, just leave up for 10 mins then kill it all
        Utils.sleep(10 * 60 * 1000); // 10 mins
        cluster.killTopology("test");
        cluster.shutdown();
*/
    }

    private static IRichSpout createKafkaSpout() {

        // setup Kafka consumer
        Properties kafkaProps = new Properties();
        kafkaProps.put("zk.connect", "10.15.62.105:2181");
        kafkaProps.put("zk.connectiontimeout.ms", "1000000");
        kafkaProps.put("groupid", "group1");

        return new KafkaSpout(kafkaProps, "accesslog", new StringScheme());
    }
}
