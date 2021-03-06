/**
 * Operator for table R_EDIT in database of recommendation
 */
package cn.cadal.rec.ol.edit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.cadal.rec.ol.dao.DBAgent;
import cn.cadal.rec.ol.dao.SQLSet;

public class RecEditOp {

	private Map<String, Integer> typeMap = null;
	
	private String DBName = "cadalrectest-76";
	private DBAgent db = null;
	
	/**
	 * Construct functions
	 */
	public RecEditOp(){
		this.db = new DBAgent(this.DBName);
		
		this.initType();
	}
	public RecEditOp(String dbname) {
		this.DBName = dbname;
		this.db = new DBAgent(dbname);
		
		this.initType();
	}
	private void initType() {
		this.typeMap = new HashMap<String, Integer>();
		this.typeMap.put("MostVisited", 0);
		this.typeMap.put("LatestVisited", 1);
		this.typeMap.put("type_0", 0);
		this.typeMap.put("type_1", 1);
		this.typeMap.put("0", 0);
		this.typeMap.put("1", 1);
	}
	
	/**
	 * To query a list of book-id by edit_date and edit_type from R_EDIT 
	 * 
	 * @param date
	 * 				date['mainpage',common], string
	 * @param type
	 * 				date, string
	 * @return		a list of book-id, string
	 */
	@SuppressWarnings("unchecked")
	public List<String> QueryByDateType(String date, String type) {
		try{
			Object[] param = new Object[2];
			int[] paramType     = new int[2];
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			param[0] = sf.parse(date);
			param[1] = this.typeMap.get(type);
			
			paramType[0] = java.sql.Types.DATE;
			paramType[1] = java.sql.Types.INTEGER;
			
			List<Map> list = this.db.executeQuery(SQLSet.QUERY_EDIT_REC_DATE_TYPE, param, paramType);
			List<String> listid = new ArrayList<String>();
			
			// to get list of book-id
			for(int i = 0;i<list.size();++i){
				Map map = list.get(i);
				for(Iterator it = map.keySet().iterator();it.hasNext();){
					String columnName = it.next().toString();
					if(columnName.equals("book_id")){
						listid.add(map.get(columnName).toString());
					}
				}
			}
			
			return listid;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * To get a list of book id by edit_date from R_EDIT
	 * 
	 * @param date
	 * 				date, string
	 * @return		list of book-id
	 */
	@SuppressWarnings("unchecked")
	public List<String> QueryByDate(String date) {
		try{
			Object[] param = new Object[1];
			int[] paramType     = new int[1];
			
			SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
			param[0] = sf.parse(date);
			
			paramType[0] = java.sql.Types.DATE;
			
			List<Map> list = this.db.executeQuery(SQLSet.QUERY_EDIT_REC_DATE, param, paramType);
			List<String> listid = new ArrayList<String>();
			
			// to get list of book-id
			for(int i = 0;i<list.size();++i){
				Map map = list.get(i);
				for(Iterator it = map.keySet().iterator();it.hasNext();){
					String columnName = it.next().toString();
					if(columnName.equals("book_id")){
						listid.add(map.get(columnName).toString());
					}
				}
			}
			
			return listid;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Main function
	 */
	public static void main(String[] args) {
		// Analyze and Test
//		RecEditOp recEditOp = new RecEditOp();
//		
//		String date = "2013-09-02";
//		String type = "0";
//		
//		List<String> listRes = recEditOp.QueryByDateType(date, type);
//		
//		System.out.println(listRes.size());
//		
//		for(int i = 0; i < listRes.size(); ++i) {
//			System.out.println(listRes.get(i));
//		}
	}
}
