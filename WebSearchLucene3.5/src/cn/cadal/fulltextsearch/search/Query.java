/*
 * Created on 2006-3-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.cadal.fulltextsearch.search;

/**
 * @author lwm
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Query {
	public String q="";
	public int start = 0;
	public int pageSize = 10;
	public float timeUsed = (float)0.0;
	public int hitSize = 0;
	
	public Query(){
		
	}
	
	public Query(String q,int start,int num)
	{
		this.q = q;
		this.start = start;
		this.pageSize = num;
	}

	public int getHitSize() {
		return hitSize;
	}

	public void setHitSize(int hitSize) {
		this.hitSize = hitSize;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public float getTimeUsed() {
		return timeUsed;
	}

	public void setTimeUsed(float timeUsed) {
		this.timeUsed = timeUsed;
	}
	
	
	
}
