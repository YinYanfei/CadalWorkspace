package cn.cadal.cassandra.cover;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import cn.cadal.cassandra.util.Transform;

public class CoverStore {
	private Transform tf = null;
	// private String FileAddr = "D:/LuceneSearch/CassandraCover/image/";  // Should be changed when using in production.
	private String FileAddr = "/var/www/nginx-default/images/covers/";
	
	/**
	 * Constructor
	 */
	public CoverStore() {
		this.tf = new Transform();
	}
	
	/**
	 * Store into database
	 */
	public boolean StoreIntoDB(String bookno, String strBytes) {
		Connection con = null;
		try{			
			String strStore = "insert into cover (bookno, image) values( '" + bookno + "','" + strBytes + "');";
			
			Class.forName("org.apache.cassandra.cql.jdbc.CassandraDriver");
			con = DriverManager.getConnection("jdbc:cassandra://10.15.62.75:9160/cadal");
			PreparedStatement statement = con.prepareStatement(strStore);
			statement.execute(strStore);
			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Deal function
	 * FileAddr = ".../LuceneSearch/CassandraCover/image/"
	 * bookno   = "02812569.jpg"
	 */
	public boolean DealImage(String bookno) {
		File file = new File(this.FileAddr + bookno);
		BufferedImage src;
		try{
			// image --> bytes
			src = javax.imageio.ImageIO.read(file);
			byte[] b = Transform.imageToBytes(src, "jpeg");
			String strBytes = Transform.byte2hex(b);
			
			// store
			this.StoreIntoDB(bookno.substring(0, 8), strBytes);
			
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Begin function
	 */
	public void Begin(String metadata) {
		File fileMeta  = new File(metadata);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(fileMeta));
			String tempString = null;
			FileWriter writer = new FileWriter(metadata + "-log", true);
			
			while((tempString = reader.readLine()) != null){
				tempString.trim();
				if(this.DealImage(tempString)){
					writer.write("success -- " + tempString + "\n");
					System.out.println("success -- " + tempString);
				}else{
					writer.write(" failed -- " + tempString + "\n");
					System.out.println(" failed -- " + tempString);
				}
			}
			
			writer.close();
			reader.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CoverStore cs = new CoverStore();
		cs.Begin("/home/cadal/covers/xad");
		
//		boolean b = cs.DealImage("02812570");
//		
//		if(b) {
//			System.out.println("Success!");
//		}else{
//			System.out.println("Failed!");
//		}
	}

	/**
	 * Get and Set functions
	 */
	public void setTf(Transform tf) {
		this.tf = tf;
	}

	public Transform getTf() {
		return tf;
	}

	public void setFileAddr(String fileAddr) {
		FileAddr = fileAddr;
	}

	public String getFileAddr() {
		return FileAddr;
	}

}
