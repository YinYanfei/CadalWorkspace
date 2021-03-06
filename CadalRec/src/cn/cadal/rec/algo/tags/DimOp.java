package cn.cadal.rec.algo.tags;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 这个类用来取出标签表中的dim*相关的分类信息
 * 
 * @author Yanfei
 *
 */
public class DimOp {

	private String fileDir = "E:/Recommendation/Tags/dim.info.dat";
	private String destin = "E:/Recommendation/Tags/dim.info.op.dat";

	/**
	 * Construct functions
	 */
	public DimOp(){
	}
	public DimOp(String fileDir, String destin){
		this.fileDir = fileDir;
		this.destin =destin;
	}
	
	/**
	 * 01025185 [10030]综合性学术文献$[7601530]音乐表演艺术$[9902040]小说$ => 01025185 综合性学术文献$音乐表演艺术$小说$
	 */
	public void Tidy(){
		File file = new File(this.fileDir);
		BufferedReader reader = null;
		FileWriter writer = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			writer = new FileWriter(this.destin);
			
			String line = "";
			while((line = reader.readLine()) != null){
				String [] lineSplit = line.split(" ");
				writer.write(lineSplit[0] + " ");
				String [] lineSplit_1_split = lineSplit[1].split("\\$");
				
				for(int i = 0; i < lineSplit_1_split.length; ++i) {
					writer.write(lineSplit_1_split[i].substring(lineSplit_1_split[i].indexOf("]") + 1) + "$");
				}
				
				writer.write("\n");
			}
			
			reader.close();
			writer.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(reader != null)
					reader.close();
				if(writer != null) 
					writer.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * To count independent dim and counting number, and write into file
	 * 
	 * @param fileIndependDim
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Integer> CountIndependDim(String fileIndependDim){
		File file = new File(this.destin);
		BufferedReader reader = null;
		FileWriter writer = null;
		
		Map<String, Integer> countMap = new HashMap<String, Integer>();
		
		try{
			reader = new BufferedReader(new FileReader(file));
			
			String line = "";
			while((line = reader.readLine()) != null){
				String lineDim = line.substring(line.indexOf(" ") + 1);
				String [] lineDimSplit = lineDim.split("\\$");
				
				for(int i = 0; i < lineDimSplit.length; ++i) {
					Integer val = 1;
					if(countMap.containsKey(lineDimSplit[i])) {
						val = countMap.get(lineDimSplit[i]);
						val += 1;
					}
					
					countMap.put(lineDimSplit[i], val);
				}
			}
			reader.close();
			
			writer = new FileWriter(fileIndependDim);
			Iterator iter = countMap.entrySet().iterator(); 
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iter.next(); 
			    Object key = entry.getKey(); 
			    Object val = entry.getValue();
			    writer.write(key + " " + val + "\n");
			} 
			writer.close();
			
			return countMap;
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try{
				if(reader != null)
					reader.close();
				if(writer != null) 
					writer.close();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test and Analyze
//		DimOp dimOp = new DimOp();
//		String fileIndependDim = "E:/Recommendation/Tags/dim.info.op.count.dat";
		
//		dimOp.Tidy();
		
//		dimOp.CountIndependDim(fileIndependDim);
	}

}
