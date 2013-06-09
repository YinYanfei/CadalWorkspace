package storm.kafka;

import java.io.Serializable;
import java.util.List;

// 这个类里面一方面是对spout进行参数的配置，另一方面也同时从KafkaConfig类里面
// 继承了Kafka的配置。
@SuppressWarnings("serial")
public class SpoutConfig extends KafkaConfig implements Serializable {
	public List<String> zkServers = null;
    public Integer zkPort = null;
    public String zkRoot = null;
    public String id = null;
    
    public long stateUpdateIntervalMs = 2000;
    
    public SpoutConfig(List<String> hosts, int partitionsPerHost, String topic, String zkRoot, String id) {
        super(hosts, partitionsPerHost, topic);
        this.zkRoot = zkRoot;
        this.id = id;
    }
}
