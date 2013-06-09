/*
 * Created on 2006-3-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.cadal.fulltextsearch.configure;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * @author lwm
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConfigureManager {
	private static Configure conf = null;
	public static void initConfigure(String confFile)throws IOException{
		conf = new Configure();
		/*Properties prop = new Properties();
		System.out.println(confFile);
        prop.load(new FileInputStream(confFile));
        conf.setIndexPath(prop.getProperty("indexPath"));
        conf.setTextRootPath(prop.getProperty("textRootPath"));
        */
		conf.setIndexPath ("D:\\text_index\\index");
		conf.setTextRootPath ("D:/text/");
	}
	
	public static Configure getConfigure() throws IOException{
		if (conf == null){
			initConfigure("");
		}
		return conf;
	}
}
