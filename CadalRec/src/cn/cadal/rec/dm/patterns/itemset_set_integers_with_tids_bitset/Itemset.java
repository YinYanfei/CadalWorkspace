package cn.cadal.rec.dm.patterns.itemset_set_integers_with_tids_bitset;

import java.util.BitSet;
import java.util.Set;

import cn.cadal.rec.dm.patterns.AbstractItemset;


/**
 * This class represents an itemset.
 *   - items are integers
 *   - the itemset a set of integers 
 *   - the transactions/sequences ids (tids) containing this itemset are represented
 * with a bitset.
 * 
 * Copyright (c) 2008-2012 Philippe Fournier-Viger
 * 
 * This file is part of the SPMF DATA MINING SOFTWARE
 * (http://www.philippe-fournier-viger.com/spmf).
 *
 * SPMF is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * SPMF is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with SPMF.  If not, see <http://www.gnu.org/licenses/>.
 */
public class Itemset extends AbstractItemset{
	 public Set<Integer> itemset; // the items
	 public int cardinality; // the number of transactions/sequences containing this itemset
	 public BitSet tidset; // the list of transaction/sequence ids containing this itemset
	 
	 /**
	  * Get this itemset as a string.
	  */
	public String toString() {
		StringBuffer r = new StringBuffer();
		for (Integer attribute : itemset) {

			r.append(attribute.toString());
			
			r.append(' ');
		}
		return r.toString();
	}
	
	/**
	 * Get the relative support of this itemset
	 * @param nbObject  the number of transactions/sequences in the database where the itemset was found
	 * @return the relative support as a double (percentage)
	 */
	public double getRelativeSupport(int nbObject) {
		return ((double) cardinality) / ((double) nbObject);
	}

	/**
	 * Get the support of this itemset
	 * @return the support of this itemset
	 */
	public int getAbsoluteSupport() {
		return cardinality;
	}

	/**
	 * Get the number of items in this itemset
	 * @return the size of this itemset
	 */
	public int size() {
		return itemset.size();
	}

	/** 
	 * Check if this itemset contains a given item
	 * @param item the given item
	 * @return true if contained
	 */
	public boolean contains(Integer item) {
		return itemset.contains(item);
	}

}
