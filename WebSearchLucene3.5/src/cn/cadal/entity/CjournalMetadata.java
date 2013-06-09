package cn.cadal.entity;

// Generated 2006-4-21 13:48:18 by Hibernate Tools 3.1.0 beta1JBIDERC2

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 *        @hibernate.class
 *         table="CMinguoqikan"
 *     
 */


@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table (name="Cjournal")
public class CjournalMetadata implements java.io.Serializable {

	// Fields    

	/**
	 * 				@hibernate.id
	 * 				generator-class="assigned" type="java.lang.Integer" column="TitleID"
	 * 			
	 */
	private Integer bookNo;

	/**
	 *            @hibernate.property
	 *             column="Title"
	 *             length="100"
	 *             not-null="true"
	 *         
	 */
	private String title;

	/**
	 *            @hibernate.property
	 *             column="Creator"
	 *             length="200"
	 *         
	 */
	private String creator;

	/**
	 *            @hibernate.property
	 *             column="Subject"
	 *             length="200"
	 *         
	 */
	private String subject;

	/**
	 *            @hibernate.property
	 *             column="Description"
	 *             length="500"
	 *         
	 */
	private String description;

	/**
	 *            @hibernate.property
	 *             column="Publisher"
	 *             length="100"
	 *         
	 */
	private String publisher;

	/**
	 *            @hibernate.property
	 *             column="Type"
	 *             length="50"
	 *         
	 */
	private String type;

	private List<CjournalDetail> issueList = new ArrayList<CjournalDetail>();

	// Constructors

	/** default constructor */
	public CjournalMetadata() {
	}

	/** minimal constructor */
	public CjournalMetadata(Integer id, String title) {
		this.bookNo = id;
		this.title = title;
	}

	/** full constructor */
	public CjournalMetadata(Integer id, String title, String creator, String subject,
			String description, String publisher, String type, List<CjournalDetail> issueList) {
		this.bookNo = id;
		this.title = title;
		this.creator = creator;
		this.subject = subject;
		this.description = description;
		this.publisher = publisher;
		this.type = type;
		this.issueList = issueList;
	}

	// Property accessors
	/**       
	 *      * 				@hibernate.id
	 * 				generator-class="assigned" type="java.lang.Integer" column="TitleID"
	 * 			
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TitleID")
	public Integer getBookNo() {
		return this.bookNo;
	}

	public void setBookNo(Integer id) {
		this.bookNo = id;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Title"
	 *             length="100"
	 *             not-null="true"
	 *         
	 */
	@Column(updatable=false, name="Title", length=100, nullable=false)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Creator"
	 *             length="200"
	 *         
	 */
	@Column(name="Creator", length=100)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Subject"
	 *             length="200"
	 *         
	 */
	@Column(name="Subject", length=200)
	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Description"
	 *             length="500"
	 *         
	 */
	@Column(name="Description", length=4000)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Publisher"
	 *             length="100"
	 *         
	 */
	@Column(name="Publisher", length=100)
	public String getPublisher() {
		return this.publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**       
	 *      *            @hibernate.property
	 *             column="Type"
	 *             length="50"
	 *         
	 */
	@Column(name="Type", length=100)
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

    @OneToMany (mappedBy="metadata",cascade=CascadeType.ALL)
    @OrderBy ("pubYear asc,pubVol asc,pubIssue asc")
	public List<CjournalDetail> getIssueList() {
		return this.issueList;
	}

	public void setIssueList(List<CjournalDetail> issueList) {
		this.issueList = issueList;
	}

}
