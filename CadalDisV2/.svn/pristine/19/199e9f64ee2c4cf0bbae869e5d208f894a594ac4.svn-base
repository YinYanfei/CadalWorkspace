package cn.cadal.dis.java.timereader;

import java.util.Date;

import cn.cadal.dis.java.sql.SqlDetail;
import cn.cadal.dis.java.utils.TimeOp;

public class GetReaderInfo {

	/**
	 * @param timeStart
	 *            : 2013-04-09 13:00
	 * @param timeEnd
	 *            : 2013-04-09 13:30
	 * @return : hongxin$zju$... [2013-04-09 13:00到2013-04-09 13:30之间用户名]
	 */
	public String ReaderInfo(String timeStart, String timeEnd) {
		SqlDetail sqlDetail = new SqlDetail();
		TimeOp timeOp = new TimeOp();
		Date date = timeOp.getFormatDate(timeEnd);
		timeEnd = timeOp.getPreDate(date, "m", -2);
		date = timeOp.getFormatDate(timeStart);
		timeStart =timeOp.getPreDate(date, "m", -2);
		return sqlDetail.queryViewDetail(timeStart, timeEnd, "VD_UserName");
	}

	/**
	 * 
	 * @param args
	 * 
	 * for UnitTest
	 */
	public static void main(String[] args) {
//		GetReaderInfo getReaderInfo = new GetReaderInfo();
//		String start = "2013-04-10 08:30";
//		String end = "2013-04-10 14:32";
//		String str = getReaderInfo.ReaderInfo(start,end);
//		System.out.println("从" + start + " 到 " + end + " 看过书的用户名为: " + str);
	}
}
