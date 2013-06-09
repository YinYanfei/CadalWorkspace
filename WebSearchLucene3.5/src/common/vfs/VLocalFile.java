package common.vfs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.filechooser.FileSystemView;

import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;

import common.utils.DjVuTextExtractor;
import common.utils.StackTraceUtil;

public class VLocalFile extends VFile {

	private static final Logger LOG = Logger.getRootLogger();

	private File file;

	private FileSystemView fileSystemView;

	// private String fileContentText;

	public VLocalFile(File f, FileSystemView fsv) {
		this(f, null, fsv);
	}

	public VLocalFile(File f, VLocalFile parent, FileSystemView fsv) {
		this.file = f;
		this.fileSystemView = fsv;
		setName(f.getName());
		setPath(f.getPath());
		setParent(parent);
	}

	public File getFile() {
		return file;
	}

	public InputStream openStream() {
		InputStream is = null;
		try {
			is = file.toURL().openStream();
		} catch (IOException ioexc) {
			ioexc.printStackTrace();
		}
		return is;
	}

	/*
	 * public String getPath(){ return file.getPath(); }
	 * 
	 * public String getName(){ return file.getName(); }
	 */
	public boolean exists() {
		return file.exists();
	}

	public boolean isDirectory() {
		return file.isDirectory();
	}

	public VFile[] list(int num) {

		File[] subfiles = fileSystemView.getFiles(file, true);
		ArrayList validList = new ArrayList();
		VLocalFile temp = null;
		for (int idx = 0; idx < subfiles.length; idx++) {

			try {
				File ifile = subfiles[idx].getCanonicalFile();
				// System.out.println("child : "+ifile.getPath());
				// File ifile = ShellFolder.getShellFolder(d);
				// System.out.println("tansformed child : "+ifile.getPath()+"
				// :name : "+ifile.getName());
				if (ifile.isDirectory()
						|| (VFile.isDisplayable(ifile.getPath()))) {
					temp = new VLocalFile(ifile, this, fileSystemView);
					// System.out.println ( "child VLocalFile : "+ temp );
					validList.add(temp);
				}
			} catch (Exception exc) {
				exc.printStackTrace();
			}
		}
		VFile[] retval = new VFile[validList.size()];
		for (int i_idx = 0; i_idx < validList.size(); i_idx++) {
			retval[i_idx] = (VFile) validList.get(i_idx);
		}
		return retval;
	}

	public String getFileContentText() {

		String fileContentText = null;

		try {
			String fp = file.getPath();
			// System.out.println("file path : "+ fp);
			if (fp.endsWith(".djvu")) {

				URL fUrl = file.toURL();
				try {
					fileContentText = DjVuTextExtractor.extractDjVuText(fUrl);
				} catch (IOException ioexc) {
					LOG.warn(StackTraceUtil.getStackTrace(ioexc));
				}

			} else if (fp.endsWith(".htm") || fp.endsWith(".html")) {
				try {
					StringBean htmlString = new StringBean();
					Parser hParser = new Parser(fp);
					hParser.setEncoding("GB18030");
					hParser.visitAllNodesWith(htmlString);
					fileContentText = htmlString.getStrings();
				} catch (Exception e) {
					LOG.warn(StackTraceUtil.getStackTrace(e));
				}

			} else if (fp.endsWith(".xml") || fp.endsWith(".opf")) {
				// todo
			} else if (fp.endsWith(".txt")) {
				try {
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(fr);
					StringBuffer i_sb = new StringBuffer(4096);
					String linestring = "";
					String linesep = System.getProperty("line.separator");
					while ((linestring = br.readLine()) != null) {
						i_sb.append(linestring);
						i_sb.append(linesep);
					}
					fileContentText = i_sb.toString();
				} catch (IOException ioexc) {
					LOG.warn(StackTraceUtil.getStackTrace(ioexc));
				}
			} else {
				assert false : VFile.UNHANDLED_FILE_FORMAT;
			}
		} catch (MalformedURLException exc) {
			exc.printStackTrace();
		}
		// System.out.println("file content text : "+fileContentText);
		return fileContentText;
	}

	String fileSeparator = System.getProperty("file.separator");

	public String toString() {
		String name = getName();
		// System.out.println ("local file name: "+ name);
		if ((getPath()).endsWith(fileSeparator)) {
			name = getPath();
		}
		return name;
	}

}
