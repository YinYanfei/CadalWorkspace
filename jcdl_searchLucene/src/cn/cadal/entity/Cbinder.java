package cn.cadal.entity;

// Generated 2006-4-10 20:48:26 by Hibernate Tools 3.1.0 beta1JBIDERC2

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

/**
 *       @hibernate.class
 *       table="CBinders"
 *     
 */
@Entity
@Table(name = "CBinders")
public class Cbinder implements java.io.Serializable {

	private Integer binderId;

	private String binderName;

	private String repository;
	
	private List<Crule> rules = new ArrayList<Crule>();
	
	private Set<UserBinder> userBinders = new HashSet<UserBinder> ();

	// Constructors
	@OneToMany (mappedBy="bindBinder")
	public  Set<UserBinder> getUserBinders() {
		return userBinders;
	}

	public void setUserBinders(Set<UserBinder> userBinders) {
		this.userBinders = userBinders;
	}

	/** default constructor */
	public Cbinder() {
	}

	/** full constructor */
	public Cbinder(String binderName, String repository, List<Crule> rules) {
		this.binderName = binderName;
		this.repository = repository;
		this.rules = rules;
	}

	// Property accessors
	/**       
	 *      *         @hibernate.id
	 *         generator-class="native"
	 *         type="java.lang.Integer"
	 *         column="BinderID"
	 *       
	 */
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column (name="BinderID")
	public Integer getBinderId() {
		return this.binderId;
	}

	public void setBinderId(Integer binderId) {
		this.binderId = binderId;
	}

	/**       
	 *      *         @hibernate.property
	 *         column="BinderName"
	 *         length="50"
	 *       
	 */
	@Column (name="BinderName", length=100)
	public String getBinderName() {
		return this.binderName;
	}

	public void setBinderName(String binderName) {
		this.binderName = binderName;
	}

	/**       
	 *      *       
	 */
	@Column (name="Repository", length=100)
	public String getRepository() {
		return this.repository;
	}

	public void setRepository(String repository) {
		this.repository = repository;
	}

	@OneToMany(mappedBy="binder",cascade = CascadeType.ALL)
	@OrderBy ("ruleIdx asc")
	public List<Crule> getRules() {
		return this.rules;
	}

	public void setRules(List<Crule> rules) {
		this.rules = rules;
	}

}
