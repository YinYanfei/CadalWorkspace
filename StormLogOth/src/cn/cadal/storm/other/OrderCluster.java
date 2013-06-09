package cn.cadal.storm.other;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class OrderCluster {
	
	private String ADDRESS = "H:/Exp/cluster_v1.txt";
	private String SORTEDADD = "H:/Exp/cluster_v1_sort.txt";
	
	/**
	 * Build map object
	 */
	public HashMap<String, Integer> ReadFileBuildMap() {
		HashMap<String, Integer> mapTmp = new HashMap<String, Integer>();
		
		File file = new File(ADDRESS);
		BufferedReader reader = null;
		
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			
			while ((tempString = reader.readLine()) != null) {
				mapTmp.put(String.valueOf(line), tempString.split(",").length);
				
//				System.out.println("line " + line + ": " + tempString.split(",").length);
				line++;
			}
			reader.close();
			
			return mapTmp;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		return null;
	}
	
	/**
	 * Write into file
	 */
	public void WriteIntoFile(TreeMap<String, Integer> sorted_map) {
		FileWriter writer = null;
		
		try {
			writer = new FileWriter(this.SORTEDADD, true);
			
			// Iterator
			for (Map.Entry<String,Integer> entry : sorted_map.entrySet()) {
				String key = entry.getKey();
		        Integer value = entry.getValue();
		        
		        writer.write(key + " -- " + value + "\n");
		   }

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
				}
			}
		}

	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		OrderCluster oc = new OrderCluster();
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		map = oc.ReadFileBuildMap();
		
		System.out.println("-*- Reading Source File Finished -*-");

		ValueComparator bvc = new ValueComparator(map);
		TreeMap<String, Integer> sorted_map = new TreeMap<String, Integer>(bvc);

		sorted_map.putAll(map);
		
		System.out.println("-*- Begin write into file[Sorted done] -*-");
		
		oc.WriteIntoFile(sorted_map);
		
//		System.out.println("unsorted map: " + map);
//		System.out.println("results: " + sorted_map);
		
		System.out.println("-*- Done -*-");
	}

}
