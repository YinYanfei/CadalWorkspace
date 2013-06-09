package cn.cadal.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

// Generated 2006-4-10 20:48:29 by Hibernate Tools 3.1.0 beta1JBIDERC2



/**
 *        @hibernate.class
 *         table="CRules"
 *     
 */
@Entity
@Table (name="CRules")
public class Crule  implements java.io.Serializable {


    // Fields    

     /**
      *            @hibernate.id
 *             generator-class="assigned"
 *             type="java.lang.Integer"
 *             column="RuleID"
 *         
     */
     private Integer ruleId;
     /**
      *            @hibernate.property
 *             column="Operator"
 *             length="10"
 *         
     */
     private String operator;
     /**
      *            @hibernate.property
 *             column="SchemaField"
 *             length="100"
 *         
     */
     private String schemaField;
     /**
      *            @hibernate.property
 *             column="QueryWord"
 *             length="100"
 *         
     */
     private String queryWord;
     
     private int ruleIdx;

     private Cbinder binder;

    // Constructors
    @ManyToOne ()
    @JoinColumn (name="BinderID")
    public Cbinder getBinder() {
		return binder;
	}


	public void setBinder(Cbinder binder) {
		this.binder = binder;
	}


	/** default constructor */
    public Crule() {
    }

    
    /** full constructor */
    public Crule(String operator, String schemaField, String queryWord) {
        this.operator = operator;
        this.schemaField = schemaField;
        this.queryWord = queryWord;
    }
    

   
    // Property accessors
    /**       
     *      *            @hibernate.id
     *             generator-class="assigned"
     *             type="java.lang.Integer"
     *             column="RuleID"
     *         
     */
    @Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	@Column (name="RuleID")
    public Integer getRuleId() {
        return this.ruleId;
    }
    
    public void setRuleId(Integer ruleId) {
        this.ruleId = ruleId;
    }
    /**       
     *      *            @hibernate.property
     *             column="Operator"
     *             length="10"
     *         
     */
    @Column (name="Operator",length=10)
    public String getOperator() {
        return this.operator;
    }
    
    public void setOperator(String operator) {
        this.operator = operator;
    }
    /**       
     *      *            @hibernate.property
     *             column="SchemaField"
     *             length="100"
     *         
     */
    @Column (name="SchemaField", length=100)
    public String getSchemaField() {
        return this.schemaField;
    }
    
    public void setSchemaField(String schemaField) {
        this.schemaField = schemaField;
    }
    /**       
     *      *            @hibernate.property
     *             column="QueryWord"
     *             length="100"
     *         
     */
    @Column (name="QueryWord", length=100)
    public String getQueryWord() {
        return this.queryWord;
    }
    
    public void setQueryWord(String queryWord) {
        this.queryWord = queryWord;
    }

    @Column (name="RuleIdx")
	public int getRuleIdx() {
		return ruleIdx;
	}


	public void setRuleIdx(int ruleIdx) {
		this.ruleIdx = ruleIdx;
	}

}
