/**
 * 只实现了对图书对象的一些优化工作，关于标签和用户尚且没有考虑
 */
package cn.cadal.rec.ol.optimize;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.cadal.rec.ol.common.Book;

public class RemoveAbnormal {
	public Map<Book, Double> scoreMap = null;
	
	/**
	 * Constructor functions
	 */
	public RemoveAbnormal(){
		this.scoreMap = new HashMap<Book, Double>();
	}

	/**
	 * Calculate score for each book
	 * 
	 * @param bookidList
	 */
	public List<Book> CalScoreForEachBookByAverageScore(List<Book> bookList){
		
		double scoreFloat = 0.0;
		for(int i = 0; i < bookList.size(); ++i){
			scoreFloat = 0.0;
			for(int j = 0; j < bookList.size(); ++j) {
				if(i != j) {
					scoreFloat += this.CalSimilar(bookList.get(i), bookList.get(j));
				}
			}
			scoreFloat = scoreFloat / (bookList.size() - 1);		// to get average score
			this.scoreMap.put(bookList.get(i), scoreFloat);
		}
		
		// return result without singular point
		return this.DetermineBook();
	}
	
	/**
	 * Calculate score for each book [Another method]
	 * 
	 * @param bookidList
	 */
	public List<Book> CalScoreForEachBookByDensity(List<Book> bookList){
		// Hold
		int HOLDNUM = bookList.size() / 3;
		
		double scoreFloat = 0.0;
		double [] scoreArrForOneBook = new double[bookList.size() - 1];
		
		System.out.println(scoreArrForOneBook.length);
		
		int idx = 0;
		for(int i = 0; i < bookList.size(); ++i){
			scoreFloat = 0.0;
			idx = 0;
			for(int j = 0; j < bookList.size(); ++j) {
				if(i != j) {
					scoreArrForOneBook[idx] = this.CalSimilar(bookList.get(i), bookList.get(j)); 
					idx++;
				}
			}
			
			Arrays.sort(scoreArrForOneBook);
			for(int t = 0; t < HOLDNUM; ++t){
				scoreFloat += scoreArrForOneBook[idx - t - 1];
			}
			scoreFloat = scoreFloat / HOLDNUM;		// to get average score
			this.scoreMap.put(bookList.get(i), scoreFloat);
		}
		
		// return result without singular point
		return this.DetermineBook();
	}
	
	/**
	 * Calculate similar for two books
	 * 
	 * @param book1
	 * @param book2
	 * @return
	 */
	private double CalSimilar(Book book1, Book book2){
		double score = 0.0;
		
		// Only calculate title similar
		score = this.Similarity(book1.getBookTitle(), book2.getBookTitle());

		// Calculate title/creator/publisher/type similar
//		score = this.Similarity(book1.getBookName(), book2.getBookName()) * 0.6 + 
//				this.Similarity(book1.getAuthor(), book2.getAuthor()) * 0.1 + 
//				this.Similarity(book1.getPress(), book2.getPress()) * 0.1 + 
//				this.Similarity(book1.getBookType(), book2.getBookType()) * 0.2;

		
		// We can add other similar calculate method in this place.
		// ...
		
		return score;
	}
	/**
	 * This function is used to calculate similar for two text
	 * 
	 * @param doc1
	 * @param doc2
	 * @return
	 */
	private double Similarity(String doc1, String doc2) {
        if (doc1 != null && doc1.trim().length() > 0 && doc2 != null && doc2.trim().length() > 0) {
            Map<String, int[]> AlgorithmMap = new HashMap<String, int[]>();
            
            List<String> doc1List = new ArrayList<String>();
            List<String> doc2List = new ArrayList<String>();
            
            // Extract word for doc1 and doc2
            Pattern patternChi = Pattern.compile("[\u4E00-\u9FA5]");
            Pattern patternEng = Pattern.compile("\\w+");
            Matcher matc = patternChi.matcher(doc1);
            while (matc.find()) {
                 doc1List.add(matc.group());
            }
            
            matc = patternEng.matcher(doc1);
            while (matc.find()) {
                 doc1List.add(matc.group());
            }
            // doc2
            matc = patternChi.matcher(doc2);
            while (matc.find()) {
                 doc2List.add(matc.group());
            }
            matc = patternEng.matcher(doc2);
            while (matc.find()) {
                 doc2List.add(matc.group());
            }
            
            //calculate the number of word for different documents
            double res = 0.0;

            for (int i = 0; i < doc1List.size(); i++) {
            	int[] fq = AlgorithmMap.get(doc1List.get(i));
				if(fq != null && fq.length == 2){
					fq[0]++;
				}else {
					fq = new int[2];
					fq[0] = 1;
					fq[1] = 0;
					AlgorithmMap.put(doc1List.get(i), fq);
				}
            }
            for (int i = 0; i < doc2List.size(); i++) {
				int[] fq = AlgorithmMap.get(doc2List.get(i));
				if (fq != null && fq.length == 2) {
					fq[1]++;
				} else {
					fq = new int[2];
					fq[0] = 0;
					fq[1] = 1;
					AlgorithmMap.put(doc2List.get(i), fq);
				}
            }
            
            // iterator hash-map to calculate cosine similar
            Iterator<String> iterator = AlgorithmMap.keySet().iterator();
            double sqdoc1 = 0;
            double sqdoc2 = 0;
            double denominator = 0; 
            while(iterator.hasNext()){
                 int[] c = AlgorithmMap.get(iterator.next());
                 denominator += c[0]*c[1];
                 sqdoc1 += c[0]*c[0];
                 sqdoc2 += c[1]*c[1];
            }
            
            res = denominator / Math.sqrt(sqdoc1*sqdoc2);
            
            if(Double.isNaN(res)) {
                 res = 0.0;
            }

            return res;
        }else{
        	return 0.0;
        }
	}
	
	/**
	 * This function is used to evaluate whether one book should be deleted or not
	 */
	private List<Book> DetermineBook(){
		double LIMIT = 0.01;
		List<Book> bookDeter = new ArrayList<Book>();
		
		// Simple evaluate
		Iterator<Book> iterator = this.scoreMap.keySet().iterator();
		while(iterator.hasNext()){
			Book key = iterator.next();
			double val = this.scoreMap.get(key);
			
			if(val > LIMIT){
				bookDeter.add(key);
			}
		}
		
		return bookDeter;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test
//		RemoveAbnormal ra = new RemoveAbnormal();
//		List<Book> bookList = new ArrayList<Book>();
//		
//		Book book1 = new Book();
//		book1.setBookId("11111111");
//		book1.setBookName("绝密档案1");
//		book1.setPress("浙江大学出版社");
//		book1.setAuthor("中国人");
//		book1.setBookType("现代图书");
//		bookList.add(book1);
//
//		Book book2 = new Book();
//		book2.setBookId("11111111");
//		book2.setBookName("绝密档案2");
//		book2.setPress("浙江大学出版社");
//		book2.setAuthor("中国人");
//		book2.setBookType("现代图书");
//		bookList.add(book2);
//	
//		Book book3 = new Book();
//		book3.setBookId("11111111");
//		book3.setBookName("Windows编程");
//		book3.setPress("浙江大学出版社");
//		book3.setAuthor("中国人");
//		book3.setBookType("现代图书");
//		bookList.add(book3);
//		
//		List<Book> bookDone = ra.CalScoreForEachBookByAverageScore(bookList);		// score average method
////	List<String> bookidDone = ra.CalScoreForEachBookByDensity(bookidList);			// score density method
//		
//		// print information
//		System.out.println("------------------Selected of books------------------");
//		for(int i = 0; i < bookDone.size(); ++i) {
//			System.out.println("=============================");
//			System.out.println(bookDone.get(i).getBookId());
//			System.out.println(bookDone.get(i).getBookName());
//			System.out.println(bookDone.get(i).getAuthor());
//			System.out.println(bookDone.get(i).getBookType());
//			System.out.println(bookDone.get(i).getPress());
//		}
	}

	/**
	 * Getter and Setter functions
	 */
	public Map<Book, Double> getScoreMap() {
		return scoreMap;
	}
	public void setScoreMap(Map<Book, Double> scoreMap) {
		this.scoreMap = scoreMap;
	}

}
