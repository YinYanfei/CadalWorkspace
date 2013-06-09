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
@Table (name="CAccessControlList")
public class AccessControlList implements Serializable {

	private Integer Id;
	
	private Group group;
	
	private Resource resource;
	
	private String operation;
	
	private boolean allowed;

	
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column (name="ID")
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	
	@Column (name="Allowed")
	public boolean isAllowed() {
		return allowed;
	}

	public void setAllowed(boolean allowed) {
		this.allowed = allowed;
	}	
	
	@Column (name="Operation")
	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	@ManyToOne()
	@JoinColumn (name="ResourceID")
	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
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
