/**
 * ֻʵ���˶�ͼ������һЩ�Ż����������ڱ�ǩ���û�����û�п���
 */
package cn.cadal.rec.ol.optimize;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.cadal.rec.ol.common.Book;
import cn.cadal.rec.ol.dao.DBAgent;
import cn.cadal.rec.ol.dao.SQLSet;

public class UserRelated {

	private String DBName = "cadalrectest-77";
	private DBAgent db = null;
	
	/**
	 * Construct function
	 */
	public UserRelated(){
		this.db = new DBAgent(this.DBName);
	}
	public UserRelated(String dbname){
		this.setDBName(dbname);
		this.db = new DBAgent(dbname);
	}
	
	/**
	 * To query U_READ_HISTORY and find all book which is readed by user
	 * 
	 * @param username
	 * 				user's name
	 * @return		list for book-id
	 */
	@SuppressWarnings("unchecked")
	public List<String> ObtainUserReadedBook(String username) {
		List<String> listBookId = new ArrayList<String>();
		
		Object[] param = new Object[1];
		int[] type     = new int[1];

		param[0] = username;
		type[0]  = java.sql.Types.VARCHAR;
		
		List<Map> list = db.executeQuery(SQLSet.QUERY_BOOKID_FROM_READ_HISTORY_BY_NAME, param, type);

		for(int i = 0;i<list.size();++i){
			Map map = list.get(i);
			
			for(Iterator it = map.keySet().iterator();it.hasNext();){
				String columnName = it.next().toString();
				if(columnName.equals("book_id")){
					listBookId.add(map.get(columnName).toString());
				}
			}
		}

		return listBookId;
	}

	/**
	 * To query U_READ_HISTORY and find all book which is readed by a list of user
	 * 
	 * @param username
	 * 				list of user's name
	 * @return		list for book-id
	 */
	public List<String> ObtainUserReadedBook(List<String> listUsername) {
		List<String> listBookId = new ArrayList<String>();
		
		for(int i = 0; i < listUsername.size(); ++i) {
			listBookId.addAll(this.ObtainUserReadedBook(listUsername.get(i)));
		}

		return listBookId;
	}
	
	/**
	 * To query U_READ_HISTORY and find user name who read book-id in parameter
	 * 
	 * @param bookid
	 * 				book id
	 * @return		list of user name
	 */
	@SuppressWarnings("unchecked")
	public List<String> ObtainUserNameByBookid(String bookid) {
		List<String> listUserName = new ArrayList<String>();
		
		Object[] param = new Object[1];
		int[] type     = new int[1];

		param[0] = bookid;
		type[0]  = java.sql.Types.VARCHAR;

		List<Map> list = db.executeQuery(SQLSet.QUERY_USERNAME_FROM_READ_HISTORY_BY_BOOK, param, type);
		
		for(int i = 0;i<list.size();++i){
			Map map = list.get(i);
			
			for(Iterator it = map.keySet().iterator();it.hasNext();){
				String columnName = it.next().toString();
				if(columnName.equals("user_name")){
					listUserName.add(map.get(columnName).toString());
				}
			}
		}
		
		return listUserName;
	}
	
	/**
	 * To query U_READ_HISTORY and find user name who read the list of book-id in parameter
	 * 
	 * @param listBookid
	 * 					list of book id
	 * @return			list of tag name
	 */
	public List<String> ObtainUserNameByListBookid(List<String> listBookid) {
		List<String> listUserName = new ArrayList<String>();
		
		for(String bookid : listBookid) {
			listUserName.addAll(this.ObtainUserNameByBookid(bookid));
		}
		
		return listUserName;
	}
	
	/**
	 * To check which book is readed by user and drop is from input
	 * 
	 * @param username
	 * 				user-name
	 * @param inBookList
	 * 				Source data for list of book
	 * @return		List of book which user does not read
	 */
	public List<Book> CheckBookByUserHistory(String username, List<Book> inBookList) {
		List<Book> listCheckedBook = new ArrayList<Book>();
		
		List<String> userReadBookId = this.ObtainUserReadedBook(username);
		
		for(Book book : inBookList) {
			if(!userReadBookId.contains(book.getBookId())){
				listCheckedBook.add(book);
			}
		}

		return listCheckedBook;
	}
	
	/**
	 * To get book information which user delete from recommendation list
	 * 
	 * @param username
	 * 				user-name
	 * @return		list of book for user deleted
	 */
	private List<String> ObtainUserDelBook(String username) {
		
		// �õ��û���ϲ�����Ƽ�ͼ�顾��ѯ���ݿ�:Ŀǰ�����ݿ���û�д洢�ⷽ������ݡ�
		
		return null;
	}
	
	/**
	 * To check which book is deleted by user and drop is from input
	 * 
	 * @param username
	 * @param inBookList
	 * @return
	 */
	public List<Book> CheckBookByUserDel(String username, List<Book> inBookList) {
		List<Book> listCheckedBook = new ArrayList<Book>();
		
		List<String> userDelBookId = this.ObtainUserDelBook(username);
		
		for(Book book : inBookList) {
			if(!userDelBookId.contains(book.getBookId())){
				listCheckedBook.add(book);
			}
		}
		
		return listCheckedBook;
	}
	
	/* ******************************************** **
	** ���ǿ��԰������溯����ģʽ�����������ļ��          **
	** ******************************************** */
	
	/**
	 * API for outer world
	 * 
	 * @param username
	 * 				user-name
	 * @param inBookList
	 * 				list of book for input
	 * @return Over checked for input list of book
	 */
	public List<Book> UserRelatedOpt(String username, List<Book> inBookList) {
		List<Book> recCheckResult = new ArrayList<Book>();
		
		// User read history
		recCheckResult = this.CheckBookByUserHistory(username, inBookList);
		
		// User delete operator on recommendation list
//		recCheckResult = this.CheckBookByUserDel(username, recCheckResult);
		
		// We can add more check depend user's behavior to optimize the result of recommendation
		
		return recCheckResult;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test
		
		
	}
	/**
	 * Setter and Getter
	 */
	public void setDBName(String dBName) {
		DBName = dBName;
	}
	public String getDBName() {
		return DBName;
	}

}