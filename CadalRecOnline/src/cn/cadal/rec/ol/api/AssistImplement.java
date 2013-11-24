package cn.cadal.rec.ol.api;

import java.util.ArrayList;
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
		System.out.println("AssistEditRecByDateType:" + date + "  " + type);
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
		System.out.println("AssistEditRecByDate:" + date);
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
		System.out.println("AssistGetBooksBySingleTag:" + tag);
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
		System.out.println("AssistGetBooksByListTag:" + listTags.toString());
		return this.bookInfo.ObtainListBookInfoByListID(this.algoTags.RecBook(listTags));
	}
	
	/**
	 * Main function
	 */
	public static void main(String[] args) {
		// Analyze and Test
		AssistImplement aa = new AssistImplement();

		String date = "2013-09-02";
		String type = "0";
		
		List<Book> listRes = aa.AssistEditRecByDateType(date, type);
		
		System.out.println(listRes.size());
		
		for(int i = 0; i < listRes.size(); ++i) {
			System.out.println("-----------");
			System.out.println(listRes.get(i).getBookId());
			System.out.println(listRes.get(i).getBookTitle());
			System.out.println(listRes.get(i).getBookAuthor());
			System.out.println(listRes.get(i).getBookPublisher());
			System.out.println(listRes.get(i).getBookType());
		}

//		List<String> listStr = new ArrayList<String>();
//		listStr.add("民国");
//		
//		List<Book> listBook = aa.AssistGetBooksByListTag(listStr);
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
