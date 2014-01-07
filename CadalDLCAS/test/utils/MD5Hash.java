package cn.edu.zju.cadal.test.utils;

import java.security.MessageDigest;

public class MD5Hash {
	public static String md5(byte[] plainByte) { 
        try { 
                MessageDigest md = MessageDigest.getInstance("MD5"); 
                md.update(plainByte); 
                byte b[] = md.digest(); 
                int i; 
                StringBuffer buf = new StringBuffer(""); 
                for (int offset = 0; offset < b.length; offset++) { 
                        i = b[offset]; 
                        if(i<0) i+= 256; 
                        if(i<16) 
                        buf.append("0"); 
                        buf.append(Integer.toHexString(i)); 
                } 
                return buf.toString();

        } catch (Exception e) { 
                e.printStackTrace(); 
        } 
        return null;
	}

	/**
	 * @param args
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		MD5Hash mdh = new MD5Hash();
		
		System.out.println("CADAL: " + mdh.md5("CADAL".getBytes()));
		System.out.println("�㽭��ѧ: " + mdh.md5("�㽭��ѧ".getBytes()));
		System.out.println("Cadal-���¶�: " + mdh.md5("Cadal-���¶�".getBytes()));
	}

}
