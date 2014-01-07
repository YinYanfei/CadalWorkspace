package cn.edu.zju.cadal.test.utils;

import org.apache.hadoop.hbase.util.Bytes;

public class HashPre {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long timestamp = System.currentTimeMillis();
		
		System.out.println(timestamp);
		System.out.println(timestamp % 7);
		
		byte prefix = (byte) (timestamp % 7);
		byte[] rowkey = Bytes.add(Bytes.toBytes(prefix), Bytes.toBytes(timestamp));

		System.out.println(prefix);
	}

}
