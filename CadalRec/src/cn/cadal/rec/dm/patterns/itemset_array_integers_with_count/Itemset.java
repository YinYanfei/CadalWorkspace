package cn.cadal.rec.dm.patterns.itemset_array_integers_with_count;
import cn.cadal.rec.dm.patterns.AbstractOrderedItemset;

/**
 * This class represents an itemset (a set of items). 
 * Items are represented by integers and stored in an array.
 * A variable is used to store the support count of the itemset.
 * 
 * Copyright (c) 2008-2012 Philippe Fournier-Viger
 * 
 * This file is part of the SPMF DATA MINING SOFTWARE
 * (http://www.philippe-fournier-viger.com/spmf).
 * 
 * SPMF is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * SPMF is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * SPMF. If not, see <http://www.gnu.org/licenses/>.
 */
public class Itemset extends AbstractOrderedItemset{
	public int[] itemset; // the array of items

	public int support = 0; // the support of this itemset
	
	/**
	 * Get the items as array
	 * @return the items
	 */
	public int[] getItems() {
		return itemset;
	}
	
	/**
	 * Constructor
	 */
	public Itemset(){
		itemset = new int[]{};
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
	 */
	public int getAbsoluteSupport(){
		return support;
	}
	
	/**
	 * Get the size of this itemset 
	 */
	public int size() {
		return itemset.length;
	}

	/**
	 * Get the item at a given position in this itemset
	 */
	public Integer get(int position) {
		return itemset[position];
	}

	/**
	 * Set the support of this itemset
	 * @param support the support
	 */
	public void setAbsoluteSupport(Integer support) {
		this.support = support;
	}

	/**
	 * Increase the support of this itemset by 1
	 */
	public void increaseTransactionCount() {
		this.support++;
	}


	/**
	 * Make a copy of this itemset but exclude a given item
	 * @param itemsetToRemove the given item
	 * @return the copy
	 */
	public Itemset cloneItemSetMinusOneItem(Integer itemsetToRemove) {
		// create the new itemset
		int[] newItemset = new int[itemset.length -1];
		int i=0;
		// for each item in this itemset
		for(int j =0; j < itemset.length; j++){
			// copy the item except if it is the item that should be excluded
			if(itemset[j] != itemsetToRemove){
				newItemset[i++] = itemset[j];
			}
		}
		return new Itemset(newItemset); // return the copy
	}

	/**
	 * Make a copy of this itemset but exclude a set of items
	 * @param itemsetToNotKeep the set of items to be excluded
	 * @return the copy
	 */
	public Itemset cloneItemSetMinusAnItemset(Itemset itemsetToNotKeep) {
		// create a new itemset
		int[] newItemset = new int[itemset.length - itemsetToNotKeep.size()];
		int i=0;
		// for each item of this itemset
		for(int j =0; j < itemset.length; j++){
			// copy the item except if it is not an item that should be excluded
			if(itemsetToNotKeep.contains(itemset[j]) == false){
				newItemset[i++] = itemset[j];
			}
		}
		return new Itemset(newItemset); // return the copy
	}

}
