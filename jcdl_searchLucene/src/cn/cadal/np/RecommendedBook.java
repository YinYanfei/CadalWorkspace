package cn.cadal.np;

import java.lang.Comparable;

public class RecommendedBook implements java.io.Serializable,Comparable<RecommendedBook>{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2337545559438385850L;
	private String bookID;
	private float  weight;
	private float   support;
	private float   confidence;
	
    public RecommendedBook() 
    {
    	bookID="";
    	weight = 0;
    	support = 0;
    	confidence = 0;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public float getConfidence() {
		return confidence;
	}

	public void setConfidence(float confidence) {
		this.confidence = confidence;
	}

	public float getSupport() {
		return support;
	}

	public void setSupport(float support) {
		this.support = support;
	}
	public int compareTo(RecommendedBook rBook) {
	// TODO Auto-generated method stub
	if (rBook.getWeight()>this.weight)
		return 1;
	else if(rBook.getWeight()<this.weight)return -1;
	return 0;
}

    
}
