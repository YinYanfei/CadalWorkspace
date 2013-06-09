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

public class UserProfile {
	public static void updateProfile(ArrayList<accessUnit> accesslog) throws Exception{
		HashMap<String,HashMap<String,Integer>> userProfile=new HashMap<String,HashMap<String,Integer>>();
		String name=null;
		String bookid=null;
		String subject=null;
		String[] keywords=null;
		
		System.out.println("start to build userProfile....");
		
		long len=accesslog.size();
		for(int i=0;i<len;i++){
			name=accesslog.get(i).username;
			bookid=accesslog.get(i).bookid;
			subject=BerkeleyDB.getBook(bookid);
			//System.out.println(bookid+":"+subject);
			if(subject==null)continue;
			keywords=subject.split("\\|");			
			int wordsLen=keywords.length;
			if(!userProfile.containsKey(name)){
				HashMap<String,Integer> curMap=new HashMap<String,Integer>();
				
				//load userProfile from db
				String interest=BerkeleyDB.getProfile(name);
				//System.out.println(interest);
				if(interest!=null&&!interest.equals("")){
					String[] interestArray=interest.split("#");
					for(int j=0;j<interestArray.length;j++){
						if(interestArray[j].equals("")||!interestArray[j].contains("\\|"))continue;
						String[] temp=interestArray[j].split("\\|");
						
						System.out.println(interestArray[j]);
						curMap.put(temp[0], Integer.parseInt(temp[1]));
					}
				}
				userProfile.put(name, curMap);
			}
			//���ļ��������д��userProfile����
			for(int j=0;j<wordsLen;j++){
				if(userProfile.get(name).containsKey(keywords[j])){
					userProfile.get(name).put(keywords[j], userProfile.get(name).get(keywords[j])+1);
				}else{
					if(!keywords[j].equals("\\N"))
						userProfile.get(name).put(keywords[j], 1);
				}
			}
		}
		//original userProfile ����
		System.out.println("start to sort userProfile....");//
		//original userProfile �������򣬽������д���ļ�
		
		Iterator<Entry<String, HashMap<String, Integer>>> iter=userProfile.entrySet().iterator();
		while(iter.hasNext()){
			Entry<String, HashMap<String, Integer>> entry=iter.next();

			String user=entry.getKey();
			HashMap<String, Integer> wordMap=entry.getValue();
			List<Entry<String, Integer>> wordList =new ArrayList<Entry<String, Integer>>(wordMap.entrySet());
			Collections.sort(wordList,new Comparator(){ 
				public int compare(Object o1, Object o2){
					return (((Entry<String, Integer>)(o1)).getValue()
							-((Entry<String, Integer>)(o2)).getValue());}});
			StringBuffer sb=new StringBuffer();
			
			int n=wordList.size();
			for(int i=n-1;i>=0;i--){
				sb.append(wordList.get(i).getKey()+"|"+wordList.get(i).getValue()+"#");	
			}
			//System.out.println(sb.toString());
			if(!sb.toString().equals(""))
			BerkeleyDB.putProfile(user, sb.toString());
		}	
		System.out.println("end to build userProfile!!!");//
	}
	public static HashMap<String,HashMap<String,Integer>> loadProfile(){
		HashMap<String,HashMap<String,Integer>> userProfile=new HashMap<String,HashMap<String,Integer>>();
		 // Get a cursor
        Cursor cursor = BerkeleyDB.getProfileDB().openCursor(null, null);

        // DatabaseEntry objects used for reading records
        DatabaseEntry foundKey = new DatabaseEntry();
        DatabaseEntry foundData = new DatabaseEntry();

        try { // always want to make sure the cursor gets closed
            while (cursor.getNext(foundKey, foundData,LockMode.DEFAULT) == OperationStatus.SUCCESS) {
            	String user=new String(foundKey.getData(),"utf-8");
            	String profile=new String(foundData.getData(),"utf-8");
            	HashMap<String,Integer> curMap=new HashMap<String,Integer>();
            	String[] words=profile.split("#");
            	for(int i=0;i<words.length;i++){
            		String[] temp=words[i].split("\\|");
            		curMap.put(temp[0],Integer.parseInt(temp[1]));
            	}
            	userProfile.put(user, curMap);
            }
        } catch (Exception e) {;
            e.printStackTrace();
        } finally {
            cursor.close();
        }
		return userProfile;
	}
}

				