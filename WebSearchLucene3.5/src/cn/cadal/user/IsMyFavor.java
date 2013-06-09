package cn.cadal.user;

import java.util.Map;

import org.hibernate.Session;

import cn.cadal.entity.Cbook;
import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class IsMyFavor extends ActionSupport implements SessionAware {

	private Map servletSession;
	private String bookNo;
	
	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	public String execute()throws Exception{
		
		String userIdStr = (String)servletSession.get("userId");
		if( null == userIdStr ){
			return LOGIN;
		}
		int userId = Integer.parseInt(userIdStr);
		if( 0 == userId){
			return LOGIN;
		}
		if(LOG.isDebugEnabled()){
			LOG.debug("userId:"+userId+" bookNo:"+bookNo);
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Cuser user = (Cuser) session.load(Cuser.class, new Integer(userId));
			Cbook book = (Cbook) session.load(Cbook.class, bookNo);
			boolean isMyFavor = user.getMyFavorite().contains(book);
			session.getTransaction().commit ();
			if(isMyFavor)
				return SUCCESS;
			else
				return INPUT;
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}
		return SUCCESS;
	}
	
	
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		this.servletSession = arg0;
	}

}
