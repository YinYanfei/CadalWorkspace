package common.vfs;

import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import common.utils.StackTraceUtil;

public class FTPSite {

	private static final Logger LOG = Logger.getLogger(FTPSite.class);

	private FTPClient ftpc = new FTPClient();

	private String ftpaddress;

	private String username;

	private String password;

	private String folder;

	public FTPSite(String faddress, String uname, String pword, String folder) {
		int idx = faddress.indexOf("//");
		if (idx >= 0)
			this.ftpaddress = faddress.substring(idx + 2);
		else {
			this.ftpaddress = faddress;
		}
		this.username = uname;
		this.password = pword;
		this.folder = folder;
	}

	String lineSeparator = System.getProperty("line.separator");

	public String toString() {
		StringBuffer temp = new StringBuffer(64);
		temp.append("ftp address : " + getFtpAddress() + lineSeparator);
		temp.append("user name : " + getUserName() + lineSeparator);
		temp.append("pass word : " + getPassword() + lineSeparator);
		temp.append("home folder : " + getFolder());

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

	public String getFolder() {
		return folder;
	}

	public void dispose() {
		try {
			if (ftpc.isConnected()) {
				ftpc.logout();
				ftpc.disconnect();
				if (LOG.isDebugEnabled()) {
					LOG.debug("close ftp connection for : " + ftpaddress);
				}
			}
		} catch (IOException iexc) {
			iexc.printStackTrace();
		}
	}

	public boolean isConnected() {
		return ftpc.isConnected();
	}

	static public int connectFTPSite(FTPSite ftpsite) {
		int retval = 0;

		if (LOG.isDebugEnabled())
			LOG.debug("ftp site info : " + System.getProperty("line.separator")
					+ ftpsite);

		FTPClient ftp = ftpsite.getFTPClient();
		String server = ftpsite.getFtpAddress();
		String username = ftpsite.getUserName();
		String password = ftpsite.getPassword();
		String folder = ftpsite.getFolder();
		try {
			int reply;

			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
					LOG.warn("disconnect the broken connection");
				} catch (IOException exc) {
					LOG.warn(StackTraceUtil.getStackTrace(exc));
				}
			}
			LOG.info("Connecting to " + server + ".");
			ftp.connect(server);
			LOG.info("Connectd to " + server + ".");
			reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftp.disconnect();
				LOG.error("FTP server refused connection.");
				retval = 1;
			}
		} catch (IOException e) {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException exc) {
					exc.printStackTrace();
				}
				LOG.error("Could not connect to server.");
				LOG.warn(StackTraceUtil.getStackTrace(e));
				retval = 2;
			}
		}
		if (retval > 0)
			return retval;

		__login: try {
			if (!ftp.login(username, password)) {
				ftp.logout();
				break __login;
			}
			LOG.info("Remote system is " + ftp.getSystemName());
			LOG.info("connected to " + server + "." + ftp.getReplyString());

			ftp.changeWorkingDirectory(folder);
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();

		} catch (FTPConnectionClosedException ftpexc) {
			LOG.info("Server closed connection.");
			LOG.warn(StackTraceUtil.getStackTrace(ftpexc));
			retval = 1;
		} catch (IOException ioexc) {
			LOG.warn(StackTraceUtil.getStackTrace(ioexc));
			retval = 1;
		} catch (NullPointerException nullexc) {
			LOG.warn(StackTraceUtil.getStackTrace(nullexc));
			retval = 1;
		}

		return retval;
	}

}
