package cn.cadal.storm.rec.main;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.cadal.storm.rec.chapterInfoOp.InsertChapterInfo;
import cn.cadal.storm.rec.chapterInfoOp.QueryChapterInfo;
import cn.cadal.storm.rec.chapterLevel.ChapterLevel;
import cn.cadal.storm.rec.postgresql.UserNameAndID;
import cn.cadal.storm.rec.userBookPage.UserBookPageInfo;

public class MainProcess {

	private UserNameAndID userNameAndID = null;			// PostgreSQL [cadal_metadata_full_dbo2]  --  To get all 'username-userid'
	private UserBookPageInfo userBookPageInfo = null;   // Cassandra [CadalDB]  --  To get 'user-book-page' info
	private ChapterLevel chapterLevel = null;			// Calculate ChapterLevel of a book for every user
	private InsertChapterInfo insertChapterInfo = null;	// Cassandra [CadalRec]  --  Insert into 'UserChapter', 'ChapterSignalMap' and 'SignalChapterMap'
	private QueryChapterInfo queryChapterInfo = null;	// Cassandra [CadalRec]  --  Query from 'UserChapter', 'ChapterSignalMap' and 'SignalChapterMap'
	
	/**
	 * Construct function
	 */
	public MainProcess() {
		this.userNameAndID = new UserNameAndID();
		this.userBookPageInfo = new UserBookPageInfo();
		this.chapterLevel = new ChapterLevel();
		this.insertChapterInfo = new InsertChapterInfo();
		this.queryChapterInfo = new QueryChapterInfo();
	}
	
	/**
	 * List<String> to List<Integer>
	 */
	private List<Integer> ListStringToListInt(List<String> listStr) {
		List<Integer> listInt = new ArrayList<Integer>();
		
		for(int i = 0; i < listStr.size(); ++i) {
			listInt.add(Integer.valueOf(listStr.get(i)).intValue());
		}
		
		return listInt;
	}
	
	/**
	 * This function is main process, major to manage data flow.
	 */
	@SuppressWarnings("unchecked")
	public void Process(){
		// get user info
		this.userNameAndID.GetUserNameAndID();   // user-id --> this.userNameAndID.useNameID[map<username, id>]
		
		int count = 0;
		try{
			// Iterator user info
			Iterator iterOut = this.userNameAndID.useNameID.keySet().iterator(); 
			while (iterOut.hasNext()) { 
			    Object username = iterOut.next(); 							// user name
			    Object userid   = userNameAndID.useNameID.get(username);	// user id
			    
			    System.out.println((++count) + " ==== " + userid.toString() + " ---- " + username.toString());
			    
			    // get all book-page info of 'username'
			    Map<String, List<String>> results = this.userBookPageInfo.GetAllInfoOfUser(username.toString());
			    
			    // Iterator results
				Iterator iterIner = results.keySet().iterator(); 
				while (iterIner.hasNext()) { 
				    Object bookid = iterIner.next(); 
	
				    Object pageid = results.get(bookid); 
			    
				    List<Integer> listPageid = this.ListStringToListInt((List<String>) pageid);
				    
				    // Check the length of listPageid
				    if((listPageid.size() == 1) && (listPageid.get(0) == 1)) {
				    	continue;
				    }
				    
				    // To get all information about chapterLevel
				    List<String> listChapterLevel = this.chapterLevel.GetChapterLevel(bookid.toString(), listPageid);
				    
				    for(int i = 0; i < listChapterLevel.size(); ++i){
					    // Judge chapterLevel whether appear in 'ChapterSignalMap'
					    List<String> checkChapterLevel = this.queryChapterInfo.QueryFromChapterSignalMap(listChapterLevel.get(i));
					    
					    if(checkChapterLevel.size() == 1) {
					    	// Insert into 'UserChapter', 'ChapterSignalMap' and 'SignalChapterMap'
					    	this.insertChapterInfo.InsertIntoChapterSignalMap(listChapterLevel.get(i), Integer.valueOf(checkChapterLevel.get(0)).intValue());
					    	
					    	this.insertChapterInfo.InsertIntoSignalChapterMap(Integer.valueOf(checkChapterLevel.get(0)).intValue(), listChapterLevel.get(i));
					    	
					    	this.insertChapterInfo.InsertIntoUserChapter(Integer.valueOf(userid.toString()).intValue(), 
					    												 0 - Integer.valueOf(checkChapterLevel.get(0)).intValue(), 
					    												 listChapterLevel.get(i));
					    	
					    } else if(checkChapterLevel.size() == 2) {
					    	// Insert into 'UserChapter'
					    	this.insertChapterInfo.InsertIntoUserChapter(Integer.valueOf(userid.toString()).intValue(), 
					    												 0 - Integer.valueOf(checkChapterLevel.get(1)).intValue(), 
					    												 listChapterLevel.get(i));
					    	
					    } else {
					    	System.out.println("ERROR");
					    }
				    }
				}
			    
				Thread.sleep(10);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Main Process
		MainProcess mp = new MainProcess();
		
		mp.Process();
		
		System.out.println("Done");
	}

}
