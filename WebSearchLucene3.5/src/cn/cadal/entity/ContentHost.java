package cn.cadal.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import common.utils.HashCodeUtil;

@Entity
@Table (name="ContentHost")
public class ContentHost implements Serializable {
	
	private Integer id;
	private String host;
	private String path;
	private String username;
	private String password;
	private Set<Cbook> collection;
	private Set<NewBook> newBookSet;
	
	private String externalUri;
	private Date createDate;
	
	private Boolean disable=false;
	
	public ContentHost () {
		
	}
	
	@Column (name="Password" , nullable=false, length=100)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column (name="Path" , nullable=false, length=100)
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Column (name="Username" , nullable=false, length=100)
	public String getUsername () {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Temporal (TemporalType.TIMESTAMP)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column (name="ExternalUri" , nullable=false, length=100)
	public String getExternalUri() {
		return externalUri;
	}
	
	public void setExternalUri(String externalUri) {
		this.externalUri = externalUri;
	}
	
	@Column (name="Host" , nullable=false, length=100)
	public String getHost() {
		return host;
	}
	
	public void setHost(String innerUri) {
		this.host = innerUri;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object arg0) {
		ContentHost right = (ContentHost)arg0;
		return (this.getHost().equals(right.getHost())
				&&this.getPath().equals(right.getPath())
				&&this.getUsername().equals(right.getUsername()));
	}

	@Override
	public int hashCode() {
		int result = HashCodeUtil.SEED;
		result = HashCodeUtil.hash (result, host);
		result = HashCodeUtil.hash (result, path);
		result = HashCodeUtil.hash (result, username);
		return result;
	}

	@OneToMany(mappedBy="repository")
	public Set<Cbook> getCollection() {
		return collection;
	}

	public void setCollection(Set<Cbook> collection) {
		this.collection = collection;
	}
	
	@OneToMany(mappedBy="host")
	public Set<NewBook> getNewBookSet(){
		return newBookSet;
	}

	public void setNewBookSet(Set<NewBook> newBookSet) {
		this.newBookSet = newBookSet;
	}

	@Column (name="Disable",nullable=false)
	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}
	
}
