package cn.cadal.quicksearch.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import cn.cadal.quicksearch.QuickSearchConfig;

public class BookRankClient {
	
	private HashMap<String, String> rankMap = null;
	private String avgRank = "";

	public BookRankClient() {
		try{
			File f = new File(QuickSearchConfig.getTxtDir() + "/avgrank.txt");
			if(!f.exists())
			{
				System.out.println("avgrank.txt not found!");
				System.exit(1);
			}
			BufferedReader input = new BufferedReader(new FileReader(f));
			String line =input.readLine();
			if(line!=null)
				avgRank = line.trim();
			else
			{
				System.out.println("invalid avg rank value!");
				System.exit(1);
			}
			input.close();
			System.out.println("avg rank value: " + avgRank);
		}catch(IOException ex){
			ex.printStackTrace();
		}
		rankMap = new HashMap<String, String>();
		try{
			File f = new File(QuickSearchConfig.getTxtDir() + "/bookrank.txt");
			if(!f.exists())
			{
				System.out.println("bookrank.txt not found!");
				System.exit(1);
			}
			BufferedReader input = new BufferedReader(new FileReader(f));
			String line = null;
			while((line = input.readLine()) != null)
			{
				String[] strs = line.split(" ");
				rankMap.put(strs[0].trim(), strs[1].trim());
			}
			input.close();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		System.out.println("rankMap size: " + rankMap.size());
	}
	
	public String getBookRank(String bookNo) {
		if(rankMap.containsKey(bookNo))
			return rankMap.get(bookNo);
		else
			return avgRank;
	}

}
