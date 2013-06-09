package cn.cadal.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name="UserRecommendBooks")
public class UserRecommendBooks implements Serializable 
{
	private Integer id;
	private String ipAddress;
	private String bookUrl;
	
	// Constructors	
	/** default constructor */
	public UserRecommendBooks() {
	}

	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column (name="ID")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column (name="IPAddress")
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	
	@Column (name="BookUrl")
	public String getBookUrl() {
		return bookUrl;
	}

	public void setBookUrl(String bookUrl) {
		this.bookUrl = bookUrl;
	}
	
	public UserRecommendBooks(String address, String bookUrl) {
		this.ipAddress = address;
		this.bookUrl = bookUrl;
	}






	
}
