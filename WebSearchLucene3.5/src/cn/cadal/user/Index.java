package cn.cadal.user; // Generated package name

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;


public class Index extends ActionSupport implements SessionAware {
  
  private Map servletSession;

	
  public void setSession (Map session) {
		this.servletSession = session;
  }

  public String execute () throws Exception {
	  CheckCookie checkCookie = new CheckCookie(servletSession);
	  checkCookie.checkCookieLogin();
	  return INPUT;
  }

    
  
 
    
}
