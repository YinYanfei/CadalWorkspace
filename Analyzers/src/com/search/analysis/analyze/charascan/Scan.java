package com.search.analysis.analyze.charascan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scan extends Charactor{
	
	/**
	 * Override Division Function
	 */
	@Override
	public boolean Division() {
		boolean signal = false;
		int position = 0;
		String str = "";
		int len = 0;
		String strTmp = "";
		
		while(position < this.getSentence().length()){
			// Ensure the value of str.
			if(this.getSentence().length() - position<= SPECIAL){
				str = this.getSentence().substring(position, this.getSentence().length());
				len = this.getSentence().length() - position;
				position = this.getSentence().length();
			}else{
				str = this.getSentence().substring(position, position + SPECIAL);
				len = SPECIAL;
				position += SPECIAL;
			}
			
			// System.out.println(str);
			
			// Deal
			while(len > 0) {
				if(this.getSegSpecial().SearchFun(str)){
					break;
				}else{
					str = str.substring(0, len - 1);
					--len;
					--position;
				}
			}
			
			if(len == 0) {
				++position;
				
				// System.out.println(position);
				
				strTmp += this.getSentence().substring(position - 1, position);
			}else if(strTmp.length() != 0){
				this.DealNoSpecial(strTmp);
				// ��������ż��뵽List��ʱΪ�˷ִʵĽ���ܹ���ԭ���ľ��ӱ���һ����˳��
				this.arrWord.add(str);
				strTmp = "";
			}
		}
		
		if(strTmp.length() != 0) {
			this.DealNoSpecial(strTmp);
			strTmp = "";
		}
		
		// Determine the value of signal.
		if(position == this.getSentence().length()){
			signal = true;
		}else{
			signal = false;
		}
		
		return signal;
	}
	
	/**
	 * Deal with NoSpecial -- ���������MaxMatch�е�Division����һ��
	 */
	private boolean DealNoSpecial(String str){
		boolean signal = false;
		int position = 0;
		String strTmp = "";
		String regex = "";
		int strLen = 0;
		
		while(position < str.length()) {
			// Ensure the value of strTmp.
			if(str.length() - position <= LENGTH){
				strTmp = str.substring(position, str.length());
				strLen = strTmp.length();
				position = str.length();
			}else{
				strTmp = str.substring(position, position + LENGTH);
				strLen = LENGTH;
				position += LENGTH;
			}
			
			// English
			regex = "^[a-zA-Z-]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher ma = pattern.matcher(strTmp);
			
			if(ma.find()){
				while(position < str.length()){
					if(this.segEnglish.searchFun(str.substring(position, position + 1))){
						strTmp += str.substring(position, position + 1);
						++position;
					}else{
						break;
					}
				}
				if(!this.segEnWord.SearchFun(strTmp))  // ƥ��������ж��Ƿ�Ϊis��am�ȵȵĵ���
					this.arrWord.add(strTmp); 		   // ��ȫ��Ӣ��ʱֱ����Ӳ���������ѭ��

				continue;
			}
			
			// Number
			if(this.segNumber.searchFun(strTmp)){
				while(position < str.length()){
					if(this.segNumber.searchFun(str.substring(position, position + 1))){
						strTmp += str.substring(position, position + 1);
						++position;
					}else{
						break;
					}
					// System.out.println(strTmp);
				}
				this.arrWord.add(strTmp);
				
				continue;
			}
			
			// Deal with strTmp
			while(strLen > 1) {
				// English
				ma = pattern.matcher(strTmp);
				if(ma.find()) {
					this.arrWord.add(strTmp);
					break;
				}
				
				// Custom
				if(this.segCustom.searchFun(strTmp)){
					this.arrWord.add(strTmp);
					break;
				}
				
				// Name
				if(strTmp.length() <= 4 && strTmp.length() >= 2){
					if(this.segName.searchFun(strTmp)){
						this.arrWord.add(strTmp);
						break;
					}
				}
				
				// Place
				if(this.segPlace.searchFun(strTmp)){
					this.arrWord.add(strTmp);
					break;
				}
				
				// Number
				if(this.segNumber.searchFun(strTmp)){
					this.arrWord.add(strTmp);
					break;
				}
				
				strTmp = strTmp.substring(0, strTmp.length() - 1);
				--strLen;
				--position;
			}
			
			if(1 == strLen) {
				if(!this.segNoise.searchFun(strTmp)){
					this.arrWord.add(strTmp);
					continue;
				}
			}
			
		}
		
		// Determine return-value signal
		if(str.length() == position) {
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
		/*
		String sentence = "�����Ѷ��ں������Ϊȴ���᲻һ����" +
			 "֧����������Ҳ������Ϊ��Ϊһ������������Ϊ��Ӧ��" +
			 "���ƫ�������ʾ�Ҹ�������������ֻʹ�����Լ�";
		*/
		// String sentence = "����Ϊ����������ı����Լ��Ŀ���";
		// String sentence = "�������Ǻ�˵��ȷʵ����";
		// String sentence = "ë����2000��ı�ҵ�ڶ��� yjfuyguy a ��ѧ";
		String sentence = "abskdsjdk";
		
		Scan scan = new Scan();
		
		scan.setSentence(sentence);
		scan.Division();

		scan.print();
	}

}
