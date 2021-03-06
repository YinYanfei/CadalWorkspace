package cn.cadal.rec.tryJava;

public class PrioriConfiguration {
	public String sourceFile;
	public String destination;
	public int threHold;
	
	/**
	 * Construct functions
	 */
	public PrioriConfiguration() {
		this.sourceFile = "";
		this.destination = "";
		this.threHold = 15;
	}
	public PrioriConfiguration(String sourceFile, String destination, int threHold) {
		this.sourceFile = sourceFile;
		this.destination = destination;
		this.threHold = threHold;
	}
	public PrioriConfiguration(PrioriConfiguration pc) {
		this.sourceFile = pc.sourceFile;
		this.destination = pc.destination;
		this.threHold = pc.threHold;
	}
	
	/**
	 * Set properties of this object
	 */
	public void SetPrioriConfiguration(String sourceFile, String destination, int threHold) {
		this.sourceFile = sourceFile;
		this.destination = destination;
		this.threHold = threHold;		
	}
	
	/**
	 * Set and get function
	 */
	public String getSourceFile() {
		return sourceFile;
	}
	public String getDestination() {
		return destination;
	}
	public int getThreHold() {
		return threHold;
	}
	public void setSourceFile(String sourceFile) {
		this.sourceFile = sourceFile;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public void setThreHold(int threHold) {
		this.threHold = threHold;
	}
	
}
