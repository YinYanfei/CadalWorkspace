package cn.cadal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// Generated 2006-4-10 20:48:28 by Hibernate Tools 3.1.0 beta1JBIDERC2

/**
 * Cvideoshot generated by hbm2java
 */
@Entity
@Table (name="VideoShotInfo")
public class Cvideoshot implements java.io.Serializable {

	// Fields
	private Integer shotNO;

	private Cvideo video;

	private Integer shotID;

	private Float starttime;

	private Float endtime;

	// Constructors

	/** default constructor */
	public Cvideoshot() {
	}

	/** minimal constructor */
	public Cvideoshot(Integer shotNO) {
		this.shotNO = shotNO;
	}

	/** full constructor */
	public Cvideoshot(Integer shotNO, Cvideo video, Integer shotID,
			Float starttime, Float endtime) {
		this.shotNO = shotNO;
		this.video = video;
		this.shotID = shotID;
		this.starttime = starttime;
		this.endtime = endtime;
	}

	// Property accessors
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column (name="ShotNO")
	public Integer getShotNO() {
		return this.shotNO;
	}

	public void setShotNO(Integer shotNO) {
		this.shotNO = shotNO;
	}

	@ManyToOne()
	@JoinColumn (name="VideoID")
	public Cvideo getVideo() {
		return this.video;
	}

	public void setVideo(Cvideo video) {
		this.video = video;
	}	

	@Column (name="ShotID")
	public Integer getShotID() {
		return this.shotID;
	}

	public void setShotID(Integer shotID) {
		this.shotID = shotID;
	}

	@Column (name="StartTime")
	public Float getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Float starttime) {
		this.starttime = starttime;
	}

	@Column (name="EndTime")
	public Float getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Float endtime) {
		this.endtime = endtime;
	}


}
