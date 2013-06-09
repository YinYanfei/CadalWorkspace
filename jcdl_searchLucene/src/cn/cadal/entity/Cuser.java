package cn.cadal.entity;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @hibernate.class table="CUser"
 */

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table (name="CUser")
public class Cuser implements java.io.Serializable {

	// Fields
	private int userId;

	private String emailAddress;

	private String password;

	private String userRights;

	private String homeTown;

	private String school;

	private String specialty;

	private Date birthDay;

	private String gender;

	private String occupation;

	private String cookieValue;

	private String introduction;

	private Set myRecommendation = new HashSet();

	private Set<Cbook> myFavorite = new HashSet<Cbook>();

	private Set myRanking = new HashSet();

	private Set myPage = new HashSet();
	
	private List<MyCalligraphies> myCalligraphies= new ArrayList<MyCalligraphies>();
	
	private Set<UserBinder> myBinders = new HashSet <UserBinder>();
	
	private Set<Group> myGroup = new HashSet<Group>();


	@ManyToMany (targetEntity=Group.class)
	@JoinTable (name="CUserGroup",
				joinColumns={@JoinColumn(name="UserID")},
				inverseJoinColumns={@JoinColumn(name="GroupID")}) 
	public Set<Group> getMyGroup() {
		return myGroup;
	}

	public void setMyGroup(Set<Group> myGroup) {
		this.myGroup = myGroup;
	}

	// Constructors
	/** default constructor */
	public Cuser() {
	}

	/** minimal constructor */
	public Cuser(String emailAddress, String password) {
		this.emailAddress = emailAddress;
		this.password = password;
	}

	/** full constructor */
	public Cuser(String emailAddress, String password, String userRights,
			String homeTown, String school, String specialty, Date birthDay,
			String gender, String occupation, String cookieValue,
			String introduction, Set myRecommendation, Set<Cbook> myFavorite,
			Set myRanking, Set myPage , Set<UserBinder> myBinders) {
		this.emailAddress = emailAddress;
		this.password = password;
		this.userRights = userRights;
		this.homeTown = homeTown;
		this.school = school;
		this.specialty = specialty;
		this.birthDay = birthDay;
		this.gender = gender;
		this.occupation = occupation;
		this.cookieValue = cookieValue;
		this.introduction = introduction;
		this.myRecommendation = myRecommendation;
		this.myFavorite = myFavorite;
		this.myRanking = myRanking;
		this.myBinders = myBinders;
		this.myPage = myPage;
	}

	// Property accessors
	/**
	 * *
	 * 
	 * @hibernate.id generator-class="native" type="long" column="UserID"
	 * 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	/**
	 * *
	 * 
	 * @hibernate.property column="EmailAddress" length="50" not-null="true"
	 */
	@Column(updatable=false, name="EmailAddress", length=100, nullable=false, unique=true)
	public String getEmailAddress() {
		return this.emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * *
	 * 
	 * @hibernate.property column="Password" length="50" not-null="true"
	 */
	@Column(name="Password", length=50, nullable=false )
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * *
	 * 
	 * @hibernate.property column="UserRights" length="50"
	 */
	@Column(name="UserRights", length=50 )
	public String getUserRights() {
		return this.userRights;
	}

	public void setUserRights(String userRights) {
		this.userRights = userRights;
	}

	/**
	 * *
	 * 
	 * @hibernate.property column="HomeTown" length="50"
	 */
	@Column(name="HomeTown", length=50 )
	public String getHomeTown() {
		return this.homeTown;
	}

	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}

	/**
	 * *
	 * 
	 * @hibernate.property column="School" length="50"
	 */
	@Column(name="School", length=50 )
	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	/**
	 * *
	 * 
	 * @hibernate.property column="Specialty" length="50"
	 */
	@Column(name="Specialty", length=50 )
	public String getSpecialty() {
		return this.specialty;
	}

	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}

	/**
	 * *
	 * 
	 * @hibernate.property column="BirthDay" length="23"
	 */
	@Column(name="BirthDay", length=23 )
	@Temporal(TemporalType.DATE)
	public Date getBirthDay() {
		return this.birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	/**
	 * *
	 * 
	 * @hibernate.property column="Gender" length="10"
	 */
	@Column(name="Gender", length=10 )
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * *
	 * 
	 * @hibernate.property column="Occupation" length="50"
	 */
	@Column(name="Occupation", length=50 )
	public String getOccupation() {
		return this.occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	/**
	 * *
	 * 
	 * @hibernate.property column="CookieValue" length="20"
	 * 
	 */
	@Column(name="CookieValue", length=20 )
	public String getCookieValue() {
		return this.cookieValue;
	}

	public void setCookieValue(String cookieValue) {
		this.cookieValue = cookieValue;
	}

	/**
	 * *
	 * 
	 * @hibernate.property column="Introduction" length="2147483647"
	 */
	@Column(name="Introduction", length=4000 )
	@Lob
	public String getIntroduction() {
		return this.introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Transient
	public Set getMyRecommendation() {
		return this.myRecommendation;
	}

	public void setMyRecommendation(Set myRecommendation) {
		this.myRecommendation = myRecommendation;
	}
	
	@ManyToMany (targetEntity=Cbook.class)
	@JoinTable (name="CUserFavorite",
				joinColumns={@JoinColumn(name="UserID")},
				inverseJoinColumns={@JoinColumn(name="BookID")})
	@OrderBy("BookType asc") 
	public Set<Cbook> getMyFavorite() {
		return this.myFavorite;
	}

	public void setMyFavorite (Set<Cbook> myFavorite) {
		this.myFavorite = myFavorite;
	}

	@Transient
	public Set getMyRanking() {
		return this.myRanking;
	}

	public void setMyRanking(Set myRanking) {
		this.myRanking = myRanking;
	}

	@Transient
	public Set getMyPage() {
		return this.myPage;
	}

	public void setMyPage(Set myPage) {
		this.myPage = myPage;
	}
	
	@OneToMany(mappedBy="user")
	@OrderBy("id")
	public List<MyCalligraphies> getMyCalligraphies(){
		return this.myCalligraphies;
	}
	
	@OneToMany(mappedBy="user")
	public Set<UserBinder> getMyBinders() {
		return this.myBinders;
	}

	public void setMyBinders(Set<UserBinder> myBinders) {
		this.myBinders = myBinders;
	}

	public void setMyCalligraphies(List<MyCalligraphies> myCalligraphies) {
		this.myCalligraphies = myCalligraphies;
	}

}
