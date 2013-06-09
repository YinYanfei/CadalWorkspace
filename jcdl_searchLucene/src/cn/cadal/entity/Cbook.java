package cn.cadal.entity;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import common.utils.HashCodeUtil;




/**
 *        @hibernate.class
 *         table="CBook"
 *     
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table (name="CBooK")
@Inheritance (strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name="BookType",
		discriminatorType=DiscriminatorType.STRING
		)
		
@DiscriminatorValue ("book")
public class Cbook implements java.io.Serializable {

	// Fields    
	private String bookNo;

	private String path;
	
	private ContentHost repository;

	/**
	 *            @hibernate.property
	 *             column="Page"
	 *             length="10"
	 *         
	 */
	private Integer page;

	/**
	 *            @hibernate.property
	 *             column="Title"
	 *             length="500"
	 *             not-null="true"
	 *         
	 */
	private String title;

	/**
	 *            @hibernate.property
	 *             column="Creator"
	 *             length="500"
	 *         
	 */
	private String creator;

	/**
	 *            @hibernate.property
	 *             column="Contributor"
	 *             length="500"
	 *         
	 */
	private String contributor;

	/**
	 *            @hibernate.property
	 *             column="Subject"
	 *             length="800"
	 *         
	 */
	private String subject;

	/**
	 *            @hibernate.property
	 *             column="Description"
	 *             length="8000"
	 *         
	 */
	private String description;

	/**
	 *            @hibernate.property
	 *             column="Publisher"
	 *             length="500"
	 *         
	 */
	private String publisher;

	/**
	 *            @hibernate.property
	 *             column="CreateDate"
	 *             length="50"
	 *         
	 */
	private String createDate;

	/**
	 *            @hibernate.property
	 *             column="Type"
	 *             length="50"
	 *         
	 */
	private String type;

	/**
	 *            @hibernate.property
	 *             column="Format"
	 *             length="50"
	 *         
	 */
	private String format;

	/**
	 *            @hibernate.property
	 *             column="Identifier"
	 *             length="200"
	 *         
	 */
	private String identifier;

	/**
	 *            @hibernate.property
	 *             column="Source"
	 *             length="300"
	 *         
	 */
	private String source;

	/**
	 *            @hibernate.property
	 *             column="ContentLanguage"
	 *             length="50"
	 *         
	 */
	private String contentLanguage;

	/**
	 *            @hibernate.property
	 *             column="Relation"
	 *             length="300"
	 *         
	 */
	private String relation;

	/**
	 *            @hibernate.property
	 *             column="Coverage"
	 *             length="300"
	 *         
	 */
	private String coverage;

	/**
	 *            @hibernate.property
	 *             column="Rights"
	 *             length="300"
	 *         
	 */
	private String rights;

	/**
	 *            @hibernate.property
	 *             column="LastDate"
	 *             length="23"
	 *         
	 */
	private Timestamp lastDate;

	/**
	 *            @hibernate.property
	 *             column="CharCreateTime"
	 *             length="50"
	 *         
	 */
	private String charCreateTime;

	/**
	 *            @hibernate.property
	 *             column="FullIndexed"
	 *             length="1"
	 *             not-null="true"
	 *         
	 */
	private boolean fullIndexed;

	
	private Set<MyRanking> userRank = new HashSet<MyRanking> ();	
	
	// Constructors

	@OneToMany (mappedBy="book")
	public  Set<MyRanking> getUserRank() {
		return userRank;
	}

	public void setUserRank(Set<MyRanking> userRank) {
		this.userRank = userRank;
	}
	
	/** default constructor */
	public Cbook() {
	}

	/** minimal constructor */
	public Cbook(String bookNo, String title, boolean fullIndexed) {
		this.bookNo = bookNo;
		this.title = title;
		this.fullIndexed = fullIndexed;
	}

	/** full constructor */
	public Cbook(String bookNo, String path, Integer page, String title,
			String creator, String contributor, String subject,
			String description, String publisher, String createDate,
			String type, String format, String identifier, String source,
			String contentLanguage, String relation, String coverage,
			String rights, Timestamp lastDate, String charCreateTime,
			boolean fullIndexed) {
		this.bookNo = bookNo;
		this.path = path;
		this.page = page;
		this.title = title;
		this.creator = creator;
		this.contributor = contributor;
		this.subject = subject;
		this.description = description;
		this.publisher = publisher;
		this.createDate = createDate;
		this.type = type;
		this.format = format;
		this.identifier = identifier;
		this.source = source;
		this.contentLanguage = contentLanguage;
		this.relation = relation;
		this.coverage = coverage;
		this.rights = rights;
		this.lastDate = lastDate;
		this.charCreateTime = charCreateTime;
		this.fullIndexed = fullIndexed;
	}

	// Property accessors
	/**       
	 *      *            @hibernate.id
	 *             generator-class="assigned"
	 *             type="java.lang.String"
	 *             column="BookNo"
	 *         
	 */
	@Id
	@Column(updatable=false, name="BookNo", length=50, nullable=false)
	public String getBookNo() {
		return this.bookNo;
	}

	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}

	@Column(name="Path", length=100 , nullable=false)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Page"
	 *             length="10"
	 *         
	 */
	
	@Column(name="Page", nullable=false)
	public Integer getPage() {
		return this.page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Title"
	 *             length="500"
	 *             not-null="true"
	 *         
	 */
	@Column(name="Title", length=500 , nullable=false)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Creator"
	 *             length="500"
	 *         
	 */
	@Column(name="Creator", length=200 , nullable=false)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Contributor"
	 *             length="500"
	 *         
	 */
	@Column(name="Contributor", length=200 , nullable=false)
	public String getContributor() {
		return this.contributor;
	}

	public void setContributor(String contributor) {
		this.contributor = contributor;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Subject"
	 *             length="800"
	 *         
	 */
	@Column(name="Subject", length=200 , nullable=false)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Description"
	 *             length="8000"
	 *         
	 */
	@Column(name="Description", length=8000 , nullable=false)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Publisher"
	 *             length="500"
	 *         
	 */
	@Column(name="Publisher", length=500 , nullable=false)
	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="CreateDate"
	 *             length="50"
	 *         
	 */
	@Column(name="CreateDate", length=200 , nullable=true)
	public String getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Type"
	 *             length="50"
	 *         
	 */
	@Column(name="Type", length=100 , nullable=false)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Format"
	 *             length="50"
	 *         
	 */
	@Column(name="Format", length=50 , nullable=false)
	public String getFormat() {
		return this.format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Identifier"
	 *             length="200"
	 *         
	 */
	@Column(name="Identifier", length=200 , nullable=false)
	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Source"
	 *             length="300"
	 *         
	 */
	
	@Column(name="Source", length=200 , nullable=false)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="ContentLanguage"
	 *             length="50"
	 *         
	 */
	@Column(name="ContentLanguage", length=50 , nullable=false)
	public String getContentLanguage() {
		return this.contentLanguage;
	}

	public void setContentLanguage(String contentLanguage) {
		this.contentLanguage = contentLanguage;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Relation"
	 *             length="300"
	 *         
	 */
	@Column(name="Relation", length=300 , nullable=false)
	public String getRelation() {
		return this.relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Coverage"
	 *             length="300"
	 *         
	 */
	@Column(name="Coverage", length=300 , nullable=false)
	public String getCoverage() {
		return this.coverage;
	}

	public void setCoverage(String coverage) {
		this.coverage = coverage;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Rights"
	 *             length="300"
	 *         
	 */
	
	@Column(name="Rights", length=300 , nullable=false)
	public String getRights() {
		return this.rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="LastDate"
	 *             length="23"
	 *         
	 */
	@Column(name="LastDate", length=23 )
	public Timestamp getLastDate() {
		return this.lastDate;
	}

	public void setLastDate(Timestamp lastDate) {
		this.lastDate = lastDate;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="CharCreateTime"
	 *             length="50"
	 *         
	 */
	@Column(name="CharCreateTime", length=50 )
	public String getCharCreateTime() {
		return this.charCreateTime;
	}

	public void setCharCreateTime(String charCreateTime) {
		this.charCreateTime = charCreateTime;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="FullIndexed"
	 *             length="1"
	 *             not-null="true"
	 *         
	 */
	@Column(name="FullIndexed", nullable=false)
	public boolean isFullIndexed() {
		return this.fullIndexed;
	}

	public void setFullIndexed(boolean fullIndexed) {
		this.fullIndexed = fullIndexed;
	}

	@ManyToOne ()
	@JoinColumn (name="HostID")
	public ContentHost getRepository() {
		return repository;
	}

	public void setRepository(ContentHost host) {
		this.repository = host;
	}
	
	public void accept (BookVisitor bv) {
	}
	
	public boolean equals (Object right) {
		Cbook rBook = (Cbook)right;
		return getBookNo() == rBook.getBookNo();
	}
	
	public int hashCode () {
		int res = HashCodeUtil.SEED;
		res = HashCodeUtil.hash (res, getBookNo());
		return res;
	}

}
