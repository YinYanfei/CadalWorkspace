package cn.cadal.user; // Generated package name

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.Map;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class Login extends ActionSupport implements SessionAware {
    
    private String emailAddress;
    private String password;
    private String cookieDate;
    private Map servletSession;
    private Cookie cookie;
	private String bookNo="";
		
    public void setSession (Map session) {
			this.servletSession = session;
    }
    
    public String execute () throws Exception {
			boolean equals = false;

			Session session = HibernateUtil.getSessionFactory (). getCurrentSession ();

			try{
			    session.beginTransaction ();
			    Criteria criteria = session.createCriteria (Cuser.class);
			    criteria.add (Restrictions.eq ("emailAddress", emailAddress.trim ()));
			    Cuser user = (Cuser) criteria.uniqueResult ();
			    if (user != null){
						if (user.getPassword ().equals (password)){
							equals = true;
							servletSession.put ("userId", String.valueOf (user.getUserId()));
							servletSession.put ("userMail", user.getEmailAddress ());
							if(cookieDate!=null && !cookieDate.equalsIgnoreCase("0")){
								int cookieDateIntValue = Integer.parseInt(cookieDate);
								int time = 0;
								switch(cookieDateIntValue){
								case 1:
									time = 3600*24*1;
									//time = 60*2;
									break;
								case 2:
									time = 3600*24*7;
									break;
								case 3:
									time = 3600*24*30;
									break;
								case 4:
									time = 3600*24*365;
									break;
								}
										
								HttpServletResponse response = ServletActionContext.getResponse();
								Random rand = new Random();
								Float randomCookie = new Float(rand.nextFloat());
								String stringValue = randomCookie.toString();
								stringValue = stringValue.substring(2);
								user.setCookieValue(stringValue);
								session.update(user);
															
								cookie = new Cookie("useCookie",stringValue);
								cookie.setMaxAge(time);
								response.addCookie(cookie);
								cookie = new Cookie("useName",getEmailAddress());
								cookie.setMaxAge(time);
								response.addCookie(cookie);
							}
						}else{
							addFieldError ("password", "密码不正确.");
						}
			    }else{
						addFieldError ("emailAddress", "用户名不存在.");
			    }
			    session.getTransaction ().commit ();
			}catch (Exception exc){
				LOG.warn (StackTraceUtil.getStackTrace (exc));
				session.getTransaction ().rollback ();
				throw new ServletException (exc);
			}
		
			if (equals){
				if (bookNo.length() > 0){
					return "book";
				}
			//////////////////////////////	
			//code added for Questionnaire
			/*	boolean questionnaire = false;
				try{
					Class.forName("net.sourceforge.jtds.jdbc.Driver");
				}catch(Exception exc){
					LOG.warn("error loading class");
				}
				String userIdStr = (String) servletSession.get("userId");
				int userId = 0;
				if( userIdStr != null ) {
					userId = Integer.parseInt(userIdStr);
				}
				try{
					Connection conn = DriverManager.getConnection("jdbc:jtds:sqlserver://10.15.62.216:1433;DatabaseName=cadal_metadata_full;user=sa;password=cadal205");
					String sql = "select * from CMyQuestionnaire where userId=" + userId;
					Statement stment = conn.createStatement();
					ResultSet rs = stment.executeQuery(sql);
					if(!rs.next()) questionnaire = true;
					rs.close();
					stment.close();
					conn.close();
				} catch (Exception exc) {
					LOG.warn(StackTraceUtil.getStackTrace(exc));
				}
				
				if(questionnaire) return "questionnaire";*/
            //code added for Questionnaire end
			//////////////////////////////////
				return SUCCESS;
			}
			return INPUT;
		}

    
    /**
     * Gets the value of email
     *
     * @return the value of email
     */
    public final String getEmailAddress() {
	return this.emailAddress;
    }

    /**
     * Sets the value of email
     *
     * @param argEmail Value to assign to this.email
     */
    public final void setEmailAddress(final String argEmail) {
	this.emailAddress = argEmail;
    }

    /**
     * Gets the value of password
     *
     * @return the value of password
     */
    public final String getPassword() {
	return this.password;
    }

    /**
     * Sets the value of password
     *
     * @param argPassword Value to assign to this.password
     */
    public final void setPassword(final String argPassword) {
	this.password = argPassword;
    }
    
     /**
     * Gets the value of cookieDate
     *
     * @return the value of cookieDate
     */
    public final String getCookieDate() {
	return this.cookieDate;
    }

    /**
     * Sets the value of cookieDate
     *
     * @param argEmail Value to assign to this.cookieDate
     */
    public final void setCookieDate(String cookieDate) {
	this.cookieDate = cookieDate;
    }

	public String getBookNo() {
		return bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

}
