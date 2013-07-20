package cn.cadal.rec.tryJava;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShinglingTry {

	public String splitStr(String sChars, int sLength) {
		String str = "";
		int sl = 0;
		for (int i = 0; i < sChars.length(); i++) {
			if ((sChars.charAt(i) + "").equals(""))
				break;
			str += sChars.charAt(i);
			sl += getStrLength(sChars.charAt(i) + "");
			if (sl >= sLength) {
				str += ",";
				sl = 0;
			}
		}
		return str;
	}

	public int getStrLength(String s) {
		int length = 0;
		for (int i = 0; i < s.length(); i++) {
			int ascii = Character.codePointAt(s, i);
			if (ascii >= 0 && ascii <= 255)
				length++;
			else
				length += 2;

		}
		return length;
	}

	/**
	 * @param args
	 */
	// public static void main(String[] args) {
	// // TODO Auto-generated method stub
	// // ShinglingTry st = new ShinglingTry();
	// // System.out.println(st.splitStr("ѩѩxxrtytyu����eeѩ��rr", 4));
	//
	// String str = "Windows�����µ�������";
	// Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
	// Matcher matcher = pattern.matcher(str);
	//			
	// if (matcher.find()){
	// System.out.println(matcher.group(1));
	// }
	// }

	public static boolean checkChs(String str) {
		boolean mark = false;
		Pattern pattern = Pattern.compile("[\u4E00-\u9FA5]");
//		Pattern pattern = Pattern.compile("\\w+");
		Matcher matc = pattern.matcher(str);
//		StringBuffer stb = new StringBuffer();
		List<String> stb = new ArrayList<String>();
		while (matc.find()) {
			mark = true;
			System.out.println("----" + matc.group());
			stb.add(matc.group()+" ");
		}

		for(String strSin : stb) {
			System.out.println(strSin);
		}
		
//		if (mark) {
//			System.out.println("ƥ����ַ���Ϊ��" + stb.toString());
//		}
		return mark;
	}

	public static void main(String[] args) {
		String str = "Windows�����µ�����Linux���";
		ShinglingTry.checkChs(str);
	}
}
