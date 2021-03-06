package cn.cadal.rec.algo.itemcf;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The algorithm to calculate relate between different items just like:
 * 
 * For each item in item catalog I1:
 * 		For each customer C who purchased I1:
 * 			For echo item I2 purchased by customer C:
 * 				Record that a customer purchased I1 and I2.
 * 		For each item I2:
 * 			Compute the similarity between I1 and I2.
 * 
 * The most import is to find a reasonable way to measure the distance between two items.
 */

public class AmazonItemCF {
	private String bookno_userno_map_file = "C:\\Users\\hongxin\\Desktop\\0702\\accesslog_user_bookid_uniq_bookno_userno.dat";
	private String userno_bookno_map_file = "C:\\Users\\hongxin\\Desktop\\0702\\accesslog_user_bookid_uniq_userno_bookno.dat";
	private String result = "C:\\Users\\hongxin\\Desktop\\0702\\result.dat";
	
	public HashMap<Integer, Itemset> booknoUsernoItemset;
	public HashMap<Integer, Itemset> usernoBooknoItemset;

	/**
	 * Construct function
	 */
	public AmazonItemCF(){
		this.booknoUsernoItemset = new HashMap<Integer, Itemset>();
		this.usernoBooknoItemset = new HashMap<Integer, Itemset>();
	}
	public AmazonItemCF(String bookno_userno_map_file, String userno_bookno_map_file){
		this.bookno_userno_map_file = bookno_userno_map_file;
		this.userno_bookno_map_file = userno_bookno_map_file;
		this.booknoUsernoItemset = new HashMap<Integer, Itemset>();
		this.usernoBooknoItemset = new HashMap<Integer, Itemset>();
	}
	
	/**
	 * Read files
	 */
	public boolean ReadMapFiles() {
		return (this.ReadBooknoUsernoMapFile() && this.ReadUsernoBooknoMapFile());
	}
	private boolean ReadBooknoUsernoMapFile(){
		File file = new File(this.bookno_userno_map_file);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			String line = null;
			while((line = reader.readLine()) != null) {
				String [] lineSplit = line.split(" ");
				
				Integer key = Integer.parseInt(lineSplit[0]);
				Itemset val = new Itemset();
				
				for(int i = 1; i < lineSplit.length; ++i) {
					val.InsertIntoItemset(Integer.parseInt(lineSplit[i]));
				}
				
				this.booknoUsernoItemset.put(key, val);
			}
			
			reader.close();
			System.out.println("Finish ReadBooknoUsernoMapFile");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}
	private boolean ReadUsernoBooknoMapFile(){
		File file = new File(this.userno_bookno_map_file);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			String line = null;
			while((line = reader.readLine()) != null) {
				String [] lineSplit = line.split(" ");
				
				Integer key = Integer.parseInt(lineSplit[0]);
				Itemset val = new Itemset();
				
				for(int i = 1; i < lineSplit.length; ++i) {
					val.InsertIntoItemset(Integer.parseInt(lineSplit[i]));
				}
				
				this.usernoBooknoItemset.put(key, val);
			}
			
			reader.close();
			System.out.println("Finish  ReadUsernoBooknoMapFile");
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Calculate Framework
	 */
	public void CalFramework(){
		System.out.println("Start CalFramework");
		List<Integer> itemInnerList = new ArrayList<Integer>();
		
		File result = new File(this.result);
		BufferedWriter bw = null;
		try{
			bw = new BufferedWriter(new FileWriter(result));
			
			for(int itemIndx = 0; itemIndx < this.booknoUsernoItemset.size(); ++itemIndx) {
				int itemNo = itemIndx + 1;
				System.out.println("processing  " + itemNo);
				Itemset itemVal = this.booknoUsernoItemset.get(itemNo);
				for(int userIndx = 0; userIndx < itemVal.size(); userIndx++) {
					int userNo = itemVal.getByIndex(userIndx);
					Itemset userVal = this.usernoBooknoItemset.get(userNo);			// userVal for Items
					
					// get I1 and I2
					for(int itemInnerIndx = 0; itemInnerIndx < userVal.size(); itemInnerIndx++) {
						int itemInnerNo = userVal.getByIndex(itemInnerIndx);
						
						if(itemNo!=itemInnerNo)
							itemInnerList.add(Integer.valueOf(itemInnerNo));
					}
				}
				
				// calculate similar
				String res = String.valueOf(itemNo);
				if(itemInnerList.size()>5000){
					itemInnerList.clear();
					continue;
				}
				
				double sim = 0.0;
				for(int i = 0; i < itemInnerList.size(); ++i) {
					// call calculate function and store result
					//// -- cosine distance
					sim = CalSimilarity_cosine_distance(itemNo, itemInnerList.get(i));
					res = res + " " + itemInnerList.get(i) + " " + sim; 

				}
				bw.write(res + "\n");

				itemInnerList.clear();
			}
			bw.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * calculate similarity using L2 norm
	 * @param bookid1
	 * @param bookid2
	 * @return similarity between bookid1 and bookid2
	 */
	public double CalSimilarity_L2_norm(int bookid1,int bookid2){
		double sim = 0.0;
		int distance = 0;
		int u1,u2;
		List<Integer> itemSet1 = this.booknoUsernoItemset.get(bookid1).getVal();
		List<Integer> itemSet2 = this.booknoUsernoItemset.get(bookid2).getVal();
		
		int i = 0;
		int j = 0;
		while(i<itemSet1.size()&&j<itemSet2.size()){
			u1 = itemSet1.get(i);
			u2 = itemSet2.get(j);
			if(u1==u2){
				i++;
				j++;
			}
			else if(u1<u2){
				i++;
				++distance;
			}
			else{
				j++;
				++distance;
			}
		}
		if(i==itemSet1.size()&&j<itemSet2.size()){
			while(j<itemSet2.size()){
				++distance;
				++j;
			}
		}
		if(j==itemSet1.size()&&i<itemSet2.size()){
			while(i<itemSet2.size()){
				++distance;
				++i;
			}
		}
		sim = 1.0/distance;
		return sim;
	}
	
	/**
	 * calculate similarity using cosine distance
	 * @param bookid1
	 * @param bookid2
	 * @return similarity between bookid1 and bookid2
	 */
	public double CalSimilarity_cosine_distance(int bookid1,int bookid2){
		double sim = 0.0;
		int numerator = 0;
		int u1,u2;
		List<Integer> itemSet1 = this.booknoUsernoItemset.get(bookid1).getVal();
		List<Integer> itemSet2 = this.booknoUsernoItemset.get(bookid2).getVal();

		int i = 0;
		int j = 0;

		while(i<itemSet1.size() && j<itemSet2.size()){
			u1=itemSet1.get(i);
			u2=itemSet2.get(j);
			if(u1==u2){
				++numerator;
				i++;j++;
			}
			else if(u1<u2)
				i++;
			else
				j++;
		}
		sim = numerator/(Math.sqrt(itemSet1.size())*Math.sqrt(itemSet2.size()));
		return sim;
	}
	
	/**
	 * calculate similarity using Wij = |N(i)^N(j)|/|N(i)|
	 *  Wij is different from Wji
	 * @param bookid1
	 * @param bookid2
	 * @return similarity between bookid1 and bookid2
	 */
	public double CalSimilarity_p53(int bookid1,int bookid2){
		double sim = 0.0;
		int numerator = 0;
		int u1,u2;
		List<Integer> itemSet1 = this.booknoUsernoItemset.get(bookid1).getVal();
		List<Integer> itemSet2 = this.booknoUsernoItemset.get(bookid2).getVal();
		
		int i = 0;
		int j = 0;
		while(i<itemSet1.size()&&j<itemSet2.size()){
			u1 = itemSet1.get(i);
			u2 = itemSet2.get(j);
			if(u1==u2){
				++numerator;
				i++;j++;
			}
			else if(u1<u2)
				i++;
			else
				j++;
		}
		sim = numerator*1.0/itemSet1.size();
		return sim;
	}
	
	/**
	 * print booknoUsernoItemset and usernoBooknoItemset
	 */
	public void PrintParameter(HashMap<Integer, Itemset> hashMap){
		for(int i = 0; i < hashMap.size(); i++){
			System.out.println("-------");
			System.out.println(i+1);
			hashMap.get(i+1).print();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		AmazonItemCF ai = new AmazonItemCF();
		ai.ReadMapFiles();
		//double sim = ai.CalSimilarity_cosine_distance(1, 1);
		//System.out.println(sim);
		ai.CalFramework();
		long end = System.currentTimeMillis();
		System.out.println(start + " " + end + " " + (end - start));
	}
	
	
	
	/**
	 * Getter and Setter
	 */
	public String getBookno_userno_map_file() {
		return bookno_userno_map_file;
	}
	public String getUserno_bookno_map_file() {
		return userno_bookno_map_file;
	}
	public HashMap<Integer, Itemset> getBooknoUsernoItemset() {
		return booknoUsernoItemset;
	}
	public HashMap<Integer, Itemset> getUsernoBooknoItemset() {
		return usernoBooknoItemset;
	}
	public void setBookno_userno_map_file(String booknoUsernoMapFile) {
		bookno_userno_map_file = booknoUsernoMapFile;
	}
	public void setUserno_bookno_map_file(String usernoBooknoMapFile) {
		userno_bookno_map_file = usernoBooknoMapFile;
	}
	public void setBooknoUsernoItemset(HashMap<Integer, Itemset> booknoUsernoItemset) {
		this.booknoUsernoItemset = booknoUsernoItemset;
	}
	public void setUsernoBooknoItemset(HashMap<Integer, Itemset> usernoBooknoItemset) {
		this.usernoBooknoItemset = usernoBooknoItemset;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
}
