package cn.cadal.entity;

public class MyImage {
	
	private String rulePath;
	
	private int ruleId;

	public int getRuleId() {
		return ruleId;
	}

	public void setRuleId(int ruleId) {
		this.ruleId = ruleId;
	}

	public String getRulePath() {
		return rulePath;
	}

	public void setRulePath(String ruleName) {
		this.rulePath = ruleName;
	}
	
	public MyImage (){
		
	}
	
	public MyImage (int id, String path) {
		this.ruleId = id;
		this.rulePath = path;
	}

}

