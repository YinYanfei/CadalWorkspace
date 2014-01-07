package cn.edu.zju.cadal.hbase;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;

public class Handler {
	
	private static Configuration conf = null;

	static {
		conf = HBaseConfiguration.create();
	}

	/**
	 * Generate handler for HBaseAdmin
	 * 
	 * @return
	 */
	public HBaseAdmin HBaseAdminHandler() {
		try {
			return new HBaseAdmin(this.conf);
		} catch (MasterNotRunningException e) {
			e.printStackTrace();
		} catch (ZooKeeperConnectionException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Generate handler for HTable using table's name
	 * 
	 * @param tablename
	 * @return
	 */
	public HTable HTableHandler(String tablename) {
		try {
			return new HTable(this.conf, tablename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Close handler of HBaseAdmin obj
	 * 
	 * @param hbaseAdmin
	 * @return
	 */
	public boolean closeHBaseAdmin(HBaseAdmin hbaseAdmin) {
		try {
			hbaseAdmin.close();
			
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Close handler of HTable obj
	 * 
	 * @param htable
	 * @return
	 */
	public boolean closeHTable(HTable htable) {
		try{
			htable.close();
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
}
