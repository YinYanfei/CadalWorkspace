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
public class SearchResult {
	private String bookID;
	private String pageNo;
	private String content;
	private String title;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * @return Returns the bookID.
	 */
	public String getBookID() {
		return bookID;
	}
	/**
	 * @param bookID The bookID to set.
	 */
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	/**
	 * @return Returns the pageNo.
	 */
	public String getPageNo() {
		return pageNo;
	}
	/**
	 * @param pageNo The pageNo to set.
	 */
	public void setPageNo(String pageNo) {
		this.pageNo = pageNo;
	}
	

}
