package log.cadal.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Main {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String sourceFileName = "H:/logAna/name/all_mid.txt";  // change 
		AnaAndCount aac = new AnaAndCount();
		
		File file = new File(sourceFileName);
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String fileName = "";
			
			while((fileName = reader.readLine()) != null){
				System.out.println("dealing......" + fileName);
				
				aac.logAna("H:/logAna/mid/" + fileName);
			}
			
			aac.school.writerIntoFile("H:/logAna/res/res_all.txt");   // change
			
			System.out.println("End");
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
