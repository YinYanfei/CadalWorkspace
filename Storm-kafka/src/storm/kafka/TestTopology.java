package storm.kafka;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.transactional.TransactionalTopologyBuilder;
import backtype.storm.tuple.Tuple;
import java.util.ArrayList;
import java.util.List;

public class TestTopology {
    public static class PrinterBolt extends BaseBasicBolt {
        @Override
        public void declareOutputFields(OutputFieldsDeclarer declarer) {
        }

        @Override
        public void execute(Tuple tuple, BasicOutputCollector collector) {
            System.out.println(tuple.toString() + " --------------------- ");
        }
        
    }
    
    public static void main(String [] args) throws Exception {
        List<String> hosts = new ArrayList<String>();
        hosts.add("10.15.62.73");
        KafkaConfig kafkaConf = new KafkaConfig(hosts, 3, "accesslog");
        kafkaConf.scheme = new StringScheme();
        
        
        LocalCluster cluster = new LocalCluster();
        TransactionalTopologyBuilder builder = new TransactionalTopologyBuilder("id", "spout", new OpaqueTransactionalKafkaSpout(kafkaConf), 1);
        builder.setBolt("printer", new PrinterBolt()).shuffleGrouping("spout");
        
        Config config = new Config();
        
        cluster.submitTopology("kafka-test", config, builder.buildTopology());
        
        Thread.sleep(6000000);
    }
}
