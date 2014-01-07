package shan.data.factors.steps;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shan.data.factors.file.FileReadOp;

public class OpStep13 {
	private FileReadOp fileReadOp = null;
	private Map<String, String> mapFile14 = null;
	
	public OpStep13() {
		this.fileReadOp = new FileReadOp();
		this.mapFile14 = this.fileReadOp.readFile_14();
	}

	/**
	 * Deal single stock information.
	 * 
	 * @param Stkcd
	 * @param Datfst
	 * @return
	 * 		"许继电气股份有限公司,有条件通过,2012000400A001,10000,000400,B,2012-12-19,NULL,1,NULL,A,1,资产收购,..."
	 */
	public String dealSingleStock(String Stkcd, String Datfst) {
		String key = Stkcd + Datfst;
		
		if(this.mapFile14.containsKey(key)) {
			return this.mapFile14.get(key);
		}
		
		return null;
	}
	
	/**
	 * Deal all stock information.
	 * 
	 * @param stockInfo
	 * @return
	 * 		<"0000012013-05-21", "许继电气股份有限公司,有条件通过,2012000400A001,10000,000400,B,2012-12-19,NULL,1,NULL,A,1,资产收购,...">,<...>,...
	 */
	public Map<String, String> dealAllStock(List<String[]> stockInfo) {
		Map<String, String> resMap = new HashMap<String, String>();
		
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
