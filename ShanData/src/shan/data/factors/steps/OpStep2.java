package shan.data.factors.steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shan.data.factors.file.FileReadOp;

public class OpStep2 {
	private FileReadOp fileReadOp = null;
	private Map<String, String[]> mapFile3 = null;
	
	public OpStep2() {
		this.fileReadOp = new FileReadOp();
		this.mapFile3 = this.fileReadOp.readFile_3();
	}

	/**
	 * Deal single stock by Stkcd, Datfst and carNum, for method one.
	 * 
	 * @param Stkcd
	 * @param Datfst
	 * @param carNum
	 * @return
	 */
	public double dealSingleStockMothed1(String Stkcd, String Datfst, int carNum) {
		if(carNum <= 0) return 0.0;
		if(carNum > 250) carNum = 250;
		try{
			if(!this.mapFile3.containsKey(Stkcd + Datfst)) {
				return -1;
			}
			String [] contents = this.mapFile3.get(Stkcd + Datfst);
			double res = 0.0;
			
			for(int idx = 254; idx > 254 - carNum; --idx) {
				res += Double.valueOf(contents[0].split(",")[idx]) - Double.valueOf(contents[1].split(",")[idx]);
			}
			
			return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	/**
	 * Deal single stock by Stkcd, Datfst and carNum, for method two.
	 * 
	 * @param Stkcd
	 * @param Datfst
	 * @param carNum
	 * @return
	 */
	public double dealSingleStockMothed2(String Stkcd, String Datfst, int carNum) {
		if(carNum <= 0) return 0.0;
		if(carNum > 250) carNum = 250;

		try{
			if(!this.mapFile3.containsKey(Stkcd + Datfst)){
				return -1;
			}
			String [] contents = this.mapFile3.get(Stkcd + Datfst);
			
			String [] arr_2 = contents[0].split(",");
			String [] arr_6 = contents[2].split(",");
			String [] arr_15 = contents[3].split(",");
			String [] arr_16 = contents[4].split(",");
			
			double res = 0.0;
			
			for(int idx = 254; idx > 254 - carNum; --idx) {
				res += Double.valueOf(arr_2[idx]) - (Double.valueOf(Double.valueOf(arr_15[idx]) + Double.valueOf(arr_16[idx]) * Double.valueOf(arr_6[idx])));
			}
			
			return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	/**
	 * Deal single Stock information.
	 * 
	 * @param Stkcd
	 * @param Datfst
	 * @return
	 * 		[12.3,45.6,78.9,0.12],[...],...
	 */
	public double[] dealSingleStock(String Stkcd, String Datfst) {
		double [] res = new double[4];
		
		res[0] = this.dealSingleStockMothed1(Stkcd, Datfst, 20);
		res[1] = this.dealSingleStockMothed1(Stkcd, Datfst, 250);
		res[2] = this.dealSingleStockMothed2(Stkcd, Datfst, 20);
		res[3] = this.dealSingleStockMothed2(Stkcd, Datfst, 250);
		
		return res;
	}
	
	/**
	 * Deal all Stock information.
	 * 
	 * @param stockInfo
	 * @return
	 * 		<"0000012013-01-06", [12.3,45.6,78.9,0.12]>,<...>,...
	 */
	public Map<String, double[]> dealAllStock(List<String[]> stockInfo) {
		Map<String, double[]> resMap = new HashMap<String, double[]>();
		
		for(int idx = 0; idx < stockInfo.size(); ++idx) {
			resMap.put(stockInfo.get(idx)[0] + stockInfo.get(idx)[1], this.dealSingleStock(stockInfo.get(idx)[0], stockInfo.get(idx)[1]));
		}
		
		return resMap;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
}
