package downbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class ftpConnect {

	public String server;
	public String username;
	public String password;
	public String remote_path; // 服务器上的资源所在的具体位置
	public String local_path; // 资源下载后存在本机的位置
	public int errorFlag; // 出错标记
	FTPClient ftp;

	ftpConnect() {
		errorFlag = 0; // 表明正常
	}

	// 登陆ftp
	public int getConnect() {
		ftp = new FTPClient();
		ftp.setControlEncoding("GBK");// 设置编码方式，解决中文乱码
		ftp.addProtocolCommandListener(new PrintCommandListener(
				new PrintWriter(System.out)));
		try {
			int reply;
			ftp.connect(server);
			System.out.println("Connected to " + server + ".");
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				System.err.println("FTP server refused connection.");
				errorFlag = 1;
				// 调用出错处理程序
				return 1; // 连接不上

			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					// do nothing
				}
			}
			System.err.println("Could not connect to server.");
			e.printStackTrace();
			System.exit(1);
		}

		return 0; // 正常
	}

	// *************************************************************************
	// 下载
	public int ftpDown() {
		try {
			ftp.setBufferSize(1000);

			if (!ftp.login(username, password)) {
				ftp.logout();
				System.out.println("登陆失败\n");
				return 1; // 登陆失败 用户或者密码错误
			}

			System.out.println("hao");

			ftp.setFileType(FTP.BINARY_FILE_TYPE);

			// Use passive mode as default because most of us are
			// behind firewalls these days.
			ftp.enterLocalPassiveMode();

			System.out.println("haobuhao");
			downbooks(ftp, remote_path, local_path);
			ftp.logout();
		} catch (FTPConnectionClosedException e) {
			System.err.println("Server closed connection.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					// do nothing
				}
			}
		}

		return 0;
	}

	// 创建文件夹
	public void CreateFiles(String local_path, String title, String remote_path) {
		// ************
		// 有重名书的处理
		// ********
		String bookname = title;
		File DirName = new File(local_path + bookname);

		int book_count = 1; // 记录重名
		if (!DirName.exists()) {
			DirName.mkdirs();
		} else // 有重名的书
		{
			while (true) {
				book_count++;
				DirName = new File(local_path + bookname + book_count);

				if (!DirName.exists())
					break;
			}
			DirName.mkdirs();

		}

		//
		this.local_path = DirName.getAbsolutePath();
		this.remote_path = remote_path + "/ptiff";
		// this.remote_path=remote_path ;
	}
	
	public void downbooks(FTPClient ftp, String remotePath, String localPath) {
		try {
			FTPFile[] ftpFiles = ftp.listFiles(remote_path);
			String filenames[] = new String[ftpFiles.length];

			for (int i = 0; i < ftpFiles.length; i++) {
				filenames[i] = ftpFiles[i].getName();
				if (ftpFiles[i].isDirectory()) {
					// 遇到目录了递归调用
					File subFile = new File(localPath + "\\" + filenames[i]);
					subFile.mkdir();

					downbooks(ftp, remotePath + "/" + filenames[i], subFile
							.getAbsolutePath());
					return;
				}

				// 遇到文件
				File local_files = new File(localPath + "\\" + filenames[i]);
				local_files.createNewFile();
				OutputStream output;

				output = new FileOutputStream(local_files);

				ftp.retrieveFile(remotePath + "/" + filenames[i], output);

				System.out.println("buhao");
				output.close();
			}
		} catch (FTPConnectionClosedException e) {
			System.err.println("Server closed connection.");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setServer(String Ftphost) {
		this.server = Ftphost;
	}

	public void setUserName(String name) {
		this.username = name;
	}

	public void setPasswd(String pwd) {
		this.password = pwd;
	}
}
