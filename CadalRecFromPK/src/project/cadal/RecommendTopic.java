package project.cadal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

public class RecommendTopic {
	public static void recommend(ArrayList<accessUnit> accesslog) throws Exception{
		HashMap<String,HashMap<String,Integer>> recMap=new HashMap<String,HashMap<String,Integer>>();
		String name=null;
		String bookid=null;
		String subject=null;
		String[] keywords=null;
		
		System.out.println("start to update recommend user by book....");
		
		long len=accesslog.size();
		for(int i=0;i<len;i++){

			name=accesslog.get(i).username;
			bookid=accesslog.get(i).bookid;
			subject=BerkeleyDB.getBook(bookid);
			if(subject==null)continue;
			keywords=subject.split("\\|");			
			int poepLen=keywords.length;
			for(int k=0;k<poepLen;k++){
				if(!recMap.containsKey(keywords[k])){
					HashMap<String,Integer> curMap=new HashMap<String,Integer>();
					
					//load userProfile from db
					String rec=BerkeleyDB.getRec("topic", keywords[k]);
					//System.out.println(rec+"::::"+keywords[k]);
					if(rec!=null&&!rec.equals("")){
						String[] recArray=rec.split("#");
						for(int j=0;j<recArray.length;j++){
							if(recArray[j].equals(""))continue;
							String[] temp=recArray[j].split("\\|");
							curMap.put(temp[0], Integer.parseInt(temp[1]));
						}
					}
					recMap.put(keywords[k], curMap);
				}
				if(recMap.get(keywords[k]).containsKey(name)){
					recMap.get(keywords[k]).put(name, recMap.get(keywords[k]).get(name)+1);
				}else{
					if(!name.equals("\\N"))
						recMap.get(keywords[k]).put(name, 1);
				}
			}
			
		}
		//original userProfile ����
		System.out.println("start to sort topic....");//
		//original userProfile �������򣬽������д���ļ�
		
		Iterator<Entry<String, HashMap<String, Integer>>> iter=recMap.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, HashMap<String, Integer>> entry=iter.next();

			bookid=entry.getKey();
			HashMap<String, Integer> map=entry.getValue();
			List<Entry<String, Integer>> recList =new ArrayList<Entry<String, Integer>>(map.entrySet());
			Collections.sort(recList,new Comparator(){ public int compare(Object o1, Object o2){return (((Entry<String, Integer>)(o1)).getValue()-((Entry<String, Integer>)(o2)).getValue());}});
			StringBuffer sb=new StringBuffer();
			
			int n=recList.size();
			for(int i=n-1;i>=0;i--){
				sb.append(recList.get(i).getKey()+"|"+recList.get(i).getValue()+"#");	
			}
			//System.out.println(sb.toString());
			if(!sb.toString().equals(""))
			BerkeleyDB.putRec("topic",bookid, sb.toString());
		}	
		System.out.println("end to update recommend list by topic!!!");//
	}
	public static String getMostActiveUser() throws Exception{
		StringBuilder sb=new StringBuilder();
		ArrayList<ActiveUserUnit> tlist=new ArrayList<ActiveUserUnit>();
		Cursor cursor = BerkeleyDB.getRecommendByTopicDB().openCursor(null, null);

		// DatabaseEntry objects used for reading records
		DatabaseEntry foundKey = new DatabaseEntry();
		DatabaseEntry foundData = new DatabaseEntry();

		try { // always want to make sure the cursor gets closed
			while (cursor.getNext(foundKey, foundData,LockMode.DEFAULT) == OperationStatus.SUCCESS) {
				ActiveUserUnit auu=new ActiveUserUnit();
				
	           	auu.topic=new String(foundKey.getData(),"utf-8");
	           	String[] users=new String(foundData.getData(),"utf-8").split("#");
	           	auu.sum=0;
	           	for(int i=0;i<users.length;i++){
	           		String[] temp=users[i].split("\\|");
	           		//System.out.println(users[i]);
	           		auu.sum+=Integer.parseInt(temp[1]);
	           		if(i<10){
	           			auu.user.add(temp[0]);
	           		}
	           	}
	           	tlist.add(auu);
			}
		} catch (Exception e) {;
           	e.printStackTrace();
		} finally {
			cursor.close();
		}
		Collections.sort(tlist,new Comparator(){ public int compare(Object o1, Object o2){return (((ActiveUserUnit)(o2)).sum-((ActiveUserUnit)(o1)).sum);}});
		ArrayList<String> temp=new ArrayList<String>();
		int ll=0;
		for(int i=0;ll<10&&i<tlist.size();i++){
			if(tlist.get(i).topic.equals("\\N"))
				continue;
			//System.out.println(tlist.get(i).topic+"****"+BerkeleyDB.getRec("topic",tlist.get(i).topic ));
			int k=0;
			int j=0;
			do{
				if(!temp.contains(tlist.get(i).user.get(j))&&!tlist.get(i).user.get(j).equals("")&&!tlist.get(i).user.get(j).equals("\\N")){
					temp.add(tlist.get(i).user.get(j));
					k++;
				}
				
				j++;
			}while(k<2&&j<tlist.get(i).user.size());
			ll++;
		}
		for(int i=0;i<temp.size();i++){
			sb.append(temp.get(i)+":"+BerkeleyDB.getUserInfo(temp.get(i))+":1.0"+"#");
		}
		return sb.toString();
		
	}
}
