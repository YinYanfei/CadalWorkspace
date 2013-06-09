package com.search.analysis.analyze.delicate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaxMatch extends Word{
	
	@Override
	public boolean Division() {
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
			
			// System.out.println(str + "----");
			
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
				if(!this.segEnWord.SearchFun(str))  // ƥ��������ж��Ƿ�Ϊis��am�ȵȵĵ���
					this.arrWord.add(str); 			// ��ȫ��Ӣ��ʱֱ����Ӳ���������ѭ��

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
				this.arrWord.add(str);
				
				continue;
			}
			
			// System.out.println(strLen);
			
			// Deal with each part.
			while(strLen > 1){
				
				// System.out.println("----" + strLen + "----");
				
				// �ж�Ӣ��
				ma = pattern.matcher(str);
				if(ma.find()) {
					this.arrWord.add(str);
					break;
				}
				
				// Custom
				if(this.segCustom.searchFun(str)){
					this.arrWord.add(str);
					break;
				}
				
				// Name
				if(str.length() >= 2 && str.length() <= 4){
					if(this.segName.searchFun(str)){
						this.arrWord.add(str);
						break;
					}
				}
				
				// Place
				if(this.segPlace.searchFun(str)){
					this.arrWord.add(str);
					break;
				}
				
				// Number
				if(this.segNumber.searchFun(str)){
					this.arrWord.add(str);
					break;
				}
				
				str = str.substring(0, str.length() - 1);
				position--;
				strLen--;
			}
			
			if(strLen == 1) {
				if(!this.segNoise.searchFun(str)){
					this.arrWord.add(str);
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MaxMatch maxMatch = new MaxMatch();
		/*
		maxMatch.setSentence("�㽭��ѧ�����ѧԺ����־��");
		maxMatch.setSentence("�ֲ�ʽȫ������������о���ʵ��");
		maxMatch.setSentence("��ҵ������Ŀ��ⱨ��");
		maxMatch.setSentence("���ڲ�����¼�������ҵ����Ϣϵͳ��" +
		 		"���ݴ洢�İ�ȫ�����������ݵķ���ȡ�ͷ��۸�����Խ" +
		 		"��Խ�������ǵ�����");
		
		maxMatch.setSentence("���ż��������ķ�չ���ݿ�İ�ȫ��" +
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
		
		maxMatch.setSentence("�����Ÿ��и�ҵ�����ݿ���ӹ㷺��Ӧ" +
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
		
		maxMatch.setSentence("�����Ѷ��ں������Ϊȴ���᲻һ����" +
				"֧����������Ҳ������Ϊ��Ϊһ������������Ϊ��Ӧ��" +
				"���ƫ�������ʾ�Ҹ�������������ֻʹ�����Լ�");
		
		*/
		maxMatch.setSentence("ë����2000���ҵ�ڶ�����ѧ");
		
		// Test Run-Time
		long startTime = System.currentTimeMillis();
		
		// Division
		maxMatch.Division();
		
		// Test Run-Time
		long endTime = System.currentTimeMillis();
		
		maxMatch.print();
		
		System.out.println("Time Used: " + (endTime - startTime));
	}

}
