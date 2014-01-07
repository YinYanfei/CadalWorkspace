package cn.edu.zju.cadal.test.utils;

public class IpTrans {

	/*****************************************************
	** 方案一
	*****************************************************/
	private static long ip2Long(String strip) {
		// 将127.0.0.1 形式的ip地址转换成10进制整数，这里没有进行任何错误处理
		long[] ip = new long[4];
		int position1 = strip.indexOf(".");
		int position2 = strip.indexOf(".", position1 + 1);
		int position3 = strip.indexOf(".", position2 + 1);
		ip[0] = Long.parseLong(strip.substring(0, position1));
		ip[1] = Long.parseLong(strip.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strip.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strip.substring(position3 + 1));
		return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3]; // ip1*256*256*256+ip2*256*256+ip3*256+ip4
	}

	private static String long2IP(long longip) {
		// 将10进制整数形式转换成127.0.0.1形式的ip地址，在命令提示符下输入ping 3396362403l
		StringBuffer sb = new StringBuffer("");
		sb.append(String.valueOf(longip >>> 24));// 直接右移24位
		sb.append(".");
		sb.append(String.valueOf((longip & 0x00ffffff) >>> 16)); // 将高8位置0，然后右移16位
		sb.append(".");
		sb.append(String.valueOf((longip & 0x0000ffff) >>> 8));
		sb.append(".");
		sb.append(String.valueOf(longip & 0x000000ff));
		return sb.toString();
	}

	/*****************************************************
	** 方案二
	*****************************************************/
	private static long ip2long(String ip) {
		String[] ips = ip.split("[.]");
		long num = 16777216L * Long.parseLong(ips[0]) + 65536L
				* Long.parseLong(ips[1]) + 256 * Long.parseLong(ips[2])
				+ Long.parseLong(ips[3]);
		return num;
	}

	private static String long2ip(long ipLong) {
		// long ipLong = 1037591503;
		long mask[] = { 0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000 };
		long num = 0;
		StringBuffer ipInfo = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			num = (ipLong & mask[i]) >> (i * 8);
			if (i > 0)
				ipInfo.insert(0, ".");
			ipInfo.insert(0, Long.toString(num, 10));
		}
		return ipInfo.toString();
	}
	
	/**
	 * Main function for test
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("ip地址的各种表现形式：\r\n");
		System.out.print("32位二进制形式：");
		System.out.println(Long.toBinaryString(3396362403l));
		System.out.print("十进制形式：");
		System.out.println(ip2Long("255.255.255.255"));
		System.out.print("普通形式：");
		System.out.println(long2IP(3396362403l));

		System.out.println("----------------------");
		System.out.println(ip2Long("10.15.62.100"));
		System.out.println(long2IP(168771147));
		System.out.println(ip2long("10.15.62.75"));
		System.out.println(long2ip(168771147));
		System.out.println("----------------------");
		System.out.println(ip2Long("10.15.62.76"));
		System.out.println(long2IP(168771148));
		System.out.println(ip2long("10.15.62.76"));
		System.out.println(long2ip(168771148));
		System.out.println("----------------------");
		System.out.println(ip2Long("10.15.62.77"));
		System.out.println(long2IP(168771149));
		System.out.println(ip2long("10.15.62.77"));
		System.out.println(long2ip(168771149));
		System.out.println("----------------------");
		System.out.println(ip2Long("10.15.62.78"));
		System.out.println(long2IP(168771150));
		System.out.println(ip2long("10.15.62.78"));
		System.out.println(long2ip(168771150));
	}

}
