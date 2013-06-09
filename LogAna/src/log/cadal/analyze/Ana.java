package log.cadal.analyze;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Ana {

	public void ana(String fileName) {
		// source
		File file = new File("H:/logAna/ini/" + fileName);
		BufferedReader reader = null;
		String str = "";
		
		//destination
		FileWriter writer = null; 
		
		// regular
		String regNum = "/[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]/";
		String regNum2 = "=[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]";
		String regPainting = "PaintingDetail";
		String regVideo = "VideoDetail";
		String regCalligraphy = "CalligraphyWeb";
		String regCover = "/cover/";
		String regJpg = "jpg";
		String regPng = "png";
		String regGif = "gif";
		String regJs = "\\.js";
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			writer = new FileWriter("H:/logAna/mid/" + fileName + ".ana", true);
			
			Pattern patNum = Pattern.compile(regNum);
			Pattern patNum2 = Pattern.compile(regNum2);
			Pattern patPainting = Pattern.compile(regPainting);
			Pattern patVideo = Pattern.compile(regVideo);
			Pattern patCalligraphy = Pattern.compile(regCalligraphy);
			Pattern patCover = Pattern.compile(regCover);
			Pattern patJpg = Pattern.compile(regJpg);
			Pattern patPng = Pattern.compile(regPng);
			Pattern patGif = Pattern.compile(regGif);
			Pattern patJs = Pattern.compile(regJs);
			
			Matcher matNum = null;
			Matcher matNum2 = null;
			Matcher matPainting = null;
			Matcher matVideo = null;
			Matcher matCalligraphy = null;
			Matcher matCover = null;
			Matcher matJpg = null;
			Matcher matPng = null;
			Matcher matGif = null;
			Matcher matJs = null;
			
			String ip = "";
			String book = "";
			String sig = "";
			
			while((str = reader.readLine()) != null){
				if(str.length() > 90) {
					// get ip-addr
					ip = str.substring(0, str.indexOf(' '));
					if(ip.length() > 16 || ip.length() < 8) {
						ip = "0.0.0.0";
					}
					
					// 判断是否为绘画、视屏和书法资源,cover
					matPainting = patPainting.matcher(str);
					matVideo = patVideo.matcher(str);
					matCalligraphy = patCalligraphy.matcher(str);
					matCover = patCover.matcher(str);
					matJpg = patJpg.matcher(str);
					matPng = patPng.matcher(str);
					matGif = patGif.matcher(str);
					matJs = patJs.matcher(str);
					
					if(matPainting.find()) {
						sig = "3";
						book = "00000000";
					}else if(matVideo.find()) {
						sig = "4";
						book = "00000000";
					}else if(matCalligraphy.find()) {
						sig = "5";
						book = "00000000";
					}else if(matCover.find() || matJpg.find() || matPng.find() || matGif.find() || matJs.find()){ // cover/image的排除
						sig = "6";
						book = "00000000";
					}else{
						// 判断是否为图书的阅读
						matNum = patNum.matcher(str);				
						if(matNum.find()) {
							sig = "1";
							book = matNum.group().substring(1, matNum.group().length()-1);
						}else{
							// 判断是否为图书的检索
							matNum2 = patNum2.matcher(str);
							if(matNum2.find()) {
								book = matNum2.group().substring(1, matNum2.group().length());
								sig = "2";
							}else{
								book = "00000000";
								sig = "6";
							}
						}
					}
	 				
					writer.write(sig + "\t" + ip + "\t" + book + "\n");
				}
			}
			
			System.out.println("..........Done..........  " + fileName);
			
			// close file
			reader.close();
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File file = new File("H:/logAna/name/all_ini.txt");  // change
		String fileName = "";
		
		Ana ana = new Ana();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			while((fileName = reader.readLine()) != null) {
				ana.ana(fileName);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		ana.ana(fileName);
	}

}
