package shan.data.firstOp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecialOp {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String fileName = "E:/baiduyundownload/2013-12-23 最新数据更新/tmp.csv";
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
		FileWriter writer = new FileWriter("E:/baiduyundownload/2013-12-23 最新数据更新/tmp_2.csv", true);
		
		String line = "";
		
		Pattern pat = Pattern.compile("(.*)\"(.*)\"(.*)");	
		while ((line = reader.readLine()) != null) {
			Matcher mat = pat.matcher(line);
			String str = "";
			
			if(mat.find()) {
				str = mat.group(2);
				
				str = str.replaceAll(",", "-");
				
				writer.write(mat.group(1) + str + mat.group(3) + "\n");
			}
		}
		
		writer.close();
		reader.close();
	}
	

}
