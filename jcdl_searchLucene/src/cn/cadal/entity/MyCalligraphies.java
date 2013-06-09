package cn.cadal.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table (name="CUserCalligraphy")
public class MyCalligraphies implements Serializable {
	
	private int id;
	
	private Cuser user;
	
	private String ruleName;
	
	private String fileName;
	
	private Date uploadTime;

	@Column(name="UploadTime", length=32)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(Date datetime) {
		this.uploadTime = datetime;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@ManyToOne()
	@JoinColumn (name="UserID")
	public Cuser getUser() {
		return user;
	}

	public void setUser(Cuser user) {
		this.user = user;
	}

	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	
	
}
