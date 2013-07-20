package cn.cadal.rec.freqitem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class FreqItemRecResult {

	private String DIR = "D:/CADAL/Recommendation/common/FreqItemset/";
	private String TWOITEM = "10.res.grep.deal.2.dat";
	private String FOURITEM = "10.res.grep.deal.4.dat";
	
	public List<String> twoItemRec = null;
	public List<String> fourItemRec = null;
	
	/**
	 * Constructor functions
	 */
	public FreqItemRecResult(){
		this.twoItemRec = new ArrayList<String>();
		this.fourItemRec = new ArrayList<String>();
	}
	public FreqItemRecResult(String twoItem, String fourItem){
		this.TWOITEM = twoItem;
		this.FOURITEM = fourItem;
		this.twoItemRec = new ArrayList<String>();
		this.fourItemRec = new ArrayList<String>();
	}
	
	/**
	 * This function is used to find the result of recommendation for two-item-frequent.
	 * 
	 * @param bookid
	 */
	public void FindTwoItemFreqRec(String bookid){
		File file = new File(this.DIR + this.TWOITEM);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			
			while((line = reader.readLine()) != null) {
				String [] lineSplit = line.split(" ");
				if(lineSplit[0].equals(bookid)) {
					this.twoItemRec.add(lineSplit[1]);
				}
				if(lineSplit[1].equals(bookid)) {
					this.twoItemRec.add(lineSplit[0]);
				}
			}
			
			// delete repeat item
			this.removeRepeat(this.twoItemRec);
			
			reader.close();
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
	}
	
	/**
	 * This function is used to find the result of recommendation for four-item-frequent.
	 * 
	 * @param bookid
	 */
	public void FindFourItemFreqRec(String bookid) {
		File file = new File(this.DIR + this.FOURITEM);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			
			while((line = reader.readLine()) != null){
				String [] lineSplit = line.split(" ");
				
				if(lineSplit[0].equals(bookid) || lineSplit[1].equals(bookid) || 
				   lineSplit[2].equals(bookid) || lineSplit[3].equals(bookid)) {
					this.fourItemRec.add(lineSplit[0]);	this.fourItemRec.add(lineSplit[1]);
					this.fourItemRec.add(lineSplit[2]); this.fourItemRec.add(lineSplit[3]);
				}
			}
			
			// delete repeat item from list
			this.removeRepeat(this.fourItemRec);
			this.fourItemRec.remove(bookid);
			
			reader.close();
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
	 * Remove repeat item of list
	 * 
	 * @param arlList
	 */
	@SuppressWarnings("unchecked")
	private void removeRepeat(List arlList) {
		HashSet h = new HashSet(arlList);
		arlList.clear();
		arlList.addAll(h);
	}
	
	/**
	 * This function is the port for web-service.
	 * 
	 * @param bookid
	 */
	public void Connector(String bookid){
		this.FindTwoItemFreqRec(bookid);
		System.out.println("Two frequent item can be used by scan this.twoItemRec");
		
		this.FindFourItemFreqRec(bookid);
		System.out.println("Four frequent item can be used by scan this.fourItemRec");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test
		FreqItemRecResult firr = new FreqItemRecResult();
		String bookid = "06200072";
		
//		firr.Connector(bookid);
		
		System.out.println("Two-item-freq result: ");
		firr.FindTwoItemFreqRec(bookid);
		for(String item : firr.twoItemRec) {
			System.out.println(item);
		}
		
		System.out.println("Four-item-freq result: ");
		firr.FindFourItemFreqRec(bookid);
		for(String item : firr.fourItemRec) {
			System.out.println(item);
		}
	}
	
	/**
	 * Getter and Setter
	 */
	public String getDIR() {
		return DIR;
	}
	public String getTWOITEM() {
		return TWOITEM;
	}
	public String getFOURITEM() {
		return FOURITEM;
	}
	public List<String> getTwoItemRec() {
		return twoItemRec;
	}
	public List<String> getFourItemRec() {
		return fourItemRec;
	}
	public void setDIR(String dIR) {
		DIR = dIR;
	}
	public void setTWOITEM(String tWOITEM) {
		TWOITEM = tWOITEM;
	}
	public void setFOURITEM(String fOURITEM) {
		FOURITEM = fOURITEM;
	}
	public void setTwoItemRec(List<String> twoItemRec) {
		this.twoItemRec = twoItemRec;
	}
	public void setFourItemRec(List<String> fourItemRec) {
		this.fourItemRec = fourItemRec;
	}
}


