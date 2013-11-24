package cn.edu.zju.cadal.test.kafka;

import kafka.producer.Partitioner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.edu.zju.cadal.test.utils.UserInfo;

public class ProducerPartitioner implements Partitioner<String> {
	public static final Logger LOG=LoggerFactory.getLogger(UserInfo.class);


	public static void main(String[] args) {

	}

	public int partition(String key, int numPartitions) {
		int result = key.length() % numPartitions;
		LOG.info("ProducerPartitioner key:" + key + " key.length()= " + key.length() + " partitions:" + numPartitions + " result:" + result);
		return result;
	}
}
