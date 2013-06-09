package com.search.analysis.analyze.initial;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.search.analysis.segment.SegInterval;

/**
 * Deal with Paragraph(paragraph is segmented by Paragraph.java and ParagraphFile.java)
 * @author D390
 * @version 1.0
 */
public class Sentence {
	
	/**
	 * Variables:
	 *  arrSentence: Store the sentences of file.
	 */
	private ArrayList<String> arrSentence;
	private String paragraph;
	private SegInterval segInterval;
	
	/**
	 * Construct functions.
	 */
	public Sentence(){
		this.arrSentence = new ArrayList<String>();
		segInterval = new SegInterval("dict\\Interval\\");
		segInterval.specialForInterval();		
	}
	Sentence(String para){
		this.setParagraph(para);
		
		this.arrSentence = new ArrayList<String>();
		segInterval = new SegInterval("dict\\Interval\\");
		segInterval.specialForInterval();
	}
	
	/**
	 * Segment sentences, according to Interval.
	 * @return:
	 *  true: success.
	 * false: fail.
	 */
	public boolean SegSentence(){
		boolean signal = false;
		
		try{
			String str = "";
			for(int i = 0; i < this.paragraph.length(); ++i){
				if(!segInterval.searchFun(this.paragraph.substring(i, i + 1))){
					str += this.paragraph.substring(i, i + 1);
				}else{
					this.arrSentence.add(str);
					str = "";
				}
			}
			
			// 注意：最后的一位可能是分隔符号，也可能不是，所以要进行判断.
			if(!segInterval.searchFun(this.paragraph.substring(this.paragraph.length()))){
				str += this.paragraph.substring(this.paragraph.length());
				this.arrSentence.add(str);
			}else{
				this.arrSentence.add(str);
			}
			
			signal = true;
		}catch(Exception e){
			signal = true;
			e.printStackTrace();
		}
		
		return signal;
	}

	/**
	 * Segment sentences, according to Interval, using for Reverse Scan.
	 * @return:
	 *  true: success.
	 * false: fail.
	 */
	public boolean SegSentenceRe(){
		boolean signal = false;
		
		try{
			String str = "";
			for(int i = 0; i < this.paragraph.length(); ++i){
				if(!segInterval.searchFun(this.paragraph.substring(i, i + 1))){
					str += this.paragraph.substring(i, i + 1);
				}else{
					this.arrSentence.add(0, str);
					str = "";
				}
			}
			
			// 注意：最后的一位可能是分隔符号，也可能不是，所以要进行判断.
			if(!segInterval.searchFun(this.paragraph.substring(this.paragraph.length()))){
				str += this.paragraph.substring(this.paragraph.length());
				this.arrSentence.add(0, str);
			}else{
				this.arrSentence.add(0, str);
			}
			
			signal = true;
		}catch(Exception e){
			signal = true;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Segment sentences,  according to regex of parameter.
	 * @return
	 *   true: success
	 *  false: fail
	 */
	public boolean SegSentence(String regex){
		boolean signal = false;
		
		try{
			String strTmp = "";
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = null;
			for(int i = 0; i < this.paragraph.length(); ++i){
				matcher = pattern.matcher(this.paragraph.substring(i, i + 1));
				
				if(matcher.find()){
					this.arrSentence.add(strTmp);
					strTmp = "";
				}else{
					strTmp += this.paragraph.substring(i, i + 1);
				}
			}
			
			// 注意：最后的一位可能是分隔符号，也可能不是，所以要进行判断.
			matcher = pattern.matcher(this.paragraph.substring(this.paragraph.length()));
			if(matcher.find()) {
				this.arrSentence.add(strTmp);
			}else{
				strTmp += this.paragraph.substring(this.paragraph.length());
				this.arrSentence.add(strTmp);
			}
			
			signal = true;
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Print arrSentence.
	 */
	public void print(){
		for(String str:this.arrSentence){
			System.out.println(str);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "the public type mycanvas must be defined in its own file 是什么,错" +
				"源文件中的公关类名要与(文件名)源文\"件中?的公。关类名.要与文<件名一【源文件中-90的公关类" +
				"名要与　文件名一.源文件中《的公";
		
		Sentence sentence = new Sentence(str);
		
		// String regex = "[ ,\\。,(,),.,\\,]";
		// sentence.SegSentence(regex);
		// sentence.print();
		
		System.out.println("-----------------------------");
		
		sentence.SegSentence();
		sentence.print();
		
		/*
		Pattern pattern = Pattern.compile(regex);
		for(int i = 0; i < str.length(); ++i){
			Matcher matcher = pattern.matcher(str.subSequence(i, i + 1));
			
			if(matcher.find()){
				System.out.println("\n----");
			}else{
				System.out.print(str.substring(i, i + 1));
			}
		}
		*/
	}

	/**
	 * Get and Set functions.
	 */
	public ArrayList<String> getArrSentence() {
		return arrSentence;
	}

	public void setArrSentence(ArrayList<String> arrSentence) {
		this.arrSentence = arrSentence;
	}
	
	public SegInterval getSegInterval() {
		return segInterval;
	}

	public void setSegInterval(SegInterval segInterval) {
		this.segInterval = segInterval;
	}

	public void setParagraph(String paragraph) {
		this.paragraph = paragraph;
	}

	public String getParagraph() {
		return paragraph;
	}

}
