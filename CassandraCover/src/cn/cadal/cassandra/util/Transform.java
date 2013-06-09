package cn.cadal.cassandra.util;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Transform {
	/**
	 * 转换Image数据为byte数组
	 * 
	 * @param image
	 *            Image对象
	 * @param format
	 *            image格式字符串.如"jpeg","png"
	 * @return byte数组
	 */
	public static byte[] imageToBytes(BufferedImage image, String format) {
		BufferedImage bImage = new BufferedImage(image.getWidth(null), image
				.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics bg = bImage.getGraphics();
		bg.drawImage(image, 0, 0, null);
		bg.dispose();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bImage, format, out);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return out.toByteArray();
	}

	/**
	 * 转换byte数组为Image
	 * 
	 * @param bytes
	 *            Image的bytes数据数组
	 * @param filename
	 *            为要生成新的文件名
	 * @return boolean
	 */
	public static boolean byteToImage(byte[] b, String filename) {
		boolean bl = false;
		File binaryFile = new File("D:/LuceneSearch/CassandraCover/image/"
				+ filename + ".jpg");
		try {
			FileOutputStream fileOutStream = new FileOutputStream(binaryFile);
			for (int i = 0; i < b.length; i++) {
				fileOutStream.write(b[i]);
			}
			fileOutStream.flush();
			bl = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bl;
		
		// BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
	}

	/**
	 * byte数组转二进制字符串
	 */
	public static String byte2hex(byte[] b) {

		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1)
				hs = hs + "0" + stmp;
			else
				hs = hs + stmp;
		}
		return hs;
	}

	/**
	 * 二进制字符串转byte数组
	 */
	public static byte[] hex2byte(String str) {
		if (str == null)
			return null;

		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer
						.decode("0x" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

}
