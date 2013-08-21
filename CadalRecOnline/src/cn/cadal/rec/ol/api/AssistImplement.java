package cn.cadal.rec.ol.api;

import java.util.List;

import cn.cadal.rec.ol.algo.AlgoTags;
import cn.cadal.rec.ol.common.Book;
import cn.cadal.rec.ol.common.BookInfo;
import cn.cadal.rec.ol.edit.RecEditOp;

public class AssistImplement implements AssistInterface {

	private String DBName = "cadalrectest-76";
	
	private RecEditOp recEditOp = null;
	private BookInfo bookInfo   = null;
	private AlgoTags algoTags   = null;
	
	/**
	 * Construct functions
	 */
	public AssistImplement(){
		this.recEditOp = new RecEditOp(this.DBName);
		this.bookInfo  = new BookInfo(this.DBName);
		this.algoTags  = new AlgoTags(this.DBName);
	}
	public AssistImplement(String dbname) {
		this.DBName = dbname;
		
		this.recEditOp = new RecEditOp(dbname);
		this.bookInfo = new BookInfo(dbname);
		this.algoTags = new AlgoTags(dbname);
	}
	
	/**
	 * To get recommendation of edit by date and type
	 * 
	 * @param date
	 * 				date parameter, String, '2013-08-10'
	 * @param type
	 * 				type parameter, String
	 * @return		list of Book object
	 */
	public List<Book> AssistEditRecByDateType(String date, String type) {
		return this.bookInfo.ObtainListBookInfoByListID(this.recEditOp.QueryByDateType(date, type));
	}
	
	/**
	 * To get recommendation of edit by date
	 * 
	 * @param date
	 * 				date parameter, String, '2013-08-10'
	 * @return		list of Book object
	 */
	public List<Book> AssistEditRecByDate(String date) {
		return this.bookInfo.ObtainListBookInfoByListID(this.recEditOp.QueryByDate(date));
	}
	
	/**
	 * To get list of book by a single tag
	 * 
	 * @param tag
	 * 				tag name, string, '民国'
	 * @return		list of Book object
	 */
	public List<Book> AssistGetBooksBySingleTag(String tag){
		return this.bookInfo.ObtainListBookInfoByListID(this.algoTags.RecBook(tag));
	}
	
	/**
	 * To get list of book by a list of tags
	 * 
	 * @param listTags
	 * 				list of tags, list
	 * @return		list of book object
	 */
	public List<Book> AssistGetBooksByListTag(List<String> listTags) {
		return this.bookInfo.ObtainListBookInfoByListID(this.algoTags.RecBook(listTags));
	}
	
	/**
	 * Main function
	 */
	public static void main(String[] args) {
		// Analyze and Test
//		AssistImplement aa = new AssistImplement();
//		
//		List<String> listStr = new ArrayList<String>();
//		listStr.add("四年");
//		listStr.add("二十四年");
//		
//		List<Book> listBook = aa.AlgoGetBooksByListTag(listStr);
//		
//		System.out.println(listBook.size());
	}
	
	/**
	 * Getter and Setter
	 */
	public String getDBName() {
		return DBName;
	}
	public RecEditOp getRecEditOp() {
		return recEditOp;
	}
	public BookInfo getBookInfo() {
		return bookInfo;
	}
	public AlgoTags getAlgoTags() {
		return algoTags;
	}
	public void setDBName(String dBName) {
		DBName = dBName;
	}
	public void setRecEditOp(RecEditOp recEditOp) {
		this.recEditOp = recEditOp;
	}
	public void setBookInfo(BookInfo bookInfo) {
		this.bookInfo = bookInfo;
	}
	public void setAlgoTags(AlgoTags algoTags) {
		this.algoTags = algoTags;
	}

}
