package cn.cadal.storm.analyze.cassandra.Util;

import java.nio.ByteBuffer;

public class CassandraUtil {

	/**
	 * 将String转换为bytebuffer，以便插入cassandra
	 */
	public ByteBuffer toByteBuffer(String value) {
		try {
			return ByteBuffer.wrap(value.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test

	}

}
