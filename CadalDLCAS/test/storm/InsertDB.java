package cn.edu.zju.cadal.test.storm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

import cn.edu.zju.cadal.test.utils.MockData;

public class InsertDB {

	private static Configuration conf = null;

	private HTable table = null;
	
	public InsertDB(String tableName) {
		conf = HBaseConfiguration.create();
		try {
			this.table = new HTable(tableName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Write information into table of HBase
	 * 
	 * @param tableName
	 * @param cfs
	 * @param key
	 * @param columnInfo
	 */
	public void WriteRow(String tableName, String cfs, String key,
			HashMap<String, String> columnInfo) {
		try {
			List<Put> listPut = new ArrayList<Put>();

			Iterator iter = columnInfo.entrySet().iterator();

			while (iter.hasNext()) {
				Put put = new Put(Bytes.toBytes(key));
				Entry entry = (Entry) iter.next();

				put.add(Bytes.toBytes(cfs),
						Bytes.toBytes(entry.getKey().toString()),
						Bytes.toBytes(entry.getValue().toString()));

				listPut.add(put);
			}

			this.table.put(listPut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test function, main.
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		String tableName = "user_info_2";
		InsertDB insertDB = new InsertDB(tableName);
		
		// String key = "Yanfei1234567890";
		// HashMap<String, String> columnInfo = new HashMap<String, String>();
		//
		// columnInfo.put("age", "22");
		// columnInfo.put("home", "Zhejiang");
		//
		// insertDB.WriteRow("user_info", "info", key, columnInfo);

		MockData mockData = new MockData();
		System.out.println(System.currentTimeMillis());
		for (int i = 0; i < 10000; i++) {
			String userInfo = mockData.getOneData().toString();

			String[] userInfoArr = userInfo.split(" ");

			String key = userInfoArr[0] + userInfoArr[1];
			String cfs = "info";
			HashMap<String, String> columnInfo = new HashMap<String, String>();

			columnInfo.put("age", userInfoArr[2]);
			columnInfo.put("hometown", userInfoArr[2]);

			insertDB.WriteRow("user_info_2", cfs, key, columnInfo);

		}
		System.out.println(System.currentTimeMillis());
	}

}
