package cn.cadal.user;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

import org.hibernate.Session;

import cn.cadal.entity.Cbook;
import cn.cadal.entity.Cuser;
import cn.cadal.np.RecommendedBook;
import cn.cadal.np.RecommendedBookComparator;
import cn.cadal.np.rmiserver.NPRecommender;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class PersonalRecommend extends ActionSupport implements SessionAware{
	private Map servletSession;
	private String favorList = "";
	private List recommendList = new ArrayList();
	private List tmpNos = new ArrayList();
	private List bookNos = new ArrayList();
	private int requestBookNo = 10;
	
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
			for(int i = 0;i < favor.size() && i < 2; i++){
				Cbook book = (Cbook)iter.next();
				favorList += book.getBookNo()+"|";
			}
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}		
		if(favorList.length()>2){
			favorList = favorList.substring(0,favorList.length()-1);
			
		}
		
		System.out.println("favorList is " + favorList);
		
		try
		{
		    NPRecommender npRecommender =(NPRecommender)Naming.lookup("//10.15.62.226:5555/NPRecommender");
//		    String bookURL = "/Reader.action?bookNo=01010257";
		    List resRecommendedBook = new ArrayList();
		    String[] ls = favorList.split("\\|");
		    for(int j=0; j<ls.length;j++)
		    {
		    	tmpNos = (ArrayList)npRecommender.getNPRecommendResults(ls[j]);
		    	resRecommendedBook.addAll(tmpNos);
		    }
		    RecommendedBookComparator comp = new RecommendedBookComparator();  
		    Collections.sort(resRecommendedBook,comp);


		    for(int i=0; i<resRecommendedBook.size()&& i<requestBookNo;i++)
	    	{
	    		RecommendedBook rBook = (RecommendedBook)(resRecommendedBook.get(i));
			
				bookNos.add(rBook.getBookID());
				System.out.println(rBook.getBookID());
				System.out.println(rBook.getWeight());
	    	}
		}
		catch(Exception  e)
		{
			System.out.println("Failed to get results : " + e.getMessage());
		}
		
		session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			if (bookNos.size() > 0){
				recommendList = session.createQuery("from Cbook cbook where cbook.bookNo "
						+"in (:namesList)").setParameterList("namesList", bookNos).list();
				session.getTransaction().commit();
			}
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}
		
		return SUCCESS;
	}
	
	
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		this.servletSession = arg0;
	}



	public String getFavorList() {
		return favorList;
	}


	public void setFavorList(String favorList) {
		this.favorList = favorList;
	}


	public List getRecommendList() {
		return recommendList;
	}

	public void setRecommendList(List recommendList) {
		this.recommendList = recommendList;
	}
}
