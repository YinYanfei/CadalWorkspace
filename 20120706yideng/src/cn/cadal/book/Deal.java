package cn.cadal.book;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Deal {
	public String file1 = "E:/H/yideng0707/dump3.txt";
	public String file2 = "E:/H/yideng0707/bookindex2.txt"; //"H:/bookno.txt";
	public String file3 = "E:/H/yideng0707/bookindex2_result.txt";
	
	public HashMap hm = new HashMap<String, String>();
	public List list = new ArrayList();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Deal deal = new Deal();
		
		// 读取已知的bookno与bookname，保存在hashmap中  ---- 严重乱码
		File f1 = new File(deal.file1);
		BufferedReader reader = null;
		
		try {
			FileInputStream ff1 = new FileInputStream(f1);
			reader = new BufferedReader(new InputStreamReader(ff1,"utf-8"));
			
			String tmpStr = null;
			while((tmpStr = reader.readLine()) != null) {
				String strArr[] = tmpStr.split("\",\"");
				
				if(strArr.length == 2){
					// System.out.println(tmpStr + " --> " + strArr[0].substring(1) + "   " + strArr[1].substring(0, strArr[1].length() - 1));		
					deal.hm.put(strArr[0].substring(1), strArr[1].substring(0, strArr[1].length() - 1));
				}
			}
			reader.close();
			
			System.out.println("------ finish one ------" + deal.hm.size());
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		// 读取要处理的bookno序列，保存在list中
		File f2 = new File(deal.file2);
		BufferedReader reader2 = null;
		
		try{
			reader2 = new BufferedReader(new FileReader(f2));
			
			String strTemp = null;
			while((strTemp = reader2.readLine()) != null) {
				deal.list.add(strTemp);
			}
			reader2.close();
			
			System.out.println("------ finish two ------" + deal.list.size());
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		// 处理并保存文件
		try{
			FileWriter writer = new FileWriter(deal.file3, true);
			int size = deal.list.size();
			for(int i = 0; i < size; ++i){
				writer.write(deal.list.get(i) + " " + deal.hm.get(deal.list.get(i)) + "\n");
			}
			
			writer.close();
			
			System.out.println("----- end -----");
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}

}
