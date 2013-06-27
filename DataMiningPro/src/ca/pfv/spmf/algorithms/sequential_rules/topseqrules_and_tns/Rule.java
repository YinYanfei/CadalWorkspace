package ca.pfv.spmf.algorithms.sequential_rules.topseqrules_and_tns;

import java.util.Map;
import java.util.Set;


/**
 * This class is for representing a sequential rule.
 * @author Philippe Fournier-Viger, 2009
 */
public class Rule implements Comparable<Rule>{
	
	private int[] itemset1; // antecedent
	private int[] itemset2; // consequent
	public int transactioncount; // absolute support
	Set<Integer> tidsI;
	Set<Integer> tidsJ;
	Set<Integer> tidsIJ;
	Map<Integer, Short> occurencesIfirst;
	Map<Integer, Short> occurencesJlast;
	public boolean expandLR = false;

	private double confidence;
	
	public Rule(int[] itemset1, int[] itemset2, double confidence, int transactioncount, 
			Set<Integer> tidsI, Set<Integer> tidsJ, Set<Integer> tidsIJ, 
			Map<Integer, Short> occurencesIfirst,
			Map<Integer, Short> occurencesJlast){
		this.itemset1 = itemset1;
		this.itemset2 = itemset2;
		this.confidence = confidence;
		this.transactioncount = transactioncount;
		this.tidsI = tidsI;
		this.tidsJ = tidsJ;
		this.tidsIJ = tidsIJ;
		this.occurencesJlast = occurencesJlast;
		this.occurencesIfirst = occurencesIfirst;
	}

	public int[] getItemset1() {
		return itemset1;
	}

	public int[] getItemset2() {
		return itemset2;
	}
	
	public int getAbsoluteSupport(){
		return transactioncount;
	}
	
	public double getRelativeSupport(int sequencecount) {
		return ((double)transactioncount) / ((double) sequencecount);
	}

	public void print(){
		System.out.println(toString());
	}
	
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i< itemset1.length; i++){
			buffer.append(itemset1[i]);
			if(i != itemset1.length-1){
				buffer.append(",");
			}
		}
		buffer.append(" ==> ");
		for(int i=0; i< itemset2.length; i++){
			buffer.append(itemset2[i]);
			if(i != itemset2.length-1){
				buffer.append(",");
			}
		}
		return buffer.toString();
	}

	public double getConfidence() {
		return confidence;
	}
	
	public int compareTo(Rule o) {
		if(o == this){
			return 0;
		}
		int compare = this.getAbsoluteSupport() - o.getAbsoluteSupport();
		if(compare !=0){
			return compare;
		}
		
		int itemset1sizeA = this.itemset1 == null ? 0 : this.itemset1.length;
		int itemset1sizeB = o.itemset1 == null ? 0 : o.itemset1.length;
		int compare2 = itemset1sizeA - itemset1sizeB;
		if(compare2 !=0){
			return compare2;
		}
		
		int itemset2sizeA = this.itemset2 == null ? 0 : this.itemset2.length;
		int itemset2sizeB = o.itemset2 == null ? 0 : o.itemset2.length;
		int compare3 = itemset2sizeA - itemset2sizeB;
		if(compare3 !=0){
			return compare3;
		}
		
		int compare4 = (int)(this.confidence  - o.confidence);
		if(compare !=0){
			return compare4;
		}

		return this.hashCode() - o.hashCode();
	}
	
	public boolean equals(Object o){
		Rule ruleX = (Rule)o;
		if(ruleX.itemset1.length != this.itemset1.length){
			return false;
		}
		if(ruleX.itemset2.length != this.itemset2.length){
			return false;
		}
		for(int i=0; i< itemset1.length; i++){
			if(this.itemset1[i] != ruleX.itemset1[i]){
				return false;
			}
		}
		for(int i=0; i< itemset2.length; i++){
			if(this.itemset2[i] != ruleX.itemset2[i]){
				return false;
			}
		}
		
		return true;
	}
}
