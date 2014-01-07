package shan.data.factors;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import shan.data.factors.file.FileReadOp;
import shan.data.factors.steps.OpStep1;
import shan.data.factors.steps.OpStep10;
import shan.data.factors.steps.OpStep11;
import shan.data.factors.steps.OpStep12;
import shan.data.factors.steps.OpStep13;
import shan.data.factors.steps.OpStep2;
import shan.data.factors.steps.OpStep3;
import shan.data.factors.steps.OpStep4;
import shan.data.factors.steps.OpStep5;
import shan.data.factors.steps.OpStep6;
import shan.data.factors.steps.OpStep7;
import shan.data.factors.steps.OpStep8;
import shan.data.factors.steps.OpStep9;

public class MainFuns {

	public FileReadOp fileReadOp = null;
	public List<String> strArrFile1 = null;
	
	public OpStep1 opStep1 = null;
	public OpStep2 opStep2 = null;
	public OpStep3 opStep3 = null;
	public OpStep4 opStep4 = null;
	public OpStep5 opStep5 = null;
	public OpStep6 opStep6 = null;
	public OpStep7 opStep7 = null;
	public OpStep8 opStep8 = null;
	public OpStep9 opStep9 = null;
	public OpStep10 opStep10= null;
	public OpStep11 opStep11= null;
	public OpStep12 opStep12= null;
	public OpStep13 opStep13= null;
	
	public MainFuns() {
		this.fileReadOp = new FileReadOp();
		this.strArrFile1 = new ArrayList<String>();
		
		this.strArrFile1 = this.fileReadOp.readFile_1();
		
		this.initSteps();
	}
	
	private void initSteps() {
		this.opStep1 = new OpStep1();
		this.opStep2 = new OpStep2();
		this.opStep3 = new OpStep3();
		this.opStep4 = new OpStep4();
		this.opStep5 = new OpStep5();
		this.opStep6 = new OpStep6();
		this.opStep7 = new OpStep7();
		this.opStep8 = new OpStep8();
		this.opStep9 = new OpStep9();
		this.opStep10= new OpStep10();
		this.opStep11= new OpStep11();
		this.opStep12= new OpStep12();
		this.opStep13= new OpStep13();
	}
	
	/**
	 * To deal single message.
	 * 
	 * @param msg
	 * @return
	 */
	public String dealSingleStock(String msg) {
		
		System.out.println(msg);
		
		String resStr = msg;
		String [] strArr = msg.split(",");
		String Stkcd = strArr[0];
		String Datfst = strArr[2];
		
		// Step-1
		double [] res_step_1 = this.opStep1.dealSingleStock(Stkcd, Datfst);
		resStr = resStr + "," + String.valueOf(res_step_1[0]) + "," + String.valueOf(res_step_1[1]);
		
		// Step-2
		double [] res_step_2 = this.opStep2.dealSingleStock(Stkcd, Datfst);
		resStr = resStr + "," + String.valueOf(res_step_2[0]) + "," + String.valueOf(res_step_2[1])
			   + "," + String.valueOf(res_step_2[2]) + "," + String.valueOf(res_step_2[3]);
		
		// Step-3
		resStr = resStr + "," + this.opStep3.dealSingleStock(Stkcd, Datfst);
		
		// Step-4
		resStr = resStr + "," + this.opStep4.dealSingleStock(Stkcd, Datfst);
		
		// Step-5
		resStr = resStr + "," + this.opStep5.dealSingleStock(Stkcd, Datfst);
		
		// Step-6
		resStr = resStr + "," + this.opStep6.dealSingleStock(Stkcd, Datfst);
		
		// Step-7
		resStr = resStr + "," + this.opStep7.dealSingleStock(Stkcd, Datfst);
		
		// Step-8
		resStr = resStr + "," + this.opStep8.dealSingleStock(Stkcd, Datfst);
		
		// Step-9
		resStr = resStr + "," + this.opStep9.dealSingleStock(Stkcd, Datfst);
		
		// Step-10
		resStr = resStr + "," + this.opStep10.dealSingleStock(Stkcd, Datfst);
		
		// Step-11
		resStr = resStr + "," + this.opStep11.dealSingleStock(Stkcd, Datfst);
		
		// Step-12
		resStr = resStr + "," + this.opStep12.dealSingleStock(Stkcd, Datfst);
		
		// Step-13
		resStr = resStr + "," + this.opStep13.dealSingleStock(Stkcd, Datfst);
		
		return resStr;
	}
	
	/**
	 * Deal all stocks' information
	 * 
	 * @return
	 */
	public List<String> dealAllStock() {
		List<String> listRes = new ArrayList<String>();
		
		for(int idx = 0; idx < strArrFile1.size(); ++idx) {
			listRes.add(this.dealSingleStock(this.strArrFile1.get(idx)));
		}
		
		return listRes;
	}
	
	/**
	 * Write into file
	 * 
	 * @param listStr
	 */
	public void writeIntoFile(final List<String> listStr) {
		FileWriter writer = null;
		
		try{
			writer = new FileWriter(FileInfo.FILE_RES_1);
			
			for(int idx = 0; idx < listStr.size(); idx++) {
				writer.write(listStr.get(idx) + "\n");
			}
		}catch(IOException e) {
			e.printStackTrace();
		}finally{
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MainFuns mf = new MainFuns();
		
		List<String> res = mf.dealAllStock();
		
		mf.writeIntoFile(res);
		
	}

}
