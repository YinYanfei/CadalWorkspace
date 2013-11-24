package cn.edu.zju.cadal.test.kafka;

import kafka.producer.Partitioner;

public class SimplePartitioner implements Partitioner<String> {

	public static void main(String[] args) {
	}

	public int partition(String key, int a_numPartitioner) {
		int partition = 0;
		int offset = key.lastIndexOf('.');
		if(offset>0)
			partition = Integer.parseInt(key.substring(offset+1)) % a_numPartitioner;
		return partition;
	}

}
