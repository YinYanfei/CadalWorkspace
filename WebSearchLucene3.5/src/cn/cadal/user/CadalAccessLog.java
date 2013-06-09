package cn.cadal.user;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CadalAccessLog {
	
	private String directory="D:/logs";
	
	private String prefix = "access_log.";
	
	private boolean rotatable = true;//do not change this property
	
	private boolean combined=true;
	
	private boolean resolveHosts = false;
	
	private String suffix = ".txt";
	
	private String fileDateFormat = "yyyy-MM-dd";
	
	private PrintWriter writer = null;
	
    private SimpleDateFormat dateFormatter = null;
	
	private SimpleDateFormat dayFormatter = null;
	
	private SimpleDateFormat monthFormatter = null;
	
	private SimpleDateFormat yearFormatter = null;
	
	private SimpleDateFormat timeFormatter = null;

	private String timeZone = null;
	
	private Date currentDate = null;
	

	
	private String dateStamp = "";
	
	private String space = " ";
	
	
	protected static final String months[] = { "Jan", "Feb", "Mar", "Apr",
		"May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	
	private long rotationLastChecked = 0L;
	
	
	
	private String ipAddr=null;
	
	private String ipHost=null;
	
	private String userName=null;
	
	private String method=null;
	
	private String requestURI=null;
	
	private String queryString=null;
	
	private String protocal=null;
	
	private String referer=null;
	
	private String ua=null;
	
	public void setMethod(String method) {
		this.method = method;
	}
	
	public void setProtocal(String protocal) {
		this.protocal = protocal;
	}
	
	public void setReferer(String referer) {
		this.referer = referer;
	}
	
	public void setUa(String ua) {
		this.ua = ua;
	}
	
	
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public CadalAccessLog()
	{
		init();
	}
	
	public void Logging()throws Exception
	{
		open();
		Date date = getDate();
		StringBuffer result = new StringBuffer();
		if (isResolveHosts())
			result.append(ipHost);	
		else
			result.append(ipAddr);
		result.append(" - ");

		if (userName!=null)
		{
			result.append(userName);
		    result.append(space);
		}
		else
			result.append("- ");
		result.append("[");
		result.append(dayFormatter.format(date)); // Day
		result.append('/');
		result.append(lookup(monthFormatter.format(date))); // Month
		result.append('/');
		result.append(yearFormatter.format(date)); // Year
		result.append(':');
		result.append(timeFormatter.format(date)); // Time
		result.append(space);
		result.append(timeZone); // Time Zone
		result.append("] \"");
		result.append(method);
        result.append(space);
        result.append(requestURI);
        if (queryString!= null) {
            result.append('?');
            result.append(queryString);
        }
        result.append(space);
        result.append(protocal);
        result.append("\" ");
        result.append("304");
        result.append(space);
        result.append("-");
        if (combined) {
                result.append(space);
                result.append("\"");
                if(referer != null)
                    result.append(referer);
                else
                    result.append("-");
                result.append("\"");

                result.append(space);
                result.append("\"");
                if(ua != null)
                    result.append(ua);
                else
                    result.append("-");
                result.append("\"");
        }
		log(result.toString());
	    close();
	}
	private String calculateTimeZoneOffset(long offset) {
		StringBuffer tz = new StringBuffer();
		if ((offset < 0)) {
			tz.append("-");
			offset = -offset;
		} else {
			tz.append("+");
		}

		long hourOffset = offset / (1000 * 60 * 60);
		long minuteOffset = (offset / (1000 * 60)) % 60;

		if (hourOffset < 10)
			tz.append("0");
		tz.append(hourOffset);

		if (minuteOffset < 10)
			tz.append("0");
		tz.append(minuteOffset);

		return tz.toString();
	}
	private Date getDate() {
		if (currentDate == null) {
			currentDate = new Date();
		} else {
			// Only create a new Date once per second, max.
			long systime = System.currentTimeMillis();
			if ((systime - currentDate.getTime()) > 1000) {
				currentDate = new Date(systime);
			}
		}

		return currentDate;
	}
	private String lookup(String month) {

		int index;
		try {
			index = Integer.parseInt(month) - 1;
		} catch (Throwable t) {
			index = 0; // Can not happen, in theory
		}
		return (months[index]);
	}
	private void init()
	{
		TimeZone tz = TimeZone.getDefault();
		timeZone = calculateTimeZoneOffset(tz.getRawOffset());
		dateFormatter = new SimpleDateFormat(fileDateFormat);
		dateFormatter.setTimeZone(tz);
		dayFormatter = new SimpleDateFormat("dd");
		dayFormatter.setTimeZone(tz);
		monthFormatter = new SimpleDateFormat("MM");
		monthFormatter.setTimeZone(tz);
		yearFormatter = new SimpleDateFormat("yyyy");
		yearFormatter.setTimeZone(tz);
		timeFormatter = new SimpleDateFormat("HH:mm:ss");
		timeFormatter.setTimeZone(tz);
		currentDate = new Date();
		dateStamp = dateFormatter.format(currentDate);
	}
	private synchronized void close() {

		if (writer == null)
			return;
		writer.flush();
		writer.close();
		writer = null;
		dateStamp = "";

	}
	public void log(String message) {

		if (rotatable) {
			// Only do a logfile switch check once a second, max.
			long systime = System.currentTimeMillis();
			if ((systime - rotationLastChecked) > 1000) {

				// We need a new currentDate
				currentDate = new Date(systime);
				rotationLastChecked = systime;

				// Check for a change of date
				String tsDate = dateFormatter.format(currentDate);

				// If the date has changed, switch log files
				if (!dateStamp.equals(tsDate)) {
					synchronized (this) {
						if (!dateStamp.equals(tsDate)) {
							close();
							dateStamp = tsDate;
							open();
						}
					}
				}

			}
		}

		// Log this message
		if (writer != null) {
			writer.println(message);
		}

	}
	private synchronized void open() {

		// Create the directory if necessary
		File dir = new File(directory);
		if (!dir.isAbsolute())
			dir = new File(System.getProperty("catalina.base"), directory);
		dir.mkdirs();

		// Open the current log file
		try {
			String pathname;
			// If no rotate - no need for dateStamp in fileName
			if (rotatable) {
				pathname = dir.getAbsolutePath() + File.separator + prefix
						+ dateStamp + suffix;
			} else {
				pathname = dir.getAbsolutePath() + File.separator + prefix
						+ suffix;
			}
			writer = new PrintWriter(new FileWriter(pathname, true), true);
		} catch (IOException e) {

			writer = null;
		}

	}
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}
	public void setIpHost(String ipHost) {
		this.ipHost = ipHost;
	}
	public boolean isResolveHosts() {
		return resolveHosts;
	}
	public void setResolveHosts(boolean resolveHosts) {
		this.resolveHosts = resolveHosts;
	}
	
}
