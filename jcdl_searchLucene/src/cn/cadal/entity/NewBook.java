package cn.cadal.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="CNewBookListing")
public class NewBook implements Serializable {

	private String bookNo;

	private String path;
	
	private ContentHost host;
	
	public NewBook () {
		
	}
	
	public NewBook (String bookNo, String path, ContentHost host) {
		this.bookNo = bookNo;
		this.path = path;
		this.host = host;
	}
	
	@Id
	public String getBookNo() {
		return bookNo;
	}
	
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
	
	@ManyToOne ()
	@JoinColumn (name="HostID")
	public ContentHost getHost() {
		return host;
	}

	public void setHost(ContentHost host) {
		this.host = host;
	}
	
	@Column (name="Path", nullable=false, length=100)
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
}
