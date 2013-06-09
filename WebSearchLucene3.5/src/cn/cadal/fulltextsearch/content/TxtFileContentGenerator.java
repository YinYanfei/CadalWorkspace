package cn.cadal.fulltextsearch.content;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class TxtFileContentGenerator implements FileContentGenerator {
	private String encoding = "utf-8";
	
	
	/**
	 * @return Returns the encoding.
	 */
	public String getEncoding() {
		return encoding;
	}
	/**
	 * @param encoding The encoding to set.
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	/* (non-Javadoc)
	 * @see com.cadal.filecontentgenerator.FileContentGenerator#getFileContent()
	 */
	public String getFileContent(File file) {
		// TODO Auto-generated method stub
		String fileContent = "";
		StringBuffer sb = new StringBuffer();
		try{
			//如果是utf-8文件，用reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			
			
			BufferedReader reader = new BufferedReader(
						new InputStreamReader(
						new FileInputStream(file),"GBK"));
			int n = 0;
			char[] c = new char[4096];
			while (true) {
				n = reader.read(c);
				if (n > 0)
					sb.append(c);
				else
					break;
			}
			reader.close();
			fileContent = sb.toString();

		}catch (IOException ex) {
			ex.printStackTrace();
		}
	    return fileContent.trim();
	}
}
