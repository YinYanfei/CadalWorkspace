package cn.edu.zju.cadal.hbase.read;

import java.io.IOException;
import java.util.ArrayList;
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

public class ReadTagOp {

	private Handler handler = null;
	private HTable htable = null;

	private byte[] cf = null;
	private byte[] cl_ip = null;
	private byte[] cl_user = null;
	private byte[] cl_bookid = null;
	private byte[] cl_tagname = null;
	private byte[] cl_time = null;

	public ReadTagOp() {
		this.handler = new Handler();
		this.htable = this.handler.HTableHandler(HBaseTable.READ_TAG);
		this.cf = Bytes.toBytes("tag");
		this.cl_ip = Bytes.toBytes("ip");
		this.cl_user = Bytes.toBytes("user");
		this.cl_bookid = Bytes.toBytes("bookid");
		this.cl_tagname = Bytes.toBytes("tagname");
		this.cl_time = Bytes.toBytes("time");
	}

	/**
	 * Insert one row into table HBaseTable.READ_COMMENT
	 * 
	 * @param ip
	 * @param user
	 * @param bookid
	 * @param tagname
	 * @param timestamp
	 * @return
	 */
	public boolean insertSingleRow(String ip, String user, String bookid,
			String tagname, long timestamp) {
		try {
			Put put = new Put(HBaseUtil.rowKey(timestamp, ip, user));
			put.add(this.cf, this.cl_ip, Bytes.toBytes(ip));
			put.add(this.cf, this.cl_user, Bytes.toBytes(user));
			put.add(this.cf, this.cl_bookid, Bytes.toBytes(bookid));
			put.add(this.cf, this.cl_tagname, Bytes.toBytes(tagname));
			put.add(this.cf, this.cl_time, Bytes.toBytes(timestamp));

			this.htable.put(put);
			// this.htable.close();

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
	 *            'ip':'10.15.62.75','user':'Yanfei','bookid':'07018720','tagname':'my
	 *            tag','time':'1234567890' ], [
	 *            'ip':'10.15.62.75','user':'Yanfei','bookid':'07018720','tagname':'my
	 *            tag','time':'1234567890' ], ... ]
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
				put.add(this.cf, this.cl_tagname,
						Bytes.toBytes(rowInfo.get("tagname")));
				put.add(this.cf, this.cl_time,
						Bytes.toBytes(Long.valueOf(rowInfo.get("time"))));

				listPut.add(put);
			}

			this.htable.put(listPut);
			// this.htable.close();

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return false;
	}

	/**
	 * 通过一段时间来获取所有的图书被打标签的信息，并写到文件中
	 * @param start
	 * @param end
	 * @param filePath
	 * @return 成功 返回true，失败 返回false
	 */
	public boolean Scanner(String start, String end, String filePath) {
		try {
			FileOp fileOp = new FileOp(filePath);
			for(int i = 0;i<HBaseUtil.numOfRegion;i++){
				Scan scan = new Scan();
				scan.setStartRow(HBaseUtil.rowkeyRegionTime(i, start));
				scan.setStopRow(HBaseUtil.rowkeyRegionTime(i, end));

				ResultScanner rs = htable.getScanner(scan);
				for (Result r : rs) {
					String record = ParseUtil.parseReadResult(r);
					fileOp.write(record);
					System.out.println(record);
				}
			}
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
		ReadTagOp readTagOp = new ReadTagOp();
		readTagOp.Scanner("2012-01-01 00:00:00", "2013-12-03 20:00:00",
				"C:\\Users\\hongxin\\Desktop\\test.out");
		// readTagOp.insertSingleRow("10.15.62.111", "hongxin", "07018721", "my tag", System.currentTimeMillis());
	}
}
