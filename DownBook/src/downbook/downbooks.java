package downbook;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class downbooks {
	public final int ThreadNum=1;
	
	public void downbooks(){
		connectToDB DbTest = new connectToDB();
		ftpConnect ftpTest = new ftpConnect();
		String files[] = getBookId();
		// 
		for(int i=0;i<files.length;i++)
			DbTest.connectDB(files[i]);
 		
		BookInfo bookin=null;
		for(int k=0;k<files.length;k++){
			bookin=(BookInfo)DbTest.bookInfo.get(files[k]);
			ftpTest.setServer( bookin.FtpHost);
			ftpTest.setUserName(bookin.Username);
			ftpTest.setPasswd(bookin.Password);
			
			ftpTest.CreateFiles(Constants.BOOKSTORE, bookin.title,bookin.real_path);
			System.out.println("登陆："+ftpTest.getConnect());
	 		if(ftpTest.getConnect()==1)   // 登陆不上
	 			continue;
	        ftpTest.ftpDown();
		}
	}
	
	public static String [] getBookId(){
		try {
		    BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Constants.BOOKID)));
		    int count=0;
		    String temp;
		    while((temp=br.readLine()) !=null){
		    	if(temp==null || temp.trim().length()<=0)
		    		break;
		    	count++;
		    }
		    br.close();
		    String filename[] = new String[count];

		    BufferedReader br1 = new BufferedReader(new InputStreamReader(new FileInputStream(Constants.BOOKID)));
		    for(int i=0;i<count;i++){ 
		    	filename[i]=br1.readLine().trim();  
		    	System.out.println(filename[i] ); 
		    }
		    
		    br1.close();
		    return filename;
		}catch(FileNotFoundException e ){ 
			System.out.println( "无法创建文件") ;
		}catch(IOException e ) { 
			System.out.println( "输入流错误") ;
		}
		
		return null;
	}
}

class DownFromFtp extends Thread{
	int start=0;
	int end=0;
	ftpConnect ftpTest=null;
	BookInfo bookin=null;
	DownFromFtp(ftpConnect ftp, BookInfo bookin,int start,int end){
		this.ftpTest=ftp;
		this.bookin=bookin;
		this.start=start;
		this.end=end;
		
	}
	
	public void run(){
		System.out.println(start+"  "+end);
		
	} 
}
 
