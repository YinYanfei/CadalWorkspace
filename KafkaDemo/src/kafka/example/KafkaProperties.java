package kafka.example;

public interface KafkaProperties {
	final static String zkConnect = "10.15.62.103:2181,10.15.62.104:2181,10.15.62.105:2181";
	final static String groupId = "group1";
	final static String topic = "_topic1";
	final static String kafkaServerURL = "10.15.62.73";
	final static int kafkaServerPort = 9092;
	final static int kafkaProducerBufferSize = 64 * 1024;
	final static int connectionTimeOut = 100000;
	final static int reconnectInterval = 10000;
}
