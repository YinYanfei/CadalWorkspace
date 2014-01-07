package cn.edu.zju.cadal.util.file;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileWriterHandler implements WriterHandler {

	/**
	 * FileWriter
	 * 
	 * @param fileName
	 * @return
	 */
	public FileWriter createHandler(final String fileName) {
		try {
			return new FileWriter(fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * FileWriter, with parameter addition
	 * 
	 * @param fileName
	 * @param addition
	 * @return
	 */
	public FileWriter createHandler(final String fileName,
			final boolean addition) {
		try {
			return new FileWriter(fileName, addition);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * To close FileWriter's handler
	 * 
	 * @param writer
	 */
	@Override
	public void closeHanlder(Writer writer) {
		try {
			if (writer != null) {
				writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
