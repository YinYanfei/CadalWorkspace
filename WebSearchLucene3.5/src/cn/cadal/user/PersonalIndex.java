package cn.cadal.user; // Generated package name

import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.cadal.entity.Cbinder;
import cn.cadal.entity.Crule;
import cn.cadal.entity.Cuser;
import cn.cadal.entity.MyCalligraphies;
import cn.cadal.entity.MyImage;
import cn.cadal.entity.UserBinder;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class PersonalIndex extends ActionSupport implements SessionAware {

	Cuser user;

	Map servletSession;
	
	private String rootCalligraphyPath = "/characterimage/";
	
	static {
		try{
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
		}catch(Exception exc){
			LOG.warn("error loading class");
		}
	}
	
	public void setSession(Map session) {
		this.servletSession = session;
	}

	List binderArray = new ArrayList();
	
	List<MyCalligraphyPic> calligraphyPicArray =  new ArrayList<MyCalligraphyPic>(8);

	List<MyImage> imageArray=new ArrayList<MyImage>();
	
	public List getBinderArray() {
		return this.binderArray;
	}

	public final String execute() throws Exception {
		if (true)
			return "redirect";
		CheckCookie checkCookie = new CheckCookie(servletSession);
		checkCookie.checkCookieLogin();
		
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			int userId = 0;
			String userIdStr = (String) servletSession.get("userId");
			if (userIdStr != null) {
				userId = Integer.parseInt(userIdStr);
			}
			
			if ( userId == 0 || userIdStr == null ){
				return INPUT;
			}
			user = (Cuser) session.load(Cuser.class, new Integer(userId));
			
			List<MyCalligraphies> calligraphyArray = user.getMyCalligraphies();
			Hibernate.initialize(calligraphyArray);
			
			for (int iidx=0; iidx < calligraphyArray.size(); iidx++){
				MyCalligraphies calligraphy = calligraphyArray.get(iidx);
				int ruleId = calligraphy.getId();
				String ruleName = calligraphy.getRuleName();
				String rulePath = rootCalligraphyPath + user.getEmailAddress()+"/"+calligraphy.getFileName();
				calligraphyPicArray.add(new MyCalligraphyPic(ruleId, ruleName, rulePath));
			}
			
			Set<UserBinder> binders = user.getMyBinders();
			Iterator binderIter = binders.iterator();

			while (binderIter.hasNext()) {
				BinderNameUrlPair nuPair = new BinderNameUrlPair();
				UserBinder userBinder = (UserBinder) binderIter.next();

				int displayRecNum = userBinder.getTopNum();

				Cbinder binder = userBinder.getBindBinder();
				nuPair.setBinderName(binder.getBinderName());

				String urlStr = null;
				String repository = binder.getRepository();

				if (repository.trim().toLowerCase().equals("modern")) {
					urlStr = "PModernAdvancedSearch.action?queryparam.recordNumPerPage="
							+ displayRecNum + "&queryparam.orderby=title";
				} else if (repository.trim().toLowerCase().equals("journal")) {
					urlStr = "PJournalAdvancedSearch.action?queryparam.recordNumPerPage="
							+ displayRecNum + "&queryparam.orderby=title";
				} else if (repository.trim().toLowerCase().equals(
						"dissertation")) {
					urlStr = "PDissertationAdvancedSearch.action?queryparam.recordNumPerPage="
							+ displayRecNum + "&queryparam.orderby=title";
				} else if (repository.trim().toLowerCase().equals("ancient")) {
					urlStr = "PAncientAdvancedSearch.action?queryparam.recordNumPerPage="
							+ displayRecNum + "&queryparam.orderby=title";
				} else if (repository.trim().toLowerCase().equals("minguo")) {
					urlStr = "PMinguoAdvancedSearch.action?queryparam.recordNumPerPage="
							+ displayRecNum + "&queryparam.orderby=title";
				} else if (repository.trim().toLowerCase().equals("english")) {
					urlStr = "PEnglishAdvancedSearch.action?queryparam.recordNumPerPage="
						+ displayRecNum + "&queryparam.orderby=title";
				} else {
					throw new ServletException("error repository type");
				}

				Iterator ruleIter = binder.getRules().iterator();
				int idx = 0;
				while (ruleIter.hasNext()) {
					Crule rule = (Crule) ruleIter.next();

					String operator = rule.getOperator().trim();
					String queryword = rule.getQueryWord().trim();
					String schemafield = rule.getSchemaField().trim();

					String ruleStr = "&queryparam.rules[" + idx + "]";
					operator = ruleStr + ".operator=" + operator;
					queryword = ruleStr + ".queryWord="
							+ URLEncoder.encode(queryword, "utf-8");
					schemafield = ruleStr + ".schemaField=" + schemafield;
					urlStr += operator;
					urlStr += queryword;
					urlStr += schemafield;
					idx++;
				}
				LOG.debug("url string : " + urlStr);
				nuPair.setBinderUrl(urlStr);
				binderArray.add(nuPair);
			}
			
			tx.commit();

			// image
			Connection conn = DriverManager.getConnection("jdbc:jtds:sqlserver://10.15.62.216:1433;DatabaseName=image;user=sa;password=cadal205");
			PreparedStatement imageSelect = conn.prepareStatement("select ruleId, path from myPicRule where userId=? order by ruleId asc");
			imageSelect.setInt(1,userId);
			ResultSet rs = imageSelect.executeQuery();
			while (rs.next()) {
				int ruleId = rs.getInt(1);
				String rulePath = rs.getString(2);
				rulePath = "/IM/uploadMyRulePics/"+rulePath;
				
				LOG.info ("ruleid:"+ruleId+" rulePath:"+rulePath);
				
				MyImage mImage = new MyImage (ruleId, rulePath);
				imageArray.add(mImage);
			}
			imageSelect.close();
			conn.close();
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
			tx.rollback();
			throw new ServletException(exc);
		}
		
	
		
		return SUCCESS;
	}

	

	public List<MyCalligraphyPic> getCalligraphyPicArray() {
		return calligraphyPicArray;
	}

	public List<MyImage> getImageArray() {
		return imageArray;
	}

	public void setImageArray(List<MyImage> imageArray) {
		this.imageArray = imageArray;
	}

}
