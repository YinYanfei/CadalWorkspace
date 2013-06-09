package cn.cadal.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Session;

import cn.cadal.entity.Cbook;
import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class MyLibraryBook extends ActionSupport implements SessionAware {

	private Map servletSession;
	private List<Cbook> myBookList = new ArrayList<Cbook>();
	
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
	
			Set favor = user.getMyFavorite();
			Iterator iter = favor.iterator();
			while(iter.hasNext()){
				Cbook book = (Cbook)iter.next();
				myBookList.add(book);
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

	public List getMyBookList() {
		return myBookList;
	}


	
}
