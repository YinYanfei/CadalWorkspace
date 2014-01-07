package shan.data.factors.steps;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shan.data.factors.file.FileReadOp;

public class OpStep10 {
	private FileReadOp fileReadOp = null;
	private Map<String, String> mapFile11 = null;
	
	public OpStep10() {
		this.fileReadOp = new FileReadOp();
		this.mapFile11 = this.fileReadOp.readFile_11();
	}

	/**
	 * Deal single stock information.
	 * 
	 * @param Stkcd
	 * @param Datfst
	 * @return
	 * 		"1123983000,1123983000"
	 */
	public String dealSingleStock(String Stkcd, String Datfst) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		try{
			int month = Integer.valueOf(Datfst.split("-")[1]);
			int year = Integer.valueOf(Datfst.split("-")[0]);
			
			String dtStrOne = "";
			String dtStrTwo = "";
			
			if(month > 7) {
				dtStrOne = String.valueOf(year) + "-06-30";
				dtStrTwo = String.valueOf(year - 1) + "-12-31";
			}else{
				dtStrOne = String.valueOf(year - 1) + "-12-31";
				dtStrTwo = String.valueOf(year - 1) + "-06-30";
			}
			
			Calendar calOne = Calendar.getInstance();
			calOne.setTime(sdf.parse(dtStrOne));
			
			Calendar calTwo = Calendar.getInstance();
			calTwo.setTime(sdf.parse(dtStrTwo));
			
			for(int idx = 0; idx < 20; idx++) {
				String key = Stkcd + dtStrOne;
				
				if(this.mapFile11.containsKey(key)){
					return this.mapFile11.get(key);
				}
				
				key = Stkcd + dtStrTwo;
				if(this.mapFile11.containsKey(key)){
					return this.mapFile11.get(key);
				}
				
				calOne.add(Calendar.YEAR, -1);
				calTwo.add(Calendar.YEAR, -1);
				
				dtStrOne = sdf.format(calOne.getTime());
				dtStrTwo = sdf.format(calTwo.getTime());
			}
			
			return "NULL,NULL";
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "NULL,NULL";
	}
	
	/**
	 * Deal all stock information.
	 * 
	 * @param stockInfo
	 * @return
	 * 		<"0000012013-05-21", "1123983000,1123983000">,<...>,...
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
