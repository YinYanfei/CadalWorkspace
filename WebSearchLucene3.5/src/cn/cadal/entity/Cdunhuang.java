package cn.cadal.entity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@DiscriminatorValue ("dunhuang")
public class Cdunhuang extends Cbook {

	public void accept(BookVisitor bv) {		
		bv.visit(this);
	}
	
}
