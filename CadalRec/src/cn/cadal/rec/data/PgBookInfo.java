package cn.cadal.rec.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.StringReader;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

/**
 * 使用IKAnalyzer分词器对cbook表中的图书信息进行分词
 * 
 * @author D390
 *
 */
public class PgBookInfo {
	public GetMetadata getMetadata = null;
	
	private String db_to_file = "E:/Recommendation/ExpData/cbookInfo.dat";
	private String segment_file = "E:/Recommendation/ExpData/cbookInfo_seg.dat";
	
	private Reader reader = null;
	private IKSegmenter iks = null;
	
	/**
	 * Construct function
	 */
	public PgBookInfo(){
	}
	public PgBookInfo(String db_to_file, String segment_file){
		this.db_to_file = db_to_file;
		this.segment_file = segment_file;
	}
	
	/**
	 * Search from postgresql to restore some columes of 'cbook' into file
	 */
	public void SearchDBWriteIntoFile() {
		this.getMetadata = new GetMetadata();
		this.getGetMetadata().MetaData(this.getDb_to_file());
	}
	
	/**
	 * Segment information of each book
	 */
	public void SegmentInfoOfBook(){
		this.reader = new StringReader("");
		this.iks = new IKSegmenter(reader, true);
		
		File file = new File(this.db_to_file);
		BufferedReader fileReader = null;
		FileWriter fileWriter = null; 
		Lexeme lexeme = null; 
		
		try{
			fileReader = new BufferedReader(new FileReader(file));
			fileWriter = new FileWriter(this.segment_file, true); 
			
			String line = null;
			int count = 1;
			while((line = fileReader.readLine()) != null) {
				String[] lineSplit = line.split("####");
				System.out.println("count: " + (count++));
				fileWriter.write(lineSplit[0]+" #### ");
				// Title
				this.iks.reset(new StringReader(lineSplit[1]));
				while((lexeme = iks.next())!=null) 
			    	fileWriter.write(lexeme.getLexemeText() + " | ");
				fileWriter.write(" #### ");
				// Creator
				this.iks.reset(new StringReader(lineSplit[2]));
				while((lexeme = iks.next())!=null) 
			    	fileWriter.write(lexeme.getLexemeText() + " | ");
				fileWriter.write(" #### ");
				// Publisher
				this.iks.reset(new StringReader(lineSplit[3]));
				while((lexeme = iks.next())!=null) 
			    	fileWriter.write(lexeme.getLexemeText() + " | ");
				fileWriter.write(" #### ");
				// Relation
				this.iks.reset(new StringReader(lineSplit[4]));
				while((lexeme = iks.next())!=null) 
			    	fileWriter.write(lexeme.getLexemeText() + " | ");
				fileWriter.write(" #### ");
				// Type
				fileWriter.write(lineSplit[5] + "\n");
			}
			
			fileWriter.close();
			fileReader.close();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if (fileReader != null)
					fileReader.close();
				if(fileWriter != null)
					fileWriter.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Main function
	 */
	public static void main(String [] args) {
//		PgBookInfo pbi = new PgBookInfo();
		// To get meta-data from postgresql
//		pbi.SearchDBWriteIntoFile();
		
		// Segment meta-data of book information
//		pbi.SegmentInfoOfBook(); // 现在在TextSimilarity.java中使用的基于单个汉子的余弦距离，所以这个步骤中的分词工作展示不需要了！
	}
	
	/**
	 * Getter and Setter
	 */
	public GetMetadata getGetMetadata() {
		return getMetadata;
	}
	public String getDb_to_file() {
		return db_to_file;
	}
	public void setGetMetadata(GetMetadata getMetadata) {
		this.getMetadata = getMetadata;
	}
	public void setDb_to_file(String dbToFile) {
		db_to_file = dbToFile;
	}
	public String getSegment_file() {
		return segment_file;
	}
	public Reader getReader() {
		return reader;
	}
	public IKSegmenter getIks() {
		return iks;
	}
	public void setSegment_file(String segmentFile) {
		segment_file = segmentFile;
	}
	public void setReader(Reader reader) {
		this.reader = reader;
	}
	public void setIks(IKSegmenter iks) {
		this.iks = iks;
	}

}
