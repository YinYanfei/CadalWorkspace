package shan.data.thirdOp;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class LogicOpThird {
	// 2013-12-27
//	public static String COMP_FILE = "E:/baiduyundownload/2013-12-27 数据处理_V2/fourOp/CompNew.csv";
//	public static String BETA_DIR = "E:/baiduyundownload/2013-12-27 数据处理_V2/fourOp/BETA-csv/";
//	public static String CAR_DIR = "E:/baiduyundownload/2013-12-27 数据处理_V2/fourOp/CAR-csv/";

	// 2013-12-29
	public static String COMP_FILE = "E:/baiduyundownload/2013-12-29 数据处理_V3/CompNew_1229.csv";
	public static String BETA_DIR = "E:/baiduyundownload/2013-12-29 数据处理_V3/Deal_3/BETA0809/";
	public static String CAR_DIR = "E:/baiduyundownload/2013-12-29 数据处理_V3/Deal_3/CAR0809/";

	public FileReaderOpThird fileReaderOpThird = null;
	
	public String Stkcd = "";
	public String Nmetc = "";
	public String Datfst = "";
	public String[] variable = { "1-Day", "2-个股日收益率", "3-个股日资本收益率", "4-等权平均市场日收益率","5-流通市值加权平均市场日收益率","6-总市值加权平均市场收益率",
			"7-等权平均市场日资本收益率","8-流通市值加权平均市场日资本收益率","9-总市值加权平均日资本收益","10-无风险收益率","11-Alpha_流通市值加权_Alpha_tmv",
			"12-风险因子_流通市值加权_Beta_tmv","13-R方_流通市值加权_R2_tmv","14-调整R方_流通市值加权_R2adj_tmv","15-Alpha_总市值加权_Alpha_mc",
			"16-风险因子_总市值加权_Beta_mc","17-R方_总市值加权_R2_mc","18-调整R方_总市值加权_R2adj_mc"};
	public String Csrciccd2 = "";
	
	public String[] arr_1 = null;
	public String[] arr_2 = null;
	public String[] arr_3 = null;
	public String[] arr_4 = null;
	public String[] arr_5 = null;
	public String[] arr_6 = null;
	public String[] arr_7 = null;
	public String[] arr_8 = null;
	public String[] arr_9 = null;
	public String[] arr_10 = null;
	public String[] arr_11 = null;
	public String[] arr_12 = null;
	public String[] arr_13 = null;
	public String[] arr_14 = null;
	public String[] arr_15 = null;
	public String[] arr_16 = null;
	public String[] arr_17 = null;
	public String[] arr_18 = null;
	
	/**
	 * Init array structures
	 */
	private void initResStruct() {
		this.Stkcd = "";
		this.Nmetc = "";
		this.Datfst = "";
		this.Csrciccd2 = "";
		
		this.arr_1 = new String[281];for(int i = 0; i < 281; ++i) this.arr_1[i] = "0";
		this.arr_2 = new String[281];for(int i = 0; i < 281; ++i) this.arr_2[i] = "0";
		this.arr_3 = new String[281];for(int i = 0; i < 281; ++i) this.arr_3[i] = "0";
		this.arr_4 = new String[281];for(int i = 0; i < 281; ++i) this.arr_4[i] = "0";
		this.arr_5 = new String[281];for(int i = 0; i < 281; ++i) this.arr_5[i] = "0";
		this.arr_6 = new String[281];for(int i = 0; i < 281; ++i) this.arr_6[i] = "0";
		this.arr_7 = new String[281];for(int i = 0; i < 281; ++i) this.arr_7[i] = "0";
		this.arr_8 = new String[281];for(int i = 0; i < 281; ++i) this.arr_8[i] = "0";
		this.arr_9 = new String[281];for(int i = 0; i < 281; ++i) this.arr_9[i] = "0";
		this.arr_10 = new String[281];for(int i = 0; i < 281; ++i) this.arr_10[i] = "0";
		this.arr_11 = new String[281];for(int i = 0; i < 281; ++i) this.arr_11[i] = "0";
		this.arr_12 = new String[281];for(int i = 0; i < 281; ++i) this.arr_12[i] = "0";
		this.arr_13 = new String[281];for(int i = 0; i < 281; ++i) this.arr_13[i] = "0";
		this.arr_14 = new String[281];for(int i = 0; i < 281; ++i) this.arr_14[i] = "0";
		this.arr_15 = new String[281];for(int i = 0; i < 281; ++i) this.arr_15[i] = "0";
		this.arr_16 = new String[281];for(int i = 0; i < 281; ++i) this.arr_16[i] = "0";
		this.arr_17 = new String[281];for(int i = 0; i < 281; ++i) this.arr_17[i] = "0";
		this.arr_18 = new String[281];for(int i = 0; i < 281; ++i) this.arr_18[i] = "0";
	}

	public LogicOpThird() {
		fileReaderOpThird = new FileReaderOpThird();
		
		this.fileReaderOpThird.readCompNew(COMP_FILE);
		this.fileReaderOpThird.readBETADir(BETA_DIR);
		this.fileReaderOpThird.readCARDir(CAR_DIR);
	}
	
	/**
	 * Single 
	 * 
	 * @param line
	 */
	public void singleIter(String line) {
		this.initResStruct();

		String [] lineArr = line.split(",");
		
		String Stkcd = lineArr[0];
		String Nmetc = lineArr[1];
		String Datfst = lineArr[2].replaceAll("/", "-");
		String Csrciccd2 = lineArr[4];
		
		System.out.println(Datfst);
		
		try {
			// simple
			this.Stkcd = Stkcd;
			this.Nmetc = Nmetc;
			this.Datfst = Datfst;
			this.Csrciccd2 = Csrciccd2;
			
			Map<String, String> CAR_SINGLE = this.fileReaderOpThird.CAR_MAP.get(Stkcd);
			Map<String, String> BETA_SINGLE = this.fileReaderOpThird.BETA_MAP.get(Stkcd);
			
			// complex data
			// 0
			String carStr = CAR_SINGLE.get(Datfst);
			String[] carStrArr = carStr.split(",");
			String betaStr = null;
			String[] betaStrArr = new String[8];
			if (BETA_SINGLE.containsKey(Datfst)) {
				betaStr = BETA_SINGLE.get(Datfst);
				betaStrArr = betaStr.split(",");
			} else {
				betaStrArr[0] = "0";
				betaStrArr[1] = "0";
				betaStrArr[2] = "0";
				betaStrArr[3] = "0";
				betaStrArr[4] = "0";
				betaStrArr[5] = "0";
				betaStrArr[6] = "0";
				betaStrArr[7] = "0";
			}
			this.arr_1[0] = Datfst;this.arr_2[0] = carStrArr[0];this.arr_3[0] = carStrArr[1];this.arr_4[0] = carStrArr[2];
			this.arr_5[0] = carStrArr[3];this.arr_6[0] = carStrArr[4];this.arr_7[0] = carStrArr[5];this.arr_8[0] = carStrArr[6];
			this.arr_9[0] = carStrArr[7];this.arr_10[0] = carStrArr[8];this.arr_11[0] = betaStrArr[0];this.arr_12[0] = betaStrArr[1];
			this.arr_13[0] = betaStrArr[2];this.arr_14[0] = betaStrArr[3];this.arr_15[0] = betaStrArr[4];this.arr_16[0] = betaStrArr[5];
			this.arr_17[0] = betaStrArr[6];this.arr_18[0] = betaStrArr[7];

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

				if (this.fileReaderOpThird.CAR_MAP.get(Stkcd).containsKey(dtStr)) {
					carStr = this.fileReaderOpThird.CAR_MAP.get(Stkcd).get(dtStr);
					carStrArr = carStr.split(",");
					if (this.fileReaderOpThird.BETA_MAP.get(Stkcd).containsKey(dtStr) && !carStrArr[0].equals("") && !carStrArr[1].equals("")) {
						betaStr = this.fileReaderOpThird.BETA_MAP.get(Stkcd).get(dtStr);
						betaStrArr = betaStr.split(",");
						this.arr_1[idx] = dtStr;this.arr_2[idx] = carStrArr[0];this.arr_3[idx] = carStrArr[1];this.arr_4[idx] = carStrArr[2];
						this.arr_5[idx] = carStrArr[3];this.arr_6[idx] = carStrArr[4];this.arr_7[idx] = carStrArr[5];this.arr_8[idx] = carStrArr[6];
						this.arr_9[idx] = carStrArr[7];this.arr_10[idx] = carStrArr[8];this.arr_11[idx] = betaStrArr[0];this.arr_12[idx] = betaStrArr[1];
						this.arr_13[idx] = betaStrArr[2];this.arr_14[idx] = betaStrArr[3];this.arr_15[idx] = betaStrArr[4];this.arr_16[idx] = betaStrArr[5];
						this.arr_17[idx] = betaStrArr[6];this.arr_18[idx] = betaStrArr[7];
						
						idx--;
					} 
//					else {
//						this.arr_1[idx] = dtStr;this.arr_2[idx] = carStrArr[0];this.arr_3[idx] = carStrArr[1];this.arr_4[idx] = carStrArr[2];
//						this.arr_5[idx] = carStrArr[3];this.arr_6[idx] = carStrArr[4];this.arr_7[idx] = carStrArr[5];this.arr_8[idx] = carStrArr[6];
//						this.arr_9[idx] = carStrArr[7];this.arr_10[idx] = carStrArr[8];this.arr_11[idx] = betaStrArr[0];this.arr_12[idx] = betaStrArr[1];
//						this.arr_13[idx] = betaStrArr[2];this.arr_14[idx] = betaStrArr[3];this.arr_15[idx] = betaStrArr[4];this.arr_16[idx] = betaStrArr[5];
//						this.arr_17[idx] = betaStrArr[6];this.arr_18[idx] = betaStrArr[7];					
//					}
					
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

				if (this.fileReaderOpThird.CAR_MAP.get(Stkcd).containsKey(dtStr)) {
					carStr = this.fileReaderOpThird.CAR_MAP.get(Stkcd).get(dtStr);
					carStrArr = carStr.split(",");
					if (this.fileReaderOpThird.BETA_MAP.get(Stkcd).containsKey(dtStr) && !carStrArr[0].equals("") && !carStrArr[1].equals("")) {
						betaStr = this.fileReaderOpThird.BETA_MAP.get(Stkcd).get(dtStr);
						betaStrArr = betaStr.split(",");
						this.arr_1[idx] = dtStr;this.arr_2[idx] = carStrArr[0];this.arr_3[idx] = carStrArr[1];this.arr_4[idx] = carStrArr[2];
						this.arr_5[idx] = carStrArr[3];this.arr_6[idx] = carStrArr[4];this.arr_7[idx] = carStrArr[5];this.arr_8[idx] = carStrArr[6];
						this.arr_9[idx] = carStrArr[7];this.arr_10[idx] = carStrArr[8];this.arr_11[idx] = betaStrArr[0];this.arr_12[idx] = betaStrArr[1];
						this.arr_13[idx] = betaStrArr[2];this.arr_14[idx] = betaStrArr[3];this.arr_15[idx] = betaStrArr[4];this.arr_16[idx] = betaStrArr[5];
						this.arr_17[idx] = betaStrArr[6];this.arr_18[idx] = betaStrArr[7];
						idx++;
					} 
//					else {
//						this.arr_1[idx] = dtStr;this.arr_2[idx] = carStrArr[0];this.arr_3[idx] = carStrArr[1];this.arr_4[idx] = carStrArr[2];
//						this.arr_5[idx] = carStrArr[3];this.arr_6[idx] = carStrArr[4];this.arr_7[idx] = carStrArr[5];this.arr_8[idx] = carStrArr[6];
//						this.arr_9[idx] = carStrArr[7];this.arr_10[idx] = carStrArr[8];this.arr_11[idx] = betaStrArr[0];this.arr_12[idx] = betaStrArr[1];
//						this.arr_13[idx] = betaStrArr[2];this.arr_14[idx] = betaStrArr[3];this.arr_15[idx] = betaStrArr[4];this.arr_16[idx] = betaStrArr[5];
//						this.arr_17[idx] = betaStrArr[6];this.arr_18[idx] = betaStrArr[7];
//					}
					
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

			for (int i = 0; i < 18; ++i) {
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
					} else if (i == 5) {
						writer.write(this.arr_6[idx_less] + ",");
					}else if (i == 6) {
						writer.write(this.arr_7[idx_less] + ",");
					}else if (i == 7) {
						writer.write(this.arr_8[idx_less] + ",");
					}else if (i == 8) {
						writer.write(this.arr_9[idx_less] + ",");
					}else if (i == 9) {
						writer.write(this.arr_10[idx_less] + ",");
					}else if (i == 10) {
						writer.write(this.arr_11[idx_less] + ",");
					}else if (i == 11) {
						writer.write(this.arr_12[idx_less] + ",");
					}else if (i == 12) {
						writer.write(this.arr_13[idx_less] + ",");
					}else if (i == 13) {
						writer.write(this.arr_14[idx_less] + ",");
					}else if (i == 14) {
						writer.write(this.arr_15[idx_less] + ",");
					}else if (i == 15) {
						writer.write(this.arr_16[idx_less] + ",");
					}else if (i == 16) {
						writer.write(this.arr_17[idx_less] + ",");
					}else if (i == 17) {
						writer.write(this.arr_18[idx_less] + ",");
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
				}else if (i == 6) {
					writer.write(this.arr_7[0] + ",");
				}else if (i == 7) {
					writer.write(this.arr_8[0] + ",");
				}else if (i == 8) {
					writer.write(this.arr_9[0] + ",");
				}else if (i == 9) {
					writer.write(this.arr_10[0] + ",");
				}else if (i == 10) {
					writer.write(this.arr_11[0] + ",");
				}else if (i == 11) {
					writer.write(this.arr_12[0] + ",");
				}else if (i == 12) {
					writer.write(this.arr_13[0] + ",");
				}else if (i == 13) {
					writer.write(this.arr_14[0] + ",");
				}else if (i == 14) {
					writer.write(this.arr_15[0] + ",");
				}else if (i == 15) {
					writer.write(this.arr_16[0] + ",");
				}else if (i == 16) {
					writer.write(this.arr_17[0] + ",");
				}else if (i == 17) {
					writer.write(this.arr_18[0] + ",");
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
					}else if (i == 6) {
						writer.write(this.arr_7[idx_large] + ",");
					}else if (i == 7) {
						writer.write(this.arr_8[idx_large] + ",");
					}else if (i == 8) {
						writer.write(this.arr_9[idx_large] + ",");
					}else if (i == 9) {
						writer.write(this.arr_10[idx_large] + ",");
					}else if (i == 10) {
						writer.write(this.arr_11[idx_large] + ",");
					}else if (i == 11) {
						writer.write(this.arr_12[idx_large] + ",");
					}else if (i == 12) {
						writer.write(this.arr_13[idx_large] + ",");
					}else if (i == 13) {
						writer.write(this.arr_14[idx_large] + ",");
					}else if (i == 14) {
						writer.write(this.arr_15[idx_large] + ",");
					}else if (i == 15) {
						writer.write(this.arr_16[idx_large] + ",");
					}else if (i == 16) {
						writer.write(this.arr_17[idx_large] + ",");
					}else if (i == 17) {
						writer.write(this.arr_18[idx_large] + ",");
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
		// DEAL
		LogicOpThird logicOpThird = new LogicOpThird();
		
		String resFileName = "E:/baiduyundownload/2013-12-29 数据处理_V3/res_2_2_1229.dat";
		Iterator iter = logicOpThird.fileReaderOpThird.CompNew.entrySet().iterator();
		int count = 0;
		while (iter.hasNext()) {
			Entry entry = (Entry) iter.next();

			String key = (String) entry.getKey();
			String val = (String) entry.getValue();
			
			System.out.println((++count) + " -- " + val);

			logicOpThird.singleIter(val);
			logicOpThird.writeIntoFile(resFileName);
		}

	}

}
