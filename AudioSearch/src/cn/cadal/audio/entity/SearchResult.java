package cn.cadal.audio.entity;

public class SearchResult {
	private String AutioNO;
	private String Title;
	private String Creator;
	private String Subject;
	private String Publisher;
	
	/**
	 * get and set function
	 */
	public String getAutioNO() {
		return AutioNO;
	}
	public void setAutioNO(String autioNO) {
		AutioNO = autioNO;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getCreator() {
		return Creator;
	}
	public void setCreator(String creator) {
		Creator = creator;
	}
	public String getSubject() {
		return Subject;
	}
	public void setSubject(String subject) {
		Subject = subject;
	}
	public void setPublisher(String publisher) {
		Publisher = publisher;
	}
	public String getPublisher() {
		return Publisher;
	}

}
