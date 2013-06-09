package cn.cadal.cunzheng;

public class ChunZhen {
	// 这是刘军做的部分
	public String beginIp;
	public String endIp;
	public String country;
	public String area;

	/**
	 * 构造函数
	 */

	public ChunZhen() {
		beginIp = endIp = country = area = "";
	}

	public String toString() {
		return this.area + "  " + this.country + "IP范围:" + this.beginIp + "-"
				+ this.endIp;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
