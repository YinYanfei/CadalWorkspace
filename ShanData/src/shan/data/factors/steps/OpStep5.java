package shan.data.factors.steps;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import shan.data.factors.file.FileReadOp;

public class OpStep5 {
	private FileReadOp fileReadOp = null;
	private Map<String, String> mapFile6 = null;
	
	public OpStep5() {
		this.fileReadOp = new FileReadOp();
		this.mapFile6 = this.fileReadOp.readFile_6();
	}

	/**
	 * Deal single stock information.
	 * 
	 * @param Stkcd
	 * @param Datfst
	 * @return
	 * 		"2388795202,0,2388795202,59744"
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
				
				if(this.mapFile6.containsKey(key)){
					return this.mapFile6.get(key);
				}
				
				key = Stkcd + dtStrTwo;
				if(this.mapFile6.containsKey(key)){
					return this.mapFile6.get(key);
				}
				
				calOne.add(Calendar.YEAR, -1);
				calTwo.add(Calendar.YEAR, -1);
				
				dtStrOne = sdf.format(calOne.getTime());
				dtStrTwo = sdf.format(calTwo.getTime());
			}
			
			return "NULL,NULL,NULL,NULL";
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return "NULL,NULL,NULL,NULL";
	}
	
	/**
	 * Deal all stock information.
	 * 
	 * @param stockInfo
	 * @return
	 * 		<"0000012013-05-21", "2388795202,0,2388795202,59744">,<...>,...
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
