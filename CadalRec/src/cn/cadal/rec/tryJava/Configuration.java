package cn.cadal.rec.tryJava;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class Configuration {
	private String CONFIGURATIONFILE = "priori.properties";
	public PrioriConfiguration prioriConf = null;
	
	/**
	 * Construct Functions
	 */
	public Configuration() {
		this.prioriConf = new PrioriConfiguration();
	}
	public Configuration(String confFile) {
		this.CONFIGURATIONFILE = confFile;
		this.prioriConf = new PrioriConfiguration();
	}
	
	/**
	 * Read and set properties
	 */
	public PrioriConfiguration ReadPropertyFile() {
		Properties props = new Properties();
		File propFile = new File(this.CONFIGURATIONFILE);
		
		if (propFile.exists()) {
			FileReader in;
			try {
				in = new FileReader(propFile);
				props.load(in);
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		String sourceFile = props.getProperty("sourceFile","");
		String destination = props.getProperty("destination", "");
		int threHold = Integer.parseInt(props.getProperty("threHold", "15"));
		
		this.prioriConf.SetPrioriConfiguration(sourceFile, destination, threHold);
		
		return this.prioriConf;
	}
	
	/**
	 * Just a Test
	 * @param args
	 */
	public static void main(String[] args){
		PrioriConfiguration pc = (new Configuration()).ReadPropertyFile();		// The way to get configurations from properties
		
		System.out.println("----------------");
		System.out.println(pc.getSourceFile());
		System.out.println(pc.getDestination());
		System.out.println(pc.getThreHold());
		System.out.println("----------------");	
	}
	
}
