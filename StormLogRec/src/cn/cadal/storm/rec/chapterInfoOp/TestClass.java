package cn.cadal.storm.rec.chapterInfoOp;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;

import cn.cadal.storm.rec.cassandra.CassandraUtil;
import cn.cadal.storm.rec.cassandra.Connector;

public class TestClass {
	private Connector connector = null;
	private Cassandra.Client client = null;
	private CassandraUtil cassandraUtil = null;
	
	public TestClass() {
		try{
			this.connector = new Connector();
			this.client = this.connector.connect();
			this.cassandraUtil = new CassandraUtil();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void finalize() {
		this.connector.finalize();
	}
	
	/**
	 * Insert into IpUser Column Family
	 */
	public boolean InsertTest(String key, int val) {
		System.out.println("------------InsertTest--------------");

		try {
			ColumnParent parent = new ColumnParent("test");

			long timeStamp = System.currentTimeMillis();

			Column idColumnPageid = new Column();
			idColumnPageid.setName(this.cassandraUtil.toByteBuffer("signal"));
			idColumnPageid.setValue(this.cassandraUtil.toByteBuffer(String.valueOf(val)));
			idColumnPageid.setTimestamp(timeStamp);
			client.insert(this.cassandraUtil.toByteBuffer(key), parent, idColumnPageid, ConsistencyLevel.ONE);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * Insert into IpUser Column Family
	 */
	public boolean InsertTest2(int key, String val) {
		System.out.println("------------InsertTest--------------");

		try {
			ColumnParent parent = new ColumnParent("test");

			long timeStamp = System.currentTimeMillis();

			Column idColumnPageid = new Column();
			idColumnPageid.setName(this.cassandraUtil.toByteBuffer("content"));
			idColumnPageid.setValue(this.cassandraUtil.toByteBuffer(val));
			idColumnPageid.setTimestamp(timeStamp);
			client.insert(this.cassandraUtil.toByteBuffer(String.valueOf(key)), parent, idColumnPageid, ConsistencyLevel.ONE);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}

	/**
	 * Insert into IpUser Column Family
	 */
	public boolean InsertTest3(int userid, int key, String val) {
		System.out.println("------------InsertTest--------------");

		try {
			ColumnParent parent = new ColumnParent("test");

			long timeStamp = System.currentTimeMillis();

			Column idColumnPageid = new Column();
			idColumnPageid.setName(this.cassandraUtil.toByteBuffer(String.valueOf(key)));
			idColumnPageid.setValue(this.cassandraUtil.toByteBuffer(val));
			idColumnPageid.setTimestamp(timeStamp);
			client.insert(this.cassandraUtil.toByteBuffer(String.valueOf(userid)), parent, idColumnPageid, ConsistencyLevel.ONE);

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TestClass tc = new TestClass();
		
		tc.InsertTest3(115119 , -200, "07018720_1.3.0.0.0");
	}

}
