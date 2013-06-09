package log.cadal.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import log.cadal.ipmatch.Match;
import log.cadal.type.Type;
import log.cadal.util.DataStruct;
import log.cadal.util.School;

public class AnaAndCount extends Thread{
	public String sourceFile = "";
	public String destinationFile = "";

	// 已知基本数据存储变量
	public Match ipMatch = null;

	// 分析与统计结果存储变量
	public School school = null;

	// 图书类型查询变量
	public Type typeMatch = null;

	/**
	 * Construct function
	 */
	public AnaAndCount() {
		this.ipMatch = new Match();
		this.school = new School();
		this.typeMatch = new Type();
		this.typeMatch.readFile();

	}

	/**
	 * Analyze
	 */
	public void logAna(String fileName) {
		this.sourceFile = fileName;
		this.destinationFile = fileName + "_ans";
		
		File file = new File(this.sourceFile);
		BufferedReader reader = null;

		String line = "";

		try {
			reader = new BufferedReader(new FileReader(file));

			String type = "";
			String schoolNo = "";
			int schoolInt = 0;
			DataStruct ds = null;

			while ((line = reader.readLine()) != null) {

				// System.out.println(++number + "    " + line);

				String str[] = line.split("	");
				if (str[0].equals("1") || str[0].equals("2")) {
					type = this.typeMatch.findType(str[2]);

					if (type.equals("book")) {
						type = "yingwen";
					} else if (type.equals("ancient")) {
						type = "guji";
					} else if (type.equals("journal")) {
						type = "mingguoqikan";
					} else if (type.equals("modern")) {
						type = "xiandai";
					} else if (type.equals("minguo")) {
						type = "mingguo";
					} else if (type.equals("dissertation")) {
						type = "xuewei";
					} else if (type.equals("english")) {
						type = "yingwen";
					} else {
						type = "xuewei";
					}
				} else if (str[0].equals("3")) {
					type = "huihua";
				} else if (str[0].equals("4")) {
					type = "shiping";
				} else if (str[0].equals("5")) {
					type = "shufa";
				} else if (str[0].equals("6")) {
					type = "qita";
				} else {
					System.out.println("ERROR");
				}

				schoolNo = this.ipMatch.match(str[1]);
								
				// 将得到的对应的学校和图书对应的类型存入school
				schoolInt = Integer.parseInt(schoolNo);
				ds = (DataStruct) this.school.school2type.get(schoolInt);
				String num = (String) ds.type2num.get(type);

				// System.out.println("------" + num + "------" + schoolInt + "------" + type);
				num = String.valueOf(Integer.parseInt(num) + 1);
				ds.type2num.put(type, num);
			}
	
			// this.school.writerIntoFile(this.destinationFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Start");

		AnaAndCount aac1 = new AnaAndCount();
	
		aac1.logAna("H:/cadal0410/access_20120412.log.1.ana");

	}

}
