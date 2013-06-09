package cn.cadal.storm.bolt.cassandra;

import java.nio.ByteBuffer;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class CassandraOp {

	java.sql.Connection con = null;
	
	/**
	 * Construct functions
	 */
	public CassandraOp() {
	}
	
	/**
	 * Store into cassandra	
	 */
	public void Store(String str) {
		String [] strList = str.split(" ");
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
		String dateStr = sdf.format(date);
		
		TTransport tr = new TFramedTransport(new TSocket("10.15.62.75", 9160));
		TProtocol proto = new TBinaryProtocol(tr);
		Cassandra.Client client = new Cassandra.Client(proto);
		
		try{
			tr.open();
			
			if(!tr.isOpen()) {
				System.out.println("Failed! try again!");
				return;
			}
			
			long timeTmp = System.currentTimeMillis();
			
			client.set_keyspace("MyKeyspace2");
			ColumnParent parent = new ColumnParent("cadal");

			long timeStamp = System.currentTimeMillis();

			Column idColumnIp = new Column();
			idColumnIp.setName(toByteBuffer("ip"));
			idColumnIp.setValue(toByteBuffer(strList[0]));
			idColumnIp.setTimestamp(timeStamp);
			client.insert(toByteBuffer(dateStr), parent, idColumnIp, ConsistencyLevel.ONE);
			
			Column idColumnBookid = new Column();
			idColumnBookid.setName(toByteBuffer("bookid"));
			idColumnBookid.setValue(toByteBuffer(strList[1]));
			idColumnBookid.setTimestamp(timeStamp);
			client.insert(toByteBuffer(dateStr), parent, idColumnBookid, ConsistencyLevel.ONE);

			Column idColumnPageid = new Column();
			idColumnPageid.setName(toByteBuffer("pageid"));
			idColumnPageid.setValue(toByteBuffer(strList[2]));
			idColumnPageid.setTimestamp(timeStamp);
			client.insert(toByteBuffer(dateStr), parent, idColumnPageid, ConsistencyLevel.ONE);

		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	/* 
     * 将String转换为bytebuffer，以便插入cassandra 
     */  
	public static ByteBuffer toByteBuffer(String value) {
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
		CassandraOp cassandraOp = new CassandraOp();
		cassandraOp.Store("10.15.62.110 37018720 00000037");
	}

}



/*		
		try{
			String insertStr = "insert into cadal(key, ip, bookid, pageid) values('" 
				+ dateStr + "', '" + strList[0] + "', '" + strList[1] + "', '" + strList[2] + "');";
			System.out.println(insertStr);
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			con = DriverManager.getConnection("jdbc:cassandra://10.15.62.75:9160/MyKeyspace2");
			PreparedStatement statement = con.prepareStatement(insertStr);
			statement.execute(insertStr);
		}catch(Exception e) {
			e.printStackTrace();
		}
*/


/*		Date date = new Date();
		System.out.println(String.valueOf(date.getTime()));

		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SS");// 其中yyyy-MM-dd是你要表示的格式
		// 可以任意组合，不限个数和次序；具体表示为：MM-month,dd-day,yyyy-year;kk-hour,mm-minute,ss-second;
		String str = sdf.format(d);
		System.out.println("The date is : " + str);
*/