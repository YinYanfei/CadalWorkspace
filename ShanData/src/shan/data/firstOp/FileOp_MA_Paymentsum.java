package shan.data.firstOp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.DriverManager;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

public class FileOp_MA_Paymentsum {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FileOp_MA_Paymentsum fo = new FileOp_MA_Paymentsum();
		String fileName = "E:/baiduyundownload/2013-12-23 最新数据更新/已有的/MA_Paymentsum_new.csv";
		String dbName   = "MA_Paymentsum";
		
		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://10.15.62.75:3306/ShanData", "cadal", "Cadal205");
		Statement stmt = (Statement) con.createStatement();
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
		String line = "";
		while ((line = reader.readLine()) != null) {
			String[] lineArr = line.split(",");
			if(lineArr.length == 2) {
				String sql = "insert into " + dbName + "(Cdbsn, Metdpay) values(\"" + lineArr[0] + "\",\"" + lineArr[1] + "\");";
				
				stmt.execute(sql);
				
				System.out.println(sql);
			}
		}

		reader.close();
		con.close();
	}

}
