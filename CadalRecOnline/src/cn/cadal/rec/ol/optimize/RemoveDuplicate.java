/**
 * ֻʵ���˶�ͼ������һЩ�Ż����������ڱ�ǩ���û�����û�п���
 */
package cn.cadal.rec.ol.optimize;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import cn.cadal.rec.ol.common.Book;

public class RemoveDuplicate {
	
	private double SIMILAR = 0.2;
	private int shinglingK = 3;
	
	private List<String> shinglingDicList = null;
	public List<BitSet> bookidBitsetList = null;
	
	/**
	 * Construct functions
	 */
	public RemoveDuplicate(){
		this.shinglingDicList = new ArrayList<String>(); 
		this.bookidBitsetList = new ArrayList<BitSet>();
	}
	public RemoveDuplicate(double similar, int shinglingK){
		this.SIMILAR = similar;
		this.shinglingK = shinglingK;
		
		this.shinglingDicList = new ArrayList<String>(); 
		this.bookidBitsetList = new ArrayList<BitSet>();
	}
	
	/**
	 * To remove duplicate for bookidList
	 * 
	 * @param bookidList
	 * @return
	 */
	public List<Book> RemoveDupFunc(List<Book> bookList){
		this.bookidBitsetList.clear();
		// Calculate shingling bit-set for total books
		for(int i = 0; i < bookList.size(); ++i) {
			BitSet bitset = this.CalBitset(bookList.get(i).getBookTitle());
			this.bookidBitsetList.add(bitset);
		}
		
		// Calculate similar and remove duplicate
		int count = 0;
		for(int j = 0; j < this.bookidBitsetList.size(); ++j) {
			for(int t = j + 1; t < this.bookidBitsetList.size(); ++t) {
				if(this.bookidBitsetList.get(j) != null && this.bookidBitsetList.get(t) != null) {
					count++;
					this.CalAndRemoveDup(j, t);
				}
			}
		}
//		System.out.println("count:" + count);
		
		// Get result item
		List<Book> resBookinfo = new ArrayList<Book>();
		for(int i = 0; i < this.bookidBitsetList.size() && i < bookList.size(); ++i) {		// ���������ĸ�������Ӧ����һ���ģ����Ƕ��ڼ�������tag�����Ƽ�ͼ�������߲�һ�£�Խ�����
			if(this.bookidBitsetList.get(i) != null){
				resBookinfo.add(bookList.get(i));
			}
		}
		
		return resBookinfo;
	}
	
	/**
	 * Calculate shingling bit-set for each book-title
	 * 
	 * @param title
	 * @return
	 */
	private BitSet CalBitset(String title){
		BitSet bitset = new BitSet();
		
		String strTitle = this.StringFilter(title);

		int len = strTitle.length();
		String shinglingStr = null;
		for(int idx = 0; idx < len; ++idx) {
			shinglingStr = strTitle.substring(idx, idx + 3 > len ? len : idx + 3);
			
			if(!this.shinglingDicList.contains(shinglingStr)) {
				this.shinglingDicList.add(shinglingStr);
			}
			bitset.set(this.shinglingDicList.indexOf(shinglingStr), true);
			
			if(idx + 3 > len) {
				break;
			}
		}
		return bitset;
	}

	/**
	 * Remove special characters of string title
	 * 
	 * @param str
	 * @return
	 * @throws PatternSyntaxException
	 */
	public String StringFilter(String str) throws PatternSyntaxException {
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~��@#��%����&*��������+|{}������������������������_ --������]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		
		return m.replaceAll("").trim();
	}
	
	/**
	 * Calculate similar and set null for some item
	 * 
	 * @param j
	 * @param t
	 */
	private void CalAndRemoveDup(int j, int t){
		BitSet bs1 = this.bookidBitsetList.get(j);
		BitSet bs2 = this.bookidBitsetList.get(t);
		
		BitSet bsAnd = (BitSet) bs1.clone();
		BitSet bsOr  = (BitSet) bs1.clone();
		
		bsAnd.and(bs2);
		bsOr.or(bs2);
		
		double sim = (bsAnd.cardinality() * 1.0) / (bsOr.cardinality() * 1.0);
//		System.out.println(j + " " + t + " " + sim); 
		if(sim > this.SIMILAR) {
			this.bookidBitsetList.set(t, null);//�Ƚ�֮�������Ϊ���������ƣ���ȥ������һ�
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		// Analyze and Test
//		RemoveDuplicate rm = new RemoveDuplicate();
//		List<Book> bookList = new ArrayList<Book>();
//		Book book = new Book();
//		book.setBookId("11111111");
//		book.setBookName("���� ���ܵ��� �й���1 ��");
//		book.setPress("�㽭��ѧ������");
//		book.setAuthor("�й���");
//		book.setBookType("�ִ�ͼ��");
//		bookList.add(book);
//
//		Book book1 = new Book();
//		book1.setBookId("11111112");
//		book1.setBookName("���ܵ��� �й���2 ׼");
//		book1.setPress("�㽭��ѧ������");
//		book1.setAuthor("�й���");
//		book1.setBookType("�ִ�ͼ��");
//		bookList.add(book1);
//
//		Book book2 = new Book();
//		book2.setBookId("11111113");
//		book2.setBookName("���ܵ��� �й���3 ��");
//		book2.setPress("�㽭��ѧ������");
//		book2.setAuthor("�й���");
//		book2.setBookType("�ִ�ͼ��");
//		bookList.add(book2);
//
//		Book book3 = new Book();
//		book3.setBookId("11111114");
//		book3.setBookName("���ܵ��� �й���4 ��");
//		book3.setPress("�㽭��ѧ������");
//		book3.setAuthor("�й���");
//		book3.setBookType("�ִ�ͼ��");
//		bookList.add(book3);
//
//		List<Book> resBookinfo = rm.RemoveDupFunc(bookList);
//
//		// print information
//		System.out.println(resBookinfo.size());
//		for(int i = 0; i < resBookinfo.size(); ++i) {
//			System.out.println("-----------------");
//			System.out.println(resBookinfo.get(i).getBookId());
//			System.out.println(resBookinfo.get(i).getBookName());	
//			System.out.println(resBookinfo.get(i).getAuthor());
//			System.out.println(resBookinfo.get(i).getPress());
//		}
	}
	
	/**
	 * Getter and Setter
	 */
	public double getSIMILAR() {
		return SIMILAR;
	}
	public int getShinglingK() {
		return shinglingK;
	}
	public List<String> getShinglingDicList() {
		return shinglingDicList;
	}
	public List<BitSet> getBookidBitsetList() {
		return bookidBitsetList;
	}
	public void setSIMILAR(double sIMILAR) {
		SIMILAR = sIMILAR;
	}
	public void setShinglingK(int shinglingK) {
		this.shinglingK = shinglingK;
	}
	public void setShinglingDicList(List<String> shinglingDicList) {
		this.shinglingDicList = shinglingDicList;
	}
	public void setBookidBitsetList(List<BitSet> bookidBitsetList) {
		this.bookidBitsetList = bookidBitsetList;
	}
}