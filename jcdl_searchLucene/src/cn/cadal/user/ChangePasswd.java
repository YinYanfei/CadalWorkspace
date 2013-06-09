package cn.cadal.user; // Generated package name

import java.util.Map;

import javax.servlet.ServletException;

import org.hibernate.Session;

import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class ChangePasswd extends ActionSupport implements SessionAware {
    
	private Map servletSession;
	Cuser user = new Cuser();	

	private String password;

	private String password2;

	private String password1;

	private boolean isEnglish = false;// cddb 2007 9 21 检测改注册页面是英文还是中文
	
    public void setSession (Map session){
    	this.servletSession = session;
    }
	/**
	 * Gets the value of user
	 * 
	 * @return the value of user
	 */
	public final Cuser getUser() {
		return this.user;
	}

	/**
	 * Sets the value of user
	 * 
	 * @param argUser
	 *            Value to assign to this.user
	 */
	public final void setUser(final Cuser argUser) {
		this.user = argUser;
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
	 * @param argPassword
	 *            Value to assign to this.password
	 */
	public final void setPassword(final String argPassword) {
		this.password = argPassword;
	}

	/**
	 * Gets the value of password1
	 * 
	 * @return the value of password1
	 */
	public final String getPassword1() {
		return this.password1;
	}

	/**
	 * Sets the value of password1
	 * 
	 * @param argPassword1
	 *            Value to assign to this.password1
	 */
	public final void setPassword1(final String argPassword1) {
		this.password1 = argPassword1;
	}

	/**
	 * Gets the value of password2
	 * 
	 * @return the value of password2
	 */
	public final String getPassword2() {
		return this.password2;
	}

	/**
	 * Sets the value of password2
	 * 
	 * @param argPassword2
	 *            Value to assign to this.password2
	 */
	public final void setPassword2(final String argPassword2) {
		this.password2 = argPassword2;
	}

	/**
	 * 将页面的语言信息设置为英文 cddb 2007.9.21
	 * 
	 */
	public final void setIsEnglish(final boolean isEnglish) {
		this.isEnglish = isEnglish;
	}
	
	public String execute() throws Exception {
	 	String userIdStr = (String) servletSession.get ("userId");
    	int userId = 0;    	
		if ( userIdStr == null ) {		
			return SUCCESS;
		} else {			
			userId = Integer.parseInt (userIdStr);
		}
		Session session = HibernateUtil.getSessionFactory ().getCurrentSession ();

		try {
		    session.beginTransaction ();
		    user = (Cuser) session.load (Cuser.class, new Integer (userId));
		    

	        if(!(user.getPassword()).equals(password)){
	        	LOG.warn ("-----------------------------------------------------------------------------");
//	        	 加入判断，对不同语言输出不同的语句 cddb 2007.9.21
	        	if(!this.isEnglish)
	        		addFieldError("passwordError", "旧密码输入错误!");
	        	else
	        		addFieldError("passwordError", "The old password is not correct!");
	        }
	        else{
	        	LOG.warn ("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
	        	user.setPassword(password1);
	        	session.save(user);
//	        	 加入判断，对不同语言输出不同的语句 cddb 2007.9.21
	        	if(!this.isEnglish)
	        		addFieldError("updatePasswordOK", "修改密码成功！");
	        	else
	        		addFieldError("updatePasswordOK", "Update password OK！");
	        }
		    session.getTransaction ().commit ();
		    LOG.warn ("=============================================================================");
		} catch (Exception exc){
			LOG.warn (StackTraceUtil.getStackTrace (exc));
		    session.getTransaction ().rollback ();
		    throw new ServletException (exc);
		}
		return SUCCESS;
    }	
		
		


}
