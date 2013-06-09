package cn.cadal.tmp;

public class JavaTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "06904734==《浙江日报》第5465期";
		String strArr[] = str.split("==");

		System.out.println(strArr[0] + " " + strArr[1]);
	}

}
