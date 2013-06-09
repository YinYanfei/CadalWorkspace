package cn.cadal.sec.storm.spout;

import java.io.Serializable;
import java.util.List;

import backtype.storm.spout.RawScheme;
import backtype.storm.spout.Scheme;

public class KafkaConfig implements Serializable {
    public List<String> hosts;
    public int port = 9092;
    public int partitionsPerHost;
    public int fetchSizeBytes = 1024*1024;
    public int socketTimeoutMs = 10000;
    public int bufferSizeBytes = 1024*1024;
    public Scheme scheme = new RawScheme();
    public String topic;

    public KafkaConfig(List<String> hosts, int partitionsPerHost, String topic) {
        this.hosts = hosts;
        this.partitionsPerHost = partitionsPerHost;
        this.topic = topic;
    }
}
