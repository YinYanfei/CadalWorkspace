package cn.cadal.quicksearch;

import java.io.*;
import java.util.Properties;

public class QuickSearchConfig {
	
	private static Properties indexBuildConfig = null;
	
	static
	{
		indexBuildConfig = new Properties();
		
	    try
	    {
	    	InputStream ins = null;
            ins = ClassLoader.getSystemResourceAsStream("quicksearchConfig.conf");
	    	indexBuildConfig.load( ins );
	    	System.out.println( "quicksearchConfig.conf loaded.");
	    }
	    catch( IOException e )
	    {
	    	System.out.println( "Failed to load quicksearchConfig.conf, IOException happened: " + e.getMessage( ) );
	    }
	}
	
	public static String getTxtDir()
	{
		return indexBuildConfig.getProperty("txtDir");
	}
	
	public static String getIndexDir()
	{
		return indexBuildConfig.getProperty("indexDir");
	}
}

