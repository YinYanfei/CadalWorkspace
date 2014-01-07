package shan.data.secondOp;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class LogicOp {
	// 2013-12-27
//	public final static String COMP_FILE = "E:/baiduyundownload/2013-12-26 数据处理/初步处理/Comp.csv";
//	public final static String AAT_DIR = "E:/baiduyundownload/2013-12-26 数据处理/初步处理/AAT/";
//	public final static String AATMA_DIR = "E:/baiduyundownload/2013-12-26 数据处理/初步处理/AAT_MA/";

	// 2013-12-29
	public final static String COMP_FILE = "E:/baiduyundownload/2013-12-29 数据处理_V3/Comp_2_28.csv";
	public final static String AAT_DIR = "E:/baiduyundownload/2013-12-29 数据处理_V3/Deal_2/AAT/";
	public final static String AATMA_DIR = "E:/baiduyundownload/2013-12-29 数据处理_V3/Deal_2/AAT_MA/";

	public FileReaderOp fileReader = null;

	public String Stkcd = "";
	public String Datfst = "";
	public String[] variable = { "1-Day", "2-成交量", "3-总流通换手率", "4-流通股换手率",
			"5-市场总市值换手率", "6-市场流通市值换手率" };
	public String Csrciccd2 = "";
	public String[] arr_1 = null;
	public String[] arr_2 = null;
	public String[] arr_3 = null;
	public String[] arr_4 = null;
	public String[] arr_5 = null;
	public String[] arr_6 = null;

	public LogicOp() {
		this.fileReader = new FileReaderOp();
		this.initFileStruct();
	}

	/**
	 * Read file and init structures
	 */
	private void initFileStruct() {
		this.fileReader.ReadComp(COMP_FILE);
		this.fileReader.ReadAAT(AAT_DIR);
		this.fileReader.ReadAATMA(AATMA_DIR);
	}

	/**
	 * Init array structures
	 */
	private void initResStruct() {
		this.Stkcd = "";
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
		this.arr_6 = new String[281];
		for(int i = 0; i < 281; ++i) {
			this.arr_6[i] = "0";
		}
	}

	/**
	 * Deal single company
	 * 
	 * @param Stkcd
	 * @param dateStr
	 * @throws ParseException
	 */
	public void singleIter(String Stkcd, String Datfst) {
		this.initResStruct();
		Datfst = Datfst.replaceAll("/", "-");

		System.out.println(Stkcd + " ---- " + Datfst);
		
		try {
			// simple data
			this.Stkcd = Stkcd;
			this.Datfst = Datfst;
			this.Csrciccd2 = this.fileReader.AAT_MAP.get(Stkcd).get(Datfst).split(",")[0];

			Map<String, String> AAT_MA_SINGLE = this.aatMaSingle(Stkcd);

			// complex data
			// 0
			String aatStr = this.fileReader.AAT_MAP.get(Stkcd).get(Datfst);
			String[] aatStrArr = aatStr.split(",");
			String aatMaStr = null;
			String[] aatMaStrArr = new String[2];
			if (AAT_MA_SINGLE.containsKey(Datfst)) {
				aatMaStr = AAT_MA_SINGLE.get(Datfst);
				aatMaStrArr = aatMaStr.split(",");
			} else {
				aatMaStrArr[0] = "0";
				aatMaStrArr[1] = "0";
			}
			this.arr_1[0] = Datfst;
			this.arr_2[0] = aatStrArr[1];
			this.arr_3[0] = aatStrArr[2];
			this.arr_4[0] = aatStrArr[3];
			this.arr_5[0] = aatMaStrArr[0];
			this.arr_6[0] = aatMaStrArr[1];

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			// 1 -> 250
			Date dt = sdf.parse(Datfst);
			int idx = 250;
			while (idx > 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);
				cal.add(Calendar.DAY_OF_YEAR, -1);
				String dtStr = sdf.format(cal.getTime());

				if (dt.compareTo(sdf.parse("2004-12-31")) < 0) {
					break;
				}

				if (this.fileReader.AAT_MAP.get(Stkcd).containsKey(dtStr)) {
					aatStr = this.fileReader.AAT_MAP.get(Stkcd).get(dtStr);
					aatStrArr = aatStr.split(",");
					if (AAT_MA_SINGLE.containsKey(dtStr)) {
						aatMaStr = AAT_MA_SINGLE.get(dtStr);
						aatMaStrArr = aatMaStr.split(",");

						this.arr_1[idx] = dtStr;
						this.arr_2[idx] = aatStrArr[1];
						this.arr_3[idx] = aatStrArr[2];
						this.arr_4[idx] = aatStrArr[3];
						this.arr_5[idx] = aatMaStrArr[0];
						this.arr_6[idx] = aatMaStrArr[1];
					} else {
						this.arr_1[idx] = dtStr;
						this.arr_2[idx] = aatStrArr[1];
						this.arr_3[idx] = aatStrArr[2];
						this.arr_4[idx] = aatStrArr[3];
						this.arr_5[idx] = "0";
						this.arr_6[idx] = "0";
					}
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

				if (this.fileReader.AAT_MAP.get(Stkcd).containsKey(dtStr)) {
					aatStr = this.fileReader.AAT_MAP.get(Stkcd).get(dtStr);
					aatStrArr = aatStr.split(",");
					if (AAT_MA_SINGLE.containsKey(dtStr)) {
						aatMaStr = AAT_MA_SINGLE.get(dtStr);
						aatMaStrArr = aatMaStr.split(",");

						this.arr_1[idx] = dtStr;
						this.arr_2[idx] = aatStrArr[1];
						this.arr_3[idx] = aatStrArr[2];
						this.arr_4[idx] = aatStrArr[3];
						this.arr_5[idx] = aatMaStrArr[0];
						this.arr_6[idx] = aatMaStrArr[1];
					} else {
						this.arr_1[idx] = dtStr;
						this.arr_2[idx] = aatStrArr[1];
						this.arr_3[idx] = aatStrArr[2];
						this.arr_4[idx] = aatStrArr[3];
						this.arr_5[idx] = "0";
						this.arr_6[idx] = "0";
					}
					idx++;
				}

				dt = cal.getTime();
			}
		} catch (Exception e) {
			try {
				FileWriter writer = new FileWriter("E:/baiduyundownload/2013-12-29 数据处理_V3/err.dat", true);
				
				writer.write(Stkcd + "," +Datfst + "\n");
				
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Write arr_* into file
	 */
	public void writeIntoFile(String fileName) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(fileName, true);

			for (int i = 0; i < 6; ++i) {
				writer.write(this.Stkcd + "," + this.Datfst + ","
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
					} else if (i == 5) {
						writer.write(this.arr_6[idx_less] + ",");
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
				} else if (i == 5) {
					writer.write(this.arr_6[0] + ",");
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
					} else if (i == 5) {
						writer.write(this.arr_6[idx_large] + ",");
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
	 * AAT_MA
	 * 
	 * @param Stkcd
	 * @return
	 */
	private Map<String, String> aatMaSingle(String Stkcd) {
		if ((Stkcd.substring(0, 3)).equals("000")) {
			return this.fileReader.AAT_MA_1A;
		} else if ((Stkcd.substring(0, 3)).equals("300")) {
			return this.fileReader.AAT_MA_0B;
		} else if ((Stkcd.substring(0, 3)).equals("002")) {
			return this.fileReader.AAT_MA_2AB;
		} else if ((Stkcd.substring(0, 3)).equals("600")
				|| (Stkcd.substring(0, 3)).equals("601")) {
			return this.fileReader.AAT_MA_2A;
		}

		return null;
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// TEST
		LogicOp logicOp = new LogicOp();
//		String resFileName = "E:/baiduyundownload/2013-12-26 数据处理/初步处理/res_2.txt";	// 2013-12-27
		
		String resFileName = "E:/baiduyundownload/2013-12-29 数据处理_V3/res_2_1229.dat";   // 2013-12-29
		
		Iterator iter = logicOp.fileReader.COMP_MAP.entrySet().iterator();
		int count = 0;
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();

			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			
			String [] valArr = val.split(",");

			System.out.println((++count) + " -- " + valArr[0] + " -- " + valArr[1]);

			logicOp.singleIter(valArr[0], valArr[1]);
			logicOp.writeIntoFile(resFileName);
		}

	}

}
