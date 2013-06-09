package cn.cadal.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import org.hibernate.Session;

import cn.cadal.entity.Cbook;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class ListHotRecommend extends ActionSupport implements SessionAware{

	private List hotRecommendList = new ArrayList();

	private Map servletSession;
	
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		servletSession = arg0;
	}

	
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
			List bookNos = session.createQuery("select rBooks.bookUrl from UserRecommendBooks rBooks where "
					+"rBooks.ipAddress = :ipAddr").setString("ipAddr","MostPopular").list();
			
			Iterator iter = session.createQuery("from Cbook cbook where cbook.bookNo "
					+"in (:namesList)").setParameterList("namesList", bookNos).iterate();
			for(int i = 0;iter.hasNext() && i < 5; i++){
				Cbook book = (Cbook)iter.next();
				hotRecommendList.add(book);
			}
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}		
		return SUCCESS;
		
	}

	public List getHotRecommendList() {
		return hotRecommendList;
	}

	public void setHotRecommendList(List hotRecommendList) {
		this.hotRecommendList = hotRecommendList;
	}

}
