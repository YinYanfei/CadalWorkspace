package cn.cadal.rec.tryJava;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class BitSetTry {

	// 过滤特殊字符
	public static String StringFilter(String str) throws PatternSyntaxException {
		// 只允许字母和数字
		// String regEx = "[^a-zA-Z0-9]";
		// 清除掉所有特殊字符
		String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_ ]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return m.replaceAll("").trim();
	}

	public void testStringFilter() throws PatternSyntaxException {
		String str = "*adCVs*34_a _09_b5*[/435^*&城池()^$$&*).{}+.|.)%%*(*.中国}34{45[]12.fd'*&999下面是中文的字符￥……{}【】。，；’“‘”？";
		System.out.println(str);
		System.out.println(StringFilter(str));
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test
		BitSet bs1 = new BitSet();
		bs1.clear();
		bs1.set(1, false);
		bs1.set(2, true);
		bs1.set(3, false);

		BitSet bs2 = new BitSet();
		bs2.clear();
		bs2.set(1, false);
		bs2.set(3, true);
		
		BitSet bsTmp = (BitSet) bs1.clone();
		
		System.out.println(bs1.cardinality());
		System.out.println(bsTmp.cardinality());
		bs1.or(bs2);
		System.out.println(bs1.cardinality());
//		
//		BitSet bs2 = new BitSet();
//		bs2.set(1, false);
//		bs2.set(3, true);
//
//		System.out.println(bs1.length());
//		// bs1.and(bs2);
//		bs1.or(bs2);
//		
//		System.out.println(bs1.length());
//		for (int i = 0; i < bs1.length(); ++i) {
//			System.out.println(bs1.get(i));
//		}

		// List
//		System.out.println("++++++++++++++++++++++++++++");
//		List<String> strList = new ArrayList<String>();
//
//		strList.add("one");
//		strList.add("two");
//
//		System.out.println(strList.indexOf("two"));
//		System.out.println(strList.size());
//		System.out.println("++++++++++++++++++++++++++++");
//
//		// list bitset
//		List<BitSet> bitsetList = new ArrayList<BitSet>();
//		bitsetList.add(new BitSet());
//		bitsetList.add(new BitSet());
//		bitsetList.add(new BitSet());
//
//		bitsetList.set(2, null);
//
//		for (int i = 0; i < bitsetList.size(); ++i) {
//			if (bitsetList.get(i) == null) {
//				System.out.println(i);
//			}
//		}
//
//		String s = "this is a 中国 test,   powerful split";
//		System.out.println(s);
//		String[] arr = s.split("[^a-zA-Z]+"); // 6个单词的数组
//		for (int i = 0; i < arr.length; i++)
//			System.out.println(arr[i]);
//
//		String str = "123abc 这个中文 cde123abc 也要提取 123ab";
//		System.out.println(str);
//		Pattern p = null;
//		Matcher m = null;
//		String value = null;
//		 p = Pattern.compile("([\u4e00-\u9fa5([0-9a-zA-Z]+)])");
//		m = p.matcher(str);
//		while (m.find()) {
//			value = m.group(0);
//			System.out.println(value);
//		}
//		
//		System.out.println("=========================");
//		BitSetTry bst = new BitSetTry();
//		bst.testStringFilter();
//		
//		System.out.println("=========================");
//		String strTitle = "好的";
//		int idx = 0;
//		System.out.println(strTitle.substring(0, idx + 3 > strTitle.length()?strTitle.length():idx));
		
	}

}
