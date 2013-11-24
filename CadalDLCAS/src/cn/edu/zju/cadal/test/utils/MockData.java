package cn.edu.zju.cadal.test.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MockData {

	public List<String> username;
	public List<String> age;
	public String[] hometown = {"zj","sd","ah","gd","bj","sh","tj","hn","jl","hlj","gs","xz","xj","qh","fj"};
	public Random rd;
	public MockData(){
		rd = new Random();
		username = new ArrayList<String>();

		age = new ArrayList<String>();
		for(int i=0;i<50;i++){
			age.add(""+ (i+15));
		}
	}
	
	public UserInfo getOneData(){
		UserInfo user = new UserInfo();
		user.setUser("" + rd.nextInt(1000000));
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("age",age.get(rd.nextInt(50)));
		map.put("hometown",hometown[rd.nextInt(15)]);
		user.setInfo(map);
		return user;
	}
	
	//for test
	public static void main(String[] args) {
		MockData mockData = new MockData();
		for(int i=0;i<100000;i++){
			System.out.println(mockData.getOneData().toString());
		}
	}
}