package cn.cadal.dis.java.timebook;

import java.util.Date;

import cn.cadal.dis.java.cassandra.CasTimeBook;
import cn.cadal.dis.java.utils.TimeOp;

public class DisTimeBook {
	
	/**
	 * @param time 
	 *               2013-04-09 13:00
	 * @return       5 [2013-04-09 12:59分的图书阅读数]
	 */
	public int BookOneMin(String time){
		CasTimeBook ctb = new CasTimeBook();
		return ctb.QueryOneMinute(time).size();
	}
	
	/**
	 * @param time 
	 *             2013-04-09 13:00
	 * @return    5 [2013-04-09 12:30到2013-04-09 13:00的图书阅读数]
	 */
	public int BookThirtyMin(String time){
		return BookAnyMin(time, 30);
	}
	
	/**
	 * @param time  
	 *              2013-04-09 13:00
	 * @return      5 [2013-04-09 12:45到2013-04-09 13:00的图书阅读数]
	 */
	public int BookFifteenMin(String time) {
		return BookAnyMin(time, 15);
	}
	
	/**
	 * 
	 * @param time
	 *            2013-04-09 13:00
	 * @param before
	 *            12
	 * @return 2013-04-09 13:00 前12分钟的被阅览的图书总数
	 */
	public int BookAnyMin(String time, int before) {
		int count = 0;
		TimeOp timeOp = new TimeOp();
		for (int i = 0; i < before; ++i) {
			Date date = timeOp.getFormatDate(time);
			String min = timeOp.getPreDate(date, "m", -i);
			count += BookOneMin(min);
		}
		return count;
	}
	
	/**
	 * @param time 
	 *              2013-04-09 13:00
	 * @return       5$12$... [2013-04-08 13:00到2013-04-09 13:00的图书阅读数,默认时间粒度为30分钟]
	 */
	public String BookTwentyFourHour(String time) {
		return BookTwentyFourHour(time, 30);
	}
	
	/**
	 * @param time  
	 *             2013-04-09 13:00
	 * @param step 
	 *             15
	 * @return     5$12$... [2013-04-08 13:00到2013-04-09 13:00的图书阅读数,时间粒度为#step分钟]
	 */
	public String BookTwentyFourHour(String time, int step) {
		String res = "";
		boolean isFirst = true;
		TimeOp timeOp = new TimeOp();
		int round = 24 * 60 / step;
		for (int i = 0; i < round; ++i) {
			int count = BookAnyMin(time, step);
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
	 * @return  5 [过去30分钟的图书量]
	 */
	public int BookThirtyMinCur(){
		int count = 0;
		TimeOp timeOp = new TimeOp();
		String curTime = timeOp.getPreDate("m", -1);
		count = BookThirtyMin(curTime);
		return count;
	}

	
	/**
	 * @return  5 [过去15分钟的图书量]
	 */
	public int BookFifteenMinCur() {
		int count = 0;
		TimeOp timeOp = new TimeOp();
		String curTime = timeOp.getPreDate("m", -1);
		count = BookFifteenMin(curTime);
		return count;
	}
	
	/**
	 * @return   5 [过去1分钟的图书量]
	 */
	public int BookOneMinCur(){
		int count = 0;
		TimeOp timeOp = new TimeOp();
		String curTime = timeOp.getPreDate("m", -1);
		count = BookOneMin(curTime);
		return count;
	}

	/**
	 * @return  5$12$... [过去24小时的图书阅读数,默认时间粒度为30分钟]
	 */
	public String BookTwentyFourHourCur() {
		return BookTwentyFourHourCur(30);
	}

	/**
	 * @param step 
	 *              15
	 * @return      5$12$... [过去24小时的图书阅读数,时间粒度为#step分钟]
	 */
	public String BookTwentyFourHourCur(int step) {
		TimeOp timeOp = new TimeOp();
		String curTime = timeOp.getPreDate("m", -1);
		return BookTwentyFourHour(curTime, step);
	}

	public static void main(String[] args) {
		DisTimeBook dtb = new DisTimeBook();
		int count = dtb.BookAnyMin("2013-04-10 14:34", 1);
		System.out.println(count);
	}
}
