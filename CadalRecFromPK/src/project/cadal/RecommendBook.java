package project.cadal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class RecommendBook {
	public static void recommend(ArrayList<accessUnit> accesslog) throws Exception{
		HashMap<String,HashMap<String,Integer>> recMap=new HashMap<String,HashMap<String,Integer>>();
		String name=null;
		String bookid=null;
		
		System.out.println("start to update recommend user by book....");
		
		long len=accesslog.size();
		for(int i=0;i<len;i++){
			name=accesslog.get(i).username;
			bookid=accesslog.get(i).bookid;
			if(!recMap.containsKey(bookid)){
				HashMap<String,Integer> curMap=new HashMap<String,Integer>();
				
				//load userProfile from db
				String rec=BerkeleyDB.getRec("book", bookid);
				if(rec!=null&&!rec.equals("")){
					String[] recArray=rec.split("#");
					for(int j=0;j<recArray.length;j++){
						String[] temp=recArray[j].split("\\|");
						curMap.put(temp[0], Integer.parseInt(temp[1]));
					}
				}
				recMap.put(bookid, curMap);
			}
			if(recMap.get(bookid).containsKey(name)){
				recMap.get(bookid).put(name, recMap.get(bookid).get(name)+1);
			}else{
				if(!name.equals("\\N"))
					recMap.get(bookid).put(name, 1);
			}
		}
		//original userProfile ����
		System.out.println("start to sort userProfile....");//
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
			BerkeleyDB.putRec("book",bookid, sb.toString());
		}	
		System.out.println("end to update recommend list by book!!!");//
	}
}
