package cn.edu.zju.cadal.test.zookeeper;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ZookeeperOP {

	private static final int SESSION_TIMEOUT = 30000;
	
	// ZK object
	private ZooKeeper zk = null;
	
	// Watcher
	private Watcher wh = new Watcher() {
		public void process(WatchedEvent event) {
			System.out.println(event.toString());
		}
	};
	
	/**
	 * Create ZooKeeper object
	 * 
	 * @throws IOException
	 */
	private void CreateZKInstance() throws IOException {
		this.zk = new ZooKeeper("10.15.62.75:2181,10.15.62.76:2181,10.15.62.77:2181", this.SESSION_TIMEOUT, this.wh);
	}

	/**
	 * Operation for ZK
	 * 
	 * @throws Exception
	 * @throws InterruptedException
	 */
	private void ZKOperation() throws Exception, InterruptedException {
		System.out.println("---- Create ZNode");
		this.zk.create("/zoo2", "myData2".getBytes(), org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		System.out.println("---- Check ZNode");
		System.out.println(new String(this.zk.getData("/zoo2", false, null)));
		
		System.out.println("---- Chance ZNode");
		this.zk.setData("/zoo2", "myData3".getBytes(), -1);
		
		System.out.println("---- Check ZNode new data");
		System.out.println(new String(this.zk.getData("/zoo2", false, null)));

		System.out.println("---- Delete ZNode");
		this.zk.delete("/zoo2", -1);
		
		System.out.println("---- Check ZNode Delete");
		System.out.println(this.zk.exists("/zoo2", false));
	}
	
	/**
	 * Close zk Object
	 * 
	 * @throws Exception
	 */
	private void ZKClose() throws Exception {
		this.zk.close();
	}
	
	/**
	 * Main function for test
	 * 
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// Test
		ZookeeperOP zkop = new ZookeeperOP();
		
		zkop.CreateZKInstance();
		
		// create
//		zkop.zk.create("/zoo2", "myData2".getBytes(), org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		
		// get
//		System.out.println(new String(zkop.zk.getData("/zoo2", false, null)));
		
		// set
//		zkop.zk.setData("/zoo2", "myData3".getBytes(), -1);
		
		// delete
//		zkop.zk.delete("/zoo2", -1);
		
		// exist
		System.out.println(zkop.zk.exists("/zoo2", false));
		
		zkop.ZKClose();
	}

}
