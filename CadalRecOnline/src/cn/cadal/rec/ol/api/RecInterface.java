package cn.cadal.rec.ol.api;

import java.util.List;

import cn.cadal.rec.ol.common.Book;
import cn.cadal.rec.ol.common.User;

/**
 * Interface for RecAlgos
 * 
 * @author Yanfei
 * 
 * UserTags is not BookTags in this class, UserTags is like user interest.
 */
public interface RecInterface {
	/**
	 * Recommendation for books
	 */
	public List<Book> RecBookByUserName(String userName);
	public List<Book> RecBookByBook(String bookid);
	public List<Book> RecBookByUserTags(List<String> listTags);
	
	/**
	 * Recommendation for tags
	 */
	public List<String> RecTagsByUserName(String userName);
	public List<String> RecTagsByBook(String bookid);
	public List<String> RecTagsByUserTags(List<String> listTags);

	/**
	 * Recommendation for users
	 */
	public List<User> RecUserByUserName(String userName);
	public List<User> RecUserByBook(String bookid);
	public List<User> RecUserByUserTags(List<String> listTags);
	
	/****************************************************************
	** Other method may be added in future work                    **
	****************************************************************/
	
}
