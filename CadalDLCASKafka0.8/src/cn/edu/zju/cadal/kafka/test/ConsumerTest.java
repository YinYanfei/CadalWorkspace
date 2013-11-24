package cn.edu.zju.cadal.kafka.test;

import java.io.File;
import java.io.FileWriter;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class ConsumerTest implements Runnable {
	private KafkaStream m_stream;
	private int m_threadNumber;

	public ConsumerTest(KafkaStream a_stream, int a_threadNumber) {
		m_stream = a_stream;
		m_threadNumber = a_threadNumber;
	}

	@Override
	public void run() {
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
		try {
			while (it.hasNext()) {
				FileWriter writer = new FileWriter(new File(m_threadNumber + ".dat"), true);
//				writer.write("Thread " + m_threadNumber + ": " + new String(it.next().message()));
//				writer.write("\n");
				System.out.println("Thread " + m_threadNumber + ": " + new String(it.next().message()));
				writer.close();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Shutting down Thread: " + m_threadNumber);
	}

	public static void main(String[] args) {
	}
}
