package cn.edu.zju.cadal.sec;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

/**
 * Calculate the number of page, reading by single ip or single user in CADAL
 * web site, if the number is larger than THRESHOLD, then we can judge this IP
 * is unfriendly.
 * 
 * @author CADAL
 */
public class SecEstCountBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	private OutputCollector _collector = null;

	private Map<String, Integer> ipSigMap = null;
	private Map<Integer, Queue<Long>> sigTimeMap = null;

	private Random random = null;
	private static int RANDOM_MAX = 10000;

	private static int THRESHOLD = 60; // 指定时间间隔内的恶意IP阈值
	private static int TIMELONGEST = 60000000; // 最长时间间隔	【1 min】

	@SuppressWarnings("rawtypes")
	@Override
	public void prepare(Map stormConf, TopologyContext context,
			OutputCollector collector) {
		this._collector = collector;

		this.ipSigMap = new HashMap<String, Integer>();
		this.sigTimeMap = new HashMap<Integer, Queue<Long>>();

		this.random = new Random();
	}

	@Override
	public void execute(Tuple input) {
		try {
			String msg = (String) input.getValue(0); // 10.15.62.110#$#hongxin#$#15023834#$#00000002#$#1386896488374218

			String[] msgArr = msg.split("#\\$#");

			// For ipSigMap
			String ipUser = msgArr[0] + "#$#" + msgArr[1];
			Integer sigForIpUser = this.random.nextInt(this.RANDOM_MAX);

			// For sigTimeMap
			Long time = Long.valueOf(msgArr[4]);

			String resIpInfo = null; // 保存处理之后预测到的恶意IP信息
			
			if (this.ipSigMap.containsKey(ipUser)) {
				sigForIpUser = this.ipSigMap.get(ipUser);
				Queue<Long> tmpQueue = this.sigTimeMap.get(sigForIpUser);

				tmpQueue.add(time);
				this.sigTimeMap.put(sigForIpUser, tmpQueue);
				
				resIpInfo = this.dealInSigTimeMap();
			} else {
				this.ipSigMap.put(ipUser, sigForIpUser);
				Queue<Long> queueVal = new LinkedList<Long>();
				queueVal.add(time);
				
				this.sigTimeMap.put(sigForIpUser, queueVal);
			}

			// emit
			if (resIpInfo != null) {
				this._collector.emit(new Values(resIpInfo));
			}

			this._collector.ack(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 这个函数非常的关键，主要做了一下的几个事情： 
	 * -- 遍历两个Map数据结构 
	 * -- 将sigTimeMap中离现在的时间超过N的queue记录删除 
	 * -- 在sigTimeMap中找出超过指定次数的Sig记录，并在ipSigMap表中找出这个ip相关信息 
	 * -- 删除恶意ip的Map结构信息 
	 * -- 删除sigTimeMap中queue为空的记录信息，以及ipSigMap中对应的记录
	 * 
	 * @param ipUser
	 * @return 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
	private String dealInSigTimeMap() {
		Long curTime = System.currentTimeMillis() * 1000;
		String ipInfo = null;

		Iterator iter = this.sigTimeMap.entrySet().iterator();

		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();
			Integer key = (Integer) entry.getKey();
			Queue<Long> val = (Queue<Long>) entry.getValue();

			if (val.size() != 0) {				// 这个地方之前写成了while循环 【重要】
				Long timeInVal = val.peek();
				if (timeInVal != null) {
					if (curTime - timeInVal > this.TIMELONGEST) {
						val.poll();
						break;
					}
				}
			}

			// 判断是否为恶意
			Iterator iter_ipSigMap = null;
			if (val.size() > this.THRESHOLD) {
				// 判断是否为恶意IP，通过value获得key【this.ipSigMap】
				iter_ipSigMap = this.ipSigMap.entrySet().iterator();
				while (iter_ipSigMap.hasNext()) {
					Entry entry_ipSigMap = (Entry) iter_ipSigMap.next();
					String key_ipSigMap = (String) entry_ipSigMap.getKey();
					Integer val_ipSigMap = (Integer) entry_ipSigMap.getValue();
					if (val_ipSigMap == key) {
						ipInfo = key_ipSigMap;
						break;
					}
				}
			}

			// 清除相关数据
			if(val.size() == 0) {
				String key_del = null;
				iter_ipSigMap = this.ipSigMap.entrySet().iterator();
				while (iter_ipSigMap.hasNext()) {
					Entry entry_ipSigMap = (Entry) iter_ipSigMap.next();
					String key_ipSigMap = (String) entry_ipSigMap.getKey();
					Integer val_ipSigMap = (Integer) entry_ipSigMap.getValue();
					
					if (val_ipSigMap == key) {
						key_del = key_ipSigMap;
						iter_ipSigMap.remove();
						break;
					}
				}
				
				iter.remove();		// sigTimeMap中删除相应的字段
			}else if (ipInfo != null) {
				// 清除两个Map的数据
				iter_ipSigMap.remove();
				iter.remove();
				
				break;
			}
		}

		return ipInfo;
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("unfriendIP"));
	}
}
