package cn.cadal.user; // Generated package name

import java.sql.Date;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import cn.cadal.entity.Cuser;

import com.opensymphony.xwork.ActionSupport;
import common.utils.HibernateUtil;
import common.utils.StackTraceUtil;

public class Registration extends ActionSupport {

	private static final Log LOG = LogFactory.getLog (Registration.class); 
	
	Cuser user = new Cuser();

	private String emailAddress;

	private String password;

	private String password2;

	private String year = "1930";

	private String month = "1";

	private String day = "1";

	private String occupation;

	private String school;

	private String province="0";

	private String city="0";

	private String gender;
	
	private boolean isEnglish=false;//cddb 2007 9 20 ����ע��ҳ����Ӣ�Ļ�������

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
	 * @param argUser Value to assign to this.user
	 */
	public final void setUser(final Cuser argUser) {
		this.user = argUser;
	}

	/**
	 * Gets the value of emailAddress
	 *
	 * @return the value of emailAddress
	 */
	public final String getEmailAddress() {
		return this.emailAddress;
	}

	/**
	 * Sets the value of emailAddress
	 *
	 * @param argEmailAddress Value to assign to this.emailAddress
	 */
	public final void setEmailAddress(final String argEmailAddress) {
		this.emailAddress = argEmailAddress;
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
	 * @param argOccupation Value to assign to occupation
	 */
	public final void setOccupation(final String argOccupation) {
		this.occupation = argOccupation;
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
	 * @param argPassword2 Value to assign to this.password2
	 */
	public final void setPassword2(final String argPassword2) {
		this.password2 = argPassword2;
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
	 * @param argYear Value to assign to this.year
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
	 * @param argMonth Value to assign to this.month
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
	 * @param argDay Value to assign to this.day
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
	 * @param argProvince Value to assign to this.province
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
	 * @param argCity Value to assign to this.city
	 */
	public final void setCity(final String argCity) {
		this.city = argCity;
	}

	public void validate() {
		// check whether email exists
	}
	/**
	 * ��ҳ���������Ϣ����ΪӢ�� cddb 2007.9.20
	 *
	 */
	public final void setIsEnglish(final boolean isEnglish){
		this.isEnglish=isEnglish;
	}
	
	public String execute() throws Exception {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		try {
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Cuser.class);
			criteria.add(Restrictions.eq("emailAddress", emailAddress));
			Cuser existedUser = (Cuser) criteria.uniqueResult();

			if (existedUser != null) {
				//�����жϣ��Բ�ͬ���������ͬ����� cddb 2007.9.20
				if(!this.isEnglish)
					addFieldError("emailAddress", "�û����Ѵ���");
				else
					addFieldError("emailAddress", "User already exists!");
			}

			String homeTown;
			if (city.equals("0")) {
				if (province.equals("0")) {
					//�����жϣ��Բ�ͬ���������ͬ����� cddb 2007.9.20
					if(!isEnglish)
						homeTown = "����";
					else
						homeTown = "Others";
				} else {
					homeTown = province;
				}
			} else {
				homeTown = province + "-" + city;
			}

			String birthStr = year + "-" + month + "-" + day;

			if (LOG.isDebugEnabled()) {
				LOG.debug("email : " + emailAddress + " password :" + password
						+ " password2 : " + password2);
				LOG.debug(" homeTownl: " + homeTown + " birthStr: " + birthStr);
			}

			Date birthDay = Date.valueOf(birthStr);

			user.setEmailAddress(emailAddress);
			user.setPassword(password);
			user.setHomeTown(homeTown);
			user.setBirthDay(birthDay);

			if (hasActionErrors() || hasFieldErrors()) {
				return INPUT;
			}

			if (LOG.isDebugEnabled()) {
				LOG.debug(user);
			}

			session.save(user);
			session.getTransaction().commit();

		} catch (Exception exc) {
			LOG.warn(StackTraceUtil.getStackTrace(exc));
			session.getTransaction().rollback();
			throw new ServletException(exc);
		}

		return SUCCESS;
	}

}
