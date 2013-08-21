package cn.cadal.rec.sgd;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import cn.cadal.rec.common.Book;
import cn.cadal.rec.common.QueryInfoFromPg;

public class RecommendSGD {

	public static void main(String[] args) {
		//for test
		RecommendSGD r = new RecommendSGD();
		List<Book> list = r.getRecommendation(2);
		for(Book book:list){
			System.out.println("---------------");
			System.out.println("boonName: " + book.getBookName());
			System.out.println("press: " + book.getPress());
			System.out.println("author: " + book.getAuthor());
			System.out.println();
		}
	}

	public List<Book> getRecommendation(int userno){
		String filePath = "D:\\CADAL\\Recommendation\\common\\SGD\\res.dat";
		List<String> bookidList = new ArrayList<String>();
		BufferedReader br = null;
		try{
			File f = new File(filePath);
			br = new BufferedReader(new FileReader(f));
			String tmp = "";
			while((tmp = br.readLine())!=null){
				String[] work = tmp.split(" ");
				if(Integer.parseInt(work[0])==userno){
					for(int i = 1;i<work.length - 1;i=i+2){
						if(i==81)
							break;
						int id = Integer.parseInt(work[i]);
						String bookNo = this.getBookNobyId(id);
						bookidList.add(bookNo);
					}
					break;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(br!=null){
				try{
					br.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		QueryInfoFromPg qifp = new QueryInfoFromPg();
		return qifp.QueryMetaData(bookidList);
	}
	
	/**
	 * 
	 * @param bookid
	 *            处理之后的id（1到1670000+）
	 * @return bookNo 数据库中的8位书号
	 */
	public String getBookNobyId(int bookid) {
		String bookNo = "";
		String filePath = "D:\\CADAL\\Recommendation\\common\\bookno_bookid.map";
		File f = new File(filePath);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String temp = "";
			while ((temp = br.readLine()) != null) {
				String[] arr = temp.split(" ");
				if (Integer.parseInt(arr[0]) == bookid) {
					bookNo = arr[1];
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return bookNo;
	}
}
