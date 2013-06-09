package cn.cadal.dis.java.timebook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.cadal.dis.java.cassandra.CasTimeBook;
import cn.cadal.dis.java.utils.TimeOp;

public class GetBookInfo {
	
	/**
	 * @param timeStart : 2013-04-09 13:00
	 * @param timeEnd   : 2013-04-09 13:30
	 * @return			: 07018720$10102339$... [2013-04-09 13:00到2013-04-09 13:30之间被阅读的读书的id信息]
	 */
	public String BookInfo(String timeStart, String timeEnd){

		CasTimeBook casTimeBook = new CasTimeBook();
		String book = "";
		TimeOp timeOp = new TimeOp();
		boolean isFirst = true;
		List<String> list = new ArrayList<String>();
		try {
			while (!timeEnd.equals(timeStart)) {
				Date date = timeOp.getFormatDate(timeEnd);
				timeEnd = timeOp.getPreDate(date, "m", -1);
				System.out.println(timeEnd);
				for (String str : casTimeBook.QueryOneMinute(timeEnd)) {
					if (!list.contains(str))
						list.add(str);
				}
			}
			for (String str : list) {
				if (isFirst) {
					book += str;
					isFirst = false;
				} else {
					book = book + "$" + str;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return book;
	}

	public static void main(String[] args) {
		GetBookInfo getBookInfo = new GetBookInfo();
		String str = getBookInfo.BookInfo("2013-04-10 14:30",
				"2013-04-10 14:32");
		System.out.println(str);
	}
}