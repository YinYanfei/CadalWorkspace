package cn.cadal.rec.coldstart;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class ColdStartByTags {

	private String DIR = "D:\\CADAL\\Recommendation\\common\\Tags\\";
	private String TAGNOTAGNAME = "tagno_tagname.map";
	private String TAGNOBOOKNO = "ana_totalTags_op_forward.dat";
	private String BOOKNOBOOKID = "bookno_bookid.map";
	
	public Map<String, Integer> tagnameTagnoMap = null;
	public Map<Integer, String> tagnoBooknoStrMap = null;
	public Map<Integer, String> booknoBookidMap = null;
	
	private Reader reader = null;
	private IKSegmenter iks = null;
	
	/**
	 * Constructor functions
	 */
	public ColdStartByTags(){
		this.tagnameTagnoMap = new HashMap<String, Integer>();
		this.tagnoBooknoStrMap = new HashMap<Integer, String>();
		this.booknoBookidMap = new HashMap<Integer, String>();
		
		this.reader = new StringReader("");
		this.iks = new IKSegmenter(reader, true);
	}
	
	/**
	 * This function is used to read files prepared
	 */
	public void ReadFile(){
		this.ReadTagnoTagname();
		this.ReadTagnoBookno();
		this.ReadBooknoBookid();
		
		System.out.println("Finish reading files");
	}
	private void ReadTagnoTagname(){
		File file = new File(this.DIR + this.TAGNOTAGNAME);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			
			while((line = reader.readLine()) != null){
				String [] lineSplit = line.split(" ");
				this.tagnameTagnoMap.put(lineSplit[1], Integer.valueOf(lineSplit[0]));
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void ReadTagnoBookno(){
		File file = new File(this.DIR + this.TAGNOBOOKNO);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			
			while((line = reader.readLine()) != null){
				this.tagnoBooknoStrMap.put(Integer.valueOf(line.substring(0, line.indexOf(" "))), line.substring(line.indexOf(" ") + 1));
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private void ReadBooknoBookid() {
		File file = new File(this.DIR + this.BOOKNOBOOKID);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = "";
			
			while((line = reader.readLine()) != null){
				String [] lineSplit = line.split(" ");
				this.booknoBookidMap.put(Integer.valueOf(lineSplit[0]), lineSplit[1]);
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * To segment tag, which is not in this.tagnameTagnoMap
	 * 
	 * @param tagSpecial
	 * @return
	 */
	public String SegStr(String tagSpecial){
		System.out.println("Segment: " + tagSpecial);
		
		this.iks.reset(new StringReader(tagSpecial));
		Lexeme lexeme = null;
		String resStr = "";
		try{
			while((lexeme = iks.next())!=null){
				String str = lexeme.getLexemeText();
				if(str.length() > 1){ 
					resStr += str + " ";
				}
			}
			
			return resStr;
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * To get recommendation for new users
	 * 
	 * @param userTags
	 * @return
	 */
	public List<String> ColdRecommendation(List<String> userTags) {
		List<String> bookidList = new ArrayList<String>();
		
		for(int i = 0; i < userTags.size(); ++i) {
			
			if(this.tagnameTagnoMap.containsKey(userTags.get(i))) {
				int tagno = this.tagnameTagnoMap.get(userTags.get(i));
				String booknoStr = this.tagnoBooknoStrMap.get(tagno);
				
				String [] booknoArr = booknoStr.split(" ");
				
				for(int j = 0; j < booknoArr.length; ++j) {
					bookidList.add(this.booknoBookidMap.get(Integer.valueOf(booknoArr[j])));
				}
			}else{
				String strSeg = this.SegStr(userTags.get(i));
				
				String [] strSegArr = strSeg.split(" ");
				
				System.out.println(strSegArr.length);
				
				for(int j = 0; j < strSegArr.length; ++j){
					if(this.tagnameTagnoMap.containsKey(strSegArr[j])){
						int tagno = this.tagnameTagnoMap.get(strSegArr[j]);
						String booknoStr = this.tagnoBooknoStrMap.get(tagno);
						
						String [] booknoArr = booknoStr.split(" ");
						
						for(int x = 0; x < booknoArr.length; ++x) {
							bookidList.add(this.booknoBookidMap.get(Integer.valueOf(booknoArr[x])));
						}
					}
				}
			}
		}
		
		return bookidList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test
		List<String> userTags = new ArrayList<String>();
		
//		userTags.add("���");
		userTags.add("�ʽ��ȱ");
		
		ColdStartByTags cst = new ColdStartByTags();
		cst.ReadFile();
		List<String> coldRecBookid = cst.ColdRecommendation(userTags);
		
		System.out.println("Recommendation Size: " + coldRecBookid.size());
		
//		for(int i = 0; i < coldRecBookid.size(); ++i) {
//			System.out.println(coldRecBookid.get(i));
//		}
	}

}
