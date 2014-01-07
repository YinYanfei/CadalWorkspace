package cn.edu.zju.cadal.utils;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class ParseUtil {

	/**
	 * 将一条阅读相关的Result记录解析成一行文本
	 * @param r Result
	 * @return a record:ip + "#$#" + user + "#$#" + bookid + "#$#" + pageno + "#$#" + comment + "#$#" + timestamp;
	 */
	public static String parseReadResult(Result r){
		String record = "";
		String ip = "";
		String user = "";
		String bookid = "";
		String pageno = "";
		String comment = "";
		String tagname = "";
		long timestamp = 0L;
		KeyValue[] kv = r.raw();
		for (int i = 0; i < kv.length; i++) {
			if (new String(kv[i].getQualifier()).equals("time")) {
				timestamp = Bytes.toLong(kv[i].getValue());
			} else if(new String(kv[i].getQualifier()).equals("ip")) {
				ip = new String(kv[i].getValue());
			} else if(new String(kv[i].getQualifier()).equals("user")){
				user = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("bookid")){
				bookid = new String(kv[i].getValue());
			}else if (new String(kv[i].getQualifier()).equals("pageno")){
				pageno = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("comment")){
				comment = new String(kv[i].getValue());
			}else
				tagname = new String(kv[i].getValue());
		}
		if(!comment.equals(""))
			record = ip + "#$#" + user + "#$#" + bookid + "#$#" + pageno + "#$#" + comment + "#$#" + timestamp;
		else if(!tagname.equals(""))
			record = ip + "#$#" + user + "#$#" + bookid + "#$#" + tagname + "#$#" + timestamp;
		else
			record = ip + "#$#" + user + "#$#" + bookid + "#$#" + pageno + "#$#" + timestamp;
		return record;
	}
	
	/**
	 * 将一条搜索相关的Result记录解析成一行文本
	 * @param r Result
	 * @return a record: ip user query [bookid] time
	 */
	public static String parseSearchResult(Result r){
		String record = "";
		String ip = "";
		String user = "";
		String query = "";
		String bookid = "";
		long timestamp = 0L;
		KeyValue[] kv = r.raw();
		for (int i = 0; i < kv.length; i++) {
			if (new String(kv[i].getQualifier()).equals("time")) {
				timestamp = Bytes.toLong(kv[i].getValue());
			} else if(new String(kv[i].getQualifier()).equals("ip")) {
				ip = new String(kv[i].getValue());
			} else if(new String(kv[i].getQualifier()).equals("user")){
				user = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("bookid")){
				bookid = new String(kv[i].getValue());
			}else{
				query = new String(kv[i].getValue());
			}
		}
		if(!bookid.equals(""))
			record = ip + "#$#" + user + "#$#" + query + "#$#" + bookid + "#$#" + timestamp;
		else
			record = ip + "#$#" + user + "#$#" + query + "#$#" + timestamp;
		return record;
	}
	
	/**
	 * 将一条推荐相关的Result记录解析成一行文本
	 * @param r Result
	 * @return a record: ip user query [bookid] time
	 */
	public static String parseRecResult(Result r){
		String record = "";
		String ip = "";
		String user = "";
		String bookid = "";
		String source = "";
		String tagname = "";
		String user_r = "";
		long timestamp = 0L;
		KeyValue[] kv = r.raw();
		for (int i = 0; i < kv.length; i++) {
			if (new String(kv[i].getQualifier()).equals("time")) {
				timestamp = Bytes.toLong(kv[i].getValue());
			} else if(new String(kv[i].getQualifier()).equals("ip")) {
				ip = new String(kv[i].getValue());
			} else if(new String(kv[i].getQualifier()).equals("user")){
				user = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("bookid")){
				bookid = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("source")) {
				source = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("tagname")){
				tagname = new String(kv[i].getValue());
			}else {
				user_r = new String(kv[i].getValue());
			}
		}
		
		if(!bookid.equals("") && !tagname.equals(""))
			record = ip + "#$#" + user + "#$#" + tagname + "#$#" + bookid + "#$#" + timestamp;
		else if(!bookid.equals("") && !source.equals(""))
			record = ip + "#$#" + user + "#$#" + bookid + "#$#" + source + "#$#" + timestamp;
		else if(!tagname.equals("") &&  !source.equals(""))
			record = ip + "#$#" + user + "#$#" + tagname + "#$#" + source + "#$#" + timestamp;
		else
			record = ip + "#$#" + user + "#$#" + user_r + "#$#" + source + "#$#" + timestamp;
		return record;
	}
	
	/**
	 * 将一条个性化主页相关的Result记录解析成一行文本
	 * @param r
	 * @return a record: ip, user, [user_f, user_v, user_r, user_s], [shareid, replyid], time
	 */
	public static String parsePersonalResult(Result r){
		String record = "";
		String ip = "";
		String user = "";
		String button = "";
		String user_f = "";
		String user_r = "";
		String user_s = "";
		String user_v = "";
		String replyid = "";
		String shareid = "";
		long time = 0L;
		KeyValue[] kv = r.raw();
		for(int i = 0;i<kv.length;i++){
			if(new String(kv[i].getQualifier()).equals("time")){
				time = Bytes.toLong(kv[i].getValue());
			} else if(new String(kv[i].getQualifier()).equals("ip")) {
				ip = new String(kv[i].getValue());
			} else if(new String(kv[i].getQualifier()).equals("user")){
				user = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("button")){
				button = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("user_f")) {
				user_f = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("user_v")){
				user_v = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("user_s")){
				user_s = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("user_r")){
				user_r = new String(kv[i].getValue());
			}else if(new String(kv[i].getQualifier()).equals("shareid")){
				shareid = new String(kv[i].getValue());
			}else {
				replyid = new String(kv[i].getValue());
			}
		}
		
		if(!button.equals(""))
			record = ip + "#$#" + user +"#$#" +  button +"#$#" +  time; 
		else if(!user_f.equals(""))
			record = ip +"#$#" +  user +"#$#" +  user_f + "#$#" +  time;
		else if(!user_r.equals(""))
			record = ip + "#$#" + user + "#$#" + user_r + "#$#" + replyid + "#$#" + time;
		else if(!user_s.equals(""))
			record = ip + "#$#" + user + "#$#" + user_s + "#$#" + shareid + "#$#" + time;
		else
			record = ip + "#$#" + user + "#$#" + user_v + "#$#" + time;
		
		return record;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
