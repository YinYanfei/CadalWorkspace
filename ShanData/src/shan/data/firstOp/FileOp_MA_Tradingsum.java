package shan.data.firstOp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.mysql.jdbc.Statement;

public class FileOp_MA_Tradingsum {
	public static void main(String[] args) throws Exception {
//		String fileName = "E:/baiduyundownload/2013-12-23 最新数据更新/MA_Tradingsum.csv";
		String fileName = "E:/baiduyundownload/2013-12-23 最新数据更新/tmp_2.csv";
		String dbName = "MA_Tradingsum";

		Class.forName("com.mysql.jdbc.Driver").newInstance();
		java.sql.Connection con = DriverManager.getConnection(
				"jdbc:mysql://10.15.62.75:3306/ShanData", "cadal", "Cadal205");
		Statement stmt = (Statement) con.createStatement();

		BufferedReader reader = new BufferedReader(new FileReader(new File(
				fileName)));
		String line = "";
		int count = 0; 
		while ((line = reader.readLine()) != null) {
			System.out.println(count++);
			String[] lineArr = line.split(",");

			if (lineArr.length == 29) {
				if (lineArr[0].equals(" ")) {
					lineArr[0] = "NULL";
				}
				if (lineArr[1].equals(" ")) {
					lineArr[1] = "NULL";
				}
				if (lineArr[2].equals(" ")) {
					lineArr[2] = "NULL";
				}
				if (lineArr[3].equals(" ")) {
					lineArr[3] = "NULL";
				}
				if (lineArr[4].equals(" ")) {
					lineArr[4] = "NULL";
				}
				if (lineArr[5].equals(" ")) {
					lineArr[5] = "NULL";
				}
				if (lineArr[6].equals(" ")) {
					lineArr[6] = "NULL";
				}
				if (lineArr[7].equals(" ")) {
					lineArr[7] = "NULL";
				}
				if (lineArr[8].equals(" ")) {
					lineArr[8] = "NULL";
				}
				if (lineArr[9].equals(" ")) {
					lineArr[9] = "NULL";
				}
				if (lineArr[10].equals(" ")) {
					lineArr[10] = "NULL";
				}
				if (lineArr[11].equals(" ")) {
					lineArr[11] = "NULL";
				}
				if (lineArr[12].equals(" ")) {
					lineArr[12] = "NULL";
				}
				if (lineArr[13].equals(" ")) {
					lineArr[13] = "NULL";
				}
				if (lineArr[14].equals(" ")) {
					lineArr[14] = "NULL";
				}
				if (lineArr[15].equals(" ")) {
					lineArr[15] = "NULL";
				}
				if (lineArr[16].equals(" ")) {
					lineArr[16] = "NULL";
				}
				if (lineArr[17].equals(" ")) {
					lineArr[17] = "NULL";
				}
				if (lineArr[18].equals(" ")) {
					lineArr[18] = "NULL";
				}
				if (lineArr[19].equals(" ")) {
					lineArr[19] = "NULL";
				}
				if (lineArr[20].equals(" ")) {
					lineArr[20] = "NULL";
				}
				if (lineArr[21].equals(" ")) {
					lineArr[21] = "NULL";
				}
				if (lineArr[22].equals(" ")) {
					lineArr[22] = "NULL";
				}
				if (lineArr[23].equals(" ")) {
					lineArr[23] = "NULL";
				}
				if (lineArr[24].equals(" ")) {
					lineArr[24] = "NULL";
				}
				if (lineArr[25].equals(" ")) {
					lineArr[25] = "NULL";
				}
				if (lineArr[26].equals(" ")) {
					lineArr[26] = "NULL";
				}
				if (lineArr[27].equals(" ")) {
					lineArr[27] = "NULL";
				}
				if (lineArr[28].equals(" ")) {
					lineArr[28] = "NULL";
				}
				try{
					String sql = "insert into "
							+ dbName
							+ "(Cdbsn,Stkcd,Poslctrs,Datfst,Datsgn,Suctrd,Datact,Typbsn,Rstypcd,Rstrtyp,Bcocd,Bcotc,Scocd,Scotc,Nmeua,Pritrd,Ttltrd,Scaletrd,Tpritrd,Partrd,Idrlatrd,Basval,Bfincon,Blaw,Bacc,Sasval,Sfincon,Slaw,Sacc) "
							+ "values(\"" + lineArr[0] + "\",\"" + lineArr[1]
							+ "\",\"" + lineArr[2] + "\",\"" + lineArr[3]
							+ "\",\"" + lineArr[4] + "\",\"" + lineArr[5]
							+ "\",\"" + lineArr[6] + "\",\"" + lineArr[7]
							+ "\",\"" + lineArr[8] + "\",\"" + lineArr[9]
							+ "\",\"" + lineArr[10] + "\",\"" + lineArr[11]
							+ "\",\"" + lineArr[12] + "\",\"" + lineArr[13]
							+ "\",\"" + lineArr[14] + "\",\"" + lineArr[15]
							+ "\",\"" + lineArr[16] + "\",\"" + lineArr[17]
							+ "\",\"" + lineArr[18] + "\",\"" + lineArr[19]
							+ "\",\"" + lineArr[20] + "\",\"" + lineArr[21]
							+ "\",\"" + lineArr[22] + "\",\"" + lineArr[23]
							+ "\",\"" + lineArr[24] + "\",\"" + lineArr[25]
							+ "\",\"" + lineArr[26] + "\",\"" + lineArr[27]
							+ "\",\"" + lineArr[28] + "\");";
					System.out.println(sql);
					stmt.execute(sql);
				}catch(Exception e) {
					continue;
				}
			}
		}

		reader.close();
		con.close();
	}

}
