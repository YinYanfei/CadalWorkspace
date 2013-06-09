package cn.cadal.user; // Generated package name

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import cn.cadal.entity.Crule;
import cn.cadal.entity.Cuser;
import cn.cadal.entity.UserBinder;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class MyLibrary extends ActionSupport implements SessionAware {

	private Map servletSession;

	private Cuser user;

	private List<Crule> rules = new ArrayList<Crule> (); 
	
	public MyLibrary () {
		Crule rule0 = new Crule("and","title","");
		rules.add(rule0);
		
		Crule rule1 = new Crule("and","creator","");
		rules.add(rule1);
		
		Crule rule2 = new Crule("and","subject","");
		rules.add(rule2);
		
		Crule rule3 = new Crule("and","publisher","");
		rules.add(rule3);
		
	}
	
	public void setSession(Map session) {
		this.servletSession = session;
	}

	public Cuser getUser() {
		return this.user;
	}

	public String execute() throws Exception {
		String userIdStr = (String) servletSession.get("userId");
		int userId = 0;

		if ( userIdStr == null) {
			return INPUT;
		}
	
		userId = Integer.parseInt(userIdStr);
		
		if (userId == 0 ){
			return INPUT;
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			session.beginTransaction();
			user = (Cuser) session.load(Cuser.class, new Integer(userId));
			Set <UserBinder> binders = user.getMyBinders ();
			Hibernate.initialize (user);
			Hibernate.initialize (binders);
			
			session.getTransaction().commit();
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
			session.getTransaction().rollback();
			throw new ServletException(exc);
		}

		return SUCCESS;
	}

	public List<Crule> getRules() {
		return rules;
	}

	public void setRules(List<Crule> rules) {
		this.rules = rules;
	}

}
