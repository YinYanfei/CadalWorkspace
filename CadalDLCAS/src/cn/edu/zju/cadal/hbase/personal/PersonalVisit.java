package cn.edu.zju.cadal.hbase.personal;

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

public class PersonalVisit {

	private Handler handler = null;
	private HTable htable = null;

	private byte[] cf = null;
	private byte[] cl_ip = null;
	private byte[] cl_user = null;
	private byte[] cl_user_v = null;
	private byte[] cl_time = null;

	public PersonalVisit() {
		this.handler = new Handler();
		this.htable = this.handler.HTableHandler(HBaseTable.PERSONAL_VISIT);
		this.cf = Bytes.toBytes("visit");
		this.cl_ip = Bytes.toBytes("ip");
		this.cl_user = Bytes.toBytes("user");
		this.cl_user_v = Bytes.toBytes("user_v");
		this.cl_time = Bytes.toBytes("time");
	}

	/**
	 * Insert single row into table HBaseTable.PERSONAL_VISIT
	 * 
	 * @param ip
	 * @param user
	 * @param user_v
	 * @param timestamp
	 * @return
	 */
	public boolean insertSingleRow(String ip, String user, String user_v, long timestamp) {
		try {
			Put put = new Put(HBaseUtil.rowKey(timestamp, ip, user));
			put.add(this.cf, this.cl_ip, Bytes.toBytes(ip));
			put.add(this.cf, this.cl_user, Bytes.toBytes(user));
			put.add(this.cf, this.cl_user_v, Bytes.toBytes(user_v));
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
	 *            'ip':'10.15.62.75','user':'Yanfei','user_v':'zju','time':'1234567890'
	 *            ],
	 *            [
	 *            'ip':'10.15.62.75','user':'Yanfei','user_v':'zju','time':'1234567890'
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
				put.add(this.cf, this.cl_user_v, Bytes.toBytes(rowInfo.get("user_v")));
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
	 * 通过一段时间来获取用户对其他用户个人页面的访问信息,并写入文件
	 * @param start
	 * @param end
	 * @param filePath
	 * @return 成功返回true, 失败返回false
	 */
	public boolean Scanner(String start, String end, String filePath){
		try{
			FileOp fileOp = new FileOp(filePath);
			for(int i =0;i<HBaseUtil.numOfRegion;i++){
				Scan scan = new Scan();
				scan.setStartRow(HBaseUtil.rowkeyRegionTime(i, start));
				scan.setStopRow(HBaseUtil.rowkeyRegionTime(i, end));
				
				ResultScanner rs = htable.getScanner(scan);
				for(Result r : rs){
					String record = ParseUtil.parsePersonalResult(r);
					fileOp.write(record);
					System.out.println(record);
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
		PersonalVisit personalVisit = new PersonalVisit();
		personalVisit.Scanner("2000-01-01 00:00:00", "2013-12-31 21:00:00","C:\\Users\\hongxin\\Desktop\\out\\share.out");
//		personalVisit.insertSingleRow("10.15.62.118", "Yanfei", "zju", System.currentTimeMillis());
	}

}
