package cn.cadal.rec.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 这个类用来对剩下的没有提供标签的图书进行标注
 * 
 * @author D390
 *
 */
public class MarkTags {

	private String DIR = "E:/Recommendation/MarkTags/";
	private String TAGBOOKID = "tagBookid.dat";
	private String CBOOKBOOKID = "cbookBookid.dat";
	private String DESTIN = "store_info.dat";
	private String DESTINTIDY = "store_info_tidy.dat";
	
	private Reader reader = null;
	private IKSegmenter iks = null;
	
	public Map<String, String> pgInfo = null;
	
	public Map<String, Integer> cbookBookid = null;
	public Map<String, Integer> tagBookid = null;
	
	/**
	 * Constructor functions
	 */
	public MarkTags(){
		this.pgInfo = new HashMap<String, String>();
		this.cbookBookid = new HashMap<String, Integer>();
		this.tagBookid = new HashMap<String, Integer>();
	}
	public MarkTags(String dir, String tagbookid, String cbookbookid, String destin) {
		this.DIR =dir;
		this.TAGBOOKID = tagbookid;
		this.CBOOKBOOKID = cbookbookid;
		this.DESTIN = destin;
		
		this.pgInfo = new HashMap<String, String>();
		this.cbookBookid = new HashMap<String, Integer>();
		this.tagBookid = new HashMap<String, Integer>();
	}
	
	/**
	 * Read files
	 */
	public void ReadFiles(){
		this.ReadCbookBookid();
		this.ReadTagBookid();
	}
	private void ReadCbookBookid(){
		File file = new File(this.DIR + this.CBOOKBOOKID);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = reader.readLine().trim()) != null) {
				this.cbookBookid.put(line, 1);
			}
			
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(reader != null)
					reader.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	private void ReadTagBookid(){
		File file = new File(this.DIR + this.TAGBOOKID);
		BufferedReader reader = null;
		String line = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			while((line = reader.readLine().trim()) != null) {
				this.tagBookid.put(line, 1);
			}
			
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(reader != null)
					reader.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Query from pg to find books without tags
	 */
	public void QueryPGCbook(){
		String Username = "cadal";      // dbusername
		String userPasswd = "Cadal205"; // passwd
		String url = "jdbc:postgresql:" + "//10.15.62.71:5432/cadal_metadata_full_dbo2";
		
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println(url);
			con = DriverManager.getConnection(url, Username, userPasswd);
			
			Statement statement = con.createStatement();
			String sql = "SELECT \"BookNo\", \"Title\", \"Creator\", \"Publisher\"FROM \"cbook\";";
			ResultSet rs = statement.executeQuery(sql);
			
			String bookno = null;
			String title = null;
			String creator = null;
			String publisher = null;
			
			if (!rs.next()) {
				System.out.println("No result");
			}else{
				do {
					bookno = rs.getString("BookNo");
					title = rs.getString("Title");
					creator = rs.getString("Creator");
					publisher = rs.getString("Publisher");
					
					if((!this.tagBookid.containsKey(bookno)) && (this.cbookBookid.containsKey(bookno))){
						this.pgInfo.put(bookno, title + " #### " + creator + " #### " + publisher); 
					}
					
				} while(rs.next());
			}

			con.close();
			
			// clear no-use variable
			this.cbookBookid.clear();
			this.tagBookid.clear();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			try{
				if(con != null)
					con.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Segment and write into file
	 */
	public void SegInfo(){
		this.reader = new StringReader("");
		this.iks = new IKSegmenter(reader, true);
		Lexeme lexeme = null;
		
		FileWriter writer = null;
		
		try{
			writer = new FileWriter(this.DIR + this.DESTIN);
			String val = null;
			
			for(String key : this.pgInfo.keySet()){
				writer.write(key + " ");
				val = this.pgInfo.get(key);
				
				String [] valSplit = val.split(" #### ");
				for(int i = 0; i< valSplit.length; ++i) {
					this.iks.reset(new StringReader(valSplit[i]));
					while((lexeme = iks.next())!=null){
						String str = lexeme.getLexemeText();
						if(str.length() > 1){ 
							writer.write(str + " | ");
						}
					}
					writer.write(" #### ");
				}
				writer.write("\n");
			}
			
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(writer != null) {
					writer.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 只取title部分作为图书的标签
	 */
	public void TagsTidy(){
		File file = new File(this.DIR + this.DESTIN);
		BufferedReader reader = null;
		FileWriter fileWriter = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			fileWriter = new FileWriter(this.DIR + this.DESTINTIDY);
			String line = null;
			
			while((line = reader.readLine()) != null){
				String [] lineSplit = line.split("####");
				String [] bookidTags = lineSplit[0].split(" ");
				String bookid = bookidTags[0];
				fileWriter.write(bookid + " ");
				
				if(bookidTags.length > 1){
					String [] tags = bookidTags[1].split("\\|");
					
					for(int i = 0; i < tags.length; ++i) {
						fileWriter.write(tags[i] + "$");
					}
				}
				fileWriter.write("\n");
			}

			reader.close();
			fileWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(reader != null)
					reader.close();
				if(fileWriter != null)
					fileWriter.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获得新的标签名称与标签编号的map文件
	 */
	@SuppressWarnings("unchecked")
	public void GetTagnameTagnoFile(){
		String fileTagsNew = "store_info_tidy.dat";
		String mapFileOld = "tagno_tagname.map";
		String mapFileNew = "tagno_tagname_new.map";
		
		int maxNo = 0;
		Map<String, Integer> tagnoTagnameMap = new HashMap<String, Integer>();
		
		File file = new File(this.DIR + mapFileOld);
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null){
				String [] lineSplit = line.split(" ");
				maxNo = Integer.valueOf(lineSplit[0]);
				tagnoTagnameMap.put(lineSplit[1], Integer.valueOf(lineSplit[0]));
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println(tagnoTagnameMap.size());
		
		file = new File(this.DIR + fileTagsNew);
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null){
				String [] lineSplit = line.split(" ");
				String [] tagsArr = null;
				if(lineSplit.length > 1){
					tagsArr = lineSplit[1].split("\\$");
					for(int i = 0; i < tagsArr.length; ++i) {
						if(!(tagnoTagnameMap.containsKey(tagsArr[i]))) {
							maxNo++;
							tagnoTagnameMap.put(tagsArr[i], maxNo);
						}
					}
				}
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println(tagnoTagnameMap.size());
		
		// Store into file
		FileWriter fileWriter = null;
		try{
			fileWriter = new FileWriter(this.DIR + mapFileNew);
			
			Iterator iter = tagnoTagnameMap.entrySet().iterator(); 
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iter.next(); 
			    Object key = entry.getKey(); 
			    Object val = entry.getValue();
			    fileWriter.write(val + " " + key + "\n");
			} 
			
			fileWriter.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获得标签与图书的对应关系
	 * 		-- 便签与图书全部都用no来表示
	 */
	@SuppressWarnings("unchecked")
	public void GetTagnoBookno() {
		String fileBookTag = "ana_totalTags.dat";
		String fileTagBook = "ana_totalTags_op_forward.dat";
		String bookidBookno = "bookno_bookid.map";
		String tagnameTagno = "tagno_tagname_new.map";
		
		Map<String, Integer> bookidBooknoMap = new HashMap<String, Integer>();
		Map<String, Integer> tagnameTagnoMap = new HashMap<String, Integer>();
		
		Map<Integer, List<Integer>> tagnoListBooknoMap = new HashMap<Integer, List<Integer>>();
		
		// 读取两个map文件
		File file = new File(this.DIR + bookidBookno);
		BufferedReader reader = null;
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = reader.readLine()) != null){
				String [] lineSplit = line.split(" ");
				bookidBooknoMap.put(lineSplit[1], Integer.valueOf(lineSplit[0]));
			}
			
			reader.close();
			
			//
			file = new File(this.DIR + tagnameTagno);
			reader = new BufferedReader(new FileReader(file));
			while((line = reader.readLine()) != null){
				String [] lineSplit = line.split(" ");
				tagnameTagnoMap.put(lineSplit[1], Integer.valueOf(lineSplit[0]));
			}
			
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("finish reading map");
		System.out.println(bookidBooknoMap.size());
		System.out.println(tagnameTagnoMap.size());
		
		// 读取已有的图书-标签的对应关系，得到标签图书的对应关系
		file = new File(this.DIR + fileBookTag);
		int bookno = 0;
		try{
			reader = new BufferedReader(new FileReader(file));
			String line = null;
			
			while((line = reader.readLine()) != null) {
				String [] lineSplit = line.split(" ");
				bookno = bookidBooknoMap.get(lineSplit[0]);
				if(lineSplit.length > 1){
					String [] tagsArr = lineSplit[1].split("\\$");
					for(int i = 0; i < tagsArr.length; i++) {
						int tagno = tagnameTagnoMap.get(tagsArr[i]);
						
						if(tagnoListBooknoMap.containsKey(tagno)) {
							tagnoListBooknoMap.get(tagno).add(bookno);
						}else{
							List<Integer> tagArr = new ArrayList<Integer>();
							tagArr.add(bookno);
							tagnoListBooknoMap.put(tagno, tagArr);
						}
					}
				}
			}
			
			reader.close();
		}catch(Exception e) {
			System.out.println(bookno);
			e.printStackTrace();
		}
		
		bookidBooknoMap.clear();
		tagnameTagnoMap.clear();
		
		System.out.println("finish deal");
		
		// store into file
		FileWriter fileWriter = null;
		
		try{
			fileWriter = new FileWriter(this.DIR + fileTagBook);
			
			Iterator iter = tagnoListBooknoMap.entrySet().iterator(); 
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iter.next(); 
			    Object key = entry.getKey();
			    List<Integer> val = (List<Integer>) entry.getValue();
			    
			    fileWriter.write(key + " ");
			    for(int i = 0; i < val.size(); ++i) {
			    	fileWriter.write(val.get(i) + " ");
			    }
			    fileWriter.write("\n");
			} 
			
			fileWriter.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		System.out.println("store");
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test
		MarkTags mt = new MarkTags();
//		
//		mt.ReadFiles();
//		System.out.println("Finish reading file!");
//		
//		mt.QueryPGCbook();
//		System.out.println("Finish query information from postgreSQL!");
//		
//		mt.SegInfo();
//		System.out.println("Segment finished!");
		
//		mt.TagsTidy();
		
//		mt.GetTagnameTagnoFile();
		
		mt.GetTagnoBookno();
		
	}
	
	/**
	 * Getter and Setter
	 */
	public Reader getReader() {
		return reader;
	}
	public IKSegmenter getIks() {
		return iks;
	}
	public Map<String, String> getPgInfo() {
		return pgInfo;
	}
	public Map<String, Integer> getCbookBookid() {
		return cbookBookid;
	}
	public Map<String, Integer> getTagBookid() {
		return tagBookid;
	}
	public void setReader(Reader reader) {
		this.reader = reader;
	}
	public void setIks(IKSegmenter iks) {
		this.iks = iks;
	}
	public void setPgInfo(Map<String, String> pgInfo) {
		this.pgInfo = pgInfo;
	}
	public void setCbookBookid(Map<String, Integer> cbookBookid) {
		this.cbookBookid = cbookBookid;
	}
	public void setTagBookid(Map<String, Integer> tagBookid) {
		this.tagBookid = tagBookid;
	}

}
