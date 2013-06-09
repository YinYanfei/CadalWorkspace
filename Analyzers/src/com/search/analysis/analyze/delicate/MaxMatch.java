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
			// 这是处理的一个英语单词匹配的特殊情况.
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
				if(!this.segEnWord.SearchFun(str))  // 匹配这个来判断是否为is、am等等的单词
					this.arrWord.add(str); 			// 完全是英语时直接添加并跳过本次循环

				continue;
			}
			
			// Judge Number ---- 数字和英语字母一样存在边界处理的问题
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
				
				// 判断英语
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
		maxMatch.setSentence("浙江大学计算机学院的张志坤");
		maxMatch.setSentence("分布式全文搜索引擎的研究与实现");
		maxMatch.setSentence("毕业设计论文开题报告");
		maxMatch.setSentence("金融财务高新技术等企业的信息系统中" +
		 		"数据存储的安全问题敏感数据的防窃取和防篡改问题越" +
		 		"来越引起人们的重视");
		
		maxMatch.setSentence("随着计算机网络的发展数据库的安全性" +
				"也面临着陆越来越多的考验作为现在的数据库技术既要保" +
				"证数据资源被网络用户共享又要保证数据不被泄密防止" +
				"数据不被不合法地更改或者破坏来越多的考验作为现在" +
				"的数据库技术来越多的考验作为现在的数据库技术来越" +
				"多的考验作为现在的数据库技术来越多的考验作为现在" +
				"的数据库技术来越多的考验作为现在的数据库技术来越" +
				"多的考验作为现在的数据库技术来越多的考验作为现在" +
				"的数据库技术来越多的考验作为现在的数据库技术来越" +
				"多的考验作为现在的数据库技术来越多的考验作为现在" +
				"的数据库技术来越多的考验作为现在的数据库技术来越" +
				"多的考验作为现在的数据库技术来越多的考验作为现在" +
				"的数据库技术来越多的考验作为现在的数据库技术");
		
		maxMatch.setSentence("伴随着各行各业对数据库更加广泛的应" +
				"用金融财务高新技术等企业的信息系统中数据存储的安" +
				"全问题敏感数据的防窃取和防篡改问题越来越引起人们" +
				"的重视因此本文就针对敏感数据加密的各种技术的功能" +
				"和特点数据库加密算法以及数据库加密的实现方式和体" +
				"系结构进行了深入的研究结合当前的一些高效加密算法" +
				"加密思想及相关的著作分析各种方法的利弊适用范围如" +
				"何使用等并且通过相关加密系统的设计实现及测试检验" +
				"对各种加密方法进行举例和对比验证了该系统中的数据" +
				"加密过程对敏感数据保护的有效性数据库安全就是保证" +
				"数据库中数据的保密性正确性保密性指保护数据库中的" +
				"数据不被非法用户获取正确性是指数据不因为故意的破" +
				"坏操作员失误或者软硬件故障导致数据错误当前数据库" +
				"系统受到的主要威胁有对数据库的不正确访问引起数据" +
				"库数据的错误为了某种目的故意破坏数据库使其不能恢" +
				"复非法访问不该访问的数据库信息用户通过网络进行数" +
				"据库访问时有可能受到各种技术的攻击未经授权非法修" +
				"改数据库数据使其失去正确性以及硬件破坏自然灾害磁" +
				"干扰等面临以上威胁现经常使用以下经典技术保证数据" +
				"安全数据库加密数据加密就是把数据信息即明文转换为" +
				"不可辨识的形式即密文的过程目的是使不应了解该数据" +
				"信息的人不能够访问将密文转变为明文的过程就是解密" +
				"加密和解密过程形成了加密系统字段加密在目前条件下" +
				"加密的粒度是每个记录的字段数据如果以文件或列为单" +
				"位进行加密必然会形成kdfokpdfogdpfgjdfjgdpfojgd" +
				"pfojpdojfgp密钥的反复使用从而降低加密系dfgerge4" +
				"统的可靠性或者因脱密时过长而无法使用密钥动态管理" +
				"数据库客体之间隐含着复杂的逻辑关系一个逻辑结构可" +
				"能对应对个数据库物理客体所以数据库加密不仅密钥量" +
				"大而且组织和存储工作比较复杂需要对密钥实现动态合" +
				"理处理数据首先要恰当的处理数据类型否则DBMS将会因" +
				"加密后的数据不符合定义的数据类型而拒绝加载其次需" +
				"要处理数据的存储问题实现数据库加密后应基本上不增" +
				"加空间开销不影响合法用户防止非法拷贝12321232123" +
				"0");
		
		maxMatch.setSentence("而网友对于韩红的行为却褒贬不一有人" +
				"支持她的率性也有人认为作为一个公众人物行为不应该" +
				"如此偏激韩红表示我根本不看评论我只痛快我自己");
		
		*/
		maxMatch.setSentence("毛新年2000年毕业于东北大学");
		
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
