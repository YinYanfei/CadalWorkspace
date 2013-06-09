package project.cadal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

public class SimUserRecommend {
	private static HashMap<String,wordCollect> userProfile=null;
	public static ArrayList<String> getUserList(){
		ArrayList<String> userList=new ArrayList<String>();
		Cursor cursor = BerkeleyDB.getUserInfoDB().openCursor(null, null);

		// DatabaseEntry objects used for reading records
		DatabaseEntry foundKey = new DatabaseEntry();
		DatabaseEntry foundData = new DatabaseEntry();

		try { // always want to make sure the cursor gets closed
			while (cursor.getNext(foundKey, foundData,LockMode.DEFAULT) == OperationStatus.SUCCESS) {
	           	String name=new String(foundKey.getData(),"utf-8");
	           	userList.add(name);
			}
		} catch (Exception e) {
           	e.printStackTrace();
		} finally {
			cursor.close();
		}
		return userList;
	}
	public static HashMap<String,wordCollect> getUserProfile(){
		HashMap<String,wordCollect> userList=new HashMap<String,wordCollect>();
		Cursor cursor = BerkeleyDB.getProfileDB().openCursor(null, null);

		// DatabaseEntry objects used for reading records
		DatabaseEntry foundKey = new DatabaseEntry();
		DatabaseEntry foundData = new DatabaseEntry();

		try { // always want to make sure the cursor gets closed
			while (cursor.getNext(foundKey, foundData,LockMode.DEFAULT) == OperationStatus.SUCCESS) {
	           	String name=new String(foundKey.getData(),"utf-8");
	           	String nn=new String(foundData.getData(),"utf-8");
	           	System.out.println(nn);
	           	String[] profiles=nn.split("#");
	           	wordCollect wc=new wordCollect();
	           	for(int i=0;i<profiles.length;i++){
	           		if(profiles[i].equals("")||!profiles[i].contains("|"))continue;
	           		String[] temp=profiles[i].split("\\|");
	           		System.out.println(profiles[i]);
	           		
	           		try{
	           			wc.wordunit.add(new wordUnit(temp[0],Integer.parseInt(temp[1])));
	           		}catch(java.lang.NumberFormatException e){
	           			System.err.println(profiles[i]+"&&&"+temp[1]);
	           		}
	           	}
	           	wc.wordnum=wc.wordunit.size();
	           	userList.put(name, wc);
			}
		} catch (Exception e) {;
           	e.printStackTrace();
		} finally {
			cursor.close();
		}
		return userList;
	}
	
	public static void recommendGroup() throws Exception{
		System.out.println("start build recommendGroup~~~");
		ArrayList<String> userList=getUserList();
		String activeUserList=RecommendTopic.getMostActiveUser();
		userProfile=getUserProfile();
		int len=userList.size();
		for(int i=0;i<len;i++){
			
			String username=userList.get(i);
			int recommendnum=0;				
			if(!username.equals("\\N")&&!username.equals("")&&userProfile.containsKey(username)){
				StringBuilder sb=new StringBuilder();
				personalRecommendList recommendunit=Recommendation(username);			
				recommendnum=recommendunit.recommendNum;	
				int count=0;
				for(int k=0;k<recommendnum;k++){
					String friendname=recommendunit.similarityList.get(k).user;
					double tempf=recommendunit.similarityList.get(k).similarity;
					if(tempf>0.8){
						sb.append(friendname+":"+BerkeleyDB.getUserInfo(friendname)+":"+recommendunit.similarityList.get(k).similarity+"#");
						count++;
					}
				}
				if(count<20){
					sb.append(activeUserList);
				}
				//System.out.println(sb.toString());
				try {
					BerkeleyDB.putRec("interest",username, sb.toString());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				//BerkeleyDB.getRecommendByInterestDB()
				BerkeleyDB.putRec("interest",username, activeUserList);
			}
		}
		System.out.println("finish build recommendGroup~~~");
	}
	public static personalRecommendList Recommendation(String CurrentUserName) throws NumberFormatException, IOException{	
		//建立hashMap/////////
		userProfileUnit currentUserProfileUnit=new userProfileUnit();
		HashMap<String,wordCollect> hashMap=userProfile;
		currentUserProfileUnit.username=CurrentUserName;
		currentUserProfileUnit.wordunitArray=hashMap.get(CurrentUserName).wordunit;
		
		
		//遍历hashMap, 产生推荐序列并排序							
				long mod1=0;
				long mod2=0;
				long multi=0;
				double similarity=0;
				int ncurrent=currentUserProfileUnit.wordunitArray.size();
				for(int k=0;k<ncurrent&&k<100;k++){
					mod2+=currentUserProfileUnit.wordunitArray.get(k).wordCount*currentUserProfileUnit.wordunitArray.get(k).wordCount;
				//	System.out.println(currentUserProfileUnit.wordunitArray.get(k).wordCount+"::::");
				}
			//	System.out.println("mod2: "+mod2);
				int simUserCount=0;
				userSimlarityUnit current_user_recommend_list=new userSimlarityUnit();
				current_user_recommend_list.username=CurrentUserName;
				Iterator iter=hashMap.entrySet().iterator();
				while(iter.hasNext()){
					Entry entry=(Entry) iter.next();
					wordCollect tempwords=(wordCollect)(entry.getValue());
					String user=(String)(entry.getKey());
					
					
					SimUserUnit tempSimUser=new SimUserUnit();
					tempSimUser.simusername=user;
					
					mod1=0;			
					multi=0;
					int n1=tempwords.wordnum;
					int n2=currentUserProfileUnit.wordunitArray.size();	
					
					if(n1<=1)continue;//////////////////////////////////
					for(int j=0;j<n1&&j<100;j++){
						for(int i=0;i<n2&&i<100;i++){
							if(tempwords.wordunit.get(j).wordName.equals(currentUserProfileUnit.wordunitArray.get(i).wordName)){
								multi+=tempwords.wordunit.get(j).wordCount*currentUserProfileUnit.wordunitArray.get(i).wordCount;
							}												
						}
						mod1+=tempwords.wordunit.get(j).wordCount*tempwords.wordunit.get(j).wordCount;
					}												
					similarity=multi/(Math.sqrt(mod1)*Math.sqrt(mod2));
					if(similarity<=0)continue;////////////////////////////////////
					tempSimUser.simlarity=similarity;
					//System.out.println(mod2+":"+mod1+":"+multi);
					current_user_recommend_list.simUserArray.add(tempSimUser);	
					simUserCount++;
					//System.out.println("simUserCount: "+simUserCount);
				}
				
				current_user_recommend_list.friendsCount=simUserCount;
			//	System.out.println("simUserCount: "+simUserCount);
				//计算的用户相似度列表进行排序，降序存储返回
				Collections.sort(current_user_recommend_list.simUserArray, new Comparator(){
					//public int compare(Object o1, Object o2){return (((SimUserUnit)(o1)).simlarity>((SimUserUnit)(o2)).simlarity)?1:-1; }
					public int compare(Object o1, Object o2){return ((Double)((SimUserUnit)(o1)).simlarity).compareTo(((SimUserUnit)(o2)).simlarity); }
				});
				
				int friendnum=current_user_recommend_list.friendsCount;
				
				
				personalRecommendList recommendlist=new personalRecommendList();
				recommendlist.recommendNum=0;
				
				for(int i=current_user_recommend_list.friendsCount-1;i>current_user_recommend_list.friendsCount-100&&i>0;i--){
					similarityUnit simunit=new similarityUnit();
					simunit.user=current_user_recommend_list.simUserArray.get(i).simusername;
					simunit.similarity=current_user_recommend_list.simUserArray.get(i).simlarity;
					recommendlist.similarityList.add(simunit);
					recommendlist.recommendNum++;
				//	System.out.println(recommendlist.recommendNum);/////////////////////////
				}
				return recommendlist;				
	}
}	 
