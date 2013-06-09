/*
 * Created on 2006-3-1
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.cadal.fulltextsearch.cache;

import java.util.ArrayList;

/**
 * @author lwm
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SearchResultCache {
	public int cacheStart = 0;            //cache¿ªÊ¼Ò³ºÅ
	public int cacheSize = 10;            //»º´æ10Ò³
	public int hitSize = 0;
	public String q = "";
	public ArrayList documents = new ArrayList(0);
}
