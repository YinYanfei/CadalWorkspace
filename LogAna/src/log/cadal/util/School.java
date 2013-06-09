package log.cadal.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class School {

	public List school2type = null;
	
	/**
	 * Construct function
	 */
	public School() {
		this.school2type = new ArrayList();
		
		for(int i = 0; i < 150; ++i) {
			DataStruct ds = new DataStruct();
			this.school2type.add(ds);
		}
	}
	
	/**
	 * Writer into file
	 */
	public void writerIntoFile(String fileName) {
		try {
			FileWriter writer = new FileWriter(fileName, true);
			DataStruct ds = null;
			
			for(int i = 0; i < 150; ++i) {
				//writer.write("------------------------");
				//writer.write(i + "\n");
				ds = (DataStruct)this.school2type.get(i);
				writer.write(i + ",");
				ds.writeIntoFile(writer);
			}
			
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test

	}

}
