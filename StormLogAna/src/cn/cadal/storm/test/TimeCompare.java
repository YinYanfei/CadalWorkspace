package cn.cadal.storm.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TimeCompare {

	Map<String, Integer> month = new HashMap<String, Integer>();
	
	/**
	 * Constructor
	 */
	public TimeCompare() {
		this.month.put("Jan", 1);
		this.month.put("Feb", 2);
		this.month.put("Mar", 3);
		this.month.put("Apr", 4);
		this.month.put("May", 5);
		this.month.put("Jun", 6);
		this.month.put("Jul", 7);
		this.month.put("Aug", 8);
		this.month.put("Sep", 9);
		this.month.put("Oct", 10);
		this.month.put("Nov", 11);
		this.month.put("Dec", 12);
	}
	
	/**
	 * Compare
	 */
	private boolean CompareTime(String oldTime, String newTime) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try{
			Date oldTimeDate = formatter.parse(oldTime);
			Date newTimeDate = formatter.parse(newTime);
			
			long milliSecond = newTimeDate.getTime() - oldTimeDate.getTime();
			
			if(milliSecond > 1800000) {
				return true;
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Convert String to Date style
	 * @param strDate
	 */
	private void StrToDate(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try{
			Date dateStyle = formatter.parse(strDate);
			
			System.out.println(dateStyle);
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		TimeCompare tc = new TimeCompare();
     
		String oldTime = "2012-12-12 08:00:00";
		String newTime = "2012-12-12 08:40:00";

		if(tc.CompareTime(oldTime, newTime)) {
			System.out.println("Yes");
		}else{
			System.out.println("No");
		}
		
		tc.StrToDate(newTime);
		
	}

}
