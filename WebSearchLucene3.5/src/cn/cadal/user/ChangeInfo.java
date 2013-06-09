package cn.cadal.user; // Generated package name

import java.sql.Date;
import java.util.Map;

import javax.servlet.ServletException;

import org.hibernate.Session;

import cn.cadal.entity.Cuser;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class ChangeInfo extends ActionSupport implements SessionAware {
	private Map servletSession;

	Cuser user = new Cuser();

	private String year = "1930";

	private String month = "1";

	private String day = "1";

	private String occupation;

	private String province;

	private String city;

	private String school;

	private String gender;

	private boolean isEnglish = false;// cddb 2007 9 21 检测改注册页面是英文还是中文

	public void setSession(Map session) {
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
	 * Gets the value of occupation
	 * 
	 * @return the value of occupation
	 */
	public final String getOccupation() {
		return this.occupation;
	}

	/**
	 * Sets the value of occupation
	 * 
	 * @param argOccupation
	 *            Value to assign to occupation
	 */
	public final void setOccupation(final String argOccupation) {
		this.occupation = argOccupation;
	}

	/**
	 * Sets the value of school
	 * 
	 * @param argSchool
	 *            Value to assign to school
	 */
	public final void setSchool(final String argSchool) {
		this.school = argSchool;
	}

	/**
	 * Gets the value of school
	 * 
	 * @return the value of school
	 */
	public final String getSchool() {
		return this.school;
	}

	/**
	 * Sets the value of gender
	 * 
	 * @param argGende
	 *            Value to assign to gender
	 */
	public final void setGender(final String argGender) {
		this.gender = argGender;
	}

	/**
	 * Gets the value of gender
	 * 
	 * @return the value of gender
	 */
	public final String getGender() {
		return this.gender;
	}

	/**
	 * Gets the value of year
	 * 
	 * @return the value of year
	 */
	public final String getYear() {
		return this.year;
	}

	/**
	 * Sets the value of year
	 * 
	 * @param argYear
	 *            Value to assign to this.year
	 */
	public final void setYear(final String argYear) {
		this.year = argYear;
	}

	/**
	 * Gets the value of month
	 * 
	 * @return the value of month
	 */
	public final String getMonth() {
		return this.month;
	}

	/**
	 * Sets the value of month
	 * 
	 * @param argMonth
	 *            Value to assign to this.month
	 */
	public final void setMonth(final String argMonth) {
		this.month = argMonth;
	}

	/**
	 * Gets the value of day
	 * 
	 * @return the value of day
	 */
	public final String getDay() {
		return this.day;
	}

	/**
	 * Sets the value of day
	 * 
	 * @param argDay
	 *            Value to assign to this.day
	 */
	public final void setDay(final String argDay) {
		this.day = argDay;
	}

	/**
	 * Gets the value of province
	 * 
	 * @return the value of province
	 */
	public final String getProvince() {
		return this.province;
	}

	/**
	 * Sets the value of province
	 * 
	 * @param argProvince
	 *            Value to assign to this.province
	 */
	public final void setProvince(final String argProvince) {
		this.province = argProvince;
	}

	/**
	 * Gets the value of city
	 * 
	 * @return the value of city
	 */
	public final String getCity() {
		return this.city;
	}

	/**
	 * Sets the value of city
	 * 
	 * @param argCity
	 *            Value to assign to this.city
	 */
	public final void setCity(final String argCity) {
		this.city = argCity;
	}

	/**
	 * 将页面的语言信息设置为英文 cddb 2007.9.21
	 * 
	 */
	public final void setIsEnglish(final boolean isEnglish) {
		this.isEnglish = isEnglish;
	}

	public String execute() throws Exception {
		String userIdStr = (String) servletSession.get("userId");
		int userId = 0;
		if (userIdStr == null) {
			return SUCCESS;
		} else {
			userId = Integer.parseInt(userIdStr);
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();

		try {
			session.beginTransaction();
			user = (Cuser) session.load(Cuser.class, new Integer(userId));

			String homeTown;
			if (city.equals("0")) {
				if (province.equals("0")) {
					// 加入判断，对不同语言输出不同的语句 cddb 2007.9.21
					if (!this.isEnglish)
						homeTown = "其他";
					else
						homeTown = "Others";
				} else {
					homeTown = province;
				}
			} else {
				homeTown = province + "-" + city;
			}

			String birthStr = year + "-" + month + "-" + day;
			Date birthDay = Date.valueOf(birthStr);

			if (LOG.isDebugEnabled()) {
				LOG
						.warn("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
				LOG.warn(" homeTownl: " + homeTown);
				LOG.warn(" birthStr: " + birthStr);
				LOG.warn(" occupation: " + occupation);
				LOG.warn(" school: " + school);
				LOG.warn(" gender: " + gender);
				LOG
						.warn("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			}
			user.setHomeTown(homeTown);
			user.setBirthDay(birthDay);
			user.setOccupation(occupation);
			user.setSchool(school);
			user.setGender(gender);

			if (LOG.isDebugEnabled()) {
				LOG.debug(user);
			}
			session.save(user);
			// 加入判断，对不同语言输出不同的语句 cddb 2007.9.21
			if (!this.isEnglish)
				addFieldError("updateInformationOK", "您的基本信息修改成功！");
			else
				addFieldError("updateInformationOK", "Update information OK！");

			session.getTransaction().commit();
			if (LOG.isDebugEnabled()) {
				LOG.warn("=============================================================================");
			}
		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
			session.getTransaction().rollback();
			throw new ServletException(exc);
		}
		return SUCCESS;
	}

}
