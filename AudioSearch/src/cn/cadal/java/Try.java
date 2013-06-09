package cn.cadal.java;

public class Try {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "96071544 #### 刘兰芳演播版-洪武大帝:第061回 #### 刘兰芳 ####  #### 中央人民广播电台";
		String[] strArr = str.split("####");
		System.out.println(strArr.length);
		for(int i = 0; i < strArr.length; ++i) {
			System.out.println(strArr[i]);
		}
	}

}
