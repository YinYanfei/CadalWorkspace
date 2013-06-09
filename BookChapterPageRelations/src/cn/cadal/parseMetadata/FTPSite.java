package cn.cadal.parseMetadata;


import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;

import cn.cadal.util.StackTraceUtil;

/**
 * FTP客户端的基类
 * @author Dell
 *
 */
public class FTPSite {
	static private final Log LOG = LogFactory.getLog(FTPSite.class);
	
	private FTPClient ftpc = new FTPClient();
	private String ftpaddress;
	private String username;
	private String password;


	public FTPSite(String faddress, String uname, String pword) {
		this.ftpaddress = faddress;
		this.username = uname;
		this.password = pword;
	}

	String lineSeparator = System.getProperty("line.separator");

	public String toString() {
		StringBuffer temp = new StringBuffer(64);
		temp.append("ftp address : " + getFtpAddress() + lineSeparator);
		temp.append("user name : " + getUserName() + lineSeparator);
		temp.append("pass word : " + getPassword() + lineSeparator);
		return temp.toString();
	}

	public FTPClient getFTPClient() {
		return ftpc;
	}

	public String getFtpAddress() {
		return ftpaddress;
	}

	public String getUserName() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	//断开与FTP服务器的连接
	public void dispose() {
		try {
			if (ftpc.isConnected()) {
				this.logout();
				ftpc.disconnect();
				LOG.info("Disclose ftp connection success for : " + ftpaddress);
			}
		} catch (IOException iexc) {
			LOG.warn(StackTraceUtil.getStackTrace(iexc));
		}
	}

	public boolean isConnected() {
		return ftpc.isConnected();
	}
	
	//连接FTP服务器,若返回值为false表示连接服务器失败
	public synchronized boolean connectFTPSite() {
		LOG.info("FTP site info : " + System.getProperty("line.separator") + this.toString());
		boolean flag=false;
		try {
			int reply;
			LOG.info("Try connect to " + ftpaddress );
			if(isConnected()==true){
				LOG.info( "The server "+ ftpaddress+" has been connected by other thread");
				return false;
			}
			ftpc.setDataTimeout(15000);
			ftpc.connect(ftpaddress);
			reply = ftpc.getReplyCode();
			ftpc.setFileType(FTP.BINARY_FILE_TYPE);
			ftpc.enterLocalPassiveMode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				LOG.warn("FTP server refused connection");
				flag=false;
			}else{
				flag=true;
				LOG.info("Connected to " + ftpaddress);
			}
		} catch (IOException e) {
			LOG.warn(StackTraceUtil.getStackTrace(e));
			if (ftpc.isConnected()) {
				try {
					ftpc.disconnect();
				} catch (IOException exc) {
					LOG.warn(StackTraceUtil.getStackTrace(exc));
				}
				flag=false;
			}
		}finally{
			return flag;
		}
	}
	//登陆
	public boolean login(){
		boolean flag=true;
		try {
			LOG.info("Try login " + ftpaddress );
			if(!ftpc.login(username, password)){
				LOG.warn("Login error.");
				flag=false;
			}else{
				LOG.info("Login " + ftpaddress + " success whit reply string: " + ftpc.getReplyString());
			}
		} catch (Exception e) {
		 	LOG.warn(StackTraceUtil.getStackTrace(e));
		 	flag=false;
		}
		return flag;
	}
	
	//退出
	public void logout(){
		try {
			ftpc.logout();
			LOG.info("Logout " + ftpaddress +" success");
		} catch (IOException e) {
			LOG.warn(StackTraceUtil.getStackTrace(e));
		}
	}
	
	//改变工作目录
	public boolean changeWorkingDirectory(String pathname){
		boolean flag=false;
		try {
			flag=ftpc.changeWorkingDirectory(pathname);
			if(flag==false){
				String newpathname=pathname.substring(0,pathname.length()-4)+"TOC";
				flag=ftpc.changeWorkingDirectory(newpathname);
			}
		} catch (IOException e) {
			LOG.warn(StackTraceUtil.getStackTrace(e));
			flag=false;
		}
		return flag;
	}
	
	//获取文件输入流,若为空则获取失败
	public InputStream retrieveFileStream(String filename) throws FTPConnectionClosedException{
		InputStream in=null;
		String possiableName="catalog.xml";
		try {
			String[] names=ftpc.listNames();
			for(String name:names){
				if(filename.equals(name)){
					possiableName=filename;
					break;
				}
			}
			
		} catch (IOException e1) {
			LOG.warn(StackTraceUtil.getStackTrace(e1));
		}
		try {
			in=ftpc.retrieveFileStream(possiableName);
		} catch (FTPConnectionClosedException e){
			throw e;
		}catch (IOException e2){
			LOG.warn(StackTraceUtil.getStackTrace(e2));
		}
		return in;
	}
}
