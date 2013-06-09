package cn.cadal.storm.exp.main;

import java.io.FileWriter;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.cadal.storm.exp.cassandra.QueryBCPRelation;
import cn.cadal.storm.exp.pg.bookTitle.BookTitle;
import cn.cadal.storm.exp.readSource.ReadSourceFile;

public class MainProcess {
	
	static private final Log LOG = LogFactory.getLog(MainProcess.class);
	
	private String EXP_ADDRESS = "H:/Exp/ClustersResult_v2/";
	
	private ReadSourceFile rsf = null;
	private BookTitle bt = null;
	private QueryBCPRelation qbr = null;
	
	/**
	 * Contrunct function
	 */
	public MainProcess() {
		this.rsf = new ReadSourceFile();
		this.bt = new BookTitle();
		this.qbr = new QueryBCPRelation();
	}
	
	/**
	 * Write into file
	 */
	private void WriteIntoFile(String filename, List<String> listContent){
		try{
			FileWriter writer = new FileWriter(this.EXP_ADDRESS + filename + ".txt", true); 
			
			for(int i = 0; i < listContent.size(); ++i) {
				writer.write(listContent.get(i));
				writer.write("\n");
			}
			
			writer.write("---------------------------------------\n");
			
			writer.close();
		}catch(Exception e) {
			LOG.warn("Write into file: " + filename);
			e.printStackTrace();
		}
	}
	
	public void Process(){
		this.rsf.ReadFile();
		
		for(int i = 0; i < this.rsf.bookChapterList.size(); ++i) {
			
			List<String> singleLineList = this.rsf.bookChapterList.get(i);
			
			System.out.println(singleLineList.get(0));
			
			List<String> bookTitle = this.bt.GetBookTitle(singleLineList);
			
			// V2
			// List<String> bookChapter = this.qbr.QueryFromBCPRelation(singleLineList);
			
			this.WriteIntoFile(singleLineList.get(0), bookTitle);
			this.WriteIntoFile(singleLineList.get(0), singleLineList);
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Main
		MainProcess mp = new MainProcess();
		
		mp.Process();

		System.out.println("Done");
	}

}
