package cn.cadal.rec.tryJava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TextSimilarity {
	
	private String sourceFile = "E:/Recommendation/Dict/cbookInfo_bookno.dat";
	private String destinFile = "E:/Recommendation/Dict/cbookInfo_bookno_score.dat";
	
	// 存储格式： {1,['论文','孔子著','人民教育出版社','古籍']}{...}
	public Map<Integer, String[]> hashBooknoInfo = null;			// 用于计算随机的两个item之前的文本相似度
	// 存储格式： ['1','论文','孔子著','人民教育出版社','古籍'][...]
	public List<String[]> listBooknoInfo = null;					// 用于全局计算所有item之前的文本相似度
	
	/**
	 * Construct function
	 */
	public TextSimilarity(){
	}
	public TextSimilarity(String sourceFile, String destinFile){
		this.sourceFile = sourceFile;
		this.destinFile = destinFile;
	}
	
	/**
	 * Read sourceFile and store into this.hashBooknoInfo
	 * 
	 * 		!! using this.hashBooknoInfo, please run this function behead
	 */
	public void ReadIntoHashBooknoInfo() {
		this.hashBooknoInfo = new HashMap<Integer, String[]>();
		
		File file = new File(this.sourceFile);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			String line = null;
			while((line = reader.readLine()) != null) {
				Integer bookno = null;
				String []info = new String[4];
				String []lineSplit = line.split("####");

				bookno = Integer.valueOf(lineSplit[0].trim());
				info[0] = lineSplit[1];
				info[1] = lineSplit[2];
				info[2] = lineSplit[3];
				info[3] = lineSplit[5];
				
				this.hashBooknoInfo.put(bookno, info);
			}

			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(reader != null) {
					reader.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Read sourceFile and store into this.listBooknoInfo
	 * 
	 * 		!! using this.listBooknoInfo, please run this function behead
	 */	
	public void ReadIntoListBooknoInfo(){
		this.listBooknoInfo = new ArrayList<String[]>();
		
		File file = new File(this.sourceFile);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			String line = null;
			while((line = reader.readLine()) != null) {
				String []info = new String[5];
				String []lineSplit = line.split("####");

				info[0] = lineSplit[0].trim();
				info[1] = lineSplit[1];
				info[2] = lineSplit[2];
				info[3] = lineSplit[3];
				info[4] = lineSplit[5];
				
				this.listBooknoInfo.add(info);
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(reader != null) {
					reader.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Calculate the cosine similarity of two document
	 * 
	 * @param 
	 * 		doc1 String type
	 * @param 
	 * 		doc2 String type
	 * @return 
	 * 		The cosine similar for doc1 and doc2
	 */
	public double Similarity(String doc1, String doc2) {
		if (doc1 != null && doc1.trim().length() > 0 && doc2 != null && doc2.trim().length() > 0) {
			Map<Integer, int[]> AlgorithmMap = new HashMap<Integer, int[]>();
			
			//calculate the number of word for different documents
			for (int i = 0; i < doc1.length(); i++) {
				char d1 = doc1.charAt(i);
				if(isHanZi(d1)){
					int charIndex = getGB2312Id(d1);
					if(charIndex != -1){
						int[] fq = AlgorithmMap.get(charIndex);
						if(fq != null && fq.length == 2){
							fq[0]++;
						}else {
							fq = new int[2];
							fq[0] = 1;
							fq[1] = 0;
							AlgorithmMap.put(charIndex, fq);
						}
					}
				}else{
					if (!Character.isSpaceChar(d1)) {
						int[] fq = AlgorithmMap.get(Integer.valueOf(d1));
						if (fq != null && fq.length == 2) {
							fq[0]++;
						} else {
							fq = new int[2];
							fq[0] = 1;
							fq[1] = 0;
							AlgorithmMap.put(Integer.valueOf(d1), fq);
						}
					}
				}
			}
			for (int i = 0; i < doc2.length(); i++) {
				char d2 = doc2.charAt(i);
				if(isHanZi(d2)){
					int charIndex = getGB2312Id(d2);
					if(charIndex != -1){
						int[] fq = AlgorithmMap.get(charIndex);
						if(fq != null && fq.length == 2){
							fq[1]++;
						}else {
							fq = new int[2];
							fq[0] = 0;
							fq[1] = 1;
							AlgorithmMap.put(charIndex, fq);
						}
					}
				}else{
					if (!Character.isSpaceChar(d2)) {
						int[] fq = AlgorithmMap.get(Integer.valueOf(d2));
						if (fq != null && fq.length == 2) {
							fq[1]++;
						} else {
							fq = new int[2];
							fq[0] = 0;
							fq[1] = 1;
							AlgorithmMap.put(Integer.valueOf(d2), fq);
						}
					}
				}
			}
			
			// iterator hash-map to calculate cosine similar
			Iterator<Integer> iterator = AlgorithmMap.keySet().iterator();
			double sqdoc1 = 0;
			double sqdoc2 = 0;
			double denominator = 0; 
			while(iterator.hasNext()){
				int[] c = AlgorithmMap.get(iterator.next());
				denominator += c[0]*c[1];
				sqdoc1 += c[0]*c[0];
				sqdoc2 += c[1]*c[1];
			}
			
			return denominator / Math.sqrt(sqdoc1*sqdoc2);
		} else {
			return 0.0;
		}
	}

	/**
	 * Chinese or not
	 * 
	 * @param ch
	 * 		Chinese word
	 * @return
	 * 		true or false
	 */
	public static boolean isHanZi(char ch) {
		return (ch >= 0x4E00 && ch <= 0x9FA5);

	}

	/**
	 * To get code of GB2312 or ASCII
	 * 
	 * @param ch 
	 * 		Chinese of GB2312 or ASCII(#128)
	 * @return 
	 * 		The place of ch in code GB2312,-1 for unidentification
	 */
	public static short getGB2312Id(char ch) {
		try {
			byte[] buffer = Character.toString(ch).getBytes("GB2312");
			if (buffer.length != 2) {
				return -1;	// 正常情况下buffer应该是两个字节，否则说明ch不属于GB2312编码，故返回'?'，此时说明不认识该字符
			}
			int b0 = (int) (buffer[0] & 0x0FF) - 161; // 编码从A1开始，因此减去0xA1=161
			int b1 = (int) (buffer[1] & 0x0FF) - 161; // 第一个字符和最后一个字符没有汉字，因此每个区只收16*6-2=94个汉字
			return (short) (b0 * 94 + b1);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * To calculate similarity between shuffle two book-no, such as (17951,9822)
	 * 
	 * @param bookno1
	 * 		Book-no, such as 17951
	 * @param bookno2
	 * 		Book-no, such as 9822
	 * @return
	 * 		score of two book
	 */
	public double CalSimilarityTextDistance(int bookno1, int bookno2){
		try{
			String []bookInfo1 = this.hashBooknoInfo.get(Integer.valueOf(bookno1));
			String []bookInfo2 = this.hashBooknoInfo.get(Integer.valueOf(bookno2));
			
			double similarity = this.Similarity(bookInfo1[0], bookInfo2[0]) * 0.6 + 
								this.Similarity(bookInfo1[1], bookInfo2[1]) * 0.1 + 
								this.Similarity(bookInfo1[2], bookInfo2[2]) * 0.1 + 
								this.Similarity(bookInfo1[3], bookInfo2[3]) * 0.2;
			
			return similarity;
		}catch(Exception e){
			e.printStackTrace();
			return 0.0;			
		}
	}
	
	/**
	 * To calculate each book-no, using this.listBooknoInfo, 
	 * 		and the result will write into this.destinFile if the score is lager than 0.0.
	 * destinFile store type: 1 235 0.7145 823 0.0981 ...->[(1,235,0.7145),(1,823,0.0981),...]
	 * 
	 * @return
	 * 		true or false for finish or not
	 */
	public boolean CalSimilarityTextDistance(){
		FileWriter writer = null;
		
		try{
			writer = new FileWriter(this.destinFile, true); 
			
			int count = 1;
			
			double score = 0.0;
			for(int outerIndx = 0; outerIndx < this.listBooknoInfo.size(); ++outerIndx) {
				System.out.println(count++);
				
				String []outerStr = this.listBooknoInfo.get(outerIndx);
				writer.write(outerStr[0] + " ");
				
				for(int innerIndx = outerIndx + 1; innerIndx < this.listBooknoInfo.size(); ++innerIndx) {
					String []innerStr = this.listBooknoInfo.get(innerIndx);
					
					score = this.Similarity(outerStr[1], innerStr[1]) * 0.6 + 
							this.Similarity(outerStr[2], innerStr[2]) * 0.1 + 
							this.Similarity(outerStr[3], innerStr[3]) * 0.1 + 
							this.Similarity(outerStr[4], innerStr[4]) * 0.2;
					if(score >= 0.0) {
						writer.write(innerStr[0] + " " + String.valueOf(score) + " ");
					}
				}
				
				writer.write("\n");
			}
			
			writer.close();
			
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			try{
				if(writer != null)
					writer.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		TextSimilarity csa = new TextSimilarity();
//		String doc1 = "中国人google";
//		String doc2 = "中国人good";
//		System.out.println(csa.Similarity(doc1, doc2));
		
//		csa.ReadIntoHashBooknoInfo();
		
		csa.ReadIntoListBooknoInfo();
		
//		System.out.println(csa.CalSimilarityTextDistance(310636, 310637));
//		System.out.println(csa.CalSimilarityTextDistance(310638, 124572));
//		System.out.println(csa.CalSimilarityTextDistance(310639, 310640));
		
		csa.CalSimilarityTextDistance();
	}
	
	/**
	 * Getters and Setters
	 */
	public String getSourceFile() {
		return sourceFile;
	}
	public String getDestinFile() {
		return destinFile;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	public void setDestinFile(String destinFile) {
		this.destinFile = destinFile;
	}
	public Map<Integer, String[]> getHashBooknoInfo() {
		return hashBooknoInfo;
	}
	public List<String[]> getListBooknoInfo() {
		return listBooknoInfo;
	}
	public void setHashBooknoInfo(Map<Integer, String[]> hashBooknoInfo) {
		this.hashBooknoInfo = hashBooknoInfo;
	}
	public void setListBooknoInfo(List<String[]> listBooknoInfo) {
		this.listBooknoInfo = listBooknoInfo;
	}

}
