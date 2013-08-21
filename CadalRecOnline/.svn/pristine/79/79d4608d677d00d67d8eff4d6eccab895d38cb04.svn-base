package cn.cadal.rec.ol.thrift;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import cn.cadal.rec.ol.api.AssistImplement;
import cn.cadal.rec.ol.api.AssistInterface;
import cn.cadal.rec.ol.api.RecImplement;
import cn.cadal.rec.ol.api.RecInterface;

public class RecAPIImp implements RecAPI.Iface {
	
	private String DBName = "cadalrectest-77";
	private RecInterface recInterface = null;
	private AssistInterface assistInterface = null;
	
	/**
	 * Construct functions
	 */
	public RecAPIImp() {
		this.recInterface = new RecImplement(this.DBName);
		this.assistInterface = new AssistImplement(this.DBName);
	}
	public RecAPIImp(String dbname) {
		this.DBName = dbname;
		this.recInterface = new RecImplement(dbname);
		this.assistInterface = new AssistImplement(dbname);
	}
	
	/**
	 * API - For functions
	 * 
	 * 注意：
	 *     这里的Book和User对象以及相应的方法名称和cn.cadal.rec.ol.common里面是一样的，所以记得进行区分
	 *     相同的方法而分开写的目的是为了前后端的耦合度更低，便于以后的更改
	 */
	@Override
	public List<Book> RecBookByBook(String bookid) throws TException {
		System.out.println("bookid: " + bookid);
		List<cn.cadal.rec.ol.common.Book> listBookCom = this.recInterface.RecBookByBook(bookid);
		return this.DealBookObject(listBookCom);
	}

	@Override
	public List<Book> RecBookByUserName(String userName) throws TException {
		List<cn.cadal.rec.ol.common.Book> listBookCom = this.recInterface.RecBookByUserName(userName);
		return this.DealBookObject(listBookCom);
	}

	@Override
	public List<Book> RecBookByUserTags(List<String> listTags) throws TException {
		List<cn.cadal.rec.ol.common.Book> listBookCom = this.recInterface.RecBookByUserTags(listTags);
		return this.DealBookObject(listBookCom);
	}

	@Override
	public List<String> RecTagsByBook(String bookid) throws TException {
		return this.recInterface.RecTagsByBook(bookid);
	}

	@Override
	public List<String> RecTagsByUserName(String userName) throws TException {
		return this.recInterface.RecTagsByUserName(userName);
	}

	@Override
	public List<String> RecTagsByUserTags(List<String> listTags) throws TException {
		return this.recInterface.RecTagsByUserTags(listTags);
	}

	@Override
	public List<User> RecUserByBook(String bookid) throws TException {
		List<cn.cadal.rec.ol.common.User> listUserCom = this.recInterface.RecUserByBook(bookid);
		return this.DealUserObject(listUserCom);
	}
	
	@Override
	public List<User> RecUserByUserName(String userName) throws TException {
		List<cn.cadal.rec.ol.common.User> listUserCom = this.recInterface.RecUserByUserName(userName);
		return this.DealUserObject(listUserCom);
	}

	@Override
	public List<User> RecUserByUserTags(List<String> listTags) throws TException {
		List<cn.cadal.rec.ol.common.User> listUserCom = this.recInterface.RecUserByUserTags(listTags);
		return this.DealUserObject(listUserCom);
	}
	
	// API for assist functions
	@Override
	public List<Book> AssistEditRecByDate(String date) throws TException {
		List<cn.cadal.rec.ol.common.Book> listBookCom = this.assistInterface.AssistEditRecByDate(date);
		return this.DealBookObject(listBookCom);
	}
	
	@Override
	public List<Book> AssistEditRecByDateType(String date, String type) throws TException {
		List<cn.cadal.rec.ol.common.Book> listBookCom = this.assistInterface.AssistEditRecByDateType(date, type);
		return this.DealBookObject(listBookCom);
	}
	
	@Override
	public List<Book> AssistGetBooksByListTag(List<String> listTags) throws TException {
		List<cn.cadal.rec.ol.common.Book> listBookCom = this.assistInterface.AssistGetBooksByListTag(listTags);
		return this.DealBookObject(listBookCom);
	}
	
	@Override
	public List<Book> AssistGetBooksBySingleTag(String tag) throws TException {
		List<cn.cadal.rec.ol.common.Book> listBookCom = this.assistInterface.AssistGetBooksBySingleTag(tag);
		return this.DealBookObject(listBookCom);
	}
	
	// Transfer cn.cadal.rec.ol.common.Book to Book in this directory
	private List<Book> DealBookObject(List<cn.cadal.rec.ol.common.Book> listBookCom) {
		List<Book> listBookRes = new ArrayList<Book>();
		
		for(int i = 0; i < listBookCom.size(); ++i) {
			Book book = new Book();
			
			book.setBookId(listBookCom.get(i).getBookId());
			book.setBookTitle(listBookCom.get(i).getBookTitle());
			book.setBookAuthor(listBookCom.get(i).getBookAuthor());
			book.setBookPublisher(listBookCom.get(i).getBookPublisher());
			book.setBookType(listBookCom.get(i).getBookType());
			
			listBookRes.add(book);
		}
		
		return listBookRes;

	}
	
	// Transfer cn.cadal.rec.ol.common.Book to Book in this directory
	private List<User> DealUserObject(List<cn.cadal.rec.ol.common.User> listUserCom) {
		List<User> listUserRes = new ArrayList<User>();
		
		for(int i =0; i < listUserCom.size(); ++i) {
			User user = new User();
			
			user.setUserId(listUserCom.get(i).getUserId());
			user.setUserName(listUserCom.get(i).getUserName());
			user.setUserHometown(listUserCom.get(i).getUserHometown());
			user.setUserSchool(listUserCom.get(i).getUserSchool());
			user.setUserGender(listUserCom.get(i).getUserGender());
			user.setUserEmail(listUserCom.get(i).getUserEmail());
			user.setUserRegisterTime(listUserCom.get(i).getUserRegisterTime());
			if(listUserCom.get(i).getUserBirthday() != null){			// UserBirthday
				user.setUserBirthday(listUserCom.get(i).getUserBirthday().toString());
			}else{
				user.setUserBirthday("");
			}
			
			listUserRes.add(user);
		}
		
		return listUserRes;
	}
	
	/**
	 * Main function
	 */
	public static void main(String [] args) throws Exception {
		// Analyze and Test
//		RecAPIImp rai = new RecAPIImp();
//
	}


}
