package project.cadal;

import java.util.HashMap;

import com.sleepycat.je.Cursor;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

public class CADAL {
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		System.out.println("start server...");
		BerkeleyDB.setUp("BDB", 1000000);
		
		BerkeleyDB.open();
		//System.out.println(BerkeleyDB.getBook("33190317"));
//		loadData.loadAccesslog("h:/accesslog.csv");
//		loadData.loadBookinfo("h:/bookinfo.csv");
//		loadData.loadUserinfo("h:/userinfo.csv");
//		UserProfile.updateProfile(loadData.accessList);
//		RecommendBook.recommend(loadData.accessList);
//		RecommendTopic.recommend(loadData.accessList);
//		SimUserRecommend.recommendGroup();
		Server server=new Server();
		server.startServer();
		/*Cursor cursor = BerkeleyDB.getProfileDB().openCursor(null, null);

        // DatabaseEntry objects used for reading records
        DatabaseEntry foundKey = new DatabaseEntry();
        DatabaseEntry foundData = new DatabaseEntry();

        try { // always want to make sure the cursor gets closed
        	int c1=0,c2=0;
            while (cursor.getNext(foundKey, foundData,LockMode.DEFAULT) == OperationStatus.SUCCESS) {
            	String user=new String(foundKey.getData(),"utf-8");
            	String profile=new String(foundData.getData(),"utf-8");
            	if(profile!=null){
            		System.out.println(user+"----->"+profile);
            		c1++;
            	}
            	c2++;
            	if(c2>1000)
            		break;
            }
            System.out.println(c1+":"+c2);
        } catch (Exception e) {;
            e.printStackTrace();
        } finally {
            cursor.close();
        }//*/
//       BerkeleyDB.close();
	}

}
