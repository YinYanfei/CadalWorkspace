package cn.cadal.rec.freqitem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

public class CountUniqBookid {

	private String DIR = "E:/Recommendation/FreqItemset/";
	private String TWOITEM = "10.res.grep.deal.2.dat";
	private String FOURITEM = "10.res.grep.deal.4.dat";
	
	public int CountTwoItem = 0;
	public int CountFourItem = 0;
	
	/**
	 * Constructor functions
	 */
	public CountUniqBookid(){
	}
	public CountUniqBookid(String dir, String twoItem, String fourItem){
		this.DIR = dir;
		this.TWOITEM = twoItem;
		this.FOURITEM = fourItem;
	}

	/**
	 * Count uniq item of Two-Freq-Item
	 */
	public void CountTwo(){
		String fileDir = this.DIR + this.TWOITEM;
		int count = this.CountCom(fileDir);
		
		if(count != -1) {
			this.CountTwoItem = count;
		}else{
			System.out.println("Count is -1, Error!");
		}
	}

	/**
	 * Count uniq item of Four-Freq-Item
	 */
	public void CountFour(){
		String fileDir = this.DIR + this.FOURITEM;
		int count = this.CountCom(fileDir);
		
		if(count != -1) {
			this.CountFourItem = count;
		}else{
			System.out.println("Count is -1, Error!");
		}
	}

	/**
	 * To count the uniq bookid for fileDir
	 * 
	 * @param fileDir
	 * 
	 * @return
	 * 		-1 for error
	 */
	private int CountCom(String fileDir){
		File file = new File(fileDir);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			Set<String> bookidSet = new HashSet<String>();
			
			while((line = reader.readLine()) != null){
				String [] lineSplit = line.split(" ");
				
				for(int i = 0; i < lineSplit.length; ++i) {
					bookidSet.add(lineSplit[i]);
				}
			}
			
			reader.close();
			return bookidSet.size();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}

		return -1;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Count and Analyze
		CountUniqBookid cub = new CountUniqBookid();
		
		cub.CountTwo();
		cub.CountFour();
		
		System.out.println("Two-Item: " + cub.CountTwoItem);
		System.out.println("Four-Item: " + cub.CountFourItem);
	}

}
