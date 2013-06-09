package cn.cadal.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import common.utils.HashCodeUtil;

@Entity
@Table (name="CScanCenter")
public class ScanCenter implements Serializable {
	
	private String no;
	
	private String name;

	public ScanCenter () {
		
	}
	
	public ScanCenter (String no) {
		this.no = no;
	}
	
	public ScanCenter (String no, String name) {
		this.no = no;
		this.name = name;
	}
	
	public boolean equals (Object right) {
		boolean retval = false;
		ScanCenter rightSC = (ScanCenter) right;
		retval = getNo().equals(rightSC.getNo());
		return retval;
	}
	
	public int hashCode () {
		int res = HashCodeUtil.SEED;
		res = HashCodeUtil.hash (res, getNo()) ;
		return res;
	}
	
	@Id
	@Column (name="ScanCenterNo", length=10)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}
	
	@Column (name="ScanCenterName", length=100)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
