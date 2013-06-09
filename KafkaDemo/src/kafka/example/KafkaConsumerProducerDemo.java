package kafka.example;

import kafka.example.Consumer;
import kafka.example.KafkaProperties;
import kafka.example.Producer;

public class KafkaConsumerProducerDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//MyProducer producerThread = new MyProducer(KafkaProperties.topic);
		//producerThread.start();

		Consumer consumerThread = new Consumer(KafkaProperties.topic);
		consumerThread.start();
	}

}
