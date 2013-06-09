package cn.cadal.cassandra.cover;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.imageio.ImageIO;

public class CoverGet {
	/**
	 * Get bytes from cassandra
	 */
	public String getCoverFromCan(String bookno) {
		Connection con = null;
		try{			
			String strGet = "select image from cover where bookno = '" + bookno + "';";
			
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			con = DriverManager.getConnection("jdbc:cassandra://10.15.62.75:9160/cadal");
			PreparedStatement statement = con.prepareStatement(strGet);
			ResultSet rs = statement.executeQuery();
			while(rs.next()){ 
				String str=rs.getString("image");
				System.out.println(str + "----"); 
			}
			rs.close();
			return "";
		}catch(Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		CoverGet cg = new CoverGet();
		System.out.println(cg.getCoverFromCan("02812569"));
	}

}
