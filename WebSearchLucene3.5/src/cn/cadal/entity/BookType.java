package cn.cadal.entity;

public class BookType implements BookVisitor {
	String bookType = "book";
	
	public String getBookType() {
		return bookType;
	}
	
	public void visit(Cdunhuang obj) {
		bookType = "dunhuang";
	}

	public void visit (Cancient obj) {
		bookType = "ancient";
	}

	public void visit (Cdissertation obj) {
		bookType = "dissertation";
	}

	public void visit (Cenglish obj) {
		bookType = "english";
	}

	public void visit (Cjournal obj) {
		bookType = "journal";
	}

	public void visit (Cminguo obj) {
		bookType = "minguo";
	}

	public void visit (Cmodern obj) {
		bookType = "modern";
	}

}
