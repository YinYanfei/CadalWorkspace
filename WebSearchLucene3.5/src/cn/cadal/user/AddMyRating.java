package cn.cadal.user;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import cn.cadal.entity.Cbook;
import cn.cadal.entity.Cuser;
import cn.cadal.entity.MyRanking;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class AddMyRating extends ActionSupport implements SessionAware {

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
			LOG.debug("[in AddMyRating.java]");
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
			
			Integer mark = Integer.parseInt(remark);

			
			Criteria criteria = session.createCriteria(MyRanking.class).setCacheable(true);
			criteria.add(Restrictions.eq("user", user));
			criteria.add(Restrictions.eq("book", book));
			
			MyRanking myRanking = (MyRanking) criteria.uniqueResult ();
			
			if (myRanking == null){			
				myRanking = new MyRanking (user, book ,mark);
			}else{
				myRanking.setRate(mark);
			}
			
			session.save(myRanking);
			
			if (LOG.isDebugEnabled()){
				LOG.debug("[in AddMyRating.java]:"+remark);
				LOG.debug("[in myRanking.user]:"+myRanking.getId());
				LOG.debug("[in myRanking.bookno]:"+myRanking.getBook().getBookNo());
				LOG.debug("[in myRanking.mark]:"+myRanking.getRate());				
			}
			
			user.getMyRanking().add (myRanking);
			session.flush();
			
			session.getTransaction().commit();
		}catch(Exception exc){
			LOG.warn(StackTraceUtil.getStackTrace(exc));
		}
		// TODO Auto-generated method stub
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
	
}
