package com.search.analysis.analyze.charascan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReScan extends Charactor{

	@Override
	public boolean Division() {
		boolean signal = false;
		int position = this.getSentence().length();
		String str = "";
		int len = 0;
		String strTmp = "";
		
		while(position > 0){
			// Ensure the value of str.
			if(position <= SPECIAL){
				str = this.getSentence().substring(0, position);
				len = position;
				position = 0;
			}else{
				str = this.getSentence().substring(position - SPECIAL, position);
				len = SPECIAL;
				position -= SPECIAL;
			}
			
			// System.out.println(str);
			
			// Deal
			while(len > 0) {
				if(this.getSegSpecial().SearchFun(str)){
					break;
				}else{
					str = str.substring(1, len);
					--len;
					++position;
				}
			}
			
			if(len == 0) {
				--position;
				
				// System.out.println(position);
				
				strTmp = this.getSentence().substring(position, position + 1) + strTmp;
			}else if(strTmp.length() != 0){
				this.DealNoSpecial(strTmp);
				// ��������ż��뵽List��ʱΪ�˷ִʵĽ���ܹ���ԭ���ľ��ӱ���һ����˳��
				this.arrWord.add(0, str);
				strTmp = "";
			}
		}
		
		if(strTmp.length() != 0) {		
			this.DealNoSpecial(strTmp);
			strTmp = "";
		}
		
		// Determine the value of signal.
		if(position == 0){
			signal = true;
		}else{
			signal = false;
		}
		
		return signal;
	}
	
	/**
	 * Deal with NoSpecial
	 */
	private boolean DealNoSpecial(String str) {
		boolean signal = false;
		int position = str.length();
		String strTmp = "";
		String regex = "";
		int strLen = 0;
		
		while(position > 0) {
			// Ensure the value of strTmp.
			if(position <= LENGTH){
				strTmp = str.substring(0, position);
				strLen = position;
				position = 0;
			}else{
				strTmp = str.substring(position - LENGTH, position);
				strLen = LENGTH;
				position -= LENGTH;
			}
			
			// English
			regex = "^[a-zA-Z-]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher ma = pattern.matcher(strTmp);
			
			if(ma.find()){
				while(position > 0){
					if(this.segEnglish.searchFun(str.substring(position - 1, position))){
						strTmp += str.substring(position - 1, position);
						--position;
					}else{
						break;
					}
				}
				if(!this.segEnWord.SearchFun(strTmp))  // ƥ��������ж��Ƿ�Ϊis��am�ȵȵĵ���
					this.arrWord.add(0, strTmp); 	   // ��ȫ��Ӣ��ʱֱ����Ӳ���������ѭ��

				continue;
			}
			
			// Number
			if(this.segNumber.searchFun(strTmp)){
				while(position > 0){
					if(this.segNumber.searchFun(str.substring(position - 1, position))){
						strTmp += str.substring(position - 1, position);
						--position;
					}else{
						break;
					}
					// System.out.println(strTmp);
				}
				this.arrWord.add(0, strTmp);
				
				continue;
			}
			
			// Deal with strTmp
			while(strLen > 1) {
				// English
				ma = pattern.matcher(strTmp);
				if(ma.find()) {
					this.arrWord.add(0, strTmp);
					break;
				}
				
				// Custom
				if(this.segCustom.searchFun(strTmp)){
					this.arrWord.add(0, strTmp);
					break;
				}
				
				// Name
				if(strTmp.length() <= 4 && strTmp.length() >= 2){
					if(this.segName.searchFun(strTmp)){
						this.arrWord.add(0, strTmp);
						break;
					}
				}
				
				// Place
				if(this.segPlace.searchFun(strTmp)){
					this.arrWord.add(0, strTmp);
					break;
				}
				
				// Number
				if(this.segNumber.searchFun(strTmp)){
					this.arrWord.add(0, strTmp);
					break;
				}
				
				strTmp = strTmp.substring(1, strTmp.length());
				++position;
				strLen--;
				
			}
			
			if(1 == strLen) {
				if(!this.segNoise.searchFun(strTmp)){
					this.arrWord.add(0, strTmp);
					continue;
				}
			}
			
		}
		
		// Determine return-value signal
		if(0 == position) {
			signal = true;
		}else{
			signal = false;
		}
		
		return signal;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		ReScan reScan = new ReScan();
		
		reScan.setSentence("ë����2000���ҵ�ڶ�����ѧ");
		reScan.Division();
		
		reScan.print();

	}


}
