package cn.edu.zju.cadal.test.utils;

import java.util.HashMap;

public class UserInfo {
	String username;
	long timeMillis;
	HashMap<String,String> info;
	
	public UserInfo(){
		this.timeMillis = System.currentTimeMillis();
	}

	public String getUser(){
		return username;
	}
	
	public long getTime(){
		return this.timeMillis;
	}
	
	public HashMap<String,String> getInfo(){
		return this.info;
	}
	
	public void setUser(String user){
		this.username = user;
	}
	
	public void setInfo(HashMap<String,String> info){
		this.info = info;
	}

	public String toString(){
		return this.username +" " + this.timeMillis + " " + info.get("age") + " " + info.get("hometown");
	}
	//test
	public static void main(String[] args){
		UserInfo k = new UserInfo();
		k.setUser("hongxin");
		HashMap<String,String> info = new HashMap<String,String>();
		info.put("hometown", "hz");
		info.put("age","21");
		k.setInfo(info);

		System.out.println(k.toString());
	}
}
