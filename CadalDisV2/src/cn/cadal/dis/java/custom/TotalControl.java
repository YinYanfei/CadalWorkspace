package cn.cadal.dis.java.custom;

import java.util.Date;

import cn.cadal.dis.java.sql.SqlTimes;
import cn.cadal.dis.java.utils.TimeOp;

public class TotalControl {
	
	private TimeOp timeOp = null;
	private SqlTimes sqlTime = null;
	
	private int STEP = 30;
	
	public TotalControl() {
		this.timeOp = new TimeOp();
		this.sqlTime = new SqlTimes();
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
	 * @param args
	 */
	public static void main(String[] args) {
//		TotalControl tc = new TotalControl();
//		
//		System.out.println(tc.StartCurStep(1, 10));
//		
//		SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
//		Date date1;
//		Date date2;
//		try {
//			date1 = sdf.parse( "2013-04-09 09:00" );
//			date2 = sdf.parse( "2013-04-09 09:20" );
//			
//			System.out.println(tc.PvOfTimeSlide(date1, date2));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
