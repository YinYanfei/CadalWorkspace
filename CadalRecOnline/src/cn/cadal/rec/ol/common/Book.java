package cn.cadal.rec.ol.common;

public class Book {
	
	private String bookId;
	private String bookTitle;
	private String bookPublisher;
	private String bookAuthor;
	private String bookType;

	public Book(){}
	
	public Book(String bookId, String bookTitle, String bookPublisher, String bookAuthor, String bookType) {
		this.bookTitle = bookTitle;
		this.bookId = bookId;
		this.bookPublisher = bookPublisher;
		this.bookAuthor = bookAuthor;
		this.bookType = bookType;
	}
	
	/**
	 * Getter and Setter
	 */
	public String getBookId() {
		return bookId;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public String getBookPublisher() {
		return bookPublisher;
	}

	public String getBookAuthor() {
		return bookAuthor;
	}

	public String getBookType() {
		return bookType;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public void setBookPublisher(String bookPublisher) {
		this.bookPublisher = bookPublisher;
	}

	public void setBookAuthor(String bookAuthor) {
		this.bookAuthor = bookAuthor;
	}

	public void setBookType(String bookType) {
		this.bookType = bookType;
	}
	
}
