/**
 * 
 */
package cn.cadal.rec.ol.algo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.cadal.rec.ol.dao.SQLSet;

public class AlgoItemCF extends RecAlgo{
	
	/**
	 * Construct function
	 */
	public AlgoItemCF() {
		super();
	}
	public AlgoItemCF(String dbname) {
		super(dbname);
	}

	/**
	 * Get recommendation by a single book-id
	 * 
	 * @param bookid
	 * 				book-id, string type
	 * @return		list of recommendation, book-id
	 */
	public List<String> RecBook(String bookid) {
		List<Integer> bookNoList = new ArrayList<Integer>();
		String sql = SQLSet.QUERY_ITEMCF_BY_BOOKID_1;
		int[] type = new int[1];
		Object[] param = new Object[1];
		
		type[0] = java.sql.Types.VARCHAR;
		param[0] = bookid;
		List<Map> list = db.executeQuery(sql,param,type);
		
		for(int i = 0;i<list.size();++i){
			Map map = list.get(i);
			for(Iterator it = map.keySet().iterator();it.hasNext();){
				String columnName = it.next().toString();
				int bkNo = Integer.valueOf(map.get(columnName).toString());
				if(!bookNoList.contains(bkNo)){
					bookNoList.add(bkNo);
				}
			}
		}
		//��booknoת���� bookid
		List<String> bookidList = new ArrayList<String>();
		for(int bookNo : bookNoList){
			String Sql = SQLSet.QUERY_BOOKID_BY_BOOKNO;
			int[] Type = new int[1];
			Object[] Param = new Object[1];
			
			Type[0] = java.sql.Types.INTEGER;
			Param[0] = bookNo;
			List<Map> temp = db.executeQuery(Sql,Param,Type);
			for(int i = 0; i< temp.size(); ++i){
				Map map = temp.get(i);
				for(Iterator it=map.keySet().iterator();it.hasNext();){
					String columnName = it.next().toString();
					bookidList.add(map.get(columnName).toString());
				}
			}
		}
		return bookidList;
	}
	
	/**
	 * Get recommendation by a list of book-id
	 * 
	 * @param listBookid
	 * 				list of book-id, string type
	 * @return		list of recommendation, book-id
	 */
	public List<String> RecBook(List<String> listBookid) {
		List<String> recList = new ArrayList<String>();		// speed optimize
		int count = listBookid.size() > 3?3:listBookid.size();
		for(int i = 0; i<count; ++i){
			for(String bookid : RecBook(listBookid.get(i))){
				if(!recList.contains(bookid))
					recList.add(bookid);
			}
		}
		return recList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test
		AlgoItemCF itemcf = new AlgoItemCF();
		List<String> listbookid = new ArrayList<String>();
		listbookid.add("07018720");
		listbookid.add("07018726");
		for(String id : itemcf.RecBook(listbookid))
			System.out.println(id);
	//	itemcf.RecBook("07018720");
	}
}