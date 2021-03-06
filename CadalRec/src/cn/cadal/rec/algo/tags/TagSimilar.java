package cn.cadal.rec.algo.tags;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TagSimilar {

	private String TAGINFO="E:/Recommendation/Tags/anaTags_op_forward_awk.dat";
	private String SIMILAR_1="E:/Recommendation/Tags/tag_similar_1.dat";
	private String SIMILAR_2="E:/Recommendation/Tags/tag_similar_2.dat";
	
	public Map<Integer, int []> hashTagInfo = null;
	public List<int []> listTagInfo = null;
	
	/**
	 * Constuct functions
	 */
	public TagSimilar(){
	}
	public TagSimilar(String taginfo, String similar_1, String similar_2){
		this.TAGINFO = taginfo;
		this.SIMILAR_1 = similar_1;
		this.SIMILAR_2 = similar_2;
	}
	
	/**
	 * Read file and into this.hashTagInfo or this.listTagInfo
	 * 		-- For first storing into this.hashTagInfo
	 * 		-- For second storing into this.listTagInfo
	 */
	public void ReadIntoHashTagInfo(){
		this.hashTagInfo = new HashMap<Integer, int []>();
		
		File file = new File(this.TAGINFO);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			String line = null;
			String [] lineSplit = null;
			int lineNo = 1;
			
			while((line = reader.readLine()) != null){
				int [] booknoList = null;
				
				lineSplit = line.split(" ");
				int size = lineSplit.length;
				booknoList = new int[size - 1];
				for(int i = 0; i < size; ++i) {
					booknoList[i] = Integer.valueOf(lineSplit[i]);
				}
				
				this.hashTagInfo.put(Integer.valueOf(lineNo), booknoList);
				lineNo += 1;
			}
			
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(reader != null)
					reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public void ReadIntoListTagInfo(){
		this.listTagInfo = new ArrayList<int []>();
		
		File file = new File(this.TAGINFO);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			String [] lineSplit = null;
			
			while((line = reader.readLine()) != null){
				int [] list = null;
				
				lineSplit = line.split(" ");
				int size = lineSplit.length;
				list = new int[size];
				
				for(int i = 0; i < size; ++i) {
					list[i] = Integer.valueOf(lineSplit[i]);
				}
				
				this.listTagInfo.add(list);
			}
			
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(reader != null)
					reader.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Calculate frame
	 * 		-- For first to calculate each similar between each tag
	 * 		-- For second to calculate each similar for tagno with others
	 * 		-- For third to calculate similar between tagno_1 and tagno_2
	 */
	public void CalFrame(){
		FileWriter writer = null;
		
		int [] tagnoInfoOuter = null;
		int [] tagnoInfoInner = null;
		int size = this.listTagInfo.size();
		double score = 0.0;
		BigDecimal compareZero = new BigDecimal(0.0);
		BigDecimal scoreBigDecimal = null;

		try{
			writer = new FileWriter(this.SIMILAR_1);
			
			for (int outer = 0; outer < size; ++outer) {
				
				System.out.println(outer + 1);
				
				tagnoInfoOuter = this.listTagInfo.get(outer);
				writer.write(String.valueOf(outer + 1) + " ");
				for (int inner = outer + 1; inner < size; ++inner) {
					tagnoInfoInner = this.listTagInfo.get(inner);
					
					score = this.CosineSimilar(tagnoInfoOuter, tagnoInfoInner);
					
					scoreBigDecimal = new BigDecimal(score);
					if(scoreBigDecimal.compareTo(compareZero) != 0) {
						writer.write(String.valueOf(inner + 1) + " " + String.valueOf(score) + " ");
					}
				}
				writer.write("\n");
			}

			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(writer != null)
					writer.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	public void CalFrame(int tagno){
		if(this.listTagInfo == null || tagno > this.listTagInfo.size()) {
			System.out.println("this.listTagInfo or tagno is larger than the size of this.listTagInfo");
			System.exit(1);
		}
		
		FileWriter writer = null;
		try{
			writer = new FileWriter(this.SIMILAR_2);
			int [] tagnoInfo = null;
			for (int i = 0; i < this.listTagInfo.size(); ++i) {
				if (i + 1 == tagno) {
					tagnoInfo = this.listTagInfo.get(i);
				}
			}

			int [] tagnoInfoOther = null;
			double score = 0.0;
			BigDecimal compareZero = new BigDecimal(0.0);
			BigDecimal scoreBigDecimal = null;
			for (int i = 0; i < this.listTagInfo.size(); ++i) {
				tagnoInfoOther = this.listTagInfo.get(i);
				score = this.CosineSimilar(tagnoInfo, tagnoInfoOther);
				scoreBigDecimal = new BigDecimal(score);
				if(scoreBigDecimal.compareTo(compareZero) != 0) {
					writer.write(String.valueOf(i+1) + " " + String.valueOf(score) + "\n");
				}
			}
			
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(writer != null)
					writer.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

	public double CalFrame(int tagno_1, int tagno_2) {
		if(this.hashTagInfo == null || tagno_1 > this.hashTagInfo.size() || tagno_2 > this.hashTagInfo.size()) {
			System.out.println("this.hashTagInfo or tagno_1/tagno_2 is larger than the size of this.hashTagInfo");
			System.exit(1);
		}
	
		int [] tagnoListOne = this.hashTagInfo.get(tagno_1);
		int [] tagnoListTwo = this.hashTagInfo.get(tagno_2);
				
		return this.CosineSimilar(tagnoListOne, tagnoListTwo);
	}
	
	/**
	 * The methods to calculate the similar
	 * 
	 */
	private double CosineSimilar(int [] tagInfoOne, int [] tagInfoTwo){
		if(tagInfoOne == null || tagInfoOne.length < 0 || tagInfoTwo == null || tagInfoTwo.length < 0){
			return 0.0;
		}
		
		Map<Integer, int[]> algorithmMap = new HashMap<Integer, int[]>();
		for(int i = 0; i < tagInfoOne.length; ++i) {
			int [] fq = algorithmMap.get(tagInfoOne[i]);
			if(fq != null && fq.length == 2){
				fq[0]++;
			}else {
				fq = new int[2];
				fq[0] = 1;
				fq[1] = 0;
				algorithmMap.put(tagInfoOne[i], fq);
			}
		}
		for(int i = 0; i < tagInfoTwo.length; ++i) {
			int [] fq = algorithmMap.get(tagInfoTwo[i]);
			if(fq != null && fq.length == 2) {
				fq[1]++;
			}else{
				fq = new int[2];
				fq[0] = 0;
				fq[1] = 1;
				algorithmMap.put(tagInfoTwo[i], fq);
			}
		}
		
		Iterator<Integer> iterator = algorithmMap.keySet().iterator();
		double sq1 = 0;
		double sq2 = 0;
		double denominator = 0; 
		while(iterator.hasNext()){
			int[] c = algorithmMap.get(iterator.next());
			denominator += c[0]*c[1];
			sq1 += c[0]*c[0];
			sq2 += c[1]*c[1];
		}

		double res = denominator / Math.sqrt(sq1*sq2);
		
		if(Double.isNaN(res))
			return 0.0;
		
		return res;
	}
	@SuppressWarnings("unused")
	private double JaccardSimilar(int [] tagInfoOne, int [] tagInfoTwo){
		if(tagInfoOne == null || tagInfoOne.length < 0 || tagInfoTwo == null || tagInfoTwo.length < 0){
			return 0.0;
		}
		
		Map<Integer, Integer> algorithmMap = new HashMap<Integer, Integer>();
		for(int i = 0; i < tagInfoOne.length; ++i) {
			algorithmMap.put(tagInfoOne[i], 0);
		}
		for(int i = 0; i < tagInfoTwo.length; ++i) {
			if(algorithmMap.containsKey(tagInfoTwo[i])) {
				algorithmMap.put(tagInfoTwo[i], 1);
			}else{
				algorithmMap.put(tagInfoTwo[i], 0);
			}
		}
		
		Iterator<Integer> iterator = algorithmMap.keySet().iterator();
		double inter = 0;
		while(iterator.hasNext()){
			if(algorithmMap.get(iterator.next()) == 1) {
				inter++;
			}
		}
		double res = inter / algorithmMap.size();

		if(Double.isNaN(res)){
			return 0.0;
		}
		
		return res;
	}
	
	/**
	 * Print function using to scan the content of this.hashTagInfo and this.listTagInfo
	 * 		- For first to print this.hashTagInfo
	 * 		- For second to print this.listTagInfo
	 */
	public void Print(String content){
		if(content.equals("HashTagInfo")){
			this.PrintHashTagInfo(this.hashTagInfo);
		}else if(content.equals("ListTagInfo")) {
			this.PrintListTagInfo(this.listTagInfo);
		}else{
			System.out.println("Using print just like:\n \tprint(\"HashTagInfo\") \n\t or \n\tprint(\"ListTagInfo\")");
		}
	}
	@SuppressWarnings("unchecked")
	private void PrintHashTagInfo(Map<Integer, int []> hashTagInfo){
		Iterator iter = hashTagInfo.keySet().iterator(); 
		while (iter.hasNext()) { 
		    int key = (Integer)iter.next(); 
		    int [] val = hashTagInfo.get(key); 
		    
		    System.out.println("-------------");
		    System.out.println(key);
		    for(int i = 0; i < val.length; ++i) {
		    	System.out.print(val[i]);
		    	System.out.print(" ");
		    }
		    System.out.println("\n");
		} 
	}
	private void PrintListTagInfo(List<int []> listTagInfo){
		int [] val = null;
		for(int outer = 0; outer < listTagInfo.size(); ++outer) {
			val = listTagInfo.get(outer);
			System.out.println("--------------");
			for(int inner = 0; inner < val.length; ++inner) {
				System.out.print(val[inner]);
				System.out.print(" ");
			}
			System.out.println("\n");
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Similar calculate
		TagSimilar ts = new TagSimilar();
		
//		int [] tagInfoOne = {1,2,3};
//		int [] tagInfoTwo = {2,3,4,5,6,7};
//		
//		System.out.println(ts.CosineSimilar(tagInfoOne, tagInfoTwo));
//		System.out.println(ts.JaccardSimilar(tagInfoOne, tagInfoTwo));

//		ts.ReadIntoHashTagInfo();
//		ts.Print("HashTagInfo");
		
//		ts.ReadIntoListTagInfo();
//		ts.Print("ListTagInfo");
		
//		ts.ReadIntoHashTagInfo();
//		System.out.println(ts.CalFrame(50413, 97207));		// 0.1270001270001905
//		System.out.println(ts.CalFrame(8, 50413));			// 0.4472135954999579
//		System.out.println(ts.CalFrame(8, 97207));			// 0.05679618342470648
		
//		ts.ReadIntoListTagInfo();
//		ts.CalFrame(8);
		
		ts.ReadIntoListTagInfo();
		ts.CalFrame();
	}
	
	/**
	 * Getter and Setter
	 */
	public String getTAGINFO() {
		return TAGINFO;
	}
	public String getSIMILAR_1() {
		return SIMILAR_1;
	}
	public String getSIMILAR_2() {
		return SIMILAR_2;
	}
	public Map<Integer, int[]> getHashTagInfo() {
		return hashTagInfo;
	}
	public List<int[]> getListTagInfo() {
		return listTagInfo;
	}
	public void setTAGINFO(String tAGINFO) {
		TAGINFO = tAGINFO;
	}
	public void setSIMILAR_1(String sIMILAR_1) {
		SIMILAR_1 = sIMILAR_1;
	}
	public void setSIMILAR_2(String sIMILAR_2) {
		SIMILAR_2 = sIMILAR_2;
	}
	public void setHashTagInfo(Map<Integer, int[]> hashTagInfo) {
		this.hashTagInfo = hashTagInfo;
	}
	public void setListTagInfo(List<int[]> listTagInfo) {
		this.listTagInfo = listTagInfo;
	}

}
