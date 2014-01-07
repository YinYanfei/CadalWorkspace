package shan.data.firstOp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.DriverManager;

import com.mysql.jdbc.Statement;

public class FileOp_DATA_Verify {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		FileOp_MA_Paymentsum fo = new FileOp_MA_Paymentsum();
//		String fileName = "E:/baiduyundownload/1225_morning_2.csv";
		String fileName = "E:/baiduyundownload/2013-12-28 数据更新/证监会通过公司08-09年.csv";           // id >= 262
		String dbName   = "DATA_Verify";
		
		Class.forName("com.mysql.jdbc.Driver").newInstance(); 
		java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://10.15.62.75:3306/ShanData", "cadal", "Cadal205");
		Statement stmt = (Statement) con.createStatement();
		
		BufferedReader reader = new BufferedReader(new FileReader(new File(fileName)));
		String line = "";
		while ((line = reader.readLine()) != null) {
			String[] lineArr = line.split(",");
			if(lineArr.length == 3) {
				String sql = "insert into " + dbName + "(comp_name, objection) values(\"" + lineArr[1] + "\",\"" + lineArr[2] + "\");";
				
				stmt.execute(sql);
				
				System.out.println(sql);
			}
		}

		reader.close();
		con.close();
	}
}
