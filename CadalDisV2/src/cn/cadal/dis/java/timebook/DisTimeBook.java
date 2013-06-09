package cn.cadal.dis.java.timebook;

import java.util.Date;

import cn.cadal.dis.java.sql.SqlTimes;
import cn.cadal.dis.java.utils.TimeOp;

public class DisTimeBook {
	
	/**
	 * @param time 
	 *               2013-04-09 13:00
	 * @return       5 [2013-04-09 12:59分的图书阅读数]
	 */
	public int BookOneMin(String time){
		SqlTimes sqlTimes = new SqlTimes();
		return sqlTimes.QueryViewTimes(time, "VT_BookTimes");
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
		SqlTimes sqlTimes = new SqlTimes();
		Date date = timeOp.getFormatDate(time);
		String start = timeOp.getPreDate(date, "m", -before-2);
		String end = timeOp.getPreDate(date, "m", -2);
		count = sqlTimes.QueryViewTimes(start, end, "VT_BookTimes");
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
				res = count + "$" + res;
		}
		return res;
	}
	
	/**
	 * @return  5 [过去30分钟的图书量]
	 */
	public int BookThirtyMinCur(){
		int count = 0;
		TimeOp timeOp = new TimeOp();
		String curTime = timeOp.getPreDate("m", -2);
		count = BookThirtyMin(curTime);
		return count;
	}

	
	/**
	 * @return  5 [过去15分钟的图书量]
	 */
	public int BookFifteenMinCur() {
		int count = 0;
		TimeOp timeOp = new TimeOp();
		String curTime = timeOp.getPreDate("m", -2);
		count = BookFifteenMin(curTime);
		return count;
	}
	
	/**
	 * @return   5 [过去1分钟的图书量]
	 */
	public int BookOneMinCur(){
		int count = 0;
		TimeOp timeOp = new TimeOp();
		String curTime = timeOp.getPreDate("m", -2);
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
		String curTime = timeOp.getPreDate("m", -2);
		return BookTwentyFourHour(curTime, step);
	}

	/**
	 * From startTime to current, calculate count of pv by step period
	 * @param over	
	 * 			 	10 (to get start time: cur - over)
	 * @param step	 
	 * 				20
	 * @return		12$10$...
	 */
	public String StartCurStep(int over, int step) {
		String result = "";
		TimeOp timeOp = new TimeOp();
		
		Date currentTime = timeOp.getFormatDate(timeOp.getCurrentDate());
		String startTime = timeOp.getPreDate(currentTime, "H", over*(-1));
		Date first = timeOp.getFormatDate(startTime);
		SqlTimes sqlTimes = new SqlTimes();
		Date end = currentTime;
		Date start = timeOp.getFormatDate(timeOp.getPreDate(currentTime, "m", step*(-1)));
		while(end.after(first)){
			result = sqlTimes.QueryViewTimes(timeOp.getFormatDate(start), timeOp.getFormatDate(end), "VT_BookTimes") + "$" + result;
			end = start;
			start = timeOp.getFormatDate(timeOp.getPreDate(end, "m", step*(-1)));
		}
		return result;
	}
	
	/**
	 * 
	 * @param startTime
	 * 					2013-04-11 17:00
	 * @param EndTime
	 *                  2013-04-12 17:00
	 * @param step      every step minutes
	 * @return          12$34$11$55
	 */
	public String StartEndStep(String startTime, String EndTime, int step){
		String res = "";
		TimeOp timeOp = new TimeOp();
		SqlTimes sqlTimes = new SqlTimes();
		Date first = timeOp.getFormatDate(startTime);
		
		Date end = timeOp.getFormatDate(EndTime);
		Date start = timeOp.getFormatDate(timeOp.getPreDate(end, "m", step*(-1)));
		
		while(end.after(first)){
			res = sqlTimes.QueryViewTimes(timeOp.getFormatDate(start), timeOp.getFormatDate(end), "VT_BookTimes") + "$" + res;
			end = start;
			start = timeOp.getFormatDate(timeOp.getPreDate(end, "m", step*(-1)));
		}
		return res;
	}
	
	public static void main(String[] args) {

//		DisTimeBook dtb = new DisTimeBook();
//		System.out.println(dtb.StartCurStep(6, 35));
//
//		String time = "2013-04-15 20:00";
//		System.out.println("2013-04-21 10:00到2013-04-22 10:00 每60分钟被访问过的书页数： " + dtb.StartEndStep("2013-04-21 10:00", "2013-04-22 10:00", 60));
//		System.out.println(time + "前10分钟被访问过的书页数：" + dtb.BookAnyMin(time, 10));
//	
//		System.out.println(time + "前30分钟被访问过的书页数：" + dtb.BookThirtyMin(time));
//		System.out.println(time + "前15分钟被访问过的书页数：" + dtb.BookFifteenMin(time));
//		System.out.println(time + "前1分钟被访问过的书页数：" + dtb.BookOneMin(time));
//		
//		System.out.println("过去的30分钟被访问过的书页数：" + dtb.BookThirtyMinCur());
//		System.out.println("过去的15分钟被访问过的书页数：" + dtb.BookFifteenMinCur());
//		System.out.println("过去的1分钟被访问过的书页数：" + dtb.BookOneMinCur());
//		
//		System.out.println(time + "前24小时，被访问过的书页数：（30分钟统计一次）：" + dtb.BookTwentyFourHour("2013-04-11 17:00"));
//		System.out.println(time + "前24小时，被访问过的书页数：（60分钟统计一次）：" + dtb.BookTwentyFourHour("2013-04-11 17:00", 60));
//		System.out.println("前24小时，被访问过的书页数：（30分钟统计一次）：" + dtb.BookTwentyFourHourCur());
//		System.out.println("前24小时，被访问过的书页数：（60分钟统计一次）：" + dtb.BookTwentyFourHourCur(60));
	}
}
