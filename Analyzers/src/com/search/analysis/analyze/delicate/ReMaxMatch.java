package com.search.analysis.analyze.delicate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReMaxMatch extends Word{
	
	@Override
	public boolean Division() {
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
				if(!this.segEnWord.SearchFun(str))  // ƥ��������ж��Ƿ�Ϊis��am�ȵȵĵ���
					this.arrWord.add(0, str); 			// ��ȫ��Ӣ��ʱֱ����Ӳ���������ѭ��

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
				this.arrWord.add(0, str);
				
				continue;
			}

			// Deal with each part.
			while(strLen > 1){
				
				// System.out.println("----" + str + "----");
				
				// �ж�Ӣ��
				ma = pattern.matcher(str);
				if(ma.find()) {
					this.arrWord.add(0, str);
					break;
				}
				
				// Custom
				if(this.segCustom.searchFun(str)){
					this.arrWord.add(0, str);
					break;
				}
				
				// Name
				if(str.length() >= 2 && str.length() <= 4){
					if(this.segName.searchFun(str)){
						this.arrWord.add(0, str);
						break;
					}
				}
				
				// Place
				if(this.segPlace.searchFun(str)){
					this.arrWord.add(0, str);
					break;
				}
				
				// Number
				if(this.segNumber.searchFun(str)){
					this.arrWord.add(0, str);
					break;
				}
				
				str = str.substring(1, str.length());
				++position;
				strLen--;
			}

			if(strLen == 1) {
				if(!this.segNoise.searchFun(str)){
					this.arrWord.add(0, str);
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
		ReMaxMatch reMaxMatch = new ReMaxMatch();
		/*
		reMaxMatch.setSentence("�㽭��ѧ�����ѧԺ����־��");
		reMaxMatch.setSentence("�ֲ�ʽȫ������������о���ʵ��");
		reMaxMatch.setSentence("��ҵ������Ŀ��ⱨ��");
		reMaxMatch.setSentence("���ڲ�����¼�������ҵ����Ϣϵͳ��" +
		 		  "���ݴ洢�İ�ȫ�����������ݵķ���ȡ�ͷ��۸�����Խ" +
		 		  "��Խ�������ǵ�����");
		
		reMaxMatch.setSentence("���ż��������ķ�չ���ݿ�İ�ȫ��" +
				  "Ҳ������½Խ��Խ��Ŀ�����Ϊ���ڵ����ݿ⼼����Ҫ��" +
		  		  "֤������Դ�������û�������Ҫ��֤���ݲ���й�ܷ�ֹ" +
				  "���ݲ������Ϸ��ظ��Ļ����ƻ���Խ��Ŀ�����Ϊ����" +
				  "�����ݿ⼼����Խ��Ŀ�����Ϊ���ڵ����ݿ⼼����Խ" +
				  "��Ŀ�����Ϊ���ڵ����ݿ⼼����Խ��Ŀ�����Ϊ����" +
				  "�����ݿ⼼����Խ��Ŀ�����Ϊ���ڵ����ݿ⼼����Խ" +
				  "��Ŀ�����Ϊ���ڵ����ݿ⼼����Խ��Ŀ�����Ϊ����" +
				  "�����ݿ⼼����Խ��Ŀ�����Ϊ���ڵ����ݿ⼼����Խ" +
				  "��Ŀ�����Ϊ���ڵ����ݿ⼼����Խ��Ŀ�����Ϊ����" +
				  "�����ݿ⼼����Խ��Ŀ�����Ϊ���ڵ����ݿ⼼����Խ" +
				  "��Ŀ�����Ϊ���ڵ����ݿ⼼����Խ��Ŀ�����Ϊ����" +
				  "�����ݿ⼼����Խ��Ŀ�����Ϊ���ڵ����ݿ⼼��");
		
		reMaxMatch.setSentence("�����Ÿ��и�ҵ�����ݿ���ӹ㷺��Ӧ" +
				  "�ý��ڲ�����¼�������ҵ����Ϣϵͳ�����ݴ洢�İ�" +
				  "ȫ�����������ݵķ���ȡ�ͷ��۸�����Խ��Խ��������" +
				  "��������˱��ľ�����������ݼ��ܵĸ��ּ����Ĺ���" +
				  "���ص����ݿ�����㷨�Լ����ݿ���ܵ�ʵ�ַ�ʽ����" +
				  "ϵ�ṹ������������о���ϵ�ǰ��һЩ��Ч�����㷨" +
				  "����˼�뼰��ص������������ַ������������÷�Χ��" +
				  "��ʹ�õȲ���ͨ����ؼ���ϵͳ�����ʵ�ּ����Լ���" +
				  "�Ը��ּ��ܷ������о����ͶԱ���֤�˸�ϵͳ�е�����" +
				  "���ܹ��̶��������ݱ�������Ч�����ݿⰲȫ���Ǳ�֤" +
				  "���ݿ������ݵı�������ȷ�Ա�����ָ�������ݿ��е�" +
				  "���ݲ����Ƿ��û���ȡ��ȷ����ָ���ݲ���Ϊ�������" +
				  "������Աʧ�������Ӳ�����ϵ������ݴ���ǰ���ݿ�" +
				  "ϵͳ�ܵ�����Ҫ��в�ж����ݿ�Ĳ���ȷ������������" +
				  "�����ݵĴ���Ϊ��ĳ��Ŀ�Ĺ����ƻ����ݿ�ʹ�䲻�ܻ�" +
				  "���Ƿ����ʲ��÷��ʵ����ݿ���Ϣ�û�ͨ�����������" +
				  "�ݿ����ʱ�п����ܵ����ּ����Ĺ���δ����Ȩ�Ƿ���" +
				  "�����ݿ�����ʹ��ʧȥ��ȷ���Լ�Ӳ���ƻ���Ȼ�ֺ���" +
				  "���ŵ�����������в�־���ʹ�����¾��似����֤����" +
				  "��ȫ���ݿ�������ݼ��ܾ��ǰ�������Ϣ������ת��Ϊ" +
				  "���ɱ�ʶ����ʽ�����ĵĹ���Ŀ����ʹ��Ӧ�˽������" +
				  "��Ϣ���˲��ܹ����ʽ�����ת��Ϊ���ĵĹ��̾��ǽ���" +
				  "���ܺͽ��ܹ����γ��˼���ϵͳ�ֶμ�����Ŀǰ������" +
				  "���ܵ�������ÿ����¼���ֶ�����������ļ�����Ϊ��" +
				  "λ���м��ܱ�Ȼ���γ�kdfokpdfogdpfgjdfjgdpfojgd" +
				  "pfojpdojfgp��Կ�ķ���ʹ�ôӶ����ͼ���ϵdfgerge4" +
				  "ͳ�Ŀɿ��Ի���������ʱ�������޷�ʹ����Կ��̬����" +
				  "���ݿ����֮�������Ÿ��ӵ��߼���ϵһ���߼��ṹ��" +
				  "�ܶ�Ӧ�Ը����ݿ���������������ݿ���ܲ�����Կ��" +
				  "�������֯�ʹ洢�����Ƚϸ�����Ҫ����Կʵ�ֶ�̬��" +
				  "������������Ҫǡ���Ĵ����������ͷ���DBMS������" +
				  "���ܺ�����ݲ����϶�����������Ͷ��ܾ����������" +
				  "Ҫ�������ݵĴ洢����ʵ�����ݿ���ܺ�Ӧ�����ϲ���" +
				  "�ӿռ俪����Ӱ��Ϸ��û���ֹ�Ƿ�����12321232123" +
				  "0");
		
		reMaxMatch.setSentence("�����Ѷ��ں������Ϊȴ���᲻һ����" +
				  "֧����������Ҳ������Ϊ��Ϊһ������������Ϊ��Ӧ��" +
				  "���ƫ�������ʾ�Ҹ�������������ֻʹ�����Լ�");
		*/
		
		// reMaxMatch.setSentence("�������˲��ܾ���ס����");
		reMaxMatch.setSentence("ë�����ҵ�ڶ�����ѧ");
		
		// Test Run-Time
		long startTime = System.currentTimeMillis();
		
		// Division
		reMaxMatch.Division();
		
		// Test Run-Time
		long endTime = System.currentTimeMillis();
		
		reMaxMatch.print();
		
		System.out.println("Time Used: " + (endTime - startTime));

	}

}
