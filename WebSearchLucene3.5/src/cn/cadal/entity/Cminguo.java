package cn.cadal.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 *        @hibernate.class
 *         table="CMinguo"
 *     
 */

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@DiscriminatorValue ("minguo")
public class Cminguo  extends Cbook {
	public void accept(BookVisitor bv) {		
		bv.visit(this);
	}
}