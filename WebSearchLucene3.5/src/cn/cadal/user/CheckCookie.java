package cn.cadal.user;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.ServletActionContext;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class CheckCookie {

	private Map servletSession;
	
	public CheckCookie(Map servletSession)
	{
		this.servletSession = servletSession;
	}
	
	public void checkCookieLogin()
	{
		int userId = 0 ;
		String userIdStr = (String) servletSession.get ("userId");
		if ( userIdStr != null ){
			userId = Integer.parseInt (userIdStr);
		    if(userId>0){
		    	return;
	    	}
		}	
		
		Cookie[] cookies = null;
		String useCookie = null;
		String serverCookie = null;
		String emailAddress = null;  
		
		HttpServletRequest request = ServletActionContext.getRequest();
		cookies = request.getCookies(); 
		
		if(cookies==null){
			servletSession.put("userId","0");
			return;
		}else if(cookies.length<2){
			servletSession.put("userId","0");
			return;
		}
		
		for(int i=0; i<cookies.length; i++)
		{
			if(cookies[i].getName().equalsIgnoreCase("useCookie"))
				useCookie = cookies[i].getValue();	
			else if(cookies[i].getName().equalsIgnoreCase("useName"))
				emailAddress = cookies[i].getValue();	
				
		}
		
		if(useCookie == null || emailAddress == null)
			return;
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			 session.beginTransaction();
		    Criteria criteria = session.createCriteria(Cuser.class);
		    criteria.add (Restrictions.eq ("emailAddress", emailAddress.trim()));
		    Cuser user = (Cuser) criteria.uniqueResult();
		    if (user != null){
		    	serverCookie = user.getCookieValue();
		    	if(serverCookie!=null && useCookie.equalsIgnoreCase(serverCookie)){	
		    		servletSession.put("userId", String.valueOf(user.getUserId()));
		    		servletSession.put("userMail", user.getEmailAddress());
		    	}
		    	
		    }
		    session.getTransaction().commit();
		}catch (Exception exc){
		    session.getTransaction().rollback();
		}
	}
}
