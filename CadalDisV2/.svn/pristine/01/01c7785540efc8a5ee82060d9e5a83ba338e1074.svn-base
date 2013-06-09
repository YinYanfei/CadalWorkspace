package cn.cadal.dis.java.timepv;

import java.util.Date;

import cn.cadal.dis.java.sql.SqlTimes;
import cn.cadal.dis.java.utils.TimeOp;

public class DisTimePV {
	
	private TimeOp timeOp = null;
	private SqlTimes sqlTime = null;
	
	private int STEP = 30;
		
	public DisTimePV() {
		this.timeOp = new TimeOp();
		this.sqlTime = new SqlTimes();
	}
	
	/**
	 * @param time : 2013-04-09 13:00
	 * @return     : 5 [2013-04-09 12:30到2013-04-09 13:00的系统访问量(书页阅读量)]
	 */
	public int PvThirtyMin(String time){
		Date date = this.timeOp.getFormatDate(time);
		
		try{
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -32));
			Date dateEnd   = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -2));
			
			return this.PvOfTimeSlide(dateStart, dateEnd);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * @param time : 2013-04-09 13:00
	 * @return     : 5 [2013-04-09 12:45到2013-04-09 13:00的系统访问量(书页阅读量)]
	 */
	public int PvFifteenMin(String time) {
		Date date = this.timeOp.getFormatDate(time);
		
		try{
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -16));
			Date dateEnd   = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -1));
			
			return this.PvOfTimeSlide(dateStart, dateEnd);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * @param time : 2013-04-09 13:00
	 * @return     : 5 [2013-04-09 12:59的系统访问量(书页阅读量)]
	 */
	public int PvOneMin(String time){
		Date date = this.timeOp.getFormatDate(time);
		
		try{
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -2));
			Date dateEnd   = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -1));
			
			return this.PvOfTimeSlide(dateStart, dateEnd);
		}catch(Exception e) {
			e.printStackTrace();
		}
				
		return 0;
	}

	/**
	 * @param time : 2013-04-09 13:00
	 * @return     : 5$12$90$... [2013-04-08 13:00到2013-04-09 13:00的系统访问量(书页阅读量)，默认的时间粒度为30分钟]
	 */
	public String PvTwentyFourHour(String time) {
		Date dateFinal = this.timeOp.getFormatDate(time);
		Date dateFirst = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateFinal, "d", -1));
		
		String result = "";
		
		try{
			Date dateEnd   = dateFinal;
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", this.STEP * (-1)));
			while(dateEnd.after(dateFirst)){
				result = this.PvOfTimeSlide(dateStart, dateEnd) + "$"  + result;
				
				dateEnd   = dateStart;
				dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", this.STEP * (-1)));
			}
			
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * @param time : 2013-04-09 13:00
	 * @param step : 15
	 * @return     : 5$12$90$... [2013-04-08 13:00到2013-04-09 13:00的系统访问量(书页阅读量)，时间粒度为#step分钟]
	 */	
	public String PvTwentyFourHour(String time, int step) {
		Date dateFinal = this.timeOp.getFormatDate(time);
		Date dateFirst = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateFinal, "d", -1));
		
		String result = "";
		
		try{
			Date dateEnd   = dateFinal;
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", step * (-1)));
			while(dateEnd.after(dateFirst)){
				result = this.PvOfTimeSlide(dateStart, dateEnd) + "$"  + result;
				
				dateEnd   = dateStart;
				dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", step * (-1)));
			}
			
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * @return     : 5 [过去30分钟的系统访问量(书页阅读量)]
	 */
	public int PvThirtyMinCur(){
		Date date = new Date();
		
		try{
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -31));
			Date dateEnd   = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -1));
			
			return this.PvOfTimeSlide(dateStart, dateEnd);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * @return     : 5 [过去15分钟的系统访问量(书页阅读量)]
	 */
	public int PvFifteenMinCur() {
		Date date = new Date();
		
		try{
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -16));
			Date dateEnd   = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -1));
			
			return this.PvOfTimeSlide(dateStart, dateEnd);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * @return     : 5 [过去1分钟的系统访问量(书页阅读量)]
	 */
	public int PvOneMinCur(){
		Date date = new Date();
		
		try{
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -2));
			Date dateEnd   = this.timeOp.getFormatDate(this.timeOp.getPreDate(date, "m", -1));
			
			return this.PvOfTimeSlide(dateStart, dateEnd);
		}catch(Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * @return     : 5$12$90$... [过去24小时的系统访问量(书页阅读量)，默认时间粒度为30分钟]
	 */
	public String PvTwentyFourHourCur() {
		Date dateFinal = this.timeOp.getFormatDate(this.timeOp.getCurrentDate());
		Date dateFirst = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateFinal, "d", -1));
		
		String result = "";
		
		try{
			Date dateEnd   = dateFinal;
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", this.STEP * (-1)));
			while(dateEnd.after(dateFirst)){
				result = this.PvOfTimeSlide(dateStart, dateEnd) + "$"  + result;
                
				dateEnd   = dateStart;
				dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", this.STEP * (-1)));
			}
			
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * @param step ： 15
	 * @return     : 5$12$90$... [过去24小时的系统访问量(书页阅读量)，时间粒度为#step分钟]
	 */
	public String PvTwentyFourHourCur(int step) {
		Date dateFinal = this.timeOp.getFormatDate(this.timeOp.getCurrentDate());
		Date dateFirst = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateFinal, "d", -1));
		
		String result = "";
		
		try{
			Date dateEnd   = dateFinal;
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", step * (-1)));
			while(dateEnd.after(dateFirst)){
				result = this.PvOfTimeSlide(dateStart, dateEnd) + "$"  + result;
				
				dateEnd   = dateStart;
				dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", step * (-1)));
			}
			
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "";
	}
	
	/**
	 * Return all times(format 23$10$...) split every STEP period
	 * @param startTime	: 2013-04-18 10:00
	 * @param EndTime	: 2013-04-18 14:00
	 * @return			: 12$10$...
	 */
	public String StartEndStep(String startTime, String EndTime) {
		Date dateFinal = this.timeOp.getFormatDate(EndTime);
		Date dateFirst = this.timeOp.getFormatDate(startTime);
		
		String result = "";
		
		try{
			Date dateEnd   = dateFinal;
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", this.STEP * (-1)));
			while(dateEnd.after(dateFirst)){
				result = this.PvOfTimeSlide(dateStart, dateEnd) + "$"  + result;
				
				dateEnd   = dateStart;
				dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", this.STEP * (-1)));
			}
			
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.STEP = 30;
		}
		
		return "";
	}
	
	/**
	 * Return all times(format 23$10$...) split every step period
	 * @param startTime	: 2013-04-18 10:00
	 * @param EndTime	: 2013-04-18 14:00
	 * @param step		: 20 (integer)
	 * @return			: 12$10$...
	 */
	public String StartEndStep(String startTime, String EndTime, int step) {
		this.STEP = step;
		
		return this.StartEndStep(startTime, EndTime);
	}

	/**
	 * Return all times(format 23$10$...) split every step period
	 * @param EndTime	: 2013-04-18 14:00
	 * @param over		: 6 (to get start time: 2013-04-18 08:00)
	 * @param step		: 20
	 * @return			: 12$10$...
	 */
	public String StartEndStep(String EndTime, int over, int step) {
		Date dateFinal = this.timeOp.getFormatDate(EndTime);
		Date dateFirst = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateFinal, "H", over * (-1)));
		
		return this.StartEndStep(this.timeOp.getFormatDate(dateFirst), this.timeOp.getFormatDate(dateFinal), step);
	}
	
	/**
	 * From startTime to current, calculate count of pv by STEP period
	 * @param startTime	: 2013-04-18 10:00
	 * @return			: 12$10$...
	 */
	public String StartCurStep(String startTime) {
		Date dateFinal = this.timeOp.getFormatDate(this.timeOp.getCurrentDate());
		Date dateFirst = this.timeOp.getFormatDate(startTime);
		
		String result = "";
		
		try{
			Date dateEnd   = dateFinal;
			Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", this.STEP * (-1)));
			while(dateEnd.after(dateFirst)){
				result = this.PvOfTimeSlide(dateStart, dateEnd) + "$"  + result;
				
				dateEnd   = dateStart;
				dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateEnd, "m", this.STEP * (-1)));
			}
			
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.STEP = 30;
		}
		
		return "";
	}

	/**
	 * From startTime to current, calculate count of pv by step period
	 * @param startTime	: 2013-04-18 10:00
	 * @param step		: 20
	 * @return			: 12$10$...
	 */
	public String StartCurStep(String startTime, int step) {
		this.STEP = step;
		
		return this.StartCurStep(startTime);
	}

	/**
	 * From startTime to current, calculate count of pv by step period
	 * @param over	: 10 (to get start time: cur - over)
	 * @param step	: 20
	 * @return		: 12$10$...
	 */
	public String StartCurStep(int over, int step) {
		Date dateCur = this.timeOp.getFormatDate(this.timeOp.getCurrentDate());
		Date dateStart = this.timeOp.getFormatDate(this.timeOp.getPreDate(dateCur, "H", over * (-1)));
		
		return this.StartCurStep(this.timeOp.getFormatDate(dateStart), step);
	}
	
	/**
	 * @param start
	 * @param end
	 * @return Times count
	 */
	public int PvOfTimeSlide(Date start, Date end) {
		try{
			return this.sqlTime.QueryViewTimes(this.timeOp.getFormatDate(start), this.timeOp.getFormatDate(end), "VT_ViewTimes");
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * Main Function
	 */
	public static void main(String[] args) {
		DisTimePV disTimePv = new DisTimePV();

		// StartCurStep
		System.out.println(disTimePv.StartCurStep(2, 10));
		
		// PvTwentyFourHour
	//	System.out.println(disTimePv.PvTwentyFourHourCur(60));
		
	//	 PvOneMinCur
//		System.out.println(disTimePv.PvOneMinCur());
		
		// PvFifteenMinCur
//		System.out.println(disTimePv.PvFifteenMinCur());
		
		// PvThirtyMinCur
//		System.out.println(disTimePv.PvThirtyMinCur());
		
		// PvOneMin
//		System.out.println(disTimePv.PvOneMin("2013-04-09 09:10"));
		
		// PvFifteenMin
//		System.out.println(disTimePv.PvFifteenMin("2013-04-10 10:05"));
		
		// PvThirtyMin
//		System.out.println(disTimePv.PvThirtyMin("2013-04-10 10:05"));
		
		// PvOfTimeSlide
//		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
//		Date date1;
//		Date date2;
//		try {
//			date1 = sdf.parse( "2013-04-09 09:00" );
//			date2 = sdf.parse( "2013-04-09 09:10" );
//			
//			System.out.println(disTimePv.PvOfTimeSlide(date1, date2));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
}
