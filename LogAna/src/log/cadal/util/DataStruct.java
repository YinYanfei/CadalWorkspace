package log.cadal.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataStruct {
	public Map type2num = null;
	
	/**
	 * construct function
	 */
	public DataStruct() {
		type2num = new HashMap();
		
		this.type2num.put("guji", "0");
		this.type2num.put("mingguo", "0");
		this.type2num.put("mingguoqikan", "0");
		this.type2num.put("xiandai", "0");
		this.type2num.put("xuewei", "0");
		this.type2num.put("yingwen", "0");
		this.type2num.put("huihua", "0");
		this.type2num.put("shiping", "0");
		this.type2num.put("shufa", "0");
		this.type2num.put("qita", "0");
	}
	
	/**
	 * Write into file
	 */
	public void writeIntoFile(FileWriter writer) {
		try {
			writer.write((String) this.type2num.get("guji") + ",");
			writer.write((String) this.type2num.get("mingguo") + ",");
			writer.write((String) this.type2num.get("mingguoqikan") + ",");
			writer.write((String) this.type2num.get("xiandai") + ",");
			writer.write((String) this.type2num.get("xuewei") + ",");
			writer.write((String) this.type2num.get("yingwen") + ",");
			writer.write((String) this.type2num.get("huihua") + ",");
			writer.write((String) this.type2num.get("shiping") + ",");
			writer.write((String) this.type2num.get("shufa") + ",");
			writer.write((String) this.type2num.get("qita") + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataStruct ds = new DataStruct();
		FileWriter writer;
		try {
			writer = new FileWriter("H:/cadal0410/try.txt", true);
			ds.writeIntoFile(writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
