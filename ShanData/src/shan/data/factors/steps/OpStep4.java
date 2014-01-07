package shan.data.factors.steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shan.data.factors.file.FileReadOp;

public class OpStep4 {
	private FileReadOp fileReadOp = null;
	private Map<String, String[]> mapFile5 = null;
	
	public OpStep4() {
		this.fileReadOp = new FileReadOp();
		this.mapFile5 = this.fileReadOp.readFile_5();
	}

	/**
	 * Deal single stock.
	 * 
	 * @param Stkcd
	 * @param Datfst
	 * @return
	 * 		"1.2,2.3,3.4,12.34,56.78,90.12,34.56","...",...
	 */
	public String dealSingleStock(String Stkcd, String Datfst) {
		String key = Stkcd + Datfst;
		
		if(this.mapFile5.containsKey(key)) {
			String [] valArr = this.mapFile5.get(key);
			
			double h1 = Double.valueOf(valArr[4]);
			double h5 = Double.valueOf(valArr[4]) + Double.valueOf(valArr[5]) 
				   + Double.valueOf(valArr[6]) + Double.valueOf(valArr[7])
				   + Double.valueOf(valArr[8]);
			double h10= Double.valueOf(valArr[4]) + Double.valueOf(valArr[5])
				   + Double.valueOf(valArr[6]) + Double.valueOf(valArr[7])
				   + Double.valueOf(valArr[8]) + Double.valueOf(valArr[9])
				   + Double.valueOf(valArr[10]) + Double.valueOf(valArr[11])
				   + Double.valueOf(valArr[12]) + Double.valueOf(valArr[13]);
			
			return String.valueOf(h1) + "," + String.valueOf(h5) + "," + String.valueOf(h10) + "," 
				   + valArr[0] + "," + valArr[1] + "," + valArr[2] + "," + valArr[3];
		}
		
		return "NULL,NULL,NULL,NULL,NULL,NULL,NULL";
	}
	
	/**
	 * Deal all stock information.
	 * 
	 * @param stockInfo
	 * @return
	 * 		<"0000012013-01-06","1.2,2.3,3.4,12.34,56.78,90.12,34.56">,<...>,...
	 */
	public Map<String, String> dealAllStock(List<String[]> stockInfo) {
		Map<String, String> resMap = new HashMap<String, String>();
		
		for(int idx = 0; idx < stockInfo.size(); idx++) {
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
