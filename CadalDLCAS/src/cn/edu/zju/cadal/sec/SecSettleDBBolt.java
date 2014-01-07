package cn.edu.zju.cadal.sec;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cn.edu.zju.cadal.utils.MySQLUtil;

/**
 * 根据获得的清理标记进行数据库的清理操作
 * 		- settle_each_minute: 每分钟清理一次，对恶意IP表中的IP是否还有效(Valid字段)进行判断和修改
 * 		- settle_each_day: 每天清理一次，将恶意IP表中的无效IP移动到Collect表中
 * 
 * @author CADAL
 */
public class SecSettleDBBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;
	
	private MySQLUtil mysqlUtil = null;
	
	private static String SETTLE_EACH_MINUTE = "settle_each_minute";
	private static String SETTLE_EACH_DAY = "settle_each_day";
	
	private static String TABLE_CURRENTDOUBTABLEIP = "CurrentDoubtableIP";
	
	private long timeStamp = 0;
	
	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;
		this.mysqlUtil = new MySQLUtil();
	}

	@SuppressWarnings("static-access")
	@Override
	public void execute(Tuple input) {
		try{
			String sig = input.getString(0);
			timeStamp = System.currentTimeMillis();
			
			if(sig.equals(this.SETTLE_EACH_MINUTE)) {
				// settle_each_minute
				this.settleEachMinute();
			}else if(sig.equals(this.SETTLE_EACH_DAY)) {
				// settle_each_day
				this.settleEachDay();
			}else{
				System.out.println("No matching");
			}
			
			this._collector.ack(input);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Settle DB for each minute
	 */
	private void settleEachMinute() {
		// TODO 对CurrentDoubtableIP表中的IP进行判断，如果超过指定的时间则将Valid字段置为 0 【无效】
		ResultSet rs = this.mysqlUtil.query(TABLE_CURRENTDOUBTABLEIP);
		String ip = "";
		String time = "";
		int times = 0;
		int valid = 0;
		
		try {
			while (rs.next()) {
				ip = rs.getString("IP");
				time = rs.getString("Time");
				times = rs.getInt("Times");
				valid = rs.getInt("Valid");
				
				if(valid == 1 && (this.timeStamp - this.mysqlUtil.tranTime(time) > ((1 << times) * 60 * 1000))) {
					this.mysqlUtil.updateValid(ip, valid);
					
					this.timeStamp = System.currentTimeMillis();		// update timestamp
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}


	}

	/**
	 * Settle DB for each day
	 */
	private void settleEachDay() {
		// TODO 将CurrentDoubtableIP表中的Valid为0的记录转移到Collector表中
		ResultSet rs = this.mysqlUtil.query(TABLE_CURRENTDOUBTABLEIP);
		String ip = "";
		String time = "";
		int times = 0;
		String userName = "";
		
		try {
			while (rs.next()) {
				ip = rs.getString("IP");
				time = rs.getString("Time");
				times = rs.getInt("Times");
				userName = rs.getString("UserName");
				
				this.mysqlUtil.insertIntoCollector(ip, userName, time, times);
			}
			
			this.mysqlUtil.delete(this.TABLE_CURRENTDOUBTABLEIP);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("settleMsgDB"));
	}

}
