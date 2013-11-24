package cn.edu.zju.cadal.kafka.message;

public class BookView {
	String ip;
	String username;
	String bookid;
	String pageid;
	long timeMillis;
	
	public BookView(String message){
		String[] strs = message.split("\\$");
		this.ip = strs[0];
		this.username = strs[1];
		this.bookid = strs[2];
		this.pageid = strs[3];
		this.timeMillis = Long.parseLong(strs[4]);
	}
	public void setIp(String ip){
		this.ip = ip;
	}
	
	public void setUsername(String name){
		this.username = name;
	}
	
	public void setBookid(String bookid){
		this.bookid = bookid;
	}
	
	public void setPageid(String pageid){
		this.pageid = pageid;
	}
	
	public void setTimeMillis(long time){
		this.timeMillis = time;
	}
	
	public String getIp(){
		return this.ip;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getBookid(){
		return this.bookid;
	}
	
	public String getPageid(){
		return this.pageid;
	}
	
	public long setTimeMillis(){
		return this.timeMillis;
	}
	
	public String toString(){
		return this.ip + " " + this.username + " " + this.bookid + " " + this.pageid + " " + this.timeMillis;
	}
	
	public static void main(String[] args) {

	}

}
