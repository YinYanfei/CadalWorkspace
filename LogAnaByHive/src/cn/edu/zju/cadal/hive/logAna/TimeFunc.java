package cn.edu.zju.cadal.hive.logAna;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.hadoop.hive.ql.exec.UDF;

public class TimeFunc extends UDF {
	/**
	 * To translate date(String type) to long type
	 * 
	 * @param dateStr
	 * @return 
	 * 		- 1234567890 : date type as long type
	 * 		- -1 : for error in translate
	 */
	public long evaluate(final String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat("d/MMM/yyyy:hh:mm:ss",Locale.ENGLISH); 
		try{
			Date date = sdf.parse(dateStr);
			return date.getTime();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	/**
	 * To judge whether dateStrOne earlier than dateStrTwo
	 * 
	 * @param dateStrOne
	 * @param dateStrTwo
	 * @return
	 * 		- true : dateStrOne <= dateStrTwo
	 * 		- false: dateStrOne > dateStrTwo
	 */
	public boolean evalue(final String dateStrOne, final String dateStrTwo) {
		final long dateStrOneLong = this.evaluate(dateStrOne);
		final long dateStrTwoLong = this.evaluate(dateStrTwo);
		
		if(dateStrOneLong <= dateStrTwoLong) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * To judge whether dateStrCmp is between dateStrStart and dateStrEnd
	 * 
	 * @param dateStrStart
	 * @param dateStrEnd
	 * @param dateStrCmp
	 * @return
	 * 		- true : dateStrStart < dateStrCmp <= dateStrEnd
	 * 		- false: dateStrCmp <= dateStrStart OR dateStrCmp > dateStrEnd
	 */
	public boolean evaluate(
			final String dateStrStart, 
			final String dateStrEnd, 
			final String dateStrCmp) {
		final long dateStrStartLong = this.evaluate(dateStrStart);
		final long dateStrEndLong = this.evaluate(dateStrEnd);
		final long dateStrCmpLong = this.evaluate(dateStrCmp);
		
		if(dateStrStartLong < dateStrCmpLong && dateStrCmpLong <= dateStrEndLong) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Test Main function
	 * 
	 * @param args
	 */
//	public static void main(final String[] args) {
//		TimeFunc timeFunc = new TimeFunc();
//		String date_1 = "24/Feb/2012:04:20:14";
//		String date_2 = "24/Feb/2012:04:20:16";
//		String date_c = "24/Feb/2012:04:20:16";
//		
//		// first 
//		System.out.println(timeFunc.evaluate(date_1));
//		System.out.println(timeFunc.evaluate(date_2));
//		
//		// second
//		System.out.println(timeFunc.evalue(date_1, date_2));
//		
//		// third
//		System.out.println(timeFunc.evaluate(date_1, date_2, date_c));
//
//	}
}
