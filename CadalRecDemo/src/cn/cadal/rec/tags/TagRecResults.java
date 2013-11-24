package cn.cadal.rec.tags;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TagRecResults {

	private String DIR = "D:/CADAL/Recommendation/common/Tags/";
	private String ANATAGS = "anaTags.dat";
	private String TAGNO_TAGNAME = "tagno_tagname.map";
	private String ANATAGS_OP_FORWARD = "ana_totalTags_op_forward.dat";
	private String BOOKNO_BOOKID = "bookno_bookid.map";
	private String TAG_SIMILAR_DONE = "tag_similar_done_deal_op_pri_op.res";
	
	public List<String> bookidResultList = null;
	public List<String> similarTags = null;
	
	/**
	 * Construct functions
	 */
	public TagRecResults(){
		this.bookidResultList = new ArrayList<String>();
		this.similarTags = new ArrayList<String>();
	}
	public TagRecResults(String DIR){
		this.DIR = DIR;
		this.bookidResultList = new ArrayList<String>();
		this.similarTags = new ArrayList<String>();
	}
	
	/**
	 * 
	 * @param booid
	 * @return
	 */
	private List<String> TagNames(String booid){
		List<String> tagsNameList = new ArrayList<String>();
		
		File file = new File(this.DIR + this.ANATAGS);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line  = "";
			String bookidLine = "";
			String tagsLine = "";
			
			while((line = reader.readLine()) != null) {
				bookidLine = line.split(" ")[0];
				if(bookidLine.equals(booid)){
					tagsLine = line.split(" ")[1];
					break;
				}
			}
			
			if(tagsLine.equals("")) {
				System.out.println("Sorry,  can not find it!");
			}else{
				String[] tagsSplit = tagsLine.split("\\$");
				
				for(int i = 0; i < tagsSplit.length; ++i) {
					if((!tagsSplit[i].equals("ר��")) && (!tagsSplit[i].equals("����"))){
						tagsNameList.add(tagsSplit[i]);
					}
				}
			}
			
			reader.close();
			
			return tagsNameList;
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param tagNames
	 * @return
	 */
	private List<Integer> TagNos(List<String> tagNames){
		List<Integer> tagNos = new ArrayList<Integer>();
		Map<String, Integer> tagnametagnoMap = new HashMap<String, Integer>();
		
		File file = new File(this.DIR + this.TAGNO_TAGNAME);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));

			String line = "";
			String [] lineSplit = null;
			
			while((line = reader.readLine()) != null){
				lineSplit = line.split(" ");
				
				tagnametagnoMap.put(lineSplit[1], Integer.valueOf(lineSplit[0]));
			}
			
			for(int i = 0; i < tagNames.size(); ++i) {
				System.out.println("----------" + tagNames.get(i));
				tagNos.add(tagnametagnoMap.get(tagNames.get(i)));
			}
			
			reader.close();
			
			return tagNos;
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
	
	/**
	 * 
	 * @param tagNos
	 * @return
	 */
	private List<Integer> BookNos(List<Integer> tagNos){
		List<Integer> booknos = new ArrayList<Integer>();
		Map<Integer, String> tagnoBooknoMap = new HashMap<Integer, String>();
		
		File file = new File(this.DIR + this.ANATAGS_OP_FORWARD);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));

			String line = "";
			String tagno = "";
			String booknoStr = "";
			
			while((line = reader.readLine()) != null){
				tagno = line.substring(0, line.indexOf(" "));
				booknoStr = line.substring(line.indexOf(" ") + 1);
				
				tagnoBooknoMap.put(Integer.valueOf(tagno), booknoStr);
			}
			
			reader.close();
			
			for(int i = 0 ; i < tagNos.size(); ++i) {
				String tmpStr = tagnoBooknoMap.get(tagNos.get(i));
				System.out.println(tagNos.get(i));
				String [] tmpStrSplit = tmpStr.split(" ");
				for(int j = 0; j < 10 && j < tmpStrSplit.length; ++j) {
					if(!booknos.contains(Integer.valueOf(tmpStrSplit[j]))){
						booknos.add(Integer.valueOf(tmpStrSplit[j]));
					}
				}
			}
			
			return booknos;
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
	
	/**
	 * 
	 * @param bookNos
	 */
	private void BookIds(List<Integer> bookNos){
		Map<Integer, String> booknoBookidMap = new HashMap<Integer, String>();
		
		File file = new File(this.DIR + this.BOOKNO_BOOKID);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			String line = "";
			String [] lineSplit = null;
			
			while((line = reader.readLine()) != null){
				lineSplit = line.split(" ");
				
				booknoBookidMap.put(Integer.valueOf(lineSplit[0]), lineSplit[1]);
			}
			
			reader.close();
			
			for(int i = 0; i < bookNos.size(); ++i) {
				this.bookidResultList.add(booknoBookidMap.get(bookNos.get(i)));
			}
		}catch(Exception e) {
			e.printStackTrace();
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
	 * 
	 * @param tagNos
	 * @return
	 */
	private List<Integer> SimilarTagNos(List<Integer> tagNos){
		List<Integer> similarTagno = new ArrayList<Integer>();
		Map<Integer, String> tagnoSimilar = new HashMap<Integer, String>();
		
		File file = new File(this.DIR + this.TAG_SIMILAR_DONE);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			Integer tagno = null;
			String similar = "";
			
			while((line = reader.readLine()) != null){
				tagno = Integer.valueOf(line.substring(0, line.indexOf(" ")));
				similar = line.substring(line.indexOf(" ") + 1);
				
				tagnoSimilar.put(tagno, similar);
			}
			
			for(Integer tag: tagNos) {
				similar = tagnoSimilar.get(tag);
				String [] similarStr = similar.split(" ");
				int count = 1;
				for(int i = 0; i < similarStr.length && i < 10; ++i) {
					if(count % 2 == 1) {
						similarTagno.add(Integer.valueOf(similarStr[i]));
					}
					count++;
				}
			}
			
			reader.close();
			
			return similarTagno;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 
	 * @param similarTagNos
	 */
	private void SimilarTagNames(List<Integer> similarTagNos) {
		Map<Integer, String> tagnoTagnameMap = new HashMap<Integer, String>();
		
		File file = new File(this.DIR + this.TAGNO_TAGNAME);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line  = "";
			
			while((line = reader.readLine()) != null) {
				tagnoTagnameMap.put(Integer.valueOf(line.split(" ")[0]), line.split(" ")[1]);
			}
			
			reader.close();
			
			for(int i = 0; i < similarTagNos.size(); ++i) {
				this.similarTags.add(tagnoTagnameMap.get(similarTagNos.get(i)));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
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
	 * This function is port for servlet
	 * 
	 * @param bookid
	 * 		this bookid is used to find more recommendation
	 */
	public void Connector(String bookid){
		List<String> tagNames = this.TagNames(bookid);
		List<Integer> tagNos = this.TagNos(tagNames);
		List<Integer> bookNos = this.BookNos(tagNos);
		this.BookIds(bookNos);
//		List<Integer> similarTagNos = this.SimilarTagNos(tagNos);
//		this.SimilarTagNames(similarTagNos);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Result and Analyze
		TagRecResults trr = new TagRecResults();
//		List<String> strListTagName = trr.TagNames("02036722");
//		
//		List<Integer> intTagNo = trr.TagNos(strListTagName);
//
//		// Tag
//		List<Integer> simiTagNo = trr.SimilarTagNos(intTagNo);
//		
//		trr.SimilarTagNames(simiTagNo);
//		
//		// Book
//		List<Integer> booknos = trr.BookNos(intTagNo);
//		
//		trr.BookIds(booknos);

		trr.Connector("02036722");
		
		System.out.println("------------ Tag ------------");
		for(int i = 0 ; i < trr.similarTags.size(); ++i) {
			System.out.println(trr.similarTags.get(i));
		}
		
		System.out.println("------------ Book ------------");
		for(int i = 0 ; i < trr.bookidResultList.size(); ++i) {
			System.out.println(trr.bookidResultList.get(i));
		}
	}
	
	/**
	 * Getter and Setter
	 */
	public String getDIR() {
		return DIR;
	}
	public String getANATAGS() {
		return ANATAGS;
	}
	public String getTAGNO_TAGNAME() {
		return TAGNO_TAGNAME;
	}
	public String getANATAGS_OP_FORWARD() {
		return ANATAGS_OP_FORWARD;
	}
	public String getBOOKNO_BOOKID() {
		return BOOKNO_BOOKID;
	}
	public String getTAG_SIMILAR_DONE() {
		return TAG_SIMILAR_DONE;
	}
	public List<String> getBookidResultList() {
		return bookidResultList;
	}
	public List<String> getSimilarTags() {
		return similarTags;
	}
	public void setDIR(String dIR) {
		DIR = dIR;
	}
	public void setANATAGS(String aNATAGS) {
		ANATAGS = aNATAGS;
	}
	public void setTAGNO_TAGNAME(String tAGNOTAGNAME) {
		TAGNO_TAGNAME = tAGNOTAGNAME;
	}
	public void setANATAGS_OP_FORWARD(String aNATAGSOPFORWARD) {
		ANATAGS_OP_FORWARD = aNATAGSOPFORWARD;
	}
	public void setBOOKNO_BOOKID(String bOOKNOBOOKID) {
		BOOKNO_BOOKID = bOOKNOBOOKID;
	}
	public void setTAG_SIMILAR_DONE(String tAGSIMILARDONE) {
		TAG_SIMILAR_DONE = tAGSIMILARDONE;
	}
	public void setBookidResultList(List<String> bookidResultList) {
		this.bookidResultList = bookidResultList;
	}
	public void setSimilarTags(List<String> similarTags) {
		this.similarTags = similarTags;
	}
	
}

