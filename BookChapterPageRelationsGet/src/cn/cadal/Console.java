package cn.cadal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Console {

	/**
	 * @param args
	 */
	static final ArrayList<String> list = new ArrayList<String>();
	
	/**
	 * Read bookno and pageno information from file
	 */
	public static void getInfo() {
		File file = new File("H:/test/bookandpage.txt");
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;

			while ((tempString = reader.readLine()) != null) {
				list.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		
	}
	
	/**
	 * Main Function
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		getInfo();
		PageChapterMatch test = new PageChapterMatch(null,0);
		
		for(int i = 0; i<list.size();i++){
			String[] tmp = list.get(i).split(" ");
			String bookNo = tmp[0];
			test.setBookNo(bookNo);
			for(int j = 1;j<tmp.length;j++){
				int pageNo = Integer.parseInt(tmp[j]);
				test.setPageNo(pageNo);
				test.getChapterInfo();
			}
		}
		
		test.tr.close();
	}


}
