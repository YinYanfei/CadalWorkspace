package cn.edu.zju.cadal.hive.rec.read;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.hadoop.hive.ql.exec.UDF;

public class TimeTransFunc extends UDF {

	/**
	 * To change the type of date
	 * 
	 * @param dateStr
	 * @return
	 * 		- "24/Feb/2012:04:20:19" => "2012-02-24 04:20:19.000000"
	 */
	public String evaluate(final String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("d/MMM/yyyy:hh:mm:ss",Locale.ENGLISH); 
		SimpleDateFormat sdf_res = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSSSSS",Locale.ENGLISH); 
		try{
			Date date = sdf.parse(dateStr);
			
			return sdf_res.format(date);
		}catch(Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Test
	 * 
	 * @param args
	 */
	public static void main(final String [] args) {
		String dateStr = "24/Feb/2012:04:20:19";
		TimeTransFunc ttf = new TimeTransFunc();
		
		System.out.println(ttf.evaluate(dateStr));
	}
	
}
