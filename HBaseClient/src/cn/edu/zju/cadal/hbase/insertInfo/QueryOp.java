package cn.edu.zju.cadal.hbase.insertInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

public class QueryOp {
	public static String TABLENAME = "access-log";

	private Utils hbaseOp = null;

	/**
	 * Construct functions
	 * 
	 * @throws Exception
	 */
	public QueryOp() throws Exception {
		this.hbaseOp = new Utils(this.TABLENAME);
	}

	/**
	 * Scan function for HBase
	 * 
	 * @param startKey
	 * @param endKey
	 */
	private ResultScanner Scan(String startKey, String endKey) {
		try {
			Scan scan = new Scan(Bytes.toBytes(startKey), Bytes.toBytes(endKey));
			scan.addColumn(Bytes.toBytes("info"), Bytes.toBytes("url"));
			return this.hbaseOp.htable.getScanner(scan);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * To get totally url for ip '165.44.19.36'
	 * --第一问--
	 */
	public void GetIpUrl() {
		int ipInteger = this.hbaseOp.TransferIPToInt("165.44.19.36");

		String startKey = ipInteger + "-" + 0;
		String endKey = ipInteger + "-" + Long.valueOf("999999999999999999");

		ResultScanner resultScanner = this.Scan(startKey, endKey);

		List<String> resList = new ArrayList<String>();

		for (Result result : resultScanner) {
			byte[] valueBytes = result.getValue(Bytes.toBytes("info"), Bytes
					.toBytes("url"));
			String val = Bytes.toString(valueBytes);
			if (!resList.contains(val)) {
				resList.add(val);
			}
		}

		// Print
		for (String str : resList) {
			System.out.println(str);
		}
	}

	/**
	 * To get url for ip '165.44.19.36' on each day, time from 00:00:00 to
	 * 12:00:00
	 */
	public void GetIpTimeUrl() {
		// 第二问，还没有想好怎么去做
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// Test
		 QueryOp queryOp = new QueryOp();
				
		 queryOp.GetIpUrl();
		 queryOp.GetIpTimeUrl();
		
		 queryOp.hbaseOp.CloseHTable();
	}
}
