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
public class ViewResult {
	private String title = "";
	private int pageNo = 0;
	private String summary = "";
	private String bookNo = "";
	private String pageUrl = "";
	
	/**
	 * @return Returns the pageNo.
	 */
	public int getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo The pageNo to set.
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	/**
	 * @return Returns the summary.
	 */
	public String getSummary() {
		return summary;
	}
	/**
	 * @param summary The summary to set.
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	/**
	 * @return Returns the title.
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title The title to set.
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * @return Returns the pageUrl.
	 */
	public String getPageUrl() {
		return pageUrl;
	}
	/**
	 * @param pageUrl The pageUrl to set.
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	public String getBookNo() {
		return bookNo;
	}
	public void setBookNo(String bookNo) {
		this.bookNo = bookNo;
	}
}
