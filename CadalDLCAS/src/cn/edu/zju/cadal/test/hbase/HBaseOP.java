package cn.edu.zju.cadal.test.hbase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseOP {

	private static Configuration conf = null;

	static {
		conf = HBaseConfiguration.create();
	}

	/**
	 * Create Tables by Java API
	 * 
	 * @param tableName
	 * @param cfs
	 * @throws IOException
	 */
	public void CreateTable(String tableName, String[] cfs) throws IOException {
		HBaseAdmin admin = new HBaseAdmin(this.conf);
		if (admin.tableExists(tableName)) {
			System.out.println("Existed!");
		} else {
			HTableDescriptor tableDesc = new HTableDescriptor(tableName);
			for (int i = 0; i < cfs.length; ++i) {
				tableDesc.addFamily(new HColumnDescriptor(cfs[i]));
			}
			admin.createTable(tableDesc);
			System.out.println("Create table successfully!");
		}
		admin.close();
	}

	/**
	 * Delete table from HBase cluster
	 * 
	 * @param tableName
	 * @throws Exception
	 */
	public void DeleteTable(String tableName) throws Exception {
		try {
			HBaseAdmin admin = new HBaseAdmin(this.conf);

			admin.disableTable(tableName);
			admin.deleteTable(tableName);
			System.out.println("Delete table successfully!");
			
			admin.close();
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * To write a row into table of HBase
	 * 
	 * @param tableName
	 * @param cfs
	 */
	public void WriteRow(String tableName, String[] cfs) {
		try {
			HTable table = new HTable(this.conf, tableName);
			Put put = new Put(Bytes.toBytes("rows2"));
			for (int j = 0; j < cfs.length; ++j) {
				put.add(Bytes.toBytes(cfs[j]), Bytes.toBytes(1),
						Bytes.toBytes("value_1"));
				put.add(Bytes.toBytes(cfs[j]), Bytes.toBytes(2),
						Bytes.toBytes("value_2"));
				table.put(put);
			}
			System.out.println("Write Successfully!");
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Delete rows from table of HBase
	 * 
	 * @param tableName
	 * @param rowKey
	 * @throws IOException
	 */
	public void DeleteRow(String tableName, String rowKey) throws IOException {
		HTable table = new HTable(this.conf, tableName);
		List list = new ArrayList();
		Delete dl = new Delete(rowKey.getBytes());
		list.add(dl);
		table.delete(list);
		System.out.println("Delete Successfully!");
		table.close();
	}

	/**
	 * Get a row from table of HBase
	 * 
	 * @param tableName
	 * @param rowKey
	 * @throws IOException
	 */
	public static void SelecteRow(String tableName, String rowKey)
			throws IOException {
		HTable table = new HTable(conf, tableName);
		Get get = new Get(rowKey.getBytes());

		Result result = table.get(get);
		for (KeyValue kv : result.raw()) {
			System.out.print(new String(kv.getRow()) + "  ");
			System.out.print(new String(kv.getFamily()) + "  ");
			System.out.print(new String(kv.getQualifier()) + "  ");
			System.out.print(kv.getTimestamp() + "  ");
			System.out.println(new String(kv.getValue()));
		}
		
		table.close();
	}

	/**
	 * Scan all table of HBase
	 * 
	 * @param tableName
	 */
	public void Scaner(String tableName) {
		try {
			HTable table = new HTable(this.conf, tableName);
			Scan scan = new Scan();
			ResultScanner rs = table.getScanner(scan);
			for (Result r : rs) {
				KeyValue[] kv = r.raw();
				for (int i = 0; i < kv.length; ++i) {
					System.out.print(new String(kv[i].getRow()) + "  ");
					System.out.print(new String(kv[i].getFamily()) + "  ");
					System.out.print(new String(kv[i].getQualifier()) + "  ");
					System.out.print(kv[i].getTimestamp());
					System.out.println(new String(kv[i].getValue()));
				}
			}
			table.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Test function, main
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		// Test
		HBaseOP hbaseOP = new HBaseOP();

		String tableName = "test_info";
		String[] cfs = new String[1];
		cfs[0] = "cfs1";
		try {
			// hbaseOP.CreateTable(tableName, cfs);
			
			// hbaseOP.WriteRow(tableName, cfs);

			// hbaseOP.SelecteRow(tableName, "rows2");
			
			// hbaseOP.Scaner(tableName);
			
			// hbaseOP.DeleteRow(tableName, "rows2");
			
			// hbaseOP.DeleteTable(tableName);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
