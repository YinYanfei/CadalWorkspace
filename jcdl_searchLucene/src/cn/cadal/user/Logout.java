package cn.cadal.user;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;


public class Logout extends ActionSupport implements SessionAware {

  
    private Map servletSession;
    private Cookie cookie;


    public void setSession (Map session) {
			this.servletSession = session;
    }
    
    public String execute () throws Exception {			
    
    		Session session = null; 
	
			try{
			    int userId = 0 ;
			    String userIdStr = (String) servletSession.get ("userId");
			    if ( userIdStr != null ){
			    	userId = Integer.parseInt (userIdStr);
			    }			    
			    if (0 == userId) {
			    	return SUCCESS;
			    }	    
//			  	load user object
				session = HibernateUtil.getSessionFactory (). getCurrentSession ();
				session.beginTransaction ();
				
				Cuser user = (Cuser) session.load(Cuser.class, new Integer(userId));
			    
			    if (user != null) {
			    	LOG.warn("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=");
			    	LOG.warn("Logout.java invoked here");
			    	servletSession.put ("userId", ""+0);
					servletSession.put ("userMail", "");					
					
					String stringValue = "0";
					user.setCookieValue(stringValue);
					session.flush ();
					
					HttpServletResponse response = ServletActionContext.getResponse();

					cookie = new Cookie("useCookie","");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					cookie = new Cookie("useName","");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				
			    }				
			    session.getTransaction ().commit ();
			}catch (Exception exc){
				LOG.warn (StackTraceUtil.getStackTrace (exc));
				session.getTransaction ().rollback ();
				throw new ServletException (exc);
			}
		
			return SUCCESS;
			
		}

}
