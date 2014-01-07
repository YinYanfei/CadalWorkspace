package cn.edu.zju.cadal.test.hive.fun;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.hadoop.hive.ql.exec.UDF;

public class TimeCompare extends UDF {
	/**
	 * Transfer Date to Long
	 * 25/Sep/2013:00:10:29
	 */
	public long evaluate(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("d/MMM/yyyy:hh:mm:ss",Locale.ENGLISH);
		try {
			Date date = sdf.parse(dateString);
			
			return date.getTime();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
}
