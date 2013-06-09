package cn.cadal.user;

import java.io.File;
import java.util.Map;
import javax.servlet.ServletException;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class MyCalligraphy extends ActionSupport implements SessionAware {

	private Map servletSession;

	private Cuser user;
	
	public void setSession(Map session) {
		this.servletSession = session;
	}
	
	public String execute() throws Exception {
		String userIdStr = (String) servletSession.get("userId");
		int userId = 0;

		if ( userIdStr == null) {
			return INPUT;
		}	
		userId = Integer.parseInt(userIdStr);		
		if (userId == 0 ) {
			return INPUT;
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			user = (Cuser) session.load(Cuser.class, new Integer(userId));
			Hibernate.initialize (user);
						
			session.getTransaction().commit();
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
			session.getTransaction().rollback();
			throw new ServletException(exc);
		}

		return SUCCESS;
	}

	public void setServletSession (Map servletSession) {
		this.servletSession = servletSession;
	}

}
