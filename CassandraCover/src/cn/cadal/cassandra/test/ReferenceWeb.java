package cn.cadal.cassandra.test;

import java.awt.image.*;
import java.awt.*;
import java.io.*;

import javax.imageio.*;

public class ReferenceWeb {

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
	public static boolean ByteToImage(byte[] b, String filename) {
		boolean bl = false;
		File binaryFile = new File("D:/LuceneSearch/CassandraCover/image/" + filename + ".jpg");
		try {
			FileOutputStream fileOutStream = new FileOutputStream(binaryFile);
			for (int i = 0; i < b.length; i++) {
				fileOutStream.write(b[i]);
			}
			fileOutStream.flush();
			bl = true;
		} catch (FileNotFoundException e) {
			// 自动生成 catch 块
			e.printStackTrace();
		}// 创建文件输出流。
		catch (IOException e) {
			// 自动生成 catch 块
			e.printStackTrace();
		}
		return bl;
	}
	/**
	 * 二进制转字符串
	 * 
	 * @param
	 * @return
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
	 * 转换byte数组为Image
	 * 
	 * @param bytes
	 *            Image的bytes数据数组
	 * @return Image
	 */
	public static Image bytesToImage(byte[] bytes) {
		Image image = Toolkit.getDefaultToolkit().createImage(bytes);

		try {
			MediaTracker mt = new MediaTracker(new Label());
			mt.addImage(image, 0);
			mt.waitForAll();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return image;
	}


	public static void main(String args[]) {
		byte[] b = null;
		File file = new File("D:/LuceneSearch/CassandraCover/image/02812569.jpg");
		BufferedImage src;
		try {
			src = javax.imageio.ImageIO.read(file);
			b = ReferenceWeb.imageToBytes(src, "jpeg");
			System.out.println("b.length=======" + b.length);
			System.out.println(b);
			new ReferenceWeb();
			System.out.println(ReferenceWeb.byte2hex(b));
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		// 构造Image对象
		if (ReferenceWeb.ByteToImage(b, "2"))
			System.out.println("文件从流中成功读出");
		else
			System.out.println("失败");

	}

}