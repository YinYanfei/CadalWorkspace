package cn.edu.zju.cadal.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamHandler implements ReaderHandler {
	/**
	 * InputStream
	 * 
	 * @param fileName
	 * @return
	 */
	@Override
	public InputStream createReaderHandler(final String fileName) {
		try {
			return new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * To close InputStream's handler
	 * 
	 * @param inputStream
	 */
	public void closeReaderHandler(InputStream inputStream) {
		try {
			if (inputStream != null) {
				inputStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


}
