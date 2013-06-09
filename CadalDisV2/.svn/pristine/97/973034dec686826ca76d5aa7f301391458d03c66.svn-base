package cn.cadal.dis.java.timebook;

import java.util.Date;

import cn.cadal.dis.java.sql.SqlDetail;
import cn.cadal.dis.java.utils.TimeOp;

public class GetBookInfo {
	
	/**
	 * @param timeStart : 2013-04-09 13:00
	 * @param timeEnd   : 2013-04-09 13:30
	 * @return			: 07018720$10102339$... [2013-04-09 13:00到2013-04-09 13:30之间被阅读的读书的id信息]
	 */
	public String BookInfo(String timeStart, String timeEnd){
		SqlDetail sqlDetail = new SqlDetail();
		TimeOp timeOp = new TimeOp();
		Date date = timeOp.getFormatDate(timeEnd);
		timeEnd = timeOp.getPreDate(date, "m", -2);
		date = timeOp.getFormatDate(timeStart);
		timeStart =timeOp.getPreDate(date, "m", -2);
		return sqlDetail.queryViewDetail(timeStart, timeEnd, "VD_BookId");
	}

	/**
	 * 
	 * @param args
	 * UnitTest
	 */
	public static void main(String[] args) {
//		GetBookInfo getBookInfo = new GetBookInfo();
//		String start = "2013-04-10 12:00";
//		String end = "2013-04-10 14:32";
//		String str = getBookInfo.BookInfo(start,
//				end);
//		System.out.println("从" + start + " 到 " + end + " 被访问过的书号为:  " + str);
	}
}
