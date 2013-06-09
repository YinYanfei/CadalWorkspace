package cn.cadal.user;

import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;

import cn.cadal.np.RecommendedBook;
import cn.cadal.np.rmiserver.NPRecommender;

import com.opensymphony.webwork.interceptor.ServletRequestAware;
import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class ListMyTopRecommend extends ActionSupport implements SessionAware , ServletRequestAware{
	
	private static final Log LOG = LogFactory.getLog(ListMyTopRecommend.class);
	private Map servletSession;
	private HttpServletRequest servletRequest;
	private List topMyRecommendList = new ArrayList();
	private List tmpNos = new ArrayList();
	private List bookNos = new ArrayList();
	private String bookNo;
	private int requestBookNo = 20;
	public String execute() throws Exception{
		

//		String bookURL = "/Reader.action?bookNo="+bookNo;
		
		try
		{
		    NPRecommender npRecommender =(NPRecommender)Naming.lookup("//10.15.62.226:5555/NPRecommender");
//		    String bookURL = "/Reader.action?bookNo=01010257";
		    tmpNos = (ArrayList)npRecommender.getNPRecommendResults(bookNo);
			for(int i=0; i<tmpNos.size()&& i<requestBookNo;i++)
			{
				RecommendedBook rBook = (RecommendedBook)(tmpNos.get(i));
				if(!rBook.getBookID().equals(bookNo)){
					bookNos.add(rBook.getBookID());
					System.out.println(rBook.getBookID());
					System.out.println(rBook.getWeight());
				}
				
			}
		}
		catch(Exception  e)
		{
			System.out.println("Failed to get results : " + e.getMessage());
		}
		if(bookNos.size()!=0){
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			for(int i = 0;i < bookNos.size();i++){
				String bookNo = (String) bookNos.get(i);
			List tempList = session.createQuery
					("from Cbook cbook where cbook.bookNo = :bookNo")
					.setString("bookNo",bookNo).list();
			topMyRecommendList.addAll(tempList);
			}
//			topMyRecommendList = session.createQuery("from Cbook cbook where cbook.bookNo "
//					+"in (:namesList)").setParameterList("namesList", bookNos).list();
			session.getTransaction().commit();
        
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}		
		}
		
		
		return SUCCESS;
	}
	
	public void setSession(Map arg0) {
		// TODO Auto-generated method stub
		this.servletSession = arg0;
	}

	public List getTopMyRecommendList() {
		return topMyRecommendList;
	}

	public void setTopMyRecommendList(List topMyRecommendList) {
		this.topMyRecommendList = topMyRecommendList;
	}

	public void setServletRequest(HttpServletRequest arg0) {
		// TODO Auto-generated method stub
		servletRequest = arg0;
	}

	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	public int getRequestBookNo() {
		return requestBookNo;
	}

	public void setRequestBookNo(int requestBookNo) {
		this.requestBookNo = requestBookNo;
	}

	
}
