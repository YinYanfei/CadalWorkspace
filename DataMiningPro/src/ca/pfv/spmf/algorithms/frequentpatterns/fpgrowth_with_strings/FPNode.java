package ca.pfv.spmf.algorithms.frequentpatterns.fpgrowth_with_strings;

import java.util.ArrayList;
import java.util.List;

/**
 * This is an implementation of a FPTree node.
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
public class FPNode {
	 // item id
	String itemID = null; 
	// support of that node
	int counter = 1;  
	
	// reference to the parent node or null if this is the root
	FPNode parent = null; 
	// references to the child(s) of that node if there is some
	List<FPNode> childs = new ArrayList<FPNode>();
	
	FPNode nodeLink = null; // link to next node with the same item id (for the header table).
	
	/**
	 * Default constructor
	 */
	FPNode(){
		
	}

	/**
	 * Return the immediate child of this node having a given ID.
	 * If there is no such child, return null;
	 */
	public FPNode getChildWithID(String id) {
		// for each child node
		for(FPNode child : childs){
			// if the id is the one that we are looking for
			if(child.itemID == id){
				// return that node
				return child;
			}
		}
		// if not found, return null
		return null;
	}

}