package cn.edu.zju.cadal.hbase.search;

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

public class SearchTermOp {
	private Handler handler = null;
	private HTable htable = null;

	private byte[] cf = null;
	private byte[] cl_ip = null;
	private byte[] cl_user = null;
	private byte[] cl_query = null;
	private byte[] cl_time = null;

	public SearchTermOp() {
		this.handler = new Handler();
		this.htable = this.handler.HTableHandler(HBaseTable.SEARCH_TERM);
		this.cf = Bytes.toBytes("term");
		this.cl_ip = Bytes.toBytes("ip");
		this.cl_user = Bytes.toBytes("user");
		this.cl_query = Bytes.toBytes("query");
		this.cl_time = Bytes.toBytes("time");
	}

	/**
	 * Insert single row into table HBaseTable.SEARCH_TERM
	 * 
	 * @param ip
	 * @param user
	 * @param query
	 * @param timestamp
	 * @return
	 */
	public boolean insertSingleRow(String ip, String user, String query, long timestamp) {
		try {
			Put put = new Put(HBaseUtil.rowKey(timestamp, ip, user));
			put.add(this.cf, this.cl_ip, Bytes.toBytes(ip));
			put.add(this.cf, this.cl_user, Bytes.toBytes(user));
			put.add(this.cf, this.cl_query, Bytes.toBytes(query));
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
	 *            'ip':'10.15.62.75','user':'Yanfei','query':'�㽭��ѧ','time':'1234567890'
	 *            ],
	 *            [
	 *            'ip':'10.15.62.75','user':'Yanfei','query':'�㽭��ѧ','time':'1234567890'
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
				Put put = new Put(HBaseUtil.rowKey(Long.valueOf(rowInfo.get("timestamp")), rowInfo.get("ip"), rowInfo.get("user")));
				put.add(this.cf, this.cl_ip, Bytes.toBytes(rowInfo.get("ip")));
				put.add(this.cf, this.cl_user, Bytes.toBytes(rowInfo.get("user")));
				put.add(this.cf, this.cl_query, Bytes.toBytes(rowInfo.get("query")));
				put.add(this.cf, this.cl_time, Bytes.toBytes(Long.valueOf(rowInfo.get("time"))));
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
	 * 通过一段时间来获取用户的检索信息,并写入文件
	 * @param start
	 * @param end
	 * @return 成功返回true, 出错返回false
	 */
	public boolean Scanner(String start, String end, String filePath){
		try{
			FileOp fileOp = new FileOp(filePath);
			for(int i=0;i<HBaseUtil.numOfRegion;i++){
				Scan scan = new Scan();
				scan.setStartRow(HBaseUtil.rowkeyRegionTime(i, start));
				scan.setStopRow(HBaseUtil.rowkeyRegionTime(i, end));
				ResultScanner rs = htable.getScanner(scan);
				for(Result r : rs){
					String record = ParseUtil.parseSearchResult(r);
					System.out.println(record);
					fileOp.write(record); // write to file
				}
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
		SearchTermOp searchTerm = new SearchTermOp();
		searchTerm.Scanner("2000-01-01 00:00:00", "2013-12-31 23:59:59","C:\\Users\\hongxin\\Desktop\\out\\searchTermOp.out");
	//	searchTerm.insertSingleRow("10.15.62.112", "Yanfei", "�㽭��ѧ", System.currentTimeMillis());
	}

}
