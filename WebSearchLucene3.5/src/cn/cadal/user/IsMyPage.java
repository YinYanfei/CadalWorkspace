package cn.cadal.user;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import cn.cadal.entity.Cbook;
import cn.cadal.entity.Cuser;
import cn.cadal.entity.MyPage;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class IsMyPage extends ActionSupport implements SessionAware {

	private Map servletSession;
	private String bookNo;
	private String pageNo;
	
	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	
	public String getPageNo() {
		return pageNo;
	}

	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
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
			LOG.debug("userId:"+userId+" bookNo:"+bookNo+" pageNo:"+pageNo);
		}
			
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Cuser user = (Cuser) session.load(Cuser.class, new Integer(userId));
			Cbook book = (Cbook) session.load(Cbook.class, bookNo);
			if (LOG.isDebugEnabled()){
				LOG.debug("[bookNo]:"+bookNo);
				LOG.debug("[pageNo]:"+pageNo);
			}
			Integer page = Integer.parseInt(pageNo);
			
			Criteria criteria = session.createCriteria(MyPage.class).setCacheable(true);
			criteria.add(Restrictions.eq("user", user));
			criteria.add(Restrictions.eq("book", book));
			criteria.add(Restrictions.eq("pageNo", page));			
			MyPage myPage = (MyPage) criteria.uniqueResult();
			session.getTransaction().commit();
			if (myPage == null){
				return INPUT;
			}
			else
				return SUCCESS;			
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
