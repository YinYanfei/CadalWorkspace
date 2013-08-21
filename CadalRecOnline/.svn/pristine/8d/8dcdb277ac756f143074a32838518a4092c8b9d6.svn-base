package cn.cadal.rec.ol.qa;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import cn.cadal.rec.ol.common.Book;
import cn.cadal.rec.ol.common.BookInfo;
import cn.cadal.rec.ol.dao.DBAgent;
import cn.cadal.rec.ol.edit.RecEditOp;

public class GuaranteeNum {
	
	private int EDIT_TYPE = 1;
	private String DBName = "cadalrectest-76";
	private DBAgent db = null;
	private RecEditOp recEditOp = null;
	
	/**
	 * Construct functions
	 */
	public GuaranteeNum() {
		this.db = new DBAgent(this.DBName);
		
		this.recEditOp = new RecEditOp(this.DBName);
	}
	public GuaranteeNum(String dbname) {
		this.DBName = dbname;
		this.db = new DBAgent(dbname);
		
		this.recEditOp = new RecEditOp(dbname);
	}
	
	/**
	 * To insure the number of recommendation.
	 * 
	 * @param listBook
	 * 				list of book object
	 * @param count
	 * 				the count for ensure
	 */
	public List<Book> InsureCount(List<Book> listBook, int count){
		List<Book> listRes = new ArrayList<Book>();
		int realSize = listBook.size();
		
		if(realSize > count){
			listRes = this.MoreCount(listBook, count);
		}else if(realSize < count){
			listRes = this.LessCount(listBook, count);
		}else{
			for(Book book:listBook) {
				listRes.add(book);
			}
		}
		
		return listRes;
	}
	
	/**
	 * To random take out book in the list
	 * 
	 * @param listBook
	 * 				list of books
	 * @param count
	 * 				count
	 * @return
	 * 				result for recommendation
	 */
	private List<Book> MoreCount(List<Book> listBook, int count) {
		List<Book> listRes = new ArrayList<Book>();
		
		Random random = new Random();
		for (int i = 0; i < count; i++) {
			int randomNum = random.nextInt(count);
			listRes.add(listBook.get(randomNum));
			listBook.remove(randomNum);
		}
		
		return listRes;
	}
	
	/**
	 * To get more books and append recommendation result
	 * 
	 * @param listBook
	 * 				list of book object
	 * @param count
	 * 				count
	 * @return
	 * 				result of recommendation
	 */
	private List<Book> LessCount(List<Book> listBook, int count) {
		List<Book> listRes = new ArrayList<Book>();
		int less = count - listBook.size();
		
		for(Book book : listBook) {
			listRes.add(book);
		}

		// query from database
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		
		String edit_date = sf.format(date).toString();
		String edit_type = "common";
		
		List<String> listid = this.recEditOp.QueryByDateType(edit_date, edit_type);
		
		// to get book object
		BookInfo bookInfo = new BookInfo();
		List<Book> listTmp = bookInfo.ObtainListBookInfoByListID(listid);
		
		for(int i = 0; i < less; ++i) {
			listRes.add(listTmp.get(i));
		}
		
		return listRes;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test

	}
	
	/**
	 * Getter and Setter
	 */
	public int getEDIT_TYPE() {
		return EDIT_TYPE;
	}
	public String getDBName() {
		return DBName;
	}
	public DBAgent getDb() {
		return db;
	}
	public void setEDIT_TYPE(int eDITTYPE) {
		EDIT_TYPE = eDITTYPE;
	}
	public void setDBName(String dBName) {
		DBName = dBName;
	}
	public void setDb(DBAgent db) {
		this.db = db;
	}

}
