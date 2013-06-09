package common.vfs;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.apache.commons.net.MalformedServerReplyException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPConnectionClosedException;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;

import common.utils.DjVuTextExtractor;
import common.utils.StackTraceUtil;

public class VFTPFile extends VFile {

	private static final Logger LOG = Logger.getRootLogger();

	private FTPSite ftpSite;

	private FTPFile ftpFile;

	private FTPListParseEngine listEngine;

	//    private String ftpFilePath;
	//    private String fileContentText;
	public VFTPFile(FTPSite ftps, String ftpFilePath) {
		super(ftpFilePath);
		this.ftpSite = ftps;
	}
	
	public VFTPFile (VFTPFile root, String path) {
		super(path);
		this.ftpSite = root.ftpSite;
		
	}

	public VFTPFile(FTPSite ftps, FTPFile ff, String childName, VFTPFile parent) {
		super();
		this.ftpSite = ftps;
		this.ftpFile = ff;

		setName(childName);
		setParent(parent);
		if (parent != null) {
			String fp = parent.getPath();
			if ("/".equals(fp)) {
				fp = fp + childName;
			} else {
				fp = fp + "/" + childName;
			}
			setPath(fp);
		}
	}

	public FTPFile getFtpFile() {
		return ftpFile;
	}

	public boolean exists() {
		boolean retval = false;
		VFile parent = getParent();
		VFile[] subfiles = parent.list(0);
		for (int idx = 0; idx < subfiles.length; idx++) {
			String rPath = subfiles[idx].getPath();
			if (rPath.equals(getPath())) {
				ftpFile = ((VFTPFile) subfiles[idx]).getFtpFile();
				retval = true;
			}
		}
		return retval;
	}

	public VFile[] list(int num) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("the path of the current dir :" + getPath());
		}

		VFile[] retval = null;
		ArrayList validList = new ArrayList();
		//todo:	if ( ! ftpFile.isDirectory() ) return retval;

		FTPFile[] files = null;
		files = pagedFTPDirList(num);

		VFTPFile temp = null;
		if (files != null) {
			for (int idx = 0; idx < files.length; idx++) {
				String fName = files[idx].getName();
				if (LOG.isDebugEnabled())
					LOG.debug("the path of the child ftp file :" + getPath()
							+ "/" + fName);

				temp = new VFTPFile(ftpSite, files[idx], fName, this);
				validList.add(temp);
			}

			if ((num == files.length) && (num > 0)) {
				VMoreStringFTPFile t = new VMoreStringFTPFile(this);
				validList.add(t);
			}
		}
		retval = (VFile[]) (validList.toArray(new VFile[0]));
		return retval;
	}

	public FTPFile[] pagedFTPDirList(int num) {

		String folder = getPath();
		FTPClient ftpc = ftpSite.getFTPClient();
		FTPFile[] files = null;

		while (null == files) {
			try {
				ftpc.changeWorkingDirectory(folder);
				if (listEngine == null) {
					listEngine = ftpc.initiateListParsing(
							"common.vfs.FilterUnixFTPEntryParser", folder);
				}
				if (0 == num) {
					files = listEngine.getFiles();
				} else if (listEngine.hasNext()) {
					files = listEngine.getNext(num);
				}
			} catch (FTPConnectionClosedException fexc) {
				LOG.warn(StackTraceUtil.getStackTrace(fexc));
				files = null;
				//Reconnect ftp
				int resultcode = 0;
				do {
					resultcode = FTPSite.connectFTPSite(ftpSite);
				} while (resultcode > 0);
			} catch (ConnectException connect) {
				LOG.warn(StackTraceUtil.getStackTrace(connect));
				files = null;
				int resultcode = 0;
				do {
					resultcode = FTPSite.connectFTPSite(ftpSite);
				} while (resultcode > 0);
			} catch (MalformedServerReplyException mal) {
				LOG.warn(StackTraceUtil.getStackTrace(mal));
				files = null;
				int resultcode = 0;
				do {
					resultcode = FTPSite.connectFTPSite(ftpSite);
				} while (resultcode > 0);
			} catch (IOException ioexc) {
				LOG.warn(StackTraceUtil.getStackTrace(ioexc));
				files = null;
				int resultcode = 0;
				do {
					resultcode = FTPSite.connectFTPSite(ftpSite);
				} while (resultcode > 0);
			} catch (NullPointerException nullexc) {
				LOG.warn(StackTraceUtil.getStackTrace(nullexc));
				int resultcode = 0;
				do {
					resultcode = FTPSite.connectFTPSite(ftpSite);
				} while (resultcode > 0);
			}
		}
		return files;
	}

	private ByteArrayOutputStream bytearrayOS;

	public InputStream openStream() {
		ByteArrayInputStream ret = null;
		while (null == ret) {
			try {
				if (bytearrayOS == null) {
					bytearrayOS = new ByteArrayOutputStream(65536);
				}
				ftpSite.getFTPClient().retrieveFile(getPath(), bytearrayOS);
				byte[] tba = null;
				if (getPath().indexOf("eng") > 0) {
					String temp = bytearrayOS.toString("UTF-16");
					//System.out.println ("before rep :" + temp.substring(0,150));
					temp = temp.replaceFirst("Unicode", "UTF-16");
					//		temp = temp.replaceFirst ("<!DOCTYPE .*>\r\n", "");
					//System.out.println ("after replacement: "+temp.substring(0,150));
					//temp = new String(temp.getBytes("UTF-16"), "UTF-8");
					tba = temp.getBytes("UTF-16");
				} else {
					tba = bytearrayOS.toByteArray();
				}

				ret = new ByteArrayInputStream(tba);//bytearrayOS.toByteArray()
				return ret;

			} catch (ConnectException connect) {
				LOG.info(StackTraceUtil.getStackTrace(connect));
				int resultcode = 0;
				do {
					resultcode = FTPSite.connectFTPSite(ftpSite);
				} while (resultcode > 0);
				
				ret = null;
				bytearrayOS = null;
				LOG.info("reconnection");
			} catch (Exception exc) {
				LOG.warn(StackTraceUtil.getStackTrace(exc));
				try {
					TimeUnit.SECONDS.sleep (5);
				} catch (InterruptedException ie) {
					//todo 
					LOG.info ("blocking 5 seconds.");
				}
			} finally {
				try {
					if (null != bytearrayOS)
						bytearrayOS.close();
				} catch (IOException ioexc) {
					LOG.warn(StackTraceUtil.getStackTrace(ioexc));
				}
			}
		}
		return ret;
	}

	/*
	 private static SAXParserFactory saxFactory ;
	 private static SAXParser saxParser ;

	 static {
	 try {
	 saxFactory = SAXParserFactory.newInstance();
	 saxFactory.setNamespaceAware (false);
	 saxFactory.setValidating (false);

	 saxParser = saxFactory.newSAXParser ();
	 }catch (Exception exc){
	 exc.printStackTrace ();
	 }
	 }

	 private PrintXMLHandler xmltext;
	 */
	public String getFileContentText() {

		String fileContentText = null;

		InputStream is = openStream();
		if (null == is)
			throw new NullPointerException(
					"the inputstream of the file mustn't be null");
		String fp = getPath();

		if (fp.endsWith(".djvu")) {
			try {
				fileContentText = DjVuTextExtractor
						.extractDjVuText(openStream());
			} catch (IOException ioexc) {
				LOG.warn(StackTraceUtil.getStackTrace(ioexc));
				fileContentText = "";
			}

		} else if (fp.endsWith(".htm") || fp.endsWith(".html")) {

			try {
				StringBean htmlString = new StringBean();
				String htmlpage = bytearrayOS.toString("GB18030");
				Parser hParser = Parser.createParser(htmlpage, "GB18030");

				hParser.visitAllNodesWith(htmlString);
				fileContentText = htmlString.getStrings();
			} catch (Exception pe) {
				pe.printStackTrace();
			}
		} else if (fp.endsWith(".xml")) {
			try {
				fileContentText = bytearrayOS.toString("GB18030");
				/*if ( xmltext == null) xmltext = new PrintXMLHandler ();
				 saxParser.parse (is, xmltext);
				 fileContentText = xmltext.getContent ();*/
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			assert false : VFile.UNHANDLED_FILE_FORMAT;
		}
		return fileContentText;
	}

	public boolean isDirectory() {
		return ftpFile.isDirectory();
	}

	public FTPListParseEngine getFTPListParseEngine() {
		return listEngine;
	}
}
