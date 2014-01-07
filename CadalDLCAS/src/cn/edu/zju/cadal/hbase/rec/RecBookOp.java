package cn.edu.zju.cadal.hbase.rec;

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

public class RecBookOp {
	private Handler handler = null;
	private HTable htable = null;

	private byte[] cf = null;
	private byte[] cl_ip = null;
	private byte[] cl_user = null;
	private byte[] cl_bookid = null;
	private byte[] cl_source = null;
	private byte[] cl_time = null;

	public RecBookOp() {
		this.handler = new Handler();
		this.htable = this.handler.HTableHandler(HBaseTable.REC_BOOK);
		this.cf = Bytes.toBytes("book");
		this.cl_ip = Bytes.toBytes("ip");
		this.cl_user = Bytes.toBytes("user");
		this.cl_bookid = Bytes.toBytes("bookid");
		this.cl_source = Bytes.toBytes("source");
		this.cl_time = Bytes.toBytes("time");
	}

	/**
	 * Insert single row into table HBaseTable.REC_BOOK
	 * 
	 * @param ip
	 * @param user
	 * @param bookid
	 * @param source
	 * @param timestamp
	 * @return
	 */
	public boolean insertSingleRow(String ip, String user, String bookid,
			String source, long timestamp) {
		try {
			Put put = new Put(HBaseUtil.rowKey(timestamp, ip, user));
			put.add(this.cf, this.cl_ip, Bytes.toBytes(ip));
			put.add(this.cf, this.cl_user, Bytes.toBytes(user));
			put.add(this.cf, this.cl_bookid, Bytes.toBytes(bookid));
			put.add(this.cf, this.cl_source, Bytes.toBytes(source));
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
	 *            'ip':'10.15.62.75','user':'Yanfei','bookid':'07018720','source':'HomePage','time':'1234567890
	 *            ' ], [
	 *            'ip':'10.15.62.75','user':'Yanfei','bookid':'07018720','source':'HomePage','time':'1234567890
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
				put.add(this.cf, this.cl_source,
						Bytes.toBytes(rowInfo.get("source")));
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
	 * 通过一段时间来获取推荐图书的点击信息,并写入文件
	 * @param start 
	 * @param end
	 * @param filePath
	 * @return 成功返回true, 失败返回false
	 */
	public boolean Scanner(String start, String end, String filePath) {
		try {
			FileOp fileOp = new FileOp(filePath);
			for(int i = 0; i<HBaseUtil.numOfRegion;i++){
				Scan scan = new Scan();
				scan.setStartRow(HBaseUtil.rowkeyRegionTime(i, start));
				scan.setStopRow(HBaseUtil.rowkeyRegionTime(i, end));
				ResultScanner rs = htable.getScanner(scan);
				for (Result r : rs) {
					String record = ParseUtil.parseRecResult(r);
					fileOp.write(record); // write to file
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
		RecBookOp recBook = new RecBookOp();
		recBook.Scanner("2000-01-01 00:00:00", "2013-12-31 21:00:00","C:\\Users\\hongxin\\Desktop\\out\\recBook.out");
//		 recBook.insertSingleRow("10.15.62.120", "Yanfei", "07018720",
	//	 "HomePage", System.currentTimeMillis());
	}

}
