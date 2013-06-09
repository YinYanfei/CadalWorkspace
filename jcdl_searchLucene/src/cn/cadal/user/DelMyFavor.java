package cn.cadal.user;

import java.util.Map;

import javax.servlet.ServletException;

import org.hibernate.Session;

import cn.cadal.entity.Cbook;
import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class DelMyFavor extends ActionSupport implements SessionAware {

	private String bookNo;

	private Map servletSession;

	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	public void setSession(Map session) {
		this.servletSession = session;
	}

	public String execute() throws Exception {

		String userIdStr = (String) servletSession.get("userId");
		if (null == userIdStr)
			return LOGIN;
		int userId = Integer.parseInt(userIdStr);
		if (0 == userId)
			return LOGIN;

		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			if (LOG.isDebugEnabled()) {
				LOG.debug("userId:" + userId + " bookNo:" + bookNo);
			}
			// load user object
			Cuser user = (Cuser) session.load(Cuser.class, new Integer(userId));
			// load book object
			Cbook favBook = (Cbook) session.load(Cbook.class, bookNo);
			if (LOG.isDebugEnabled()) {
				LOG.debug("user :" + user);
				LOG.debug("book :" + favBook);
			}
			// save
			user.getMyFavorite().remove(favBook);
			session.flush();
			session.getTransaction().commit();
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
			session.getTransaction().rollback();
			throw new ServletException(exc);
		} 
		return SUCCESS;
	}

}
