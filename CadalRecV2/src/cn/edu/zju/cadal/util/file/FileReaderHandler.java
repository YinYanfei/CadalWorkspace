package cn.edu.zju.cadal.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class FileReaderHandler implements ReaderHandler {

	/**
	 * Reader
	 * 
	 * @param fileName
	 * @return
	 */
	@Override
	public Reader createReaderHandler(final String fileName) {
		try {
			return new InputStreamReader(
					new FileInputStream(new File(fileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * To close Reader's handler
	 * 
	 * @param reader
	 */
	public void closeReaderHandler(Reader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
