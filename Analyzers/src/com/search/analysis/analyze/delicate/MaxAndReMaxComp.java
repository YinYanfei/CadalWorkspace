package com.search.analysis.analyze.delicate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaxAndReMaxComp extends Word{
	
	/**
	 * Variables
	 */
	private ArrayList<String> arrWord1;
	private ArrayList<String> arrWord2;

	/**
	 * Construct Function
	 */
	public MaxAndReMaxComp(){
		this.arrWord1 = new ArrayList<String>();
		this.arrWord2 = new ArrayList<String>();
	}
	
	/**
	 * Get the most completed split.
	 */
	public boolean Division(){
		boolean signal = false;
		String str1 = "";
		String str2 = "";
		
		// Devision maxMatch and reMaxMatch
		this.arrWord1.clear();
		this.arrWord2.clear();
		signal = ((this.DivisionMaxMatch()) && (this.DivisionReMaxMatch()));
				
		// Deal
		Iterator<String> iter1 = arrWord1.iterator();
		Iterator<String> iter2 = arrWord2.iterator();
		
		while(iter1.hasNext() && iter2.hasNext()) {			
			str1 = iter1.next();
			str2 = iter2.next();
			
			if(str1.equals(str2)){
				this.arrWord.add(str1);
			}else{
				this.arrWord.add(str1);
				this.arrWord.add(str2);
			}
		}
		
		// Deal with the last of MaxMatch or ReMaxMatch
		while(iter1.hasNext()) {
			str1 = iter1.next();
			this.arrWord.add(str1);
		}
		
		while(iter2.hasNext()) {
			str2 = iter2.next();
			this.arrWord.add(str2);
		}

		return signal;
	}
	
	/**
	 * DevisionComplete Help Function
	 */
	@SuppressWarnings("unused")
	private boolean judge(String strTmp) {
		boolean signal = true;
		
		for(String str:this.getArrWord()){
			if(str.equals(strTmp)){
				signal = false;
			}
		}
		
		return signal;
	}
	
	/**
	 * Division
	 */
	private boolean DivisionMaxMatch() {
		boolean signal = false;
		String str = "";
		String regex = "";
		int position = 0;
		int strLen = 0;
		
		while(position < this.sentence.length()){
			// Get a substring of sentence
			if(this.sentence.length() - position <= LENGTH){
				str = this.sentence.substring(position, this.sentence.length());
				strLen = this.sentence.length() - position;
				position = this.sentence.length();
			}else{
				str = this.sentence.substring(position, position + LENGTH);
				position = position + LENGTH;
				strLen = LENGTH;
			}

			// Judge English or Chinese
			// ���Ǵ����һ��Ӣ�ﵥ��ƥ����������.
			regex = "^[a-zA-Z-]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher ma = pattern.matcher(str);
			
			if(ma.find()){
				while(position < this.sentence.length()){
					if(this.segEnglish.searchFun(this.sentence.substring(position, position + 1))){
						str += this.sentence.substring(position, position + 1);
						++position;
					}else{
						break;
					}
				}
				if(!this.segEnWord.SearchFun(str)) 		// ƥ��������ж��Ƿ�Ϊis��am�ȵȵĵ���
					this.arrWord1.add(str); 			// ��ȫ��Ӣ��ʱֱ����Ӳ���������ѭ��

				continue;
			}
			
			// Judge Number ---- ���ֺ�Ӣ����ĸһ�����ڱ߽紦�������
			if(this.segNumber.searchFun(str)){
				while(position < this.sentence.length()){
					if(this.segNumber.searchFun(this.sentence.substring(position, position + 1))){
						str += this.sentence.substring(position, position + 1);
						++position;
					}else{
						break;
					}
				}
				this.arrWord1.add(str);
				
				continue;
			}
			
			// System.out.println(strLen);
			
			// Deal with each part.
			while(strLen > 1){
				// �ж�Ӣ��
				ma = pattern.matcher(str);
				if(ma.find()) {
					this.arrWord1.add(str);
					break;
				}
				
				// Custom
				if(this.segCustom.searchFun(str)){
					this.arrWord1.add(str);
					break;
				}
				
				// Name
				if(str.length() >= 2 && str.length() <= 4){
					if(this.segName.searchFun(str)){
						this.arrWord1.add(str);
						break;
					}
				}
				
				// Place
				if(this.segPlace.searchFun(str)){
					this.arrWord1.add(str);
					break;
				}
				
				// Number
				if(this.segNumber.searchFun(str)){
					this.arrWord1.add(str);
					break;
				}
				
				str = str.substring(0, str.length() - 1);
				position--;
				strLen--;
			}
			
			if(strLen == 1) {
				if(!this.segNoise.searchFun(str)){
					this.arrWord1.add(str);
					// position--;
					continue;
				}
			}
		}
		
		// Judge success or not.
		if(this.getSentence().length() == position){
			signal = true;
		}else{
			signal = false;
		}
		
		return signal;
	}
	
	private boolean DivisionReMaxMatch(){
		boolean signal = false;
		String str = "";
		String regex = "";
		int position = this.getSentence().length();
		int strLen = 0;
		
		while(position > 0){
			// Get a substring of sentence
			if(position <= LENGTH){
				str = this.sentence.substring(0, position);
				strLen = position;
				position = 0;
			}else{
				str = this.sentence.substring(position - LENGTH, position);
				position = position - LENGTH;
				strLen = LENGTH;
			}
			
			// Judge English or Chinese
			// ���Ǵ����һ��Ӣ�ﵥ��ƥ����������.
			regex = "^[a-zA-Z-]+$";
			Pattern pattern = Pattern.compile(regex);
			Matcher ma = pattern.matcher(str);
			
			if(ma.find()){
				while(position > 0){
					if(this.segEnglish.searchFun(this.sentence.substring(position - 1, position))){
						str = this.sentence.substring(position - 1, position) + str;
						--position;
					}else{
						break;
					}
				}
				if(!this.segEnWord.SearchFun(str))  	// ƥ��������ж��Ƿ�Ϊis��am�ȵȵĵ���
					this.arrWord2.add(0, str); 			// ��ȫ��Ӣ��ʱֱ����Ӳ���������ѭ��

				continue;
			}
			
			// Judge Number ---- ���ֺ�Ӣ����ĸһ�����ڱ߽紦�������
			if(this.segNumber.searchFun(str)){
				while(position > 0){
					if(this.segNumber.searchFun(this.sentence.substring(position - 1, position))){
						str = this.sentence.substring(position - 1, position) + str;
						--position;
					}else{
						break;
					}
				}
				this.arrWord2.add(0, str);
				
				continue;
			}

			// Deal with each part.
			while(strLen > 1){
				// �ж�Ӣ��
				ma = pattern.matcher(str);
				if(ma.find()) {
					this.arrWord2.add(0, str);
					break;
				}
				
				// Custom
				if(this.segCustom.searchFun(str)){
					this.arrWord2.add(0, str);
					break;
				}
				
				// Name
				if(str.length() >= 2 && str.length() <= 4){
					if(this.segName.searchFun(str)){
						this.arrWord2.add(0, str);
						break;
					}
				}
				
				// Place
				if(this.segPlace.searchFun(str)){
					this.arrWord2.add(0, str);
					break;
				}
				
				// Number
				if(this.segNumber.searchFun(str)){
					this.arrWord2.add(0, str);
					break;
				}
				
				str = str.substring(1, str.length());
				++position;
				strLen--;
			}

			if(strLen == 1) {
				if(!this.segNoise.searchFun(str)){
					this.arrWord2.add(0, str);
					continue;
				}
			}
			
		}
		
		// Judge success or not.
		if(0 == position){
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
		String str = "��һ����ʿ����ͨ��ֱ�ӹ۲�����" +
				"״̬��Ԥ������ʱ������һЩˮ�����Ĵ�˵��������ˮ" +
				"���״̬��������һ���ĸ��ʹ�ϵҲ����˵ˮ���״̬����" +
				"��ʱ������صĴ�ʱ���Ǿ�������״̬�۲�״̬ˮ���" +
				"״̬������״̬����״̬�������ϣ���õ�һ���㷨����Ϊ" +
				"��ʿͨ��ˮ�������Ʒ������û��ֱ�ӹ۲����������" +
				"�µõ������ı仯�������������һ��Ӧ�þ�������ʶ��" +
				"���ǵ����ⶨ��������ͨ�������������ź�Ԥ���ԭ��" +
				"��������Ϣ�����������źž��ǹ۲�״̬ʶ���������" +
				"��������״̬������Ҫע��������κ�һ" +
				"��Ӧ���й۲�״̬�ĸ���������״̬�ĸ����п��ܲ�" +
				"һ�����������Ǿ���������Ʒ�ģ��HMM�������������";
		MaxAndReMax maxAndReMax = new MaxAndReMax();
		
		maxAndReMax.setSentence(str);
		
		maxAndReMax.Division();
		maxAndReMax.print();
	}
	
	/**
	 * Set and Get Functions
	 */
	public ArrayList<String> getArrWord1() {
		return arrWord1;
	}
	
	public void setArrWord1(ArrayList<String> arrWord1) {
		this.arrWord1 = arrWord1;
	}

	public ArrayList<String> getArrWord2() {
		return arrWord2;
	}

	public void setArrWord2(ArrayList<String> arrWord2) {
		this.arrWord2 = arrWord2;
	}

}
