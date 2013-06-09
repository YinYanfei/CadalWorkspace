package cn.cadal.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.interceptor.ServletRequestAware;
import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class LogPageNum extends ActionSupport implements SessionAware, ServletRequestAware{
	
	private HttpServletRequest servletRequest;
	private Map servletSession;

	public void setSession(Map arg0) {
		servletSession = arg0;
	}
	public void setServletRequest(HttpServletRequest arg0) {
		servletRequest = arg0;
	}
	
	private String bookNo;
	
	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNO) {
		this.bookNo = bookNO;
	}

	private String pageNo;
	
	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	public String execute () throws Exception {
		setlogInfo();
		return SUCCESS;
	}
	public void setlogInfo()
    {
	  try
	  {
    	HttpServletRequest request = ServletActionContext.getRequest();
    	String ipAd=request.getRemoteAddr();
    	String ipHost=request.getRemoteHost();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		String userIdStr = (String ) servletSession.get("userId");
		Integer userId = 0;
		if (userIdStr != null) {
			 userId = Integer.parseInt(userIdStr);
		}
		String emAddr=null;
		if (userId>0) {
			Cuser user = (Cuser)session.load (Cuser.class, userId);
			emAddr=user.getEmailAddress();
		}
		String method=request.getMethod();
		String requestURI=request.getRequestURI();
		String queryString=request.getQueryString();
		String protocal = request.getProtocol();
		String referer=request.getHeader("referer");
		String ua=request.getHeader("user-agent");
		CadalAccessLog CAL=new CadalAccessLog();
		CAL.setIpAddr(ipAd);
		CAL.setIpHost(ipHost);
		CAL.setUserName(emAddr);
		CAL.setMethod(method);
		CAL.setProtocal(protocal);
		CAL.setQueryString(queryString);
		CAL.setRequestURI(requestURI);
		CAL.setReferer(referer);
		CAL.setUa(ua);
		CAL.Logging();
		tx.commit();
	  }
	  catch(Exception e)
	  {
		  LOG.warn(StackTraceUtil.getStackTrace(e));
	  }
    }
}
