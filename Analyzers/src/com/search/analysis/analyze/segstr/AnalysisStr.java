package com.search.analysis.analyze.segstr;

import java.util.ArrayList;

import com.search.analysis.analyze.charascan.Charactor;
import com.search.analysis.analyze.charascan.ReScan;
import com.search.analysis.analyze.charascan.Scan;
import com.search.analysis.analyze.delicate.MaxAndReMax;
import com.search.analysis.analyze.delicate.MaxAndReMaxComp;
import com.search.analysis.analyze.delicate.MaxMatch;
import com.search.analysis.analyze.delicate.MaxMatchImprove;
import com.search.analysis.analyze.delicate.ReMaxMatch;
import com.search.analysis.analyze.delicate.ReMaxMatchImprove;
import com.search.analysis.analyze.delicate.Word;

public class AnalysisStr {
	
	/**
	 * Variables
	 */
	private Word word = null;
	private Charactor charactor = null;		// word 和 charactor 两者用一个就可以了
	
	private ArrayList<String> resultArr = null;
	
	/**
	 * Construct Function
	 */
	public AnalysisStr() {
		this.resultArr = new ArrayList<String>();
		
		// 可以根据需要选择想要的分词方法
		this.charactor = new Scan();
		// this.charactor = new ReScan();

		this.word = new MaxMatch();
		// this.word = new MaxMatchImprove();
		// this.word = new ReMaxMatch();
		// this.word = new ReMaxMatchImprove();
		// this.word = new MaxAndReMax();
		// this.word = new MaxAndReMaxComp();
	}
	
	/**
	 * Analysis Function
	 * 	sentence(String): the content will be analyzed
	 * 	 signal(integer): to determine using word(0) or charactor(1)
	 */
	public boolean AnalysisFun(String sentence, int signal){
		boolean tmp = true;
		
		try{
			if((this.word != null) && (0 == signal)) {
				this.word.setSentence(sentence);
				this.word.Division();

				this.resultArr.addAll(this.word.getArrWord());
			}else if((this.charactor != null) && (1 == signal)) {
				this.charactor.setSentence(sentence);
				this.charactor.Division();
				
				this.resultArr.addAll(this.charactor.getArrWord());
			}else{
				tmp = false;
			}
		}catch(Exception e) {
			e.printStackTrace();
			tmp = false;
		}
		
		return tmp;
	}
	

	/**
	 * Main Function for Test
	 */
	public static void main(String[] args) {
		// Just for Test

	}

	/**
	 * Set and Get Functions
	 */
	public Word getWord() {
		return word;
	}

	public void setWord(Word word) {
		this.word = word;
	}

	public Charactor getCharactor() {
		return charactor;
	}

	public void setCharactor(Charactor charactor) {
		this.charactor = charactor;
	}

	public ArrayList<String> getResultArr() {
		return resultArr;
	}

	public void setResultArr(ArrayList<String> resultArr) {
		this.resultArr = resultArr;
	}

}
