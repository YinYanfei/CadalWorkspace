package shan.data.fourOp;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class LogicOpFour {
	// // 2013-12-29
//	public final static String COMP_NEW = "E:/baiduyundownload/2013-12-27 数据处理_V2/fourOp/CompNew.csv";
//	public final static String GARCH_DIR = "E:/baiduyundownload/2013-12-27 数据处理_V2/fourOp/GARCH-csv/";

	// 2013-12-29
	public final static String COMP_NEW = "E:/baiduyundownload/2013-12-29 数据处理_V3/CompNew_1229.csv";
	public final static String GARCH_DIR = "E:/baiduyundownload/2013-12-29 数据处理_V3/Deal_3/GARCH0809/";
	
	public FileReaderOpFour fileReaderOpFour = null;

	public String Stkcd = "";
	public String Nmetc = "";
	public String Datfst = "";
	public String[] variable = { "1-Day", "2-波动率_Sma20", "3-波动率_Sma60", "4-波动率_Ewma","5-波动率_Garch"};
	public String Csrciccd2 = "";
	
	public String[] arr_1 = null;
	public String[] arr_2 = null;
	public String[] arr_3 = null;
	public String[] arr_4 = null;
	public String[] arr_5 = null;

	public LogicOpFour(){
		this.fileReaderOpFour = new FileReaderOpFour();
		
		this.fileReaderOpFour.readCompNew(COMP_NEW);
		this.fileReaderOpFour.readGarchDir(GARCH_DIR);
	}
	
	/**
	 * Init array structures
	 */
	private void initResStruct() {
		this.Stkcd = "";
		this.Nmetc = "";
		this.Datfst = "";
		this.Csrciccd2 = "";
		
		this.arr_1 = new String[281];
		for(int i = 0; i < 281; ++i) {
			this.arr_1[i] = "0";
		}
		this.arr_2 = new String[281];
		for(int i = 0; i < 281; ++i) {
			this.arr_2[i] = "0";
		}
		this.arr_3 = new String[281];
		for(int i = 0; i < 281; ++i) {
			this.arr_3[i] = "0";
		}
		this.arr_4 = new String[281];
		for(int i = 0; i < 281; ++i) {
			this.arr_4[i] = "0";
		}
		this.arr_5 = new String[281];
		for(int i = 0; i < 281; ++i) {
			this.arr_5[i] = "0";
		}
	}

	public void singleIter(String line) {
		this.initResStruct();

		String [] lineArr = line.split(",");
		
		String Stkcd = lineArr[0];
		String Nmetc = lineArr[1];
		String Datfst = lineArr[2].replace("/", "-");
		
		System.out.println(lineArr[2]);
		System.out.println(Datfst);
		
		String Csrciccd2 = lineArr[4];
				
		try {
			// simple
			this.Stkcd = Stkcd;
			this.Nmetc = Nmetc;
			this.Datfst = Datfst;
			this.Csrciccd2 = Csrciccd2;
			Map<String, String> COMP_NEW_SINGLE = this.fileReaderOpFour.GarchMap.get(Stkcd); 
			
			// complex data
			// 0
			String compStr = COMP_NEW_SINGLE.get(Datfst);
			String[] compStrArr = compStr.split(",");

			this.arr_1[0] = Datfst;
			this.arr_2[0] = compStrArr[0];
			this.arr_3[0] = compStrArr[1];
			this.arr_4[0] = compStrArr[2];
			this.arr_5[0] = compStrArr[3];

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			// 1 -> 250
			Date dt = sdf.parse(Datfst);
			int idx = 250;
			while (idx > 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);
				cal.add(Calendar.DAY_OF_YEAR, -1);
				String dtStr = sdf.format(cal.getTime());

				if (dt.compareTo(sdf.parse("2004-12-31")) < 0) {System.out.println("-=-==--=-=-=-=");
					break;
				}

				if (COMP_NEW_SINGLE.containsKey(dtStr)) {
					compStr = COMP_NEW_SINGLE.get(dtStr);
					compStrArr = compStr.split(",");
					
					this.arr_1[idx] = dtStr;
					this.arr_2[idx] = compStrArr[0];
					this.arr_3[idx] = compStrArr[1];
					this.arr_4[idx] = compStrArr[2];
					this.arr_5[idx] = compStrArr[3];
					
					idx--;
				}

				dt = cal.getTime();
			}

			// 251 -> 280
			dt = sdf.parse(Datfst);
			idx = 251;
			while (idx < 281) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);
				cal.add(Calendar.DAY_OF_YEAR, 1);
				String dtStr = sdf.format(cal.getTime());

				if (dt.compareTo(sdf.parse("2013-12-31")) > 0) {
					break;
				}

				if (COMP_NEW_SINGLE.containsKey(dtStr)) {
					compStr = COMP_NEW_SINGLE.get(dtStr);
					compStrArr = compStr.split(",");
					
					this.arr_1[idx] = dtStr;
					this.arr_2[idx] = compStrArr[0];
					this.arr_3[idx] = compStrArr[1];
					this.arr_4[idx] = compStrArr[2];
					this.arr_5[idx] = compStrArr[3];
					
					idx++;
				}

				dt = cal.getTime();
			}
		} catch (Exception e) {
			e.printStackTrace();
//			try {
//				FileWriter writer = new FileWriter("E:/baiduyundownload/2013-12-27 数据处理_V2/fourOp/err.dat", true);
//				
//				writer.write(Stkcd + "," +Datfst + "\n");
//				
//				writer.close();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
		}
	}
	
	/**
	 * Write arr_* into file
	 */
	public void writeIntoFile(String fileName) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(fileName, true);

			for (int i = 0; i < 5; ++i) {
				writer.write(this.Stkcd + "," + this.Nmetc + "," +  this.Datfst + "," 
						+ this.variable[i] + "," + this.Csrciccd2 + ",");
				for (int idx_less = 1; idx_less < 251; idx_less++) {
					if (i == 0) {
						writer.write(this.arr_1[idx_less] + ",");
					} else if (i == 1) {
						writer.write(this.arr_2[idx_less] + ",");
					} else if (i == 2) {
						writer.write(this.arr_3[idx_less] + ",");
					} else if (i == 3) {
						writer.write(this.arr_4[idx_less] + ",");
					} else if (i == 4) {
						writer.write(this.arr_5[idx_less] + ",");
					}
				}

				if (i == 0) {
					writer.write(this.arr_1[0] + ",");
				} else if (i == 1) {
					writer.write(this.arr_2[0] + ",");
				} else if (i == 2) {
					writer.write(this.arr_3[0] + ",");
				} else if (i == 3) {
					writer.write(this.arr_4[0] + ",");
				} else if (i == 4) {
					writer.write(this.arr_5[0] + ",");
				}

				for (int idx_large = 251; idx_large < 281; idx_large++) {
					if (i == 0) {
						writer.write(this.arr_1[idx_large] + ",");
					} else if (i == 1) {
						writer.write(this.arr_2[idx_large] + ",");
					} else if (i == 2) {
						writer.write(this.arr_3[idx_large] + ",");
					} else if (i == 3) {
						writer.write(this.arr_4[idx_large] + ",");
					} else if (i == 4) {
						writer.write(this.arr_5[idx_large] + ",");
					}
				}

				writer.write("\n");
			}

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// OP
		LogicOpFour logicOpFour = new LogicOpFour();
		String resFileName = "E:/baiduyundownload/2013-12-29 数据处理_V3/res_3_1229.dat"; 
		
		Iterator iter = logicOpFour.fileReaderOpFour.CompNew.entrySet().iterator();
		int count = 0;
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();

			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			
			System.out.println((++count) + " -- " + val);

			logicOpFour.singleIter(val);
			logicOpFour.writeIntoFile(resFileName);
		}
	}

}
