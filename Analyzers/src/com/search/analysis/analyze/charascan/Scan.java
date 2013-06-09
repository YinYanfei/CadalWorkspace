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
				// 放在这里才加入到List中时为了分词的结果能够和原来的句子保持一定的顺序
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
	 * Deal with NoSpecial -- 这个方法和MaxMatch中的Division方法一样
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
				if(!this.segEnWord.SearchFun(strTmp))  // 匹配这个来判断是否为is、am等等的单词
					this.arrWord.add(strTmp); 		   // 完全是英语时直接添加并跳过本次循环

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
		String sentence = "而网友对于韩红的行为却褒贬不一有人" +
			 "支持她的率性也有人认为作为一个公众人物行为不应该" +
			 "如此偏激韩红表示我根本不看评论我只痛快我自己";
		*/
		// String sentence = "他因为理屈词穷而改变了自己的看法";
		// String sentence = "内塔尼亚胡说的确实在理";
		// String sentence = "毛新年2000年的毕业于东北 yjfuyguy a 大学";
		String sentence = "abskdsjdk";
		
		Scan scan = new Scan();
		
		scan.setSentence(sentence);
		scan.Division();

		scan.print();
	}

}
