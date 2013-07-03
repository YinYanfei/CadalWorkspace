package cn.cadal.rec.dm.patterns.itemset_array_integers_with_tids_bitset;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

import cn.cadal.rec.dm.patterns.AbstractMutableOrderedItemset;
import cn.cadal.rec.dm.patterns.AbstractOrderedItemset;


/**
 * This class represents an itemset (a set of items)
 *   - items are integers
 *   - the itemset is an array of integers sorted by lexical order
 *   - an item should not appear more than once in an itemset.
 *   - the ids of transactions/sequences containing this itemset is represented
 *     as a bitset.
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
public class Itemset extends AbstractOrderedItemset{
	
	// The list of items contained in this itemset, ordered by 
	// lexical order
	public int[] itemset; // the array of items
	
	// The list of transactions/sequences containing this itemset
	private BitSet transactionsIds = new BitSet();
	public int cardinality =0;  // the cardinality of the above bitset
	
	/**
	 * Constructor of an empty itemset
	 */
	public Itemset(){
		
	}
	
	/**
	 * Constructor 
	 * @param an item that should be added to the new itemset
	 */
	public Itemset(int item){
		itemset = new int[]{item};
	}
	
	/**
	 * Constructor 
	 * @param an array of items that should be added to the new itemset
	 */
	public Itemset(int [] items){
		this.itemset = items;
	}

	/**
	 * Get the support of this itemset
	 * @return the support of this itemset
	 */
	public int getAbsoluteSupport(){
		return cardinality;
	}

	/**
	 * Get the list of items in this itemset
	 * @return the list of items
	 */
	public int[] getItems(){
		return itemset;
	}
	
	/**
	 * Get the item at a given position of this itemset
	 * @param position the position of the item to be returned
	 * @return the item
	 */
	public Integer get(int index){
		return itemset[index];
	}

	/**
	 * Set the list of transaction/sequence ids containing this itemset.
	 * @param listTransactionIds the list of transaction/sequence ids.
	 * @param cardinality the cardinality of the list.
	 */
	public void setTIDs(BitSet listTransactionIds, int cardinality) {
		this.transactionsIds = listTransactionIds;
		this.cardinality = cardinality;
	}

	/**
	 * Get the size of this itemset.
	 */
	public int size(){
		return itemset.length;
	}

	/**
	 * Get the list of transactions/sequences containing this itemset.
	 * @return
	 */
	public BitSet getTransactionsIds() {
		return transactionsIds;
	}


}
