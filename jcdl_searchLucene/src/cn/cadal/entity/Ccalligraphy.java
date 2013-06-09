package cn.cadal.entity;
// Generated 2006-4-10 20:48:28 by Hibernate Tools 3.1.0 beta1JBIDERC2

import java.sql.Timestamp;


/**
 *        @hibernate.class
 *         table="CCalligraphy"
 *     
 */

public class Ccalligraphy  implements java.io.Serializable {


    // Fields    

     /**
      *            @hibernate.id
 *             generator-class="assigned"
 *             type="java.lang.String"
 *             column="BookNo"
 *         
     */
     private String bookNo;
     /**
      *            @hibernate.property
 *             column="URL"
 *             length="100"
 *         
     */
     private String url;
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
 *             length="50"
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


    // Constructors

    /** default constructor */
    public Ccalligraphy() {
    }

	/** minimal constructor */
    public Ccalligraphy(String bookNo, String title, boolean fullIndexed) {
        this.bookNo = bookNo;
        this.title = title;
        this.fullIndexed = fullIndexed;
    }
    
    /** full constructor */
    public Ccalligraphy(String bookNo, String url, Integer page, String title, String creator, String contributor, String subject, String description, String publisher, String createDate, String type, String format, String identifier, String source, String contentLanguage, String relation, String coverage, String rights, Timestamp lastDate, String charCreateTime, boolean fullIndexed) {
        this.bookNo = bookNo;
        this.url = url;
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

    public String getBookNo() {
        return this.bookNo;
    }
    
    public void setBookNo(String bookNo) {
        this.bookNo = bookNo;
    }
    /**       
     *      *            @hibernate.property
     *             column="URL"
     *             length="100"
     *         
     */

    public String getUrl() {
        return this.url;
    }
    
    public void setUrl(String url) {
        this.url = url;
    }
    /**       
     *      *            @hibernate.property
     *             column="Page"
     *             length="10"
     *         
     */

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

    public String getContentLanguage() {
        return this.contentLanguage;
    }
    
    public void setContentLanguage(String contentLanguage) {
        this.contentLanguage = contentLanguage;
    }
    /**       
     *      *            @hibernate.property
     *             column="Relation"
     *             length="50"
     *         
     */

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

    public boolean isFullIndexed() {
        return this.fullIndexed;
    }
    
    public void setFullIndexed(boolean fullIndexed) {
        this.fullIndexed = fullIndexed;
    }
   








}
