package shan.data.fiveOp;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class LogicOpFive {
	// 2013-12-29
	public final static String COMP_NEW = "E:/baiduyundownload/2014-01-01 数据处理_V4/股价综合/08-13 stock_360.csv";
	public final static String STOCK_DIR = "E:/baiduyundownload/2014-01-01 数据处理_V4/股价综合/股价数据/";
	
	public FileReaderOpFive fileReaderOpFour = null;

	public String Stkcd = "";
	public String Nmetc = "";
	public String Datfst = "";
	public String[] variable = { "1-Day", "2-Opnprc", "3-Hiprc", "4-Loprc","5-Clsprc","6-Dnshrtrd",
								 "7-Dnvaltrd","8-Dsmvosd","9-Dsmvtll","10-Dretwd","11-Dretnd","12-Adjprcwd",
								 "13-Adjprcnd","14-Markettype","15-Capchgdt","16-Trdsta"};
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

	public LogicOpFive(){
		this.fileReaderOpFour = new FileReaderOpFive();
		
		this.fileReaderOpFour.readCompNew(COMP_NEW);
		this.fileReaderOpFour.readGarchDir(STOCK_DIR);
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
		for(int i = 0; i < 281; ++i) this.arr_1[i] = "0";
		this.arr_2 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_2[i] = "0";
		this.arr_3 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_3[i] = "0";
		this.arr_4 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_4[i] = "0";
		this.arr_5 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_5[i] = "0";
		this.arr_6 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_6[i] = "0";
		this.arr_7 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_7[i] = "0";
		this.arr_8 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_8[i] = "0";
		this.arr_9 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_9[i] = "0";
		this.arr_10 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_10[i] = "0";
		this.arr_11 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_11[i] = "0";
		this.arr_12 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_12[i] = "0";
		this.arr_13 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_13[i] = "0";
		this.arr_14 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_14[i] = "0";
		this.arr_15 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_15[i] = "0";
		this.arr_16 = new String[281];
		for(int i = 0; i < 281; ++i) this.arr_16[i] = "0";
	}

	public void singleIter(String line) {
		this.initResStruct();

		String [] lineArr = line.split(",");
		
		String Stkcd = lineArr[0];
		String Nmetc = lineArr[1];
		String Datfst = lineArr[2];
		
		System.out.println(lineArr[2]);
		System.out.println(Datfst);
		
		String Csrciccd2 = lineArr[3];
		
		try {
			// simple
			this.Stkcd = Stkcd;
			this.Nmetc = Nmetc;
			this.Datfst = Datfst;
			this.Csrciccd2 = Csrciccd2;
			Map<String, String> COMP_NEW_SINGLE = this.fileReaderOpFour.StockPriceMap.get(Stkcd); 
			
			// complex data
			// 0
			String compStr = COMP_NEW_SINGLE.get(Datfst);
			String[] compStrArr = compStr.split(",");

			this.arr_1[0] = Datfst;
			this.arr_2[0] = compStrArr[0];
			this.arr_3[0] = compStrArr[1];
			this.arr_4[0] = compStrArr[2];
			this.arr_5[0] = compStrArr[3];
			this.arr_6[0] = compStrArr[4];
			this.arr_7[0] = compStrArr[5];
			this.arr_8[0] = compStrArr[6];
			this.arr_9[0] = compStrArr[7];
			this.arr_10[0] = compStrArr[8];
			this.arr_11[0] = compStrArr[9];
			this.arr_12[0] = compStrArr[10];
			this.arr_13[0] = compStrArr[11];
			this.arr_14[0] = compStrArr[12];
			this.arr_15[0] = compStrArr[13];
			this.arr_16[0] = compStrArr[14];

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			// 1 -> 250
			Date dt = sdf.parse(Datfst);
			int idx = 250;
			while (idx > 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(dt);
				cal.add(Calendar.DAY_OF_YEAR, -1);
				String dtStr = sdf.format(cal.getTime());

				if (dt.compareTo(sdf.parse("2002-12-31")) < 0) {
					System.out.println("-=-==--=-=-=-=");
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
					this.arr_6[idx] = compStrArr[4];
					this.arr_7[idx] = compStrArr[5];
					this.arr_8[idx] = compStrArr[6];
					this.arr_9[idx] = compStrArr[7];
					this.arr_10[idx] = compStrArr[8];
					this.arr_11[idx] = compStrArr[9];
					this.arr_12[idx] = compStrArr[10];
					this.arr_13[idx] = compStrArr[11];
					this.arr_14[idx] = compStrArr[12];
					this.arr_15[idx] = compStrArr[13];
					this.arr_16[idx] = compStrArr[14];
					
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

				if (dt.compareTo(sdf.parse("2014-12-31")) > 0) {
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
					this.arr_6[idx] = compStrArr[4];
					this.arr_7[idx] = compStrArr[5];
					this.arr_8[idx] = compStrArr[6];
					this.arr_9[idx] = compStrArr[7];
					this.arr_10[idx] = compStrArr[8];
					this.arr_11[idx] = compStrArr[9];
					this.arr_12[idx] = compStrArr[10];
					this.arr_13[idx] = compStrArr[11];
					this.arr_14[idx] = compStrArr[12];
					this.arr_15[idx] = compStrArr[13];
					this.arr_16[idx] = compStrArr[14];
					
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

			for (int i = 0; i < 16; ++i) {
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
					}else if (i == 5) {
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
		LogicOpFive logicOpFour = new LogicOpFive();
		String resFileName = "E:/baiduyundownload/2014-01-01 数据处理_V4/res_4_130101_360.dat"; 
		
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
