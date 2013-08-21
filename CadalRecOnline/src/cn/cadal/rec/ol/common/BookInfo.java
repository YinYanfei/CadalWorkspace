package cn.cadal.rec.ol.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.cadal.rec.ol.dao.DBAgent;
import cn.cadal.rec.ol.dao.SQLSet;

public class BookInfo {

	private String DBName = "cadalrectest";
	private DBAgent db = null;
	
	/**
	 * Construct function
	 */
	public BookInfo(){
		this.db = new DBAgent(this.DBName);
	}
	public BookInfo(String dbName){
		this.DBName = dbName;
		this.db = new DBAgent(dbName);
	}
	
	/**
	 * To extract the result of db-op
	 * 
	 * @param list
	 * 			list for map
	 * @return	list of book object
	 */
	@SuppressWarnings("unchecked")
	private List<Book> DealResList(List<Map> list) {
		List<Book> listBook = new ArrayList<Book>();
		
		for(int i = 0;i<list.size();++i){
			Book book = new Book();
			
			Map map = list.get(i);
			for(Iterator it = map.keySet().iterator();it.hasNext();){
				String columnName = it.next().toString();
				if(columnName.equals("book_id")){
					book.setBookId((String) map.get(columnName));
				}else if(columnName.equals("book_title")){
					book.setBookTitle((String)map.get(columnName));
				}else if(columnName.equals("book_author")) {
					book.setBookAuthor((String)map.get(columnName));
				}else if(columnName.equals("book_publisher")) {
					book.setBookPublisher((String)map.get(columnName));
				}else if(columnName.equals("book_type")) {
					book.setBookType((String)map.get(columnName));
				}
			}
			
			listBook.add(book);
		}
		
		return listBook;
	}
	
	/**
	 * To obtain information for a single book by the no of it
	 * 
	 * @param bookno
	 * 				no integer for book
	 * @return
	 * 				object book
	 */
	@SuppressWarnings("unchecked")
	public Book ObtainSingleBookInfoByNO(int bookno) {
		Object[] param = new Object[1];
		int[] type     = new int[1];
		
		param[0] = bookno;
		type[0]  = java.sql.Types.INTEGER;
		
		List<Map> list = db.executeQuery(SQLSet.QUERY_BOOK_INFO_BY_NO, param, type);
		
		List<Book> listBook = this.DealResList(list);
		
		return listBook.size() > 0?listBook.get(0):null;
	}
	
	/**
	 * To obtain information for a single book by the id of it
	 * 
	 * @param bookid
	 * 				id string for book
	 * @return
	 * 				object book
	 */
	@SuppressWarnings("unchecked")
	public Book ObtainSingleBookInfoByID(String bookid) {
		Object[] param = new Object[1];
		int[] type     = new int[1];
		
		param[0] = bookid;
		type[0]  = java.sql.Types.VARCHAR;
		
		List<Map> list = db.executeQuery(SQLSet.QUERY_BOOK_INFO_BY_ID, param, type);

		List<Book> listBook = this.DealResList(list);
		
		return listBook.size() > 0?listBook.get(0):null;
	}
	
	/**
	 * To obtain information for a list book by list number for books
	 * 
	 * @param listno
	 * 				list integer of book number
	 * @return
	 * 				list of book object
	 */
	public List<Book> ObtainListBookInfoByListNO(List<Integer> listno) {
		List<Book> listBook = new ArrayList<Book>();
		
		for(int i = 0; i < listno.size(); ++i) {
			Book book = this.ObtainSingleBookInfoByNO(listno.get(i));
			if(book != null) {
				listBook.add(book);
			}
		}
		
		return listBook;
	}
	
	/**
	 * To obtain information for a list book by list id for books
	 * 
	 * @param listid
	 * 				list string of book id
	 * @return
	 * 				list of book object
	 */
	public List<Book> ObtainListBookInfoByListID(List<String> listid) {
		List<Book> listBook = new ArrayList<Book>();
		
		for(int i = 0; i < listid.size(); ++i) {
			Book book = this.ObtainSingleBookInfoByID(listid.get(i));
			if(book != null) {
				listBook.add(book);
			}
		}
		
		return listBook;
	}
	
	// 可能还需要一些方法来处理几个特定属性的获取等工作,userinfo同样存在这样的需求
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test
//		String dbname = "cadalrectest";
//		
//		BookInfo bi = new BookInfo(dbname);
//		
		// ...
	}
	
	/**
	 * Getter and Setter
	 */
	public DBAgent getDb() {
		return db;
	}
	public void setDb(DBAgent db) {
		this.db = db;
	}
}
