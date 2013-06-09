package cn.cadal.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import common.utils.HashCodeUtil;

@Entity
@Table (name="Cresource")
public class Resource implements Serializable {

	private Integer Id;
	
	private ScanCenter scanCenter;
	
	private String bookType;
	
	private Set<AccessControlList> aclSet = new HashSet<AccessControlList>(); 
	
	public Resource () {
		
	}
	
	public Resource (ScanCenter center, String bookType) {
		this.scanCenter = center;
		this.bookType = bookType;
	}
	
	public boolean equals (Object right) {
		boolean retval = false;
		Resource rightResource = (Resource)right;
		
		retval = (getBookType().equals(rightResource.getBookType()))
				&& (getScanCenter().equals(rightResource.getScanCenter()));

		return retval;
	}
	
	public int hashCode () {
		int res = HashCodeUtil.SEED;
		res = HashCodeUtil.hash (res, getBookType());
		res = HashCodeUtil.hash (res, getScanCenter());
		return res;
	}
	
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}
	
	@Column (name="BookType", length=300)
	public String getBookType() {
		return bookType;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}

	@ManyToOne ()
	@JoinColumn (name="ScanCenter")
	public ScanCenter getScanCenter() {
		return scanCenter;
	}

	public void setScanCenter(ScanCenter scanCenter) {
		this.scanCenter = scanCenter;
	}

	@OneToMany(mappedBy="resource")
	public Set<AccessControlList> getAclSet() {
		return aclSet;
	}

	public void setAclSet(Set<AccessControlList> aclSet) {
		this.aclSet = aclSet;
	}
	
}
