package cn.edu.zju.cadal.test.kafka;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import kafka.javaapi.producer.Producer;
import kafka.javaapi.producer.ProducerData;
import kafka.producer.ProducerConfig;

public class mySimpleProducer {

	public static void main(String[] args) {
		Properties props = new Properties();
		props.put("broker.list","0:10.15.62.70:9092");
		props.put("serializer.class", "kafka.serializer.StringEncoder"); //选择用哪个类来进行序列化,这里用系统默认的序列化类
	//	props.put("partitioner.class","cn.cadal.SimplePartitioner"); 
		//partitioner.class cannot be used when broker.list is set
		//如果是在使用zookeeper搭建分布式的情况下（zookeeper based broker discovery），
		//我们可以用编码来实现partition的分配策略
		
		ProducerConfig config = new ProducerConfig(props);
		Producer<String,String> producer = new Producer<String,String>(config);
		Random rnd = new Random();
		for(int i = 0;i<10000;i++){
			long runtime = new Date().getTime();
			String ip = "10.15.62." + rnd.nextInt(255);
			String msg = runtime + ",www.example.com," + ip;
			List<String> msgs = new ArrayList<String>();
			msgs.add(msg);
			ProducerData<String,String> producerData = new ProducerData<String,String>("T_pagevisits",msgs);
			producer.send(producerData);
		}
		producer.close();
	}
}
