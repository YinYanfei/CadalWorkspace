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

import cn.edu.zju.cadal.utils.FileOp;
import cn.edu.zju.cadal.utils.HBaseTable;
import cn.edu.zju.cadal.utils.HBaseUtil;
import cn.edu.zju.cadal.utils.ParseUtil;

public class ReadInfoBookOp extends ReadInfoOp {
	
	private HTable htable = null;
	
	public ReadInfoBookOp() {
		this.htable = this.handler.HTableHandler(HBaseTable.READ_INFO_BOOK);
		this.cf = Bytes.toBytes("info_book");
	}
	
	/**
	 * Insert single row into table HBaseTable.READ_INFO_USER
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
			Put put = new Put(HBaseUtil.rowkeyBookTime(bookid, timestamp));
			
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
	 *        [
	 *            [
	 *            'ip':'10.15.62.75','user':'Yanfei','bookid':'07018720','pageno':'0000001','timestamp':'1234567890'
	 *            ],
	 *            [
	 *            'ip':'10.15.62.75','user':'Yanfei','bookid':'07018720','pageno':'0000001','timestamp':'1234567890'
	 *            ],
	 *            ...
	 *        ]
	 * 
	 * @return
	 */
	public boolean insertMultiRow(List<Map<String, String>> listRowInfo) {
		try {
			List<Put> listPut = new ArrayList<Put>();
			
			for(Map<String,String> rowInfo: listRowInfo) {
				Put put = new Put(HBaseUtil.rowkeyBookTime(rowInfo.get("bookid"), Long.valueOf(rowInfo.get("timestamp"))));
				
				put.add(this.cf, this.cl_ip, Bytes.toBytes(rowInfo.get("ip")));
				put.add(this.cf, this.cl_user, Bytes.toBytes(rowInfo.get("user")));
				put.add(this.cf, this.cl_bookid, Bytes.toBytes(rowInfo.get("bookid")));
				put.add(this.cf, this.cl_pageno, Bytes.toBytes(rowInfo.get("pageno")));
				put.add(this.cf, this.cl_time, Bytes.toBytes(Long.valueOf(rowInfo.get("timestamp"))));
				
				listPut.add(put);
			}
			
			this.htable.put(listPut);
			// htable.close();
			
			return true;
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * 读取指定bookid的图书被阅读信息,写到指定文件中
	 * @param bookid 
	 * @param filePath 
	 * @return 成功 返回true，失败 返回false
	 */
	public boolean Scanner(String bookid,String filePath){
		return this.Scanner(bookid,"2000-01-01 00:00:00","2099-12-31 23:59:59",filePath);
	}
	
	/**
	 * 读取指定bookid在一定时间内的被阅读信息，并写到指定文件中
	 * @param bookid
	 * @param start
	 * @param end
	 * @param filePath
	 * @return 成功 返回true，失败 返回false
	 */
	public boolean Scanner(String bookid, String start, String end,String filePath){
		try{
			Scan scan = new Scan();
			byte[] rowStart = new byte[16];// 8 + 8  bookid + time
			byte[] rowEnd = new byte[16];
			System.arraycopy(Bytes.toBytes(bookid), 0, rowStart, 0, 8);
			System.arraycopy(Bytes.toBytes(bookid), 0, rowEnd, 0, 8);
			System.arraycopy(HBaseUtil.time2ByteArr(start), 0, rowStart, 8, 8);
			System.arraycopy(HBaseUtil.time2ByteArr(end), 0, rowEnd, 8, 8);
			scan.setStartRow(rowStart);
			scan.setStopRow(rowEnd);
			
			ResultScanner rs = htable.getScanner(scan);
			FileOp fileOp = new FileOp(filePath);
			for(Result r : rs){
				String record = ParseUtil.parseReadResult(r);
				fileOp.write(record);
				System.out.println(record);
			}
			fileOp.close();
			return true;
		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TEST
		ReadInfoBookOp readInfoBookOp = new ReadInfoBookOp();
		readInfoBookOp.Scanner("07018720","C:\\Users\\hongxin\\Desktop\\test.out");
//		readInfoBookOp.Scanner("07018720","2012-01-01 00:00:00","2013-12-03 20:00:00");
//		readInfoBookOp.insertSingleRow("10.15.62.110", "Yanfei", "07018720", "00000001", System.currentTimeMillis());
	}

}
