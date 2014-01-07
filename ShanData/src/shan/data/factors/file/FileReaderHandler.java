package shan.data.factors.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
			return new BufferedReader(
					new FileReader(new File(fileName)));
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
