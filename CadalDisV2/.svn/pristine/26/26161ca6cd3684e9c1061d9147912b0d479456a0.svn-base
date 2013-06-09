package cn.cadal.dis.java.timereader;

import java.util.Date;

import cn.cadal.dis.java.sql.SqlTimes;
import cn.cadal.dis.java.utils.TimeOp;

public class DisTimeReader {
	
	/**
	 * @param time
	 *            2013-04-09 13:00
	 * @param before
	 *            12
	 * @return 2013-04-09 13:00 前12分钟的用户总数
	 */
	public int ReaderAnyMin(String time, int before) {
		int count = 0;
		TimeOp timeOp = new TimeOp();
		SqlTimes sqlTimes = new SqlTimes();
		Date date = timeOp.getFormatDate(time);
		String start = timeOp.getPreDate(date, "m", -before-2);
		String end = timeOp.getPreDate(date, "m", -2);
		count = sqlTimes.QueryViewTimes(start, end, "VT_UserTimes");
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
		SqlTimes sqlTimes = new SqlTimes();
		return sqlTimes.QueryViewTimes(time, "VT_UserTimes");
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
				res = count + "$" + res;
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
		String curTime = timeOp.getPreDate("m", -2);
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
		String curTime = timeOp.getPreDate("m", -2);
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
		String curTime = timeOp.getPreDate("m", -2);
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
		String curTime = timeOp.getPreDate("m", -2);
		return ReaderTwentyFourHour(curTime, step);
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
			result = sqlTimes.QueryViewTimes(timeOp.getFormatDate(start), timeOp.getFormatDate(end), "VT_UserTimes") + "$" + result;
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
			res = sqlTimes.QueryViewTimes(timeOp.getFormatDate(start), timeOp.getFormatDate(end), "VT_UserTimes") + "$" + res;
			end = start;
			start = timeOp.getFormatDate(timeOp.getPreDate(end, "m", step*(-1)));
		}
		return res;
	}
	
	
	public static void main(String[] args) {
//	DisTimeReader dtr = new DisTimeReader();
//	System.out.println(dtr.StartEndStep("2013-04-21 10:00", "2013-04-22 10:00", 60));
//		System.out.println(dtr.StartCurStep(2, 15));
//
//		String time = "2013-04-11 17:00";
//		
//		System.out.println(time + "前10分钟看过书的用户数：" + dtr.ReaderAnyMin(time, 10));
//	
//		System.out.println(time + "前30分钟看过书的用户数：" + dtr.ReaderThirtyMin(time));
//		System.out.println(time + "前15分钟看过书的用户数：" + dtr.ReaderFifteenMin(time));
//		System.out.println(time + "前1分钟看过书的用户数：" + dtr.ReaderOneMin(time));
//		
//		System.out.println("过去的30分钟看过书的用户数：" + dtr.ReaderThirtyMinCur());
//		System.out.println("过去的15分钟看过书的用户数：" + dtr.ReaderFifteenMinCur());
//		System.out.println("过去的1分钟看过书的用户数：" + dtr.ReaderOneMinCur());
//		
//		System.out.println(time + "前24小时，看过书的用户数（30分钟统计一次）：" + dtr.ReaderTwentyFourHour("2013-04-11 17:00"));
//		System.out.println(time + "前24小时，看过书的用户数（60分钟统计一次）：" + dtr.ReaderTwentyFourHour("2013-04-11 17:00", 60));
//		System.out.println("前24小时，看过书的用户数（30分钟统计一次）：" + dtr.ReaderTwentyFourHourCur());
//		System.out.println("前24小时，看过书的用户数（60分钟统计一次）：" + dtr.ReaderTwentyFourHourCur(60));
	}
}
