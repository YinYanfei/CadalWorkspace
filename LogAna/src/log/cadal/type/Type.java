package log.cadal.type;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class Type {
	
	public Map map = null;
	public String fileName = "H:/logAna/parament/type.txt";
	
	/**
	 * construct function
	 */
	public Type() {
		this.map = new HashMap();
	}
	
	/**
	 * read file and insert into the map
	 */
	public void readFile() {
		File file = new File(this.fileName);
		
		try{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = reader.readLine()) != null) {
				String str[] = line.split(" ");
				if(str.length == 2) {					
					this.map.put(str[1], str[0]);
				}else {
					continue;
				}
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get type of book
	 */
	public String findType(String bookid) {
		if(this.map.containsKey(bookid)) {
			return (String) this.map.get(bookid);
		}else{
			return "guji";
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		Type type = new Type();
		
		type.readFile();
		
		System.out.println(type.findType("16019982"));

	}

}
