package cn.cadal.dis.java.timereader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.cadal.dis.java.cassandra.CasTimeReader;
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
		CasTimeReader casTimeReader = new CasTimeReader();
		String user = "";
		TimeOp timeOp = new TimeOp();
		boolean isFirst = true;
		List<String> list = new ArrayList<String>();
		try {
			while (!timeEnd.equals(timeStart)) {
				Date date = timeOp.getFormatDate(timeEnd);
				timeEnd = timeOp.getPreDate(date, "m", -1);
				System.out.println(timeEnd);
				for (String str : casTimeReader.QueryOneMinute(timeEnd)) {
					if (!list.contains(str))
						list.add(str);
				}
			}
			for (String str : list) {
				if (isFirst) {
					user += str;
					isFirst = false;
				} else {
					user = user + "$" + str;
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return user;
	}

	public static void main(String[] args) {
		GetReaderInfo getReaderInfo = new GetReaderInfo();
		String str = getReaderInfo.ReaderInfo("2013-04-10 14:30",
				"2013-04-10 14:32");
		System.out.println(str);
	}
}
