package cn.cadal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// Generated 2006-4-21 13:48:18 by Hibernate Tools 3.1.0 beta1JBIDERC2

/**
 *        @hibernate.class
 *         table="CMinguoqikanDetail"
 *     
 */

@Entity
@Table (name="CjournalDetail")
public class CjournalDetail implements java.io.Serializable {

	// Fields    

	/**
	 * 				@hibernate.id
	 * 				generator-class="assigned" type="java.lang.Integer" column="IndexID"
	 * 			
	 */

	private Integer id;
	
	//private String title;
	
	private String bookNo;
	
	private ContentHost repository;

	private String path;

	private CjournalMetadata metadata;

	
	/**
	 *            @hibernate.property
	 *             column="Pdate"
	 *             length="20"
	 *         
	 */
	private String pubDate;

	
	/**
	 *            @hibernate.property
	 *             column="Pvol"
	 *             length="5"
	 *         
	 */
	private String pubVol;

	/**
	 *            @hibernate.property
	 *             column="Pissue"
	 *             length="5"
	 *         
	 */
	private String pubIssue;

	/**
	 *            @hibernate.property
	 *             column="StartPage"
	 *             length="50"
	 *         
	 */
	private String startPage;

	/**
	 *            @hibernate.property
	 *             column="Pyear"
	 *             length="5"
	 *         
	 */
	private String pubYear;


	// Constructors

	/** default constructor */
	public CjournalDetail() {
	}

	/** minimal constructor */
	public CjournalDetail(Integer id, String startPage) {
		this.id = id;
		this.startPage = startPage;
	}

	
	// Property accessors
	/**       
	 *      * 				@hibernate.id
	 * 				generator-class="assigned" type="java.lang.Integer" column="IndexID"
	 * 			
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="TitleID"
	 *         
	 */
	@ManyToOne ()
	@JoinColumn (name="TitleID")
	public CjournalMetadata getMetadata () {
		return this.metadata;
	}

	public void setMetadata (CjournalMetadata metadata) {
		this.metadata = metadata;
	}

	
	@Column(name="StartPage", length=50)
	public String getStartPage() {
		return this.startPage;
	}

	public void setStartPage(String startPage) {
		this.startPage = startPage;
	}
	

	@Column(name="Pissue", length=50)
	public String getPubIssue() {
		return pubIssue;
	}

	public void setPubIssue(String issue) {
		pubIssue = issue;
	}

	@Column(name="Pvol", length=50)
	public String getPubVol() {
		return pubVol;
	}

	public void setPubVol(String vol) {
		pubVol = vol;
	}

	@Column(name="Pyear", length=100)
	public String getPubYear() {
		return pubYear;
	}

	public void setPubYear(String year) {
		pubYear = year;
	}

	@ManyToOne()
	@JoinColumn (name="HostID")
	public ContentHost getRepository() {
		return repository;
	}

	public void setRepository(ContentHost repository) {
		this.repository = repository;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}

	@Column (name="BookNo", length=50)
	public String getBookNo() {
		return bookNo;
	}
	
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	
	@Column (name="Path",length =100)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}
