package cn.cadal.storm.rec.chapterInfoOp;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;

import cn.cadal.storm.rec.cassandra.CassandraUtil;
import cn.cadal.storm.rec.cassandra.Connector;

public class InsertChapterInfo {
	
	private Connector connector = null;
	private Cassandra.Client client = null;
	private CassandraUtil cassandraUtil = null;
	
	public InsertChapterInfo() {
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
	 * Insert into CF -- "UserChapter"
	 * @param int userid: the key of column   like '119115'
	 * @param int signal: chapter's signal	  like '23'
	 * @param String content: chapter's value like '"07018720_1.0.0.0.0"'
	 */
	public boolean InsertIntoUserChapter(int userid, int signal, String content){
//		System.out.println("------------InsertIntoUserChapter--------------");

		try {
			ColumnParent parent = new ColumnParent("UserChapter");

			long timeStamp = System.currentTimeMillis();

			Column userChapter = new Column();
			userChapter.setName(this.cassandraUtil.toByteBuffer(String.valueOf(signal)));
			userChapter.setValue(this.cassandraUtil.toByteBuffer(content));
			userChapter.setTimestamp(timeStamp);
			client.insert(this.cassandraUtil.toByteBuffer(String.valueOf(userid)), parent, userChapter, ConsistencyLevel.QUORUM);

			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Insert into CF -- "ChapterSignalMap"
	 * @param String content: chapter's value like '"07018720_1.0.0.0.0"'
	 * @param int signal: chapter's signal	  like '23'
	 * This function will columate 'maxid' automatic.
	 */
	public boolean InsertIntoChapterSignalMap(String content, int signal){
//		System.out.println("------------InsertIntoChapterSignalMap--------------");

		try {
			ColumnParent parent = new ColumnParent("ChapterSignalMap");

			long timeStamp = System.currentTimeMillis();
			
			// insert into new column
			Column chapterSignal = new Column();
			chapterSignal.setName(this.cassandraUtil.toByteBuffer("signal"));
			chapterSignal.setValue(this.cassandraUtil.toByteBuffer(String.valueOf(signal)));
			chapterSignal.setTimestamp(timeStamp);
			client.insert(this.cassandraUtil.toByteBuffer(content), parent, chapterSignal, ConsistencyLevel.QUORUM);
			
			// update 'maxid' column
			Column maxidColumn = new Column();
			maxidColumn.setName(this.cassandraUtil.toByteBuffer("maxid"));
			maxidColumn.setValue(this.cassandraUtil.toByteBuffer(String.valueOf(signal + 1)));
			maxidColumn.setTimestamp(timeStamp);
			client.insert(this.cassandraUtil.toByteBuffer("maxid"), parent, maxidColumn, ConsistencyLevel.QUORUM);			
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Insert into CF -- "SignalChapterMap"
	 * @param int signal: chapter's signal	  like '23'
	 * @param String content: chapter's value like '"07018720_1.0.0.0.0"'
	 */
	public boolean InsertIntoSignalChapterMap(int signal, String content){
//		System.out.println("------------InsertIntoSignalChapterMap--------------");

		try {
			ColumnParent parent = new ColumnParent("SignalChapterMap");

			long timeStamp = System.currentTimeMillis();

			Column signalChapter = new Column();
			signalChapter.setName(this.cassandraUtil.toByteBuffer("content"));
			signalChapter.setValue(this.cassandraUtil.toByteBuffer(content));
			signalChapter.setTimestamp(timeStamp);
			client.insert(this.cassandraUtil.toByteBuffer(String.valueOf(signal)), parent, signalChapter, ConsistencyLevel.QUORUM);
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Update 'maxid' in CF 'ChapterSignalMap'
	 */
	public boolean UpdateMaxid(String signal) {
		
		try{
			ColumnParent parent = new ColumnParent("ChapterSignalMap");
			
			long timeStamp = System.currentTimeMillis();
			
			Column signalCol = new Column();
			signalCol.setName(this.cassandraUtil.toByteBuffer("maxid"));
			signalCol.setValue(this.cassandraUtil.toByteBuffer(signal));
			signalCol.setTimestamp(timeStamp);
			
			this.client.insert(this.cassandraUtil.toByteBuffer("maxid"), parent, signalCol, ConsistencyLevel.QUORUM);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
//		InsertChapterInfo ici = new InsertChapterInfo();
		
//		ici.InsertIntoUserChapter(119115, 24, "07018720_1.0.0.0.0");
//		ici.InsertIntoChapterSignalMap("07018720_1.1.0.0.0", 2);
//		ici.InsertIntoSignalChapterMap(23, "07018720_2.0.0.0.0");
//		ici.UpdateMaxid("1");
		
	}

}
