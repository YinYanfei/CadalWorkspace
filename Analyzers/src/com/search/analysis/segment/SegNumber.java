package com.search.analysis.segment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.search.analysis.util.CreateHashMap;

public class SegNumber extends Segment{

	/**
	 * Construct Function
	 */
	public SegNumber(String url) {
		this.hashMap = new CreateHashMap(url + "NumberDict.txt", url + "reuse.txt");
	}
	
	/**
	 * Search Function
	 * �������ֵ�ʶ����������������ıȽϵĴ��ԣ��д��Ľ� ---- ������С������Ϊ�ֽ����������ʶ��.(����)
	 */
	public boolean searchFun(String number) {
		boolean signal = true;
		
		String reg = "^[0-9,.,������,��]+$";			// �������ֵ�ƥ��
		
		Pattern pattern = Pattern.compile(reg);
		Matcher ma = pattern.matcher(number);
		
		if(ma.find()){
			signal = true;
		}else{
			// ���������Լ�������������ֵ�ƥ��
			for(int i = 0; signal && i < number.length(); i++) {
				if(this.hashMap.search(number.substring(i, i + 1))){
					continue;
				}else{
					signal = false;
					break;
				}
			}
		}
		
		return signal;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SegNumber segNumber = new SegNumber("dict\\Number\\");
		
		System.out.println(segNumber.searchFun("��"));
		/*
		String reg = "^[0-9,.,������,��]+$";
		String number = "90.����";
		
		Pattern pattern = Pattern.compile(reg);
		Matcher ma = pattern.matcher(number);
		
		System.out.println(ma.find());
		*/
	}

}
