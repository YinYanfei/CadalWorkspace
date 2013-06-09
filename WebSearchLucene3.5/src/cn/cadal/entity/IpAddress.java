package cn.cadal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name="CIPTable")
public class IpAddress implements Serializable {

	private Integer Id;
	
	private String address;

	private Group group;

	public IpAddress () {
		
	}
	
	public IpAddress (String address, Group group) {
		this.address = address;
		this.group = group;
	}
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO) 
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	
	@Column (name="IpAddress", length=100)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ManyToOne()
	@JoinColumn (name="GroupID")
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

}
