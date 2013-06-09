package cn.cadal.storm.analyze.topology;

import cn.cadal.storm.analyze.bolt.basis.BasisBolt;
import cn.cadal.storm.analyze.bolt.filter.BookPageFilterBolt;
import cn.cadal.storm.analyze.bolt.filter.BookUserFilterBolt;
import cn.cadal.storm.analyze.bolt.filter.QueryBookFilterBolt;
import cn.cadal.storm.analyze.bolt.filter.UserIpFilterBolt;
import cn.cadal.storm.analyze.bolt.handle.BookPageBolt;
import cn.cadal.storm.analyze.bolt.handle.BookUserBolt;
import cn.cadal.storm.analyze.bolt.handle.QueryBookBolt;
import cn.cadal.storm.analyze.bolt.handle.UserIpBolt;
import cn.cadal.storm.analyze.spout.FileReaderSpout;
import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.utils.Utils;

public class TopologyBuild {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("line", new FileReaderSpout(), 1);
		
		builder.setBolt("BasisFilter", new BasisBolt(), 1).shuffleGrouping("line");

		builder.setBolt("UserIpFilter", new UserIpFilterBolt(), 12).shuffleGrouping("BasisFilter");
		builder.setBolt("BookPageFilter", new BookPageFilterBolt(), 12).shuffleGrouping("BasisFilter");
		builder.setBolt("BookUserFilter", new BookUserFilterBolt(), 12).shuffleGrouping("BasisFilter");
		builder.setBolt("QueryBookFilter", new QueryBookFilterBolt(), 6).shuffleGrouping("BasisFilter");
		
		builder.setBolt("UserIp", new UserIpBolt(), 16).shuffleGrouping("UserIpFilter");
		builder.setBolt("BookPage", new BookPageBolt(), 16).shuffleGrouping("BookPageFilter");
		builder.setBolt("BookUser", new BookUserBolt(), 12).shuffleGrouping("BookUserFilter");
		builder.setBolt("QueryBook", new QueryBookBolt(), 1).allGrouping("QueryBookFilter");

		Config conf = new Config();
		conf.setDebug(true);
		
		if(args != null && args.length > 0) {
			conf.setNumWorkers(1);
			
			try{
				StormSubmitter.submitTopology(args[0], conf, builder.createTopology());
			}catch(Exception e){
				e.printStackTrace();
			}
		}else{
			LocalCluster cluster = new LocalCluster();
			cluster.submitTopology("topology", conf, builder.createTopology());
			
			Utils.sleep(1000000000);
			cluster.killTopology("topology");
			cluster.shutdown();
		}		
	}

}
