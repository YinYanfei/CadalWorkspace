package cn.edu.zju.cadal.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BufferedReaderHandler implements ReaderHandler {
	/**
	 * BufferedReader
	 * 
	 * @param fileName
	 * @return
	 */
	@Override
	public BufferedReader createReaderHandler(final String fileName) {
		try {
			return new BufferedReader(new FileReader(new File(fileName)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * To close BufferedReader's handler
	 * 
	 * @param reader
	 */
	public void closeReaderHandler(BufferedReader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
