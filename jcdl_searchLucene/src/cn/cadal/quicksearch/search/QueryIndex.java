package cn.cadal.quicksearch.search;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;

public class QueryIndex {
	
	/**
	 * Variables
	 */
	private ArrayList<Integer> arrTopic;
	private ArrayList<String> arrIndex;
	private int []arrTop;
	
	// 
	private ArrayList<String> arrIndex5;
	private ArrayList<Integer> arrTopic5;
	
	/**
	 * Construct Function
	 */
	public QueryIndex(){
		this.arrIndex = new ArrayList<String>();
		this.arrTopic = new ArrayList<Integer>();
		
		this.arrIndex5 = new ArrayList<String>();	// 值
		this.arrTopic5 = new ArrayList<Integer>();	// ID
	}
	
	/**
	 * Read file to get arrTopic and arrIndex
	 */
	public boolean readFileIndex() {
		boolean signal = false;
		
		try{
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("E:/query/queryindex.txt"),"UTF-8"));
			
			String strLine = "";
			
			while((strLine = input.readLine()) != null) {
				this.arrIndex.add(strLine.substring(0, strLine.length() - 4));
			}
			
			input.close();
			signal = true;
		}catch(Exception e) {
			e.printStackTrace();
			signal = false;
		}
		
		return signal;
	}
	public boolean readFileTopic() {
		boolean signal = false;
		
		try{
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("E:/query/querytopic4.txt"),"UTF-8"));
			
			String strLine = "";
			String[] strTmp;
			while((strLine = input.readLine()) != null) {
				strTmp = strLine.split(" ");
				
				for(int i = 0; i < strTmp.length; ++i) {
					this.arrTopic.add(Integer.parseInt(strTmp[i]));
				}
			}
			input.close();
			signal = true;
		}catch(Exception e) {
			e.printStackTrace();
			signal = false;
		}
		
		return signal;
	}
	
	/**
	 * Get queryTopic
	 */
	public void queryTopicGet(int index) {
		arrTop = new int[20];
		
		for(int i =0; i < 20; ++i){
			arrTop[i] = this.arrTopic.get((index) * 20 + i);
		}
		
		//
		for(int j = 0; j < 20; ++j) {
			System.out.println(arrTop[j]);
		}
		//
	}
	
	/**
	 * Get queryIndex
	 */
	public void queryIndexGet(){
		for(int i = 0; i < 20; ++i) {
			if(arrTop[i] > 0)
				System.out.println(this.arrIndex.get(arrTop[i] - 1));
			else
				System.out.println("0");
		}		
	}
	
	/**
	 * Topic5
	 */
	/*public void queryTopicGet5(int index) {
		try{
			BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream("E:/query/querytopic5.txt")));
			String str;
			int tmp;
			if(index < 500) {
				for(int j = 0; j < index; ++j){
					System.out.println(input.readLine());
				}
				
				str = input.readLine();
				
				System.out.println("-=-=-=-=-=-=-=-=-=-" + str);
				
				String strTmp[] = str.split(" ");
				// 读入index对应的行号的ID
				for(int i = 0; i < strTmp.length; ++i) {
					this.arrTopic5.add(Integer.parseInt(strTmp[i]));
				}
			}
			
			input.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Index5
	 */
	/*public void queryIndexGet5() {
		try{
			for(int i = 0; i < this.arrTopic5.size(); ++i) {
				this.arrIndex5.add(this.arrIndex.get(this.arrTopic5.get(i) - 1));
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	*/
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		QueryIndex queryIndex = new QueryIndex();
		
		queryIndex.readFileTopic();
		
		queryIndex.readFileIndex();
		
		queryIndex.queryTopicGet(1);
		
		System.out.println("------------------");
	}

	/**
	 * Set and Get functions
	 */
	public ArrayList<Integer> getArrTopic() {
		return arrTopic;
	}

	public void setArrTopic(ArrayList<Integer> arrTopic) {
		this.arrTopic = arrTopic;
	}

	public ArrayList<String> getArrIndex() {
		return arrIndex;
	}

	public void setArrIndex(ArrayList<String> arrIndex) {
		this.arrIndex = arrIndex;
	}

	public int[] getArrTop() {
		return arrTop;
	}

	public void setArrTop(int[] arrTop) {
		this.arrTop = arrTop;
	}

	public ArrayList<String> getArrIndex5() {
		return arrIndex5;
	}

	public void setArrIndex5(ArrayList<String> arrIndex5) {
		this.arrIndex5 = arrIndex5;
	}

	public void setArrTopic5(ArrayList<Integer> arrTopic5) {
		this.arrTopic5 = arrTopic5;
	}

	public ArrayList<Integer> getArrTopic5() {
		return arrTopic5;
	}
	
	

}
