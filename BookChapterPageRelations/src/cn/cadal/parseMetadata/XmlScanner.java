package cn.cadal.parseMetadata;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPConnectionClosedException;

import cn.cadal.util.StackTraceUtil;

public class XmlScanner extends Thread {
	static private final Log LOG = LogFactory.getLog(XmlScanner.class);
	int offset = -1;
	FTPSite ftpc1 = null;// 111-118中的某一台
	FTPSite ftpc2 = null;// 223
	ParseCatalog pc = null;
	Connection con = null;
	PreparedStatement ps1;

	public XmlScanner(FTPSite ftpc1, FTPSite ftpc2, Connection con) throws SQLException {
		this.ftpc1 = ftpc1;
		this.ftpc2 = ftpc2;
		this.con = con;
		this.ps1 = con.prepareStatement("select \"BookNo\",\"Path\",\"Page\",\"HostID\" from cbook order by \"BookNo\" limit \'1000\' offset ?");
		this.pc = new ParseCatalog();
	}

	public void run(){
		while(true){
			if(Console.BeginOffset>Console.EndOffset){
				LOG.info("BeginOffset has been Larger than EndOffset，Current thread completed. Waiting for other threads to complete");
				break;
			}
			offset=Console.BeginOffset;
			LOG.info("Thread gets offset，Begin to Handle ，offset = "+offset);
			Console.BeginOffset+=1000;
			int index=-1;//记录处理到当前100条记录中的第几条
			try{
				ps1.setInt(1, offset);
				ResultSet rs1=ps1.executeQuery();
				while(rs1.next()){
					index++;
					String bookNo=rs1.getString(1);
					String path=rs1.getString(2);
					int page=rs1.getInt(3);
					int hostID=rs1.getInt(4);
					if(hostID==40||hostID==51||hostID==171){//这些不用处理
						LOG.error("BOOK "+bookNo+" HostID is "+hostID+",Bypass,offset: "+(this.offset+index));
						continue;
					}
					String externalUri=Console.CONTENTHOST.get(hostID);
					String ip=externalUri.substring(7, 19);
					pc.bookId=bookNo;
					pc.endPage=page;
					pc.resetLevels();
					//如果是位于223主机
					if(ip.endsWith("223")){
						String afterip="";
						if(externalUri.length()>20){
							afterip=externalUri.substring(19,externalUri.length()-1);
						}
						String xmlpath=afterip+path+"/meta";
						while(ftpc2.connectFTPSite()==false){
							Thread.sleep(10000);
						}
						while(ftpc2.login()==false){
							Thread.sleep(10000);
						}
						if(ftpc2.changeWorkingDirectory(xmlpath)==false){
							LOG.error(bookNo + " Error in changing working directory " + "offset is: " + (this.offset+index));
							ftpc2.dispose();
							continue;
						}
						InputStream in=null;
						try{
							in=ftpc2.retrieveFileStream("Catalog.xml");
						}catch (FTPConnectionClosedException e){
							LOG.warn(StackTraceUtil.getStackTrace(e));
						}
						if(in==null){
							LOG.error(bookNo + " Error in Getting Input File Stream " + "offset is: " + (this.offset+index));
							System.out.println(bookNo + " Error in Getting Input File Stream " + "offset is: " + (this.offset+index));
							ftpc2.dispose();
							continue;
						}else{
							LOG.info("Start parsing offset: "+(this.offset+index));
							boolean flag=false;
							flag=pc.doParse(in);
							pc.resetLevels();
							if(flag==false){
								System.out.println(bookNo + "Parsing File Error " + "offset is: " + (this.offset+index));
								LOG.error(bookNo + " Parsing File Error " + "offset is: " + (this.offset+index));
							}else{
								LOG.info("Finish parsing offset: "+(this.offset+index));
							}
							try {
								in.close();
								while (ftpc2.getFTPClient().completePendingCommand()==false) { 
									System.out.println("CompletePendingCommand failed,try again.bookNo: "+bookNo+" Offset: "+(this.offset+index));
									Thread.sleep(1000);
								}
							} catch (IOException e) {
								LOG.warn(StackTraceUtil.getStackTrace(e));
							}finally{
								ftpc2.dispose();
							}
						}
					}else{//如果位于111-118主机
						int lastNum=(int)(ip.charAt(ip.length()-1)-'0');
						String xmlpath="ebookspub/pub"+lastNum+path+"/meta";
						while(ftpc1.connectFTPSite()==false){
							Thread.sleep(10000);
						}
						while(ftpc1.login()==false){
							Thread.sleep(10000);
						}
						if(ftpc1.changeWorkingDirectory(xmlpath)==false){
							LOG.error(bookNo + " Error in Changing Working Directory  " + "offset is: " + (this.offset+index));
							System.out.println(bookNo + " Error in Changing Working Directory  " + "offset is: " + (this.offset+index));
							ftpc1.dispose();
							continue;
						}
						InputStream in=null;
						try{
							in=ftpc1.retrieveFileStream("Catalog.xml");
						}catch (FTPConnectionClosedException e){
							LOG.warn(StackTraceUtil.getStackTrace(e));
						}
						if(in==null){
							LOG.error(bookNo + " Error in Getting Input File Stream  " + "offset is: " + (this.offset+index));
							System.out.println(bookNo + " Error in Getting Input File Stream  " + "offset is: " + (this.offset+index));
							ftpc1.dispose();
							continue;
						}else{
							LOG.info("Start parsing offset: "+(this.offset+index));
							boolean flag=false;
							flag=pc.doParse(in);
							pc.resetLevels();
							if(flag==false){
								LOG.error(bookNo + " Parsing File Error  " + "offset is: " + (this.offset+index));
								System.out.println(bookNo + " Parsing File Error  " + "offset is: " + (this.offset+index));
							}else{
								LOG.info("Finish parsing offset:"+(this.offset+index));
							}
							try {
								in.close();
								while (ftpc1.getFTPClient().completePendingCommand()==false) { 
									System.out.println("CompletePendingCommand failed,try again.bookNo: "+bookNo+" Offset:"+(this.offset+index));
									Thread.sleep(1000);
								}
							} catch (IOException e) {
								LOG.warn(StackTraceUtil.getStackTrace(e));
							}finally{
								ftpc1.dispose();
							}
						}
					}
				}
			}catch(Exception e){//这个遇到异常意味着当前循环的1000条记录没有被全部执行
				LOG.warn(StackTraceUtil.getStackTrace(e));
				LOG.error(" Error in SQL Sentence   " + "offset为: " + (this.offset+index));
				System.out.println(" Error in SQL Sentence   " + "offset为: " + (this.offset+index));
			}finally{
				LOG.info("Thread finishs handling, the beginning offset="+offset);
			}
		}
	}
}
