package cn.cadal.entity;
// Generated 2006-4-10 20:48:26 by Hibernate Tools 3.1.0 beta1JBIDERC2

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 *        @hibernate.class
 *         table="CDissertation"
 *     
 */

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@DiscriminatorValue ("dissertation")
public class Cdissertation extends Cbook {
	
	public void accept(BookVisitor bv) {		
		bv.visit(this);
	}
}
