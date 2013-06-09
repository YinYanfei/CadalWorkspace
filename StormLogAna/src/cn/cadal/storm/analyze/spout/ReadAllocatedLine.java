package cn.cadal.storm.analyze.spout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.LineNumberReader;
import java.util.ArrayList;

import backtype.storm.utils.Utils;

/**
 * @author Yanfei
 *  -- Define: FILELISTADDRESS
 *  -- Useing: Recall function ReadAppointedLineNumber
 *  -- Return: All lines of file in FILELISTADDRESS, return one by one from the first line of the first file
 *  		   to the last line of the last file in FILELISTADDRESS.
 */

public class ReadAllocatedLine {
	// Control which file to read
	static String FILELISTADDRESS = "/home/addr.txt";
//	static String FILELISTADDRESS = "H:/test/addr.txt";
	static ArrayList<String> READFILELIST = new ArrayList<String>();
	static int INDEXOFLIST = 0;
	
	static String FINISHFILE = "/home/finish.txt";
//	static String FINISHFILE = "H:/test/finish.txt";
	
	static ArrayList<String> LINESOFFILE = null;
	
	// Control read one file
	static String FILENAME = "";
	static int LINENUMBER = 0;
	static int TOTALLINENUMBER = 0;
	
	// End signal
	static int SIGNAL = 1;
	
	// Read TOTALLINENUMBER to get READFILELIST
	@SuppressWarnings("static-access")
	public ReadAllocatedLine(){
		this.LINESOFFILE = new ArrayList<String>();
		
		System.out.println("=====================ReadAllocatedLine========================");
		this.READFILELIST = new ArrayList<String>();
		File file = new File(this.FILELISTADDRESS);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String tmp = "";
			
			while((tmp = reader.readLine()) != null){
				this.READFILELIST.add(tmp);
			}
			
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
		
		this.FILENAME = this.READFILELIST.get(INDEXOFLIST);
		this.getTotalLines();
		
		// write into finish file
		this.WriteFinish(FILENAME);
	}
	
	// Reset Static number
	@SuppressWarnings("static-access")
	public void ResetStatic(){
		this.INDEXOFLIST += 1;
		if(this.INDEXOFLIST < this.READFILELIST.size()){
			
			this.LINESOFFILE.clear();
			this.LINESOFFILE = null;
			this.LINESOFFILE = new ArrayList<String>();
			
			Utils.sleep(50000);
		
			this.FILENAME = this.READFILELIST.get(INDEXOFLIST);
			
			// write into finish file
			this.WriteFinish(FILENAME);
			
			this.getTotalLines();
		}else{
			this.SIGNAL = 0;
		}
		
		this.LINENUMBER = 0;
	}

	// Read line number LINENUMBER from file FILENAME, and return allocated file
	@SuppressWarnings("static-access")
	public String ReadAppointedLineNumber(){
		if(this.SIGNAL == 0){
			Utils.sleep(10000000);
			return "";
		}
		
		String result = "";
		System.out.println(this.LINENUMBER + "  " + this.TOTALLINENUMBER);
		try{
			if (this.LINENUMBER >= this.TOTALLINENUMBER) {
				this.ResetStatic();
				result = this.LINESOFFILE.get(LINENUMBER);
				this.LINENUMBER++;
			}else{
				result = this.LINESOFFILE.get(LINENUMBER);
				this.LINENUMBER++;
			}
			
			return result;
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
		
	}
	
	// Get Total Line Number
	static void getTotalLines() {
		
		// Write Back READFILELIST
		try {
			FileWriter writer = new FileWriter(FILELISTADDRESS);
			for(int i = INDEXOFLIST; i < READFILELIST.size(); ++i) {
				writer.write(READFILELIST.get(i));
				writer.write("\n");
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try{
			FileReader in = new FileReader(FILENAME);
			LineNumberReader reader = new LineNumberReader(in);
			String s = reader.readLine();
			LINESOFFILE.add(s);
			int lines = 0;
			while (s != null) {
				lines++;
				s = reader.readLine();
				LINESOFFILE.add(s);
			}
			
			reader.close();
			in.close();
			
			TOTALLINENUMBER = lines;
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Write the finish filename into file
	 */
	public void WriteFinish(String filename) {
		try {
			FileWriter writer = new FileWriter(FINISHFILE, true);
			writer.write(filename);
			writer.write("\n");
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ReadAllocatedLine ral = new ReadAllocatedLine();
		
		// test -- getTotalLines
//		ral.getTotalLines();
//		System.out.println(ral.TOTALLINENUMBER);
//		System.out.println("-------------------------");
		
		// test -- ReadAllocatedLine
//		System.out.println(ral.READFILELIST.size());
//		for(int i = 0; i < ral.READFILELIST.size(); ++i){
//			System.out.println(ral.READFILELIST.get(i));
//		}
//		System.out.println("-------------------------");
		
		// test -- ReadAppointedLineNumber
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		System.out.println(ral.ReadAppointedLineNumber());
		
		// Write Back READFILELIST
		try {
			FileWriter writer = new FileWriter("H:/test/addr2.txt");

			writer.write("first");
			writer.write("\n");
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			FileWriter writer = new FileWriter("H:/test/addr2.txt");
			
			writer.write("first");
			writer.write("\n");
			writer.write("second");
			writer.write("\n");
			
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
