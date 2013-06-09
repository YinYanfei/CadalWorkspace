package cn.cadal.storm.analyze.bolt.util;

import java.util.HashMap;
import java.util.Map;

public class TimeConvert {

	Map<String, String> timeMap = null;
	
	/**
	 * Construct function
	 */
	public TimeConvert() {
		this.timeMap = new HashMap<String, String>();
		
		this.timeMap.put("Jan", "01");
		this.timeMap.put("Feb", "02");
		this.timeMap.put("Mar", "03");
		this.timeMap.put("Apr", "04");
		this.timeMap.put("May", "05");
		this.timeMap.put("Jun", "06");
		this.timeMap.put("Jul", "07");
		this.timeMap.put("Aug", "08");
		this.timeMap.put("Sep", "09");
		this.timeMap.put("Oct", "10");
		this.timeMap.put("Nov", "11");
		this.timeMap.put("Dec", "12");
	}
	
	/**
	 * Parameter: time string in log file  -- "2012-12-12 23:09:21" 
	 * 
	 *    return: normalized time string   -- "2012-12-12 23:09:21" 
	 */
	public String NormalTime(String oldType) {
		int placeOfFirstColon = oldType.indexOf(":");
		
		// Split date and time
		String timeStr = oldType.substring(placeOfFirstColon + 1);
		String dateStr = oldType.substring(0, placeOfFirstColon);
		
		// deal with dateStr
		String[] dateStrList = dateStr.split("/");
		String newDateStyle = dateStrList[2] + "-" + this.timeMap.get(dateStrList[1]) + "-" + dateStrList[0];
		
//		System.out.println(dateStr + "  " + newDateStyle + " " + timeStr);	
		
		return newDateStyle + " " + timeStr;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		TimeConvert tc = new TimeConvert();
//		
//		// Test
//		String time1 = "15/Jan/2012:00:00:30";
//		String time2 = "15/Feb/2012:00:02:20";
//		String time3 = "14/Mar/2012:01:27:06";
//		String time4 = "04/Apr/2012:01:27:04";
//		String time5 = "04/May/2012:01:27:20";
//		String time6 = "07/Jun/2012:00:00:16";
//		String time7 = "07/Jul/2012:01:27:04";
//		String time8 = "09/Aug/2012:01:27:05";
//		String time9 = "09/Sep/2012:01:27:09";
//		String time10 = "17/Oct/2012:01:27:02";
//		String time11 = "07/Nov/2012:01:27:02";
//		String time12 = "10/Dec/2012:01:27:29";
//		
//		tc.NormalTime(time1);
//		tc.NormalTime(time2);
//		tc.NormalTime(time3);
//		tc.NormalTime(time4);
//		tc.NormalTime(time5);
//		tc.NormalTime(time6);
//		tc.NormalTime(time7);
//		tc.NormalTime(time8);
//		tc.NormalTime(time9);
//		tc.NormalTime(time10);
//		tc.NormalTime(time11);
//		tc.NormalTime(time12);
		
	}

}
