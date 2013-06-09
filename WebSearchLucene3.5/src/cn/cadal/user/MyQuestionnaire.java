package cn.cadal.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.cadal.entity.Cbook;
import cn.cadal.entity.Cuser;
import cn.cadal.entity.MyImage;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.interceptor.ServletRequestAware;
import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class MyQuestionnaire extends ActionSupport implements SessionAware, ServletRequestAware{
	
	static {
		try{
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
		}catch(Exception exc){
			LOG.warn("error loading class");
		}
	}
	
	private HttpServletRequest servletRequest;
	private Map servletSession;

	public void setSession(Map arg0) {
		servletSession = arg0;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		servletRequest = arg0;
	}
	
	private String idea;
	
	public String getIdea() {
		return idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

	private String type;
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type=type;
	}
	public String execute () throws Exception {
		
		String userIdStr = (String) servletSession.get("userId");
		int userId = 0;
		if( userIdStr != null ) {
			userId = Integer.parseInt(userIdStr);
		}
		if(idea!=null && !idea.equalsIgnoreCase(""))
		{
			try{
				Connection conn = DriverManager.getConnection("jdbc:jtds:sqlserver://10.15.62.216:1433;DatabaseName=cadal_metadata_full;user=sa;password=cadal205");
				String sql = "insert into CMyQuestionnaire (userId, idea, submitDate) values (" + userId +", '" + idea + "', GETDATE())";
				Statement stment = conn.createStatement();
				stment.executeUpdate(sql);
				stment.close();
				conn.close();
				HttpServletResponse response = ServletActionContext.getResponse();
				Cookie cookie = new Cookie("questionnaire","yes");
				cookie.setMaxAge(3600*24*365);
				response.addCookie(cookie);
			} catch (Exception exc) {
				LOG.warn(StackTraceUtil.getStackTrace(exc));
			}
		}
		if(type.equalsIgnoreCase("login"))
		{
			return "login";
		}
		else
		{
			return "normal";
		}
	}
}
