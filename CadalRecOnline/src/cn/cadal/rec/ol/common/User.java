package cn.cadal.rec.ol.common;

import java.sql.Timestamp;

public class User {

	private int userId;
	private String userName;
	private String userHometown;
	private String userSchool;
	private Timestamp userBirthday;
	private String userGender;
	private String userEmail;
	private String userRegisterTime;
	
	/**
	 * Construct functions
	 */
	public User(){}
	
	public User(int userId, String userName, String userHometown, String userSchool, 
			Timestamp userBirthday, String userGender, String userEmail, String userRegisterTime) {
		this.userId = userId;
		this.userName = userName;
		this.userHometown = userHometown;
		this.userSchool = userSchool;
		this.userBirthday = userBirthday;
		this.userGender = userGender;
		this.userEmail = userEmail;
		this.userRegisterTime = userRegisterTime;
	}

	/**
	 * Getter and Setter
	 */
	public int getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserHometown() {
		return userHometown;
	}

	public String getUserSchool() {
		return userSchool;
	}

	public Timestamp getUserBirthday() {
		return userBirthday;
	}

	public String getUserGender() {
		return userGender;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public String getUserRegisterTime() {
		return userRegisterTime;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserHometown(String userHometown) {
		this.userHometown = userHometown;
	}

	public void setUserSchool(String userSchool) {
		this.userSchool = userSchool;
	}

	public void setUserBirthday(Timestamp userBirthday) {
		this.userBirthday = userBirthday;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public void setUserRegisterTime(String userRegisterTime) {
		this.userRegisterTime = userRegisterTime;
	}
	
}
