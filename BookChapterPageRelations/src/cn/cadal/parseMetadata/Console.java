package cn.cadal.parseMetadata;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import cn.cadal.util.StackTraceUtil;

public class Console {
	static private final Log LOG = LogFactory.getLog(Console.class);
	static int BeginOffset = -1;
	static int EndOffset=-1;
	static Map<Integer,String> CONTENTHOST=new HashMap<Integer,String>(); 

	// 连接到PG
	public static Connection connectPG(String url, String username,
			String password) {
		Connection con = null;
		try {
			Class.forName("org.postgresql.Driver");
			try {
				con = DriverManager.getConnection(url, username, password);
			} catch (SQLException e) {
				LOG.warn(StackTraceUtil.getStackTrace(e));
			}
		} catch (ClassNotFoundException e1) {
			LOG.warn(StackTraceUtil.getStackTrace(e1));
		}
		return con;
	}

	public static void main(String[] args) throws SAXException, IOException,
			InterruptedException {
		// 必须输入从数据库第几条数据开始取记录
		if (args.length != 1) {
			System.out.println("Please Input BeginOffset!");
			System.exit(0);
		}
		BeginOffset = Integer.parseInt(args[0]);
		// 连接PG
		String url = "jdbc:postgresql://10.15.62.71:5432/cadal_metadata_full_dbo2";
		String username = "cadal";
		String password = "Cadal205";
		Connection con = Console.connectPG(url, username, password);
		// 获取书总数
		try{
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery("SELECT count(\"BookNo\") FROM cbook");
			r.next();
			EndOffset=r.getInt(1);
		}catch(SQLException e){
			System.out.println("Cannot calcuate the total number of books, cannot get the condition of program termination.");
			System.exit(0);
		}
		// 把contenthost读到内存
		try{
			Statement s=con.createStatement();
			ResultSet r=s.executeQuery("select \"id\",\"ExternalUri\" from contenthost");
			while(r.next()){
				int id=r.getInt(1);
				String externalUri=r.getString(2);
				CONTENTHOST.put(id, externalUri);
			}
		}catch(SQLException e){
			System.out.println("Cannot load ContentHost!");
			System.exit(0);
		}
		
		LOG.info("Program starts to run, BeginOffset: " + BeginOffset);
		// 创建FTP主机
		FTPSite ftpc111 = new FTPSite("10.15.61.111", "fabu", "cadaldata");
		FTPSite ftpc112 = new FTPSite("10.15.61.112", "fabu", "cadaldata");
		FTPSite ftpc113 = new FTPSite("10.15.61.113", "fabu", "cadaldata");
		FTPSite ftpc114 = new FTPSite("10.15.61.114", "fabu", "cadaldata");
		FTPSite ftpc115 = new FTPSite("10.15.61.115", "fabu", "cadaldata");
		FTPSite ftpc116 = new FTPSite("10.15.61.116", "fabu", "cadaldata");
		FTPSite ftpc117 = new FTPSite("10.15.61.117", "fabu", "cadaldata");
		FTPSite ftpc118 = new FTPSite("10.15.61.118", "fabu", "cadaldata");
		FTPSite ftpc223 = new FTPSite("10.15.61.223", "cadal", "Cadal205");
		try {
			XmlScanner xs1 = new XmlScanner(ftpc111, ftpc223, con);
			xs1.start();
			XmlScanner xs2 = new XmlScanner(ftpc112, ftpc223, con);
			xs2.start();
			XmlScanner xs3 = new XmlScanner(ftpc113, ftpc223, con);
			xs3.start();
			XmlScanner xs4 = new XmlScanner(ftpc114, ftpc223, con);
			xs4.start();
			XmlScanner xs5 = new XmlScanner(ftpc115, ftpc223, con);
			xs5.start();
			XmlScanner xs6 = new XmlScanner(ftpc116, ftpc223, con);
			xs6.start();
			XmlScanner xs7 = new XmlScanner(ftpc117, ftpc223, con);
			xs7.start();
			XmlScanner xs8 = new XmlScanner(ftpc118, ftpc223, con);
			xs8.start();

		} catch (Exception e) {
			LOG.warn(StackTraceUtil.getStackTrace(e));
			System.out.println("Error in creating XmlScanner ");
		}
	}
}
