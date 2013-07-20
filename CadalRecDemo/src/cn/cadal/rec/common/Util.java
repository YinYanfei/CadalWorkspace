package cn.cadal.rec.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Util {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File f = new File("D:\\CADAL\\Recommendation\\common\\booklist1");
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader(f));
			String line = "";
			int count = 0;
			while((line = br.readLine())!=null){
				String[] temp = line.split(" ");
				count += temp.length;
			}
			br.close();
			System.out.println("length of booklist: " + count);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
