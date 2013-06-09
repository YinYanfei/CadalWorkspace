package cn.cadal.storm.rec.dataObtain;

import java.io.FileWriter;
import java.util.Iterator;
import java.util.List;

import cn.cadal.storm.rec.chapterInfoOp.QueryChapterInfo;
import cn.cadal.storm.rec.postgresql.UserNameAndID;

public class ObtainUserChapterInfo {

	private UserNameAndID userNameAndID = null;
	private QueryChapterInfo queryChapterInfo = null;
	
	private String FILENAME = "H:/test/chapterInfoNew.txt";
	
	/**
	 * Construct function
	 */
	public ObtainUserChapterInfo() {
		this.userNameAndID = new UserNameAndID();
		this.queryChapterInfo = new QueryChapterInfo();
	}
	
	/**
	 * Write into file
	 */
	private void WriteIntoFile(String userid, List<String> chapterList){
		try{
			FileWriter writer = new FileWriter(this.FILENAME, true);
			
			writer.write(userid + " ");
			
			for(int i = 0; i < chapterList.size(); ++i) {
				writer.write(chapterList.get(i) + " ");
			}
			
			writer.write("\n");
			
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Obtain function
	 */
	@SuppressWarnings("unchecked")
	public void ObtainFun() {
		// Get userName-useId info
		this.userNameAndID.GetUserNameAndID();
		Iterator iter = userNameAndID.useNameID.keySet().iterator(); 

		while (iter.hasNext()) {
		    Object key = iter.next(); 
		    Object val = userNameAndID.useNameID.get(key);
		    
		    System.out.println(key.toString() + " => " + val.toString());
		    
		    List<String> listTmp = this.queryChapterInfo.QueryFromUserChapter(Integer.valueOf(val.toString()).intValue());
		    
		    this.WriteIntoFile(val.toString(), listTmp);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ObtainUserChapterInfo obtainUserChapterInfo = new ObtainUserChapterInfo();

		obtainUserChapterInfo.ObtainFun();

	}

}
