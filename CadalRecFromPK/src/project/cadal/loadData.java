package project.cadal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class loadData {
	public static ArrayList<accessUnit> accessList;
	public static HashMap<String,String> bookinfoTable;
	public static HashMap<String, String> userinfoTable;

	public static ArrayList<accessUnit> loadAccesslog(String filename){
		accessList=new ArrayList<accessUnit>();	
		System.out.println("start to load Accesslog.csv....");//
		File file= new File(filename);
		BufferedReader br = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			br.readLine();
			String[] ss=null;
			String accessReader=null;
			while((accessReader=br.readLine())!=null){
				accessReader=ChineseConvert.simplized(accessReader);
				ss=accessReader.split("\",\"");
				if(ss[1].equals("\\N"))continue;
				accessUnit temp=new accessUnit();;
				temp.username=ss[1];
				temp.bookid=ss[2];
				accessList.add(temp);		
			}
			br.close();//
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("finish  loading Accesslog.csv!!!");//
		return accessList;
		
	}
	public static void loadBookinfo(String filename){
		System.out.println("start to load bookinfo.csv....");//
		File file= new File(filename);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			br.readLine();
			String[] ss=null;
			String accessReader=null;
			while((accessReader=br.readLine())!=null){
				accessReader=ChineseConvert.simplized(accessReader);
				ss=accessReader.split("\",\"");
				if(ss[3]!=null&&!ss[3].equals("")&&!ss[3].equals("\\N"))
				BerkeleyDB.putBook(ss[1],ss[3]);//
			}
			br.close();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		System.out.println("finish  loading bookinfo.csv!!!");
	}
	public static void loadUserinfo(String filename){
		System.out.println("start to load UserInfo.csv....");//
		File file= new File(filename);
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			br.readLine();
			String[] ss=null;
			String accessReader=null;
			while((accessReader=br.readLine())!=null){
				accessReader=ChineseConvert.simplized(accessReader);
				ss=accessReader.split("\",\"");
				if(ss[0].equals("\\N"))continue;
				BerkeleyDB.putUserInfo(ss[0].substring(1),ss[2]);
				
			}
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
		System.out.println("finish  loading userinfo.csv!!!");
	}
}
