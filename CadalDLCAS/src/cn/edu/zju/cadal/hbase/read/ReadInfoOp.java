package cn.edu.zju.cadal.hbase.read;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

import cn.edu.zju.cadal.hbase.Handler;
import cn.edu.zju.cadal.utils.FileOp;
import cn.edu.zju.cadal.utils.HBaseTable;
import cn.edu.zju.cadal.utils.HBaseUtil;
import cn.edu.zju.cadal.utils.ParseUtil;

public class ReadInfoOp {
	protected Handler handler = null;
	private HTable htable = null;

	protected byte[] cf = null;
	protected byte[] cl_ip = null;
	protected byte[] cl_user = null;
	protected byte[] cl_bookid = null;
	protected byte[] cl_pageno = null;
	protected byte[] cl_time = null;

	public ReadInfoOp() {
		this.handler = new Handler();
		this.htable = this.handler.HTableHandler(HBaseTable.READ_INFO);
		this.cf = Bytes.toBytes("info");
		this.cl_ip = Bytes.toBytes("ip");
		this.cl_user = Bytes.toBytes("user");
		this.cl_bookid = Bytes.toBytes("bookid");
		this.cl_pageno = Bytes.toBytes("pageno");
		this.cl_time = Bytes.toBytes("time");
	}

	/**
	 * Insert single row into table HBaseTable.READ_INFO
	 * 
	 * @param ip
	 * @param user
	 * @param bookid
	 * @param pageno
	 * @param timestamp
	 * @return
	 */
	public boolean insertSingleRow(String ip, String user, String bookid,
			String pageno, long timestamp) {
		try {
			Put put = new Put(HBaseUtil.rowKey(timestamp, ip, user));
			put.add(this.cf, this.cl_ip, Bytes.toBytes(ip));
			put.add(this.cf, this.cl_user, Bytes.toBytes(user));
			put.add(this.cf, this.cl_bookid, Bytes.toBytes(bookid));
			put.add(this.cf, this.cl_pageno, Bytes.toBytes(pageno));
			put.add(this.cf, this.cl_time, Bytes.toBytes(timestamp));

			this.htable.put(put);
			// htable.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Insert multiple rows each time
	 * 
	 * @param listRowInfo
	 *            [ [
	 *            'ip':'10.15.62.75','user':'Yanfei','bookid':'07018720','pageno':'0000001','time':'1234567890
	 *            ' ], [
	 *            'ip':'10.15.62.75','user':'Yanfei','bookid':'07018720','pageno':'0000001','time':'1234567890
	 *            ' ], ... ]
	 * 
	 * @return
	 */
	public boolean insertMultiRow(List<Map<String, String>> listRowInfo) {
		try {
			List<Put> listPut = new ArrayList<Put>();

			for (Map<String, String> rowInfo : listRowInfo) {
				Put put = new Put(HBaseUtil.rowKey(
						Long.valueOf(rowInfo.get("timestamp")),
						rowInfo.get("ip"), rowInfo.get("user")));

				put.add(this.cf, this.cl_ip, Bytes.toBytes(rowInfo.get("ip")));
				put.add(this.cf, this.cl_user,
						Bytes.toBytes(rowInfo.get("user")));
				put.add(this.cf, this.cl_bookid,
						Bytes.toBytes(rowInfo.get("bookid")));
				put.add(this.cf, this.cl_pageno,
						Bytes.toBytes(rowInfo.get("pageno")));
				put.add(this.cf, this.cl_time,
						Bytes.toBytes(Long.valueOf(rowInfo.get("time"))));

				listPut.add(put);
			}

			this.htable.put(listPut);
			// htable.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 获取一段时间用户阅读书页的信息，并写入文件
	 * @param start 起始时间 YYYY-MM-DD HH:mm:ss
	 * @param end 结束时间  YYYY-MM-DD HH:mm:ss
	 * @param filePath 
	 * @return 成功返回true,失败返回false
	 */
	public boolean Scanner(String start, String end, String filePath) {
		try {
			FileOp fileOp = new FileOp(filePath);
			int count = 0;
			for (int i = 0; i < HBaseUtil.numOfRegion; i++) {
				Scan scan = new Scan();
				scan.setStartRow(HBaseUtil.rowkeyRegionTime(i, start));
				scan.setStopRow(HBaseUtil.rowkeyRegionTime(i, end));
				ResultScanner rs = htable.getScanner(scan);
				for (Result r : rs) {
					count ++;
					String record = ParseUtil.parseReadResult(r);
					fileOp.write(record);
					System.out.println(record);
				}
			}
			System.out.println("total: " + count);
			fileOp.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TEST
		ReadInfoOp readInfoOp = new ReadInfoOp();
		readInfoOp.Scanner("2013-12-09 18:47:00", "2013-12-13 08:00:00","C:\\Users\\hongxin\\Desktop\\out\\readInfoOp.out");
		// HBaseUtil.time2ByteArr("2013-12-05 12:00:00.123456");
		// readInfoOp.insertSingleRow("10.15.62.242", "CADAL", "00000001",
		// "00000001", 1386566359062000L);//13:19
		// readInfoOp.insertSingleRow("10.15.62.242", "CADAL", "00000002",
		// "00000001", 1386566419062001L);
		// readInfoOp.insertSingleRow("10.15.62.242", "CADAL", "00000003",
		// "00000001", 1386566479062002L);
		// readInfoOp.insertSingleRow("10.15.62.242", "CADAL", "00000004",
		// "00000001", 1386566539062003L);
		// readInfoOp.insertSingleRow("10.15.62.242", "CADAL", "00000005",
		// "00000001", 1386566599062004L);
		// readInfoOp.insertSingleRow("10.15.62.242", "CADAL", "00000006",
		// "00000001", 1386566659062005L);
		// readInfoOp.insertSingleRow("10.15.62.242", "CADAL", "00000007",
		// "00000001", 1386566719062006L);
		// System.out.println(System.currentTimeMillis());

	}
}
