package getBookId;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookId {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("D:/LuceneSearch/downbook/file/bookid.txt");
		
		
		String reg = "[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			FileWriter writer = new FileWriter("D:/LuceneSearch/downbook/file/bookid_res.txt", true); 
			
			Pattern bat = Pattern.compile(reg);
			Matcher mat = null;
			
			String line = reader.readLine();
			mat = bat.matcher(line);
			
			while(mat.find()){
				writer.write(mat.group() + "\n");
			}
			
			reader.close();
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}

}
