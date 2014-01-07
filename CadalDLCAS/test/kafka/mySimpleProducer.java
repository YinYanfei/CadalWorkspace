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
		props.put("serializer.class", "kafka.serializer.StringEncoder"); //ѡ�����ĸ������������л�,������ϵͳĬ�ϵ����л���
	//	props.put("partitioner.class","cn.cadal.SimplePartitioner"); 
		//partitioner.class cannot be used when broker.list is set
		//�������ʹ��zookeeper��ֲ�ʽ������£�zookeeper based broker discovery����
		//���ǿ����ñ�����ʵ��partition�ķ������
		
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
