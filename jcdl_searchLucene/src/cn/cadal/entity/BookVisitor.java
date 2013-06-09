package cn.cadal.entity;

public interface BookVisitor {

	public void visit (Cmodern obj);
	public void visit (Cdissertation obj);
	public void visit (Cjournal obj);
	public void visit (Cenglish obj);
	public void visit (Cminguo obj);
	public void visit (Cancient obj);
	public void visit (Cdunhuang obj);
	
}
