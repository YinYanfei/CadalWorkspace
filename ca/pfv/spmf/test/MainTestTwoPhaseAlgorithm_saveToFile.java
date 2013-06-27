package ca.pfv.spmf.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import ca.pfv.spmf.algorithms.frequentpatterns.two_phase.AlgoTwoPhase;
import ca.pfv.spmf.algorithms.frequentpatterns.two_phase.Itemsets;
import ca.pfv.spmf.algorithms.frequentpatterns.two_phase.UtilityTransactionDatabase;

/**
 * Example of how to use the TWOPhase Algorithm in source code.
 * @author Philippe Fournier-Viger, 2010
 */
public class MainTestTwoPhaseAlgorithm_saveToFile {

	public static void main(String [] arg) throws IOException{
		
		String input = fileToPath("DB_Utility.txt");
		String output = "C:\\patterns\\hui.txt";

		int min_utility = 30;  // 

		// Loading the database into memory
		UtilityTransactionDatabase database = new UtilityTransactionDatabase();
		database.loadFile(input);
		
		// Applying the Two-Phase algorithm
		AlgoTwoPhase twoPhase = new AlgoTwoPhase();
		Itemsets highUtilityItemsets = twoPhase.runAlgorithm(database, min_utility);
		
		highUtilityItemsets.saveResultsToFile(output, database.getTransactions().size());

		twoPhase.printStats();

	}

	public static String fileToPath(String filename) throws UnsupportedEncodingException{
		URL url = MainTestTwoPhaseAlgorithm_saveToFile.class.getResource(filename);
		 return java.net.URLDecoder.decode(url.getPath(),"UTF-8");
	}
}
