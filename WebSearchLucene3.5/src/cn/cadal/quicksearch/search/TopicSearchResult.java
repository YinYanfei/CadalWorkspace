package cn.cadal.quicksearch.search;

public class TopicSearchResult {

	private int ID;
	private String queryWord;
	private String times;
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getQueryWord() {
		return queryWord;
	}
	public void setQueryWord(String queryWord) {
		this.queryWord = queryWord;
	}
	public void setTimes(String times) {
		this.times = times;
	}
	public String getTimes() {
		return times;
	}
	
	
}
