package cn.cadal.user; // Generated package name

import java.util.Map;

import javax.servlet.ServletException;

import org.hibernate.Session;

import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;


public class MyLibraryInfo extends ActionSupport implements SessionAware {
    
    private Map servletSession;
    private Cuser user;

    public void setSession (Map session){
    	this.servletSession = session;
    }

    public Cuser getUser (){
    	return this.user;
    }

    public String execute () throws Exception {
    	String userIdStr = (String) servletSession.get ("userId");
    	int userId = 0;    	
		if ( userIdStr == null ) {
			LOG.warn ("-----------------------------------------------------------------------------");
			return SUCCESS;
		} else {
			LOG.warn ("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			userId = Integer.parseInt (userIdStr);
		}
		Session session = HibernateUtil.getSessionFactory ().getCurrentSession ();

		try {
		    session.beginTransaction ();
		    user = (Cuser) session.load (Cuser.class, new Integer (userId));		    
		    
		    
		    LOG.warn ("=============================================================================");
			
		    LOG.warn (user.getEmailAddress());
		    LOG.warn (user.getPassword());
		    LOG.warn (user.getHomeTown());
		    LOG.warn (user.getSchool());
		    LOG.warn (user.getBirthDay());
		    LOG.warn (user.getGender());
		    LOG.warn (user.getOccupation());
	        
		    session.getTransaction ().commit ();
		} catch (Exception exc){
		    LOG.warn (StackTraceUtil.getStackTrace (exc));
		    session.getTransaction ().rollback ();
		    throw new ServletException (exc);
		}
		return SUCCESS;
    }
    
}
