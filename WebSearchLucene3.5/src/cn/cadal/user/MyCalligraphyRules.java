package cn.cadal.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import cn.cadal.entity.Cuser;
import cn.cadal.entity.MyCalligraphies;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class MyCalligraphyRules extends ActionSupport implements SessionAware {

	private Map servletSession;

	private Cuser user;

	private List<MyCalligraphyPic> ruleList = new ArrayList<MyCalligraphyPic>(8);
	
	private String rootCalligraphyPath = "/characterimage/";
	
	@Override
	public String execute() throws Exception {
		String userIdStr = (String) servletSession.get("userId");
		int userId = 0;
		if (userIdStr == null) {
			return INPUT;
		}
		userId = Integer.parseInt(userIdStr);
		if (userId == 0) {
			return INPUT;
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			user = (Cuser) session.load(Cuser.class, new Integer(userId));
			List<MyCalligraphies> calligraphies = user.getMyCalligraphies();
			Hibernate.initialize(calligraphies);
			for (int iidx=0; iidx < calligraphies.size(); iidx++){
				MyCalligraphies calligraphy = calligraphies.get(iidx);
				int ruleId = calligraphy.getId();
				String ruleName = calligraphy.getRuleName();
				String rulePath = rootCalligraphyPath + user.getEmailAddress()+"/"+calligraphy.getFileName();
				ruleList.add(new MyCalligraphyPic(ruleId, ruleName, rulePath));
			}
			session.getTransaction().commit();
		}catch(HibernateException hexc){
			LOG.warn(StackTraceUtil.getStackTrace(hexc));
			session.getTransaction().rollback();
			throw new ServletException(hexc);
			
		}catch(Exception exc){
			LOG.warn(StackTraceUtil.getStackTrace(exc));
			throw new ServletException(exc);
		}finally{
			
		}

		return SUCCESS;
	}
	
	public void setSession(Map session) {
		this.servletSession = session;
	}

	public List<MyCalligraphyPic> getRuleList() {
		return ruleList;
	}

	public void setRuleList(List<MyCalligraphyPic> ruleList) {
		this.ruleList = ruleList;
	}


	

}
