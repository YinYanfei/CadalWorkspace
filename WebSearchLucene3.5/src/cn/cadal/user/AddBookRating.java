package cn.cadal.user;

import java.sql.Date;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Locale;



import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import cn.cadal.entity.Cbook;
import cn.cadal.entity.Cuser;
import cn.cadal.entity.BookRating;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class AddBookRating extends ActionSupport implements SessionAware {

	private Map servletSession;
	
	private String bookNo;
	private String remark;
	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String execute() throws Exception {
		
		if (LOG.isDebugEnabled()){
			LOG.debug("[in AddBookRating.java]");
		}
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try{
			session.beginTransaction();
			//load user object
			if (LOG.isDebugEnabled()){
				LOG.warn("servlet session map :"+servletSession);
			}
			String userIdStr = (String) servletSession.get("userId");
			
			if(userIdStr == null) {
				return SUCCESS;
			}
			int userId = Integer.parseInt (userIdStr);
			
			
			Cuser user = (Cuser) session.load(Cuser.class, new Integer(userId));
			Cbook book = (Cbook) session.load(Cbook.class, bookNo);
			
			if (LOG.isDebugEnabled()){
				LOG.debug("[bookNo]:"+bookNo);
				LOG.debug("[remark]:"+remark);
			}

			Criteria criteria = session.createCriteria(BookRating.class).setCacheable(true);
			criteria.add(Restrictions.eq("user", user));
			criteria.add(Restrictions.eq("book", book));
			java.util.Date now = new java.util.Date();
			Date sqlnow = new Date(now.getYear(), now.getMonth(), now.getDate());
			BookRating bookRating = (BookRating) criteria.uniqueResult ();
			
			
			if (bookRating == null){
				if(!remark.equalsIgnoreCase(":-1:-1:-1:-1:-1:-1:-1:"))
				{
					bookRating = new BookRating (user, book ,remark);
					bookRating.setSubmitDate(sqlnow);
					session.save(bookRating);
				}		
			}
			else
			{
				bookRating.setSubmitDate(sqlnow);
				if(remark.equalsIgnoreCase(":-1:-1:-1:-1:-1:-1:-1:"))
					remark = bookRating.getRate();
				else
				{
					bookRating.setRate(remark);
					session.save(bookRating);
				}
			}
			
			session.flush();
			session.getTransaction().commit();
		}catch(Exception exc){
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}
		// TODO Auto-generated method stub
		LOG.info("hello:"+remark);
		remarks = remark.split(":");
		LOG.info("hello 2:"+remarks.toString());
		
		return SUCCESS;
	}

	public void setSession(Map servletSession) {
		this.servletSession = servletSession;
	}

	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	
	String [] remarks = null;
	public String getMark0() {
		if(remarks[1].equalsIgnoreCase("-1"))
			return "未评价";
		if(remarks[1].equalsIgnoreCase("5"))
			return "基础教育";
		if(remarks[1].equalsIgnoreCase("4"))
			return "高等教育";
		if(remarks[1].equalsIgnoreCase("3"))
			return "学术研究";
		if(remarks[1].equalsIgnoreCase("2"))
			return "休闲娱乐";
		if(remarks[1].equalsIgnoreCase("1"))
			return "随便看看";
		return remarks[1];
	}
	public String getMark1() {
		if(remarks[2].equalsIgnoreCase("-1"))
			return "未评价";
		return remarks[2];
	}
	public String getMark2() {
		if(remarks[3].equalsIgnoreCase("-1"))
			return "未评价";
		return remarks[3];
	}
	public String getMark3() {
		if(remarks[4].equalsIgnoreCase("-1"))
			return "未评价";
		return remarks[4];
	}
	public String getMark4() {
		if(remarks[5].equalsIgnoreCase("-1"))
			return "未评价";
		return remarks[5];
	}
	public String getMark5() {
		if(remarks[6].equalsIgnoreCase("-1"))
			return "未评价";
		return remarks[6];
	}
	public String getMark6() {
		if(remarks[7].equalsIgnoreCase("-1"))
			return "未评价";
		return remarks[7];
	}
	
}
