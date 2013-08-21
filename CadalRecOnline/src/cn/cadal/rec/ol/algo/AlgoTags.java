/**
 * 
 */
package cn.cadal.rec.ol.algo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.cadal.rec.ol.dao.SQLSet;

public class AlgoTags extends RecAlgo{
	
	/**
	 * Construct function
	 */
	public AlgoTags() {
		super();
	}
	public AlgoTags(String dbname) {
		super(dbname);
	}

	/**
	 * Get recommendation by a single tag(tagname)
	 * 
	 * @param tag
	 * 				tagname, just like '民国'
	 * @return		list of recommendation, book-id
	 */
	public List<String> RecBook(String tag) {
		List<String> bookidList = new ArrayList<String>();
		String sql = SQLSet.QUERY_BOOKID_BY_TAGNAME;
		int[] type = new int[1];
		Object[] param = new Object[1];
		
		type[0] = java.sql.Types.VARCHAR;
		param[0] = tag;
		
		List<Integer> bookNoList = new ArrayList<Integer>();
		List<Map> list = db.executeQuery(sql,param,type);
		for(int i = 0;i < list.size(); ++i){
			Map map = list.get(i);
			for(Iterator it = map.keySet().iterator();it.hasNext();){
				String columnName = it.next().toString();
				bookNoList.add((Integer) map.get(columnName)); 
			}
		}
		
		//bookno转化成bookid
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
	 * Get recommendation by a list of tag(tagname)
	 * 
	 * @param listTags
	 * 				list of tagname, string type
	 * @return		list of recommendation, book-id
	 */
	public List<String> RecBook(List<String> listTags) {
		List<String> booknoList = new ArrayList<String>();
		for(String tagname : listTags){
			for(String bookid: RecBook(tagname)){
				if(!booknoList.contains(bookid))
					booknoList.add(bookid);
			}
		}
		return booknoList;
	}
	
	/**
	 * Get tags which are related with the single bookid(parameter)
	 * 
	 * @param bookid
	 * 				bookid, string type
	 * @return		list of tagname, string
	 */
	public List<String> RecTags(String bookid){
		List<String> tagList = new ArrayList<String>();
		String sql = SQLSet.QUERY_TAGNAME_BY_BOOKID;
		int[] type = new int[1];
		Object[] param = new Object[1];
		
		type[0] = java.sql.Types.VARCHAR;
		param[0] = bookid;
		
		List<Map> list = db.executeQuery(sql,param,type);
		for(int i = 0; i < list.size(); ++i){
			Map map = list.get(i);
			for(Iterator it = map.keySet().iterator();it.hasNext();){
				String columnName = it.next().toString();
				tagList.add(map.get(columnName).toString());
			}
		}
		return tagList;
	}
	
	/**
	 * Get list of tagname which are related with list of bookid(parameter)
	 * 
	 * @param listBookid
	 * 				list of bookid
	 * @return		list of tagname
	 */
	public List<String> RecTags(List<String> listBookid) {
		List<String> tagList = new ArrayList<String>();
		for(String bookid : listBookid){
			for(String tag : RecTags(bookid)){
				if(!tagList.contains(tag))
					tagList.add(tag);
			}
		}
		return tagList;
	}
	
	/**
	 * To get tags by the user's interest, such as occupation, tags and so on
	 * 
	 * @param interest
	 * 				tags and other can be identified as interest
	 * @return		list of tagname
	 */
	public List<String> RecTagsByUserInterest(String interest) {
		// 对标签的推荐暂不考虑
		return null;
	}
	
	/**
	 * To get list of tags by the user's interest, such as occupation, tags and so on
	 * 
	 * @param listInterest
	 * 				list of tags and other can be identified as interest
	 * @return		list of tagname
	 */	
	public List<String> RecTagsByUserInterest(List<String> listInterest) {
		// 对标签的推荐暂不考虑
		return null;
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Analyze and Test

		AlgoTags tags = new AlgoTags();
		tags.RecBook("民国");
		List<String> bookids = new ArrayList<String>();
		bookids.add("07018720");
		bookids.add("07018721");
		bookids.add("07018722");
		for(String tag: tags.RecTags(bookids))
			System.out.println(tag);
		
	}

}
