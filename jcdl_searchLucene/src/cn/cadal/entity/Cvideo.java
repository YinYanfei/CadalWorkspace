package cn.cadal.entity;

// Generated 2006-4-10 20:48:27 by Hibernate Tools 3.1.0 beta1JBIDERC2

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * Cvideo generated by hbm2java
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "VideoMetadata")
public class Cvideo implements java.io.Serializable {

	// Fields    

	private Integer videoNO;

	private String title;

	private String author;

	private String description;

	private String copyright;

	private String rating;

	private List<Cvideoshot> shots = new ArrayList<Cvideoshot>();

	// Constructors

	/** default constructor */
	public Cvideo() {
	}

	/** minimal constructor */
	public Cvideo(Integer videoNO) {
		this.videoNO = videoNO;
	}

	/** full constructor */
	public Cvideo(Integer videoNO, String title, String author,
			String description, String copyright, String rating, List<Cvideoshot> shots) {
		this.videoNO = videoNO;
		this.title = title;
		this.author = author;
		this.description = description;
		this.copyright = copyright;
		this.rating = rating;
		this.shots = shots;
	}

	// Property accessors
	@Id
	@Column (name="VideoID")
	public Integer getVideoNO() {
		return this.videoNO;
	}

	public void setVideoNO(Integer videoNO) {
		this.videoNO = videoNO;
	}

	@Column(name = "Title", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "Author", length = 50)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Column(name = "Description", length = 200)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "Copyright", length = 50)
	public String getCopyright() {
		return this.copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	@Column(name = "Rating", length = 50)
	public String getRating() {
		return this.rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	@OneToMany(mappedBy = "video")
	@OrderBy("shotID asc")
	public List<Cvideoshot> getShots() {
		return this.shots;
	}

	public void setShots(List<Cvideoshot> shots) {
		this.shots = shots;
	}

}