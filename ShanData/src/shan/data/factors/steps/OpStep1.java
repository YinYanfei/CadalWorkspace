package shan.data.factors.steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shan.data.factors.file.FileReadOp;

public class OpStep1 {

	private FileReadOp fileReadOp = null;
	private Map<String, double[]> mapFile2 = null;
	
	public OpStep1() {
		this.fileReadOp = new FileReadOp();
		this.mapFile2 = this.fileReadOp.readFile_2();
	}
	
	/**
	 * Deal single stock information.Stkcd and Datfst from file_1.
	 * 
	 * @param Stkcd
	 * @param Datfst
	 * @return
	 * 		[123.4(P20),5678.9(P250)],[...],...
	 */
	public double[] dealSingleStock(String Stkcd, String Datfst) {
		double [] resArr = new double[2];
		
		resArr[0] = this.dealSingleStockForPNum(Stkcd, Datfst, 20);
		resArr[1] = this.dealSingleStockForPNum(Stkcd, Datfst, 250);
		
		return resArr;
	}
	
	/**
	 * Deal all stock information.
	 * 
	 * @param listStockInfo  ["000001","2013-01-06"],["...","..."],...
	 * @return
	 * 		<"0000012013-01-06",[123.4(P20),5678.9(P250)]>,<...>,...
	 */
	public Map<String, double[]> dealAllStock(List<String[]> listStockInfo) {
		Map<String, double[]> resMap = new HashMap<String, double[]>();
		
		for(int idx = 0; idx < listStockInfo.size(); ++idx) {
			String [] strArr = listStockInfo.get(idx);
			
			resMap.put(strArr[0]+strArr[1], this.dealSingleStock(strArr[0], strArr[1]));
		}
		
		return resMap;
	}
	
	/**
	 * Deal single stock with stkcd, datfst and pNum
	 * 
	 * @param Stkcd
	 * @param Datfst
	 * @param pNum
	 * @return
	 */
	private double dealSingleStockForPNum(String Stkcd, String Datfst, int pNum) {
		if(pNum <= 0) return 0.0;
		if(pNum > 250) pNum = 250;
		
		try{
			if(!this.mapFile2.containsKey(Stkcd + Datfst)) {
				return -1;
			}
			double [] ClsprcArr = this.mapFile2.get(Stkcd + Datfst);
			
			double sum = 0.0;
			
			for(int idx = 249; idx > 249 - pNum; --idx) {
				sum += ClsprcArr[idx];
			}
			
			return sum / pNum;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
