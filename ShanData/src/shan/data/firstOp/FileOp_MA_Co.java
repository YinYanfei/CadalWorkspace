package shan.data.firstOp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.Statement;

public class FileOp_MA_Co {
	public static void main(String[] args) throws Exception {
		String fileName = "E:/baiduyundownload/2013-12-23 最新数据更新/MA_Co.csv";
		String dbName = "MA_Co";

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		java.sql.Connection con = DriverManager.getConnection(
				"jdbc:mysql://10.15.62.75:3306/ShanData", "cadal", "Cadal205");
		Statement stmt = (Statement) con.createStatement();

		BufferedReader reader = new BufferedReader(new FileReader(new File(
				fileName)));
		String line = "";
		String date = "2004-3-29";
		java.util.Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		while ((line = reader.readLine()) != null) {
			String[] lineArr = line.split(",");

			if (lineArr.length == 12) {
				d = sdf.parse(lineArr[1]);
				java.sql.Date date_sql = new java.sql.Date(d.getTime());

				if (lineArr[4].equals("")) {
					lineArr[4] = "NULL";
				}
				if (lineArr[5].equals("")) {
					lineArr[5] = "NULL";
				}
				if (lineArr[6].equals("")) {
					lineArr[6] = "NULL";
				}
				if (lineArr[7].equals("")) {
					lineArr[7] = "NULL";
				}
				if (lineArr[8].equals("")) {
					lineArr[8] = "NULL";
				}
				if (lineArr[9].equals("")) {
					lineArr[9] = "NULL";
				}
				if (lineArr[10].equals("")) {
					lineArr[10] = "NULL";
				}
				if (lineArr[11].equals("")) {
					lineArr[11] = "NULL";
				}
				try {
					String sql = "insert into "
							+ dbName
							+ "(Cocd,Rndate,Cotc,Idcdtc,Stktc,Nmetc,Listloc,Idshchtc,Ntrtc,Ndstc,Nmdstc,Cuncd) "
							+ "values(\"" + lineArr[0] + "\",\"" + date_sql
							+ "\",\"" + lineArr[2] + "\",\"" + lineArr[3]
							+ "\",\"" + lineArr[4] + "\",\"" + lineArr[5]
							+ "\",\"" + lineArr[6] + "\",\"" + lineArr[7]
							+ "\",\"" + lineArr[8] + "\",\"" + lineArr[9]
							+ "\",\"" + lineArr[10] + "\",\"" + lineArr[11]
							+ "\");";

					stmt.execute(sql);

					System.out.println(sql);
				} catch (Exception e) {
					continue;
				}
			}
		}

		reader.close();
		con.close();
	}

}
