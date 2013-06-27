package ca.pfv.spmf.algorithms.sequentialpatterns.BIDE_and_prefixspan_with_strings;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * Implementation of a sequence.
 * A sequence is a list of itemsets.
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
public class SequentialPattern{
	public long shift = 0;
	
	private final List<Itemset> itemsets = new ArrayList<Itemset>();
	private int id; // id de la sequence
	
	// List of IDS of all patterns that contains this one.
	private Set<Integer> sequencesID = null;
	
	public SequentialPattern(int id){
		this.id = id;
	}
	
	public String getRelativeSupportFormated(int transactionCount) {
		double frequence = ((double)sequencesID.size()) / ((double) transactionCount);
		// pretty formating :
		DecimalFormat format = new DecimalFormat();
		format.setMinimumFractionDigits(0); 
		format.setMaximumFractionDigits(5); 
		return format.format(frequence);
	}
	
	public int getAbsoluteSupport(){
		return sequencesID.size();
	}

	public void addItemset(Itemset itemset) {
		itemsets.add(itemset);
	}
	
	public SequentialPattern cloneSequence(){
		SequentialPattern sequence = new SequentialPattern(getId());
		for(Itemset itemset : itemsets){
			sequence.addItemset(itemset.cloneItemSet());
		}
		return sequence;
	}

	public void print() {
		System.out.print(toString());
	}
	
	public String toString() {
		StringBuffer r = new StringBuffer("");
		for(Itemset itemset : itemsets){
			r.append('(');
			for(String item : itemset.getItems()){
				String string = item.toString();
				r.append(string);
				r.append(' ');
			}
			r.append(')');
		}

		//  print the list of Pattern IDs that contains this pattern.
		if(getSequencesID() != null){
			r.append("  Sequence ID: ");
			for(Integer id : getSequencesID()){
				r.append(id);
				r.append(' ');
			}
		}
		return r.append("    ").toString();
	}
	
	public String itemsetsToString() {
		StringBuffer r = new StringBuffer("");
		for(Itemset itemset : itemsets){
			for(String item : itemset.getItems()){
				String string = item.toString();
				r.append(string);
				r.append(' ');
			}
			r.append('}');
		}
		return r.append("    ").toString();
	}
	
	public int getId() {
		return id;
	}

	public List<Itemset> getItemsets() {
		return itemsets;
	}
	
	public Itemset get(int index) {
		return itemsets.get(index);
	}
	
	// new : nov. 2009.
	public String getIthItem(int i) { 
		for(int j=0; j< itemsets.size(); j++){
			if(i < itemsets.get(j).size()){
				return itemsets.get(j).get(i);
			}
			i = i- itemsets.get(j).size();
		}
		return null;
	}
	
	public int size(){
		return itemsets.size();
	}

	public Set<Integer> getSequencesID() {
		return sequencesID;
	}

	public void setSequencesID(Set<Integer> sequencesID) {
		this.sequencesID = sequencesID;
	}
	
	/**
	 * Return the sum of the size of all itemsets of this sequence.
	 */
	public int getItemOccurencesTotalCount(){
		int count =0;
		for(Itemset itemset : itemsets){
			count += itemset.size();
		}
		return count;
	}


	public SequentialPattern cloneSequenceMinusItems(Map<String, Set<Integer>> mapSequenceID, double relativeMinSup) {
		SequentialPattern sequence = new SequentialPattern(getId());
		for(Itemset itemset : itemsets){
			Itemset newItemset = itemset.cloneItemSetMinusItems(mapSequenceID, relativeMinSup);
			if(newItemset.size() !=0){ 
				sequence.addItemset(newItemset);
			}
		}
		return sequence;
	}

	public void setID(int id2) {
		id = id2;
	}

}
