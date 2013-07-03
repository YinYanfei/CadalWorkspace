package cn.cadal.rec.algo.apriori;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import cn.cadal.rec.dm.algorithms.frequentpatterns.apriori.AlgoApriori;


/**
 * Example of how to use APRIORI algorithm from the source code.
 * @author Philippe Fournier-Viger (Copyright 2008)
 */
public class MainTestApriori_saveToFile {

	public static void main(String [] arg) throws IOException{
		
//		String input = fileToPath("E:/DMTest/contextPasquier99.txt");
		String input = "E:/DMTest/contextPasquier99.txt";
		String output = "E:/DMTest/Result/frequent_itemsets.txt";  // the path for saving the frequent itemsets found
		
		int minsup = 3; // means a minsup of 2 transaction (we used a relative support)
		
		// Applying the Apriori algorithm
		AlgoApriori apriori = new AlgoApriori();
		apriori.runAlgorithm(minsup, input, output);
		apriori.printStats();
	}
	
	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = MainTestApriori_saveToFile.class.getResource(filename);
		return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}