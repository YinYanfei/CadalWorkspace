package cn.cadal.rec.ol.qa;

import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.cadal.rec.ol.common.Book;
import cn.cadal.rec.ol.dao.DBAgent;
import cn.cadal.rec.ol.dao.SQLSet;

public class RecResultInsert {

	private String DBName = "cadalrectest";
	private DBAgent db = null;
	
	/**
	 * Construct functions
	 */
	public RecResultInsert(){
		this.db = new DBAgent(this.DBName);
	}
	public RecResultInsert(String dbname) {
		this.DBName = dbname;
		this.db = new DBAgent(dbname);
	}
	
	/**
	 * Insert recommendation result into databases.
	 * 
	 * @param listRecBook
	 * 				list of book object
	 * @param disCount
	 * 				the number to display
	 * @param userno
	 * 				user number
	 * @param time
	 * 				time for recommendation
	 */
	public void RecResInsert(List<Book> listRecBook, int disCount, int userno, Timestamp time) {
		int fkNo = this.ResInsert(userno, time);
		
		if(fkNo > 0) {
			this.RedDetailInsert(listRecBook, disCount, fkNo);
		}
	}
	
	/**
	 * To insert userno and time into R_RESULT
	 * 
	 * @param userno
	 * 				userno
	 * @param time
	 * 				recommendation time
	 * @return		the foreign key for R_RESULT_DETAIL
	 */
	@SuppressWarnings("unchecked")
	private int ResInsert(int userno, Timestamp time){
		// insert into information
		Object[] param = new Object[2];
		int[] type     = new int[2];
		
		param[0] = userno;
		param[1] = time;
		type[0]  = java.sql.Types.INTEGER;
		type[1]  = java.sql.Types.TIMESTAMP;
		
		int count = this.db.executeUpdate(SQLSet.QUERY_BOOK_INFO_BY_NO, param, type);
		if(count < 0) {
			return -1;
		}
	
		// query max foreign key for user no
		int fkNo = -1;
		
		Object[] paramQuery = new Object[1];
		int[] typeQuery     = new int[1];
		
		paramQuery[0] = userno;
		typeQuery[0]  = java.sql.Types.INTEGER;
		
		List<Map> list = this.db.executeQuery(SQLSet.QUERY_MAX_REC_RESULT_ID, paramQuery, typeQuery);
		
		for(int i = 0; i < list.size(); ++i){
			Map map = list.get(i);
			for(Iterator it = map.keySet().iterator();it.hasNext();){
				String columnName = it.next().toString();
				if(columnName.equals("rec_result_id")){
					fkNo = Integer.valueOf(map.get(columnName).toString());
				}
			}
		}
		
		return fkNo;
	}
	
	/**
	 * To insert into recommendation detail into table R_RESULT_DETAIL
	 * 
	 * @param listRecBook
	 * 				list of Book object
	 * @param disCount
	 * 				the first #disCount for display
	 * @param fkForRes
	 * 				foreign key to R_RESULT
	 */
	private void RedDetailInsert(List<Book> listRecBook, int disCount, int fkForRes){
		if(disCount > listRecBook.size())
			disCount = listRecBook.size();
		
		Object[] param = new Object[3];
		int[] type     = new int[3];
		
		param[0] = fkForRes;
		
		type[0] = java.sql.Types.INTEGER;
		type[1] = java.sql.Types.VARCHAR;
		type[2] = java.sql.Types.INTEGER;
		
		int count = 1;
		for(Book book : listRecBook) {
			param[1] = book.getBookId();
			if(disCount > count) {
				param[2] = 1;
			}else{
				param[2] = 0;
			}
			
			this.db.executeUpdate(SQLSet.INSERT_REC_RESULT_DETAIL, param, type);
			count += 1;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test
//		java.util.Date date=new java.util.Date();
//		Timestamp tt=new Timestamp(date.getTime());
//		
//		System.out.println(tt);
		
	} 
	
	/**
	 * Getter and Setter
	 */
	public String getDBName() {
		return DBName;
	}
	public DBAgent getDb() {
		return db;
	}
	public void setDBName(String dBName) {
		DBName = dBName;
	}
	public void setDb(DBAgent db) {
		this.db = db;
	}

}
