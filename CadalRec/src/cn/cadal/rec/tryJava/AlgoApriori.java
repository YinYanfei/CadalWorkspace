package cn.cadal.rec.tryJava;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

public class AlgoApriori {
	protected long startTimestamp; 	// start time of last execution
	protected long endTimestamp; 	// end time of last execution

	public int countUser;			// the number of users
	public int countBook;			// the number of books
	public ArrayList<Integer> countCandidate;		// the number of candidate in different dimensions
	
	public ArrayList<ArrayList<String>> dataSource;						// source data read from file
	public HashMap<HashSet<String>, ArrayList<Integer>> oneDimenCorrelate;		// the first traverse of dataSource, One-Dimensional Correlation
	public HashMap<ArrayList<String>, ArrayList<Integer>> xDimenCorrelate;			// X-Dimensional Correlate, mid result for x dimensional
	public HashMap<ArrayList<String>, ArrayList<Integer>> nDimenCorrelate;			// N-Dimensional Correlate, the result of this program
	
	/**
	 * Construct functions
	 */
	public AlgoApriori() {
		this.startTimestamp = 0;
		this.endTimestamp = 0;
		this.countCandidate = new ArrayList<Integer>();
		this.countUser = 0;
		this.countBook = 0;
		this.dataSource = new ArrayList<ArrayList<String>>();
		this.oneDimenCorrelate = new HashMap<HashSet<String>, ArrayList<Integer>>();
		this.xDimenCorrelate = new HashMap<ArrayList<String>, ArrayList<Integer>>();
		this.nDimenCorrelate = new HashMap<ArrayList<String>, ArrayList<Integer>>();
	}
	public AlgoApriori(AlgoApriori algoApriori) {
		this.startTimestamp = algoApriori.startTimestamp;
		this.endTimestamp = algoApriori.endTimestamp;
		this.countCandidate = algoApriori.countCandidate;
		this.countUser = algoApriori.countUser;
		this.countBook = algoApriori.countBook;
		this.dataSource = algoApriori.dataSource;
		this.oneDimenCorrelate = algoApriori.oneDimenCorrelate;
		this.xDimenCorrelate = algoApriori.xDimenCorrelate;
		this.nDimenCorrelate = algoApriori.nDimenCorrelate;
	}
	public AlgoApriori( ArrayList<Integer> countCandidate, 
						int countUser, int countBook, 
						ArrayList<ArrayList<String>> dataSource, 
						HashMap<HashSet<String>, ArrayList<Integer>> oneDimenCorrelate, 
						HashMap<ArrayList<String>, ArrayList<Integer>> xDimenCorrelate, 
						HashMap<ArrayList<String>, ArrayList<Integer>> nDimenCorrelate){
		this.startTimestamp = this.endTimestamp = 0;
		this.countCandidate = countCandidate;
		this.countUser = countUser;
		this.countBook = countBook;
		this.dataSource = dataSource;
		this.oneDimenCorrelate = oneDimenCorrelate;
		this.xDimenCorrelate = xDimenCorrelate;
		this.nDimenCorrelate = nDimenCorrelate;
	}
	
	/**
	 * Read source file and store source into this.dataSource
	 * @param fileName
	 */
	private void ReadSourceFile(String fileName) {
		File file = new File(fileName);
		BufferedReader reader = null;
		
		try{
			reader = new BufferedReader(new FileReader(file));
			String tmpStr = "";
			
			while((tmpStr = reader.readLine()) != null) {
				String[] strArr = tmpStr.split(" ");
				ArrayList<String> arrList = new ArrayList<String>();
				for(String item : strArr) {
					arrList.add(item);
				}
				this.dataSource.add(arrList);
			}
			
			reader.close();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null) {
				try{
					reader.close();
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Check hashMap meet threHold or not
	 * @param dimenCorrelate
	 * @param threHold
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private HashMap<ArrayList<String>, ArrayList<Integer>> CheckThreHold(HashMap<ArrayList<String>, ArrayList<Integer>> dimenCorrelate, int threHold) {
		HashMap<ArrayList<String>, ArrayList<Integer>> dimenCorrelateTmp = new HashMap<ArrayList<String>, ArrayList<Integer>>();
		
		Iterator iter = dimenCorrelate.entrySet().iterator(); 
		while(iter.hasNext()) {
			Map.Entry entry = (Map.Entry)iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if(((ArrayList<Integer>)val).size() >= threHold) {
				dimenCorrelateTmp.put((ArrayList<String>)key, (ArrayList<Integer>)val);
			}
		}
		
		return dimenCorrelateTmp;
	}
	
	/**
	 * To calculate one-dimensional correlate by traverse this.dataSource
	 */
	@SuppressWarnings("unchecked")
	public void CalOneDimenCorrelate(int threHold){
		if(this.dataSource.size() <= 0 || threHold <= 1) {
			System.out.println("this.dataSource is none or threHold is irrational!");
			System.exit(1);
		}
		
		HashMap<ArrayList<String>, ArrayList<Integer>> oneDimenCorrelateTmp = new HashMap<ArrayList<String>, ArrayList<Integer>>();
		
		// traverse this.dataSource and store bookNo-userIdList in this.oneDimenCorrelate
		for(int i = 0; i < this.dataSource.size(); ++i) {
			ArrayList<String> arrInner = this.dataSource.get(i);
			for(int j = 1; j < arrInner.size(); ++j) {
				if(oneDimenCorrelateTmp.containsKey(arrInner.get(j))) {
					oneDimenCorrelateTmp.get(arrInner.get(j)).add(Integer.valueOf(arrInner.get(0)));
				}else{
					ArrayList<Integer> arrValNew = new ArrayList<Integer>();
					ArrayList<String> arrKeyNew = new ArrayList<String>();
					arrValNew.add(Integer.valueOf(arrInner.get(0)));
					arrKeyNew.add(arrInner.get(j));
					oneDimenCorrelateTmp.put(arrKeyNew, arrValNew);
				}
			}
		}
		
		// traverse this.oneDimenCorrelate to delete items, which do not conform parameter threHold
		Iterator iter = oneDimenCorrelateTmp.entrySet().iterator(); 
		while (iter.hasNext()) {
			System.out.println("----------");
			Map.Entry entry = (Map.Entry)iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			if(((ArrayList<Integer>)val).size() >= threHold) {
				this.oneDimenCorrelate.put((HashSet<String>)key, (ArrayList<Integer>)val);
			}
		} 
	}
	
	/**
	 * To calculate dimensional add one item than dimenCorrelate by merge parameter dimenCorrelate and this.oneDimenCorrelate
	 * @param dimenCorrelate
	 * @param threHold
	 */
	@SuppressWarnings("unchecked")
	public HashMap<ArrayList<String>, ArrayList<Integer>> CalAddOneDimenCorrelate(HashMap<ArrayList<String>, ArrayList<Integer>> dimenCorrelate, int threHold) {
		if(this.oneDimenCorrelate.size() <= 0 || dimenCorrelate.size() <= 0 || threHold <= 0) {
			System.out.println("this.oneDimenCorrelate is none or parameter is irrational!");
			System.exit(1);
		}
		
		// Traverse this.oneDimenCorrelate and parameter dimenCorrelate to get total candidate sets
		Iterator iterOuter = dimenCorrelate.entrySet().iterator();
		while(iterOuter.hasNext()) {
			Map.Entry entryOuter = (Map.Entry)iterOuter.next();
			Object keyOuter = entryOuter.getKey();
			Object valOuter = entryOuter.getValue();
			Iterator iterInner = this.oneDimenCorrelate.entrySet().iterator();
			while(iterInner.hasNext()) {
				Map.Entry entryInner = (Map.Entry)iterInner.next();
				Object keyInner = entryInner.getKey();
				Object valInner = entryInner.getValue();
				
				if(!((ArrayList<String>)keyOuter).contains((String)keyInner)) {
					int count = ((ArrayList<Integer>)valInner).size();
					for(int i = 0; i < count; ++i) {
						if(!((ArrayList<Integer>)valOuter).contains(((ArrayList<Integer>)valInner).get(i))) {
							((ArrayList<Integer>)valOuter).add(((ArrayList<Integer>)valInner).get(i));
						}
					}
				}
			}
		}
		
		return this.CheckThreHold(dimenCorrelate, threHold);
	}
	
	/**
	 * To calculate any dimensional correlate by merge dimenCorrelateFirst and dimenCorrelateSecond
	 * @param dimenCorrelateFirst
	 * @param dimenCorrelateSecond
	 * @param threHold
	 */
	public HashMap<ArrayList<String>, ArrayList<Integer>> CalAnyDimenCorrelate(HashMap<ArrayList<String>, ArrayList<Integer>> dimenCorrelateFirst, 
								     HashMap<ArrayList<String>, ArrayList<Integer>> dimenCorrelateSecond, int threHold){
		if(dimenCorrelateFirst.size() <= 0 || dimenCorrelateSecond.size() <= 0 || threHold <= 0) {
			System.out.println("parameter is irrational!");
			System.exit(1);
		}


		return null;
	}
	
	/**
	 * To calculate this.nDimenCorrelate, which is the final result
	 * @param threHold
	 */
	public void CalAnyDimenCorrelate(int threHold) {
		
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		PrioriConfiguration pc = (new Configuration()).ReadPropertyFile();
		AlgoApriori algoApriori = new AlgoApriori();
		
		// ReadSourceFile test
		algoApriori.ReadSourceFile(pc.getSourceFile());
//		for(int i = 0; i < algoApriori.dataSource.size(); ++i) {
//			ArrayList<String> tmp = algoApriori.dataSource.get(i);
//			for(int j = 0; j < tmp.size(); j++) {
//				System.out.println(tmp.get(j));
//			}
//		}
		
		// CalOneDimenCorrelate test
		algoApriori.CalOneDimenCorrelate(30);
		System.out.println(algoApriori.oneDimenCorrelate.size());
//		Iterator iter = algoApriori.oneDimenCorrelate.entrySet().iterator(); 
//		while (iter.hasNext()) { 
//			Map.Entry entry = (Map.Entry)iter.next();
//			System.out.println(entry.getKey());
//			System.out.println(entry.getValue());
//		} 
		
		
	}
}
