package cn.edu.zju.cadal.test.kafka;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.javaapi.producer.ProducerData;
import kafka.producer.ProducerConfig;
import cn.edu.zju.cadal.test.utils.MockData;
import cn.edu.zju.cadal.test.utils.UserInfo;


//high level producer api，封装自定义消息。自动识别brokers,没有实现Partitioner的情况下随机向broker发数据。
public class KafkaProducer {

	public static void main(String[] args) throws InterruptedException {
		Properties props = new Properties();
		props.put("zk.connect","10.15.62.76:2181,10.15.62.75:2181,10.15.62.77:2181");
		props.put("serializer.class", "cn.edu.zju.cadal.test.utils.UserInfoMessage"); // 选择用哪个类来进行序列化,这里用的是自定义的序列化类
		props.put("partitioner.class","cn.edu.zju.cadal.test.kafka.ProducerPartitioner");
		props.put("zk.connectiontimeout.ms", "6000");
		ProducerConfig config = new ProducerConfig(props);
		
		List<String> key = new ArrayList<String>();
		key.add("h");
		key.add("ho");
		key.add("hon");
		key.add("hong");
		key.add("hongx");
		key.add("hongxi");
		key.add("hongxin");
		// make up data
		Producer<String, UserInfo> producer = new Producer<String, UserInfo>(config);
		MockData mockData = new MockData();
		for (int i = 0; i < 100000; i++) {
			UserInfo user = mockData.getOneData();
//			System.out.println(user.toString());
			List<UserInfo> msg = new ArrayList<UserInfo>();
			msg.add(user);

//			ProducerData<String, UserInfo> data1 = new ProducerData<String, UserInfo>("topic1114", msg);
//			ProducerData<String, UserInfo> data1 = new ProducerData<String, UserInfo>("t0",msg);
			ProducerData<String,UserInfo> data2 = new ProducerData<String,UserInfo>("t0",key.get(i%7),msg);

//			System.out.println("send data: " + i);
//			producer.send(data1);
//			Thread.sleep(10);
			producer.send(data2);
		}
		producer.close();
	}
}