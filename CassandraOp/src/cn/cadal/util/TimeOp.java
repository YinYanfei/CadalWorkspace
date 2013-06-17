package cn.cadal.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeOp {

	private Calendar calendar=Calendar.getInstance();
	
	/**
	 * 得到当前的时间，时间格式yyyy-MM-dd HH:mm
	 * @return
	 */
	public String getCurrentDate(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(new Date());
	}
	
	/**
	 * 得到当前的时间,自定义时间格式
	 * y 年 M 月 d 日 H 时 m 分 s 秒
	 * @param dateFormat 输出显示的时间格式
	 * @return
	 */
	public String getCurrentDate(String dateFormat){
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		return sdf.format(new Date());
	}
	
	/**
	 * 日期格式化，默认日期格式yyyy-MM-dd HH:mm
	 * @param date
	 * @return
	 */
	public String getFormatDate(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(date);
	}
	
	/**
	 * 日期格式化，默认日期格式yyyy-MM-dd HH:mm
	 * @param date
	 * @return
	 */
	public Date getFormatDate(String date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			return sdf.parse(date);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * 日期格式化，自定义输出日期格式
	 * @param date
	 * @return
	 */
	public String getFormatDate(Date date,String dateFormat){
		SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}
	
	/**
	 * 返回当前日期的前一个时间日期，amount为正数 当前时间后的时间 为负数 当前时间前的时间
	 * 默认日期格式yyyy-MM-dd
	 * @param field 日历字段
	 * y 年 M 月 d 日 H 时 m 分 s 秒
	 * @param amount 数量
	 * @return 一个日期
	 */
	@SuppressWarnings("static-access")
	public String getPreDate(String field,int amount){
		calendar.setTime(new Date());
		if(field!=null&&!field.equals("")){
			if(field.equals("y")){
				calendar.add(calendar.YEAR, amount);
			}else if(field.equals("M")){
				calendar.add(calendar.MONTH, amount);
			}else if(field.equals("d")){
				calendar.add(calendar.DAY_OF_MONTH, amount);
			}else if(field.equals("H")){
				calendar.add(calendar.HOUR, amount);
			}else if(field.equals("m")) {
				calendar.add(calendar.MINUTE, amount);
			}else if(field.equals("s")) {
				calendar.add(calendar.SECOND, amount);
			}
		}else{
			return null;
		}		
		return getFormatDate(calendar.getTime());
	}
	
	/**
	 * 某一个日期的前一个日期
	 * @param d,某一个日期
	 * @param field 日历字段
	 * y 年 M 月 d 日 H 时 m 分 s 秒
	 * @param amount 数量
	 * @return 一个日期
	 */
	@SuppressWarnings("static-access")
	public String getPreDate(Date d,String field,int amount){
		calendar.setTime(d);
		if(field!=null&&!field.equals("")){
			if(field.equals("y")){
				calendar.add(calendar.YEAR, amount);
			}else if(field.equals("M")){
				calendar.add(calendar.MONTH, amount);
			}else if(field.equals("d")){
				calendar.add(calendar.DAY_OF_MONTH, amount);
			}else if(field.equals("H")){
				calendar.add(calendar.HOUR, amount);
			}else if(field.equals("m")) {
				calendar.add(calendar.MINUTE, amount);
			}else if(field.equals("s")) {
				calendar.add(calendar.SECOND, amount);
			}
		}else{
			return null;
		}		
		return getFormatDate(calendar.getTime());
	}
	
	/**
	 * 某一个时间的前一个时间
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public String getPreDate(String date) throws ParseException{
		Date d=new SimpleDateFormat().parse(date);
		String preD=getPreDate(d,"d",1);
		Date preDate=new SimpleDateFormat().parse(preD);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		
		return sdf.format(preDate);
	}

	/**
	 * Main function
	 */
	public static void main(String[] args) {
		TimeOp to = new TimeOp();
		
		SimpleDateFormat sdf = new  SimpleDateFormat( "yyyy-MM-dd HH:mm" );
		Date date;
		try {
			date = sdf.parse( "2002-10-08 15:05" );
			
			System.out.println(to.getPreDate(date, "m", 15));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
