package cn.cadal.dis.java.timereader;

import java.util.Date;

import cn.cadal.dis.java.cassandra.CasTimeReader;
import cn.cadal.dis.java.utils.TimeOp;

public class DisTimeReader {
	
	/**
	 * 
	 * @param time
	 *            2013-04-09 13:00
	 * @param before
	 *            12
	 * @return 2013-04-09 13:00 前12分钟的用户总数
	 */
	public int ReaderAnyMin(String time, int before) {
		int count = 0;
		TimeOp timeOp = new TimeOp();
		for (int i = 0; i < before; ++i) {
			Date date = timeOp.getFormatDate(time);
			String min = timeOp.getPreDate(date, "m", -i);
			count += ReaderOneMin(min);
		}
		return count;
	}
	
	/**
	 * @param time 
	 *             2013-04-09 13:00
	 * @return     5 [2013-04-09 12:30到2013-04-09 13:00的用户数]
	 */
	public int ReaderThirtyMin(String time) {
		return ReaderAnyMin(time, 30);
	}

	/**
	 * @param time  
	 *           2013-04-09 13:00
	 * @return   5 [2013-04-09 12:45到2013-04-09 13:00的用户数]
	 */
	public int ReaderFifteenMin(String time) {
		return ReaderAnyMin(time, 15);
	}

	/**
	 * @param time
	 *             2013-04-09 13:00
	 * @return 
	 *             5 [2013-04-09 12:59分的用户数]
	 */
	public int ReaderOneMin(String time) {
		CasTimeReader ctr = new CasTimeReader();
		return ctr.QueryOneMinute(time).size();
	}

	/**
	 * @param time
	 *           2013-04-09 13:00
	 * @return   5$12$... [2013-04-08 13:00到2013-04-09 13:00的用户数,默认时间粒度为30分钟]
	 */
	public String ReaderTwentyFourHour(String time) {
		return ReaderTwentyFourHour(time, 30);
	}

	/**
	 * @param time
	 *              2013-04-09 13:00
	 * @param step
	 *              15
	 * @return 
	 *              5$12$... [2013-04-08 13:00到2013-04-09 13:00的用户数,时间粒度为#step分钟]
	 */
	public String ReaderTwentyFourHour(String time, int step) {
		String res = "";
		boolean isFirst = true;
		TimeOp timeOp = new TimeOp();
		int round = 24 * 60 / step;
		for (int i = 0; i < round; ++i) {
			// System.out.println(time);
			int count = ReaderAnyMin(time, step);
			Date date = timeOp.getFormatDate(time);
			time = timeOp.getPreDate(date, "m", -step);
			if (isFirst) {
				res = res + count;
				isFirst = false;
			} else
				res = res + "$" + count;
		}
		return res;
	}

	/**
	 * @return 
	 *           5 [过去30分钟的用户数]
	 */
	public int ReaderThirtyMinCur() {
		int count = 0;
		TimeOp timeOp = new TimeOp();
		String curTime = timeOp.getPreDate("m", -1);
		count = ReaderThirtyMin(curTime);
		return count;
	}

	/**
	 * @return 
	 *            5 [过去15分钟的用户数]
	 */
	public int ReaderFifteenMinCur() {
		int count = 0;
		TimeOp timeOp = new TimeOp();
		String curTime = timeOp.getPreDate("m", -1);
		count = ReaderFifteenMin(curTime);
		return count;
	}

	/**
	 * @return  
	 *           5 [过去1分钟的用户数]
	 */
	public int ReaderOneMinCur() {
		int count = 0;
		TimeOp timeOp = new TimeOp();
		String curTime = timeOp.getPreDate("m", -1);
		count = ReaderOneMin(curTime);
		return count;
	}

	/**
	 * @return 
	 *           5$12$... [过去24小时的用户数,默认时间粒度为30分钟]
	 */
	public String ReaderTwentyFourHourCur() {
		return ReaderTwentyFourHourCur(30);
	}

	/**
	 * @param step
	 *             15
	 * @return  
	 *             5$12$... [过去24小时的用户数,时间粒度为#step分钟]
	 */
	public String ReaderTwentyFourHourCur(int step) {
		TimeOp timeOp = new TimeOp();
		String curTime = timeOp.getPreDate("m", -1);
		return ReaderTwentyFourHour(curTime, step);
	}

	public static void main(String[] args) {
		DisTimeReader dtr = new DisTimeReader();
		int count = dtr.ReaderAnyMin("2013-04-10 14:34", 1);
		System.out.println(count);
	}
}
