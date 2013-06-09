package cn.cadal.user;

public class MyCalligraphyPic {
	
	private int ruleId;
	
	private String calligraphyRuleName;
	
	private String calligraphyPath;

	public MyCalligraphyPic( int id, String name, String path) {
		ruleId = id;
		calligraphyPath = path;
		calligraphyRuleName = name;
	}

	public String getCalligraphyPath() {
		return calligraphyPath;
	}

	public void setCalligraphyPath(String calligraphyPath) {
		this.calligraphyPath = calligraphyPath;
	}

	public String getCalligraphyRuleName() {
		return calligraphyRuleName;
	}

	public void setCalligraphyRuleName(String calligraphyRuleName) {
		this.calligraphyRuleName = calligraphyRuleName;
	}

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

}
