package cn.cadal.dis.java.uv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import cn.cadal.dis.java.cassandra.CassandraOp;

public class DataUV {
	
	private CassandraOp co = null;
	
	public DataUV(){
		this.co = new CassandraOp();
	}
	
	// Current Data of uv
	public Map<String, ArrayList<String>> CurrentUV(){
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String min = sDateFormat.format(new java.util.Date());
		
		System.out.println(min);
		
		return this.CurrentUV(min);
	}
	
	// Allocate Time to get data of 
	public Map<String, ArrayList<String>> CurrentUV(String min) {
		try{
			this.co.QueryRecordMinute(min);
			return this.co.MapUserBook;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return this.co.MapUserBook;
	}
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		DataUV duv = new DataUV();
		
		Map<String, ArrayList<String>> mapTmp = duv.CurrentUV();
		
		Iterator iter = mapTmp.keySet().iterator(); 
		while (iter.hasNext()) { 
		    Object key = iter.next(); 
		    Object val = mapTmp.get(key);
		    
		    System.out.println("-------------------------------");
		    
		    System.out.println(key);
		    ArrayList<String> alTmp = (ArrayList<String>) val;
		    System.out.println("========");
		    for(int i = 0; i < alTmp.size(); ++i) {
		    	System.out.println(alTmp.get(i));
		    }
		}
	}
}
