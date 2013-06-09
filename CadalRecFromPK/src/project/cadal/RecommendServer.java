package project.cadal;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.thrift.TException;

public class RecommendServer implements Recommend.Iface{
	@Override
	public String recommendActiveUserByBook(String bookid)
			throws TException {
		System.out.println("recommendActiveUserByBook is requested");
		try {
			String recommend=BerkeleyDB.getRec("book", bookid);
			if(recommend==null){
				return "";
			}else{
				List<String> temp=new ArrayList<String>();
				String[] t=recommend.split("#");
				for(int i=0;i<t.length;i++){
					temp.add(t[i].split("\\|")[0]);
				}
				
				String strTmp = "";
				for(int i = 0; i < temp.size(); ++i) {
					strTmp += temp.get(i) + "#";
				}
				
				return strTmp; 
			}
			
			
		} catch (Exception e) {
			//e.printStackTrace();
			//如果没有该用户长度为0的ArrayList
			return "";
		}
	} 
	@Override
	public String recommendActiveUserByInterest(String interest)
			throws TException {
		System.out.println("recommendActiveUserByInterest is requested");
		try {
			String recommend=BerkeleyDB.getRec("topic", interest);
			if(recommend==null) {
				return "";
			}else{
				List<String> temp=new ArrayList<String>();
				String[] t=recommend.split("#");
				for(int i=0;i<t.length;i++){
					temp.add(t[i].split("\\|")[0]);
				}
				
				String strTmp = "";
				
				for(int i = 0; i < temp.size(); ++i) {
					strTmp += temp.get(i) + "#";
				}
				
				return strTmp; 
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//如果 没有这个话题，则返回长度为0的ArrayList
			return "";
		}
	}
	@Override
	public String recommendByInterest(String username) throws TException {
		System.out.println("recommendByInterest is requested " + username);
		List<user> tempList=new ArrayList<user>();
		HashSet<String> set=new HashSet<String>();
		try {
			String recommend=BerkeleyDB.getRec("interest", username);
			if(recommend==null)
				return "";
			else{
				String[] t=recommend.split("#");
				for(int i=0;i<t.length;i++){
					String[] str=t[i].split(":");
					user tempuser=new user();
					tempuser.username=str[0];
					tempuser.school=str[1]+"##"+BerkeleyDB.getProfile(str[0]);
					if(set.contains(str[0])||str[0].equals(username))
						continue;
					set.add(str[0]);
					tempList.add(tempuser);
				}
				
				String strTmp = "";
				for(int i = 0; i < tempList.size(); ++i) {
					strTmp += tempList.get(i).username + "#";
				}
				
				return strTmp; 
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//如果没有该用户长度为0的ArrayList<user>
			return "";
		}
	}
	@Override
	public String update(String accesslog, String bookfile, String userinfo)
			throws TException {
		System.out.println("update the data... raw data file:"+accesslog+","+bookfile+","+userinfo);

		if(accesslog!=null)
			loadData.loadAccesslog(accesslog);
		if(bookfile!=null)
			loadData.loadBookinfo(bookfile);
		if(userinfo!=null)
			loadData.loadUserinfo(userinfo);
		try {
			UserProfile.updateProfile(loadData.accessList);
			RecommendBook.recommend(loadData.accessList);
			RecommendTopic.recommend(loadData.accessList);
			SimUserRecommend.recommendGroup();
			return "update ok";
		} catch (Exception e) {
			return "error update";
		}
		
	}

}
