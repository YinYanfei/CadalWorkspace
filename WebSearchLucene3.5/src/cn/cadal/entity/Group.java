package cn.cadal.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table (name="CGroup")
public class Group implements Serializable {

	private Integer Id;
	
	private String name;
	
	private String description;
	
	private Set<IpAddress> ipSet = new HashSet<IpAddress> ();
	
	private Set<Cuser> userSet = new HashSet<Cuser> ();
	
	private Set<AccessControlList> aclSet = new HashSet<AccessControlList> ();
	
	public Group () {
		
	}
	
	public Group (String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	@ManyToMany(
			targetEntity=Cuser.class,
			mappedBy="myGroup")
	public Set<Cuser> getUserSet() {
		return userSet;
	}

	public void setUserSet(Set<Cuser> userSet) {
		this.userSet = userSet;
	}

	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column (name="GroupID")
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	
	@Column (name="Description", length=300)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column (name="Name", length=300)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@OneToMany(mappedBy="group")
	public Set<IpAddress> getIpSet() {
		return ipSet;
	}

	public void setIpSet(Set<IpAddress> ipSet) {
		this.ipSet = ipSet;
	}

	@OneToMany(mappedBy="group")
	public Set<AccessControlList> getAclSet() {
		return aclSet;
	}

	public void setAclSet(Set<AccessControlList> aclSet) {
		this.aclSet = aclSet;
	}
	
}
