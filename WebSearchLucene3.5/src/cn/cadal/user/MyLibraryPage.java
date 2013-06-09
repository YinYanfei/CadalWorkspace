package cn.cadal.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

public class MyLibraryPage extends ActionSupport implements SessionAware {

	private Map servletSession;
	private List myPageList = new ArrayList();
	
	public String execute() throws Exception{

		String userIdStr = (String) servletSession.get("userId");
		if( null == userIdStr ) {
			return LOGIN;
		}
		int userId = Integer.parseInt(userIdStr);
		if( 0 == userId ){
			return LOGIN;
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			Cuser user = (Cuser) session.load(Cuser.class, new Integer(userId));			
			Criteria criteria = session.createCriteria(MyPage.class).setCacheable(true);
			criteria.add(Restrictions.eq("user", user));			
			List page = criteria.list();
			if(page.isEmpty()){
				LOG.debug("null value in user.getMyPage()");				
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("[before iterator]");			
			}
			Iterator iter = page.iterator();
			
			while(iter.hasNext()){				
				MyPage bookpage = (MyPage)iter.next();
				myPageList.add(bookpage);
				if (LOG.isDebugEnabled()) {
					LOG.debug("mypage-bookno:" + bookpage.getBook().getBookNo());
					LOG.debug("mypage-page:" + bookpage.getPageNo());
				}
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("[after iterator]");			
			}
			session.getTransaction().commit();
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}
		return SUCCESS;
	}
	
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		this.servletSession = arg0;
	}

	public List getMyPageList() {
		return myPageList;
	}


	
}
