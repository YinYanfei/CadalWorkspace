package ca.pfv.spmf.algorithms.sequential_rules.trulegrowth_with_strings;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an itemset (a set of items)
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
public class Itemset{
	private final List<String> items = new ArrayList<String>(); // ordered
//	Set<Integer> transactionsIds = new HashSet<Integer>();
	
	public Itemset(){
	}
	
	public Itemset(String item){
		items.add(item);
	}

	public Itemset(Itemset itemset){
		items.addAll(itemset.getItems());
	}

	public void addItem(String value){
			items.add(value);
	}

	public List<String> getItems(){
		return items;
	}
	
	public String get(int index){
		return items.get(index);
	}
	
	public void print(){
		System.out.print(toString());
	}
	
	public String toString(){
		StringBuffer r = new StringBuffer ();
		for(String attribute : items){
			r.append(attribute);
			r.append(' ');
		}
		return r.toString();
	}

	
	public int size(){
		return items.size();
	}
}
