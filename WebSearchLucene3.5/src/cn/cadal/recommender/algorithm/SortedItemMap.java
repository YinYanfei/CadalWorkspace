/*
 * Created on 2004-12-20
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.algorithm;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import cn.cadal.recommender.input.RatingComparator;
import cn.cadal.recommender.spi.Item;
import cn.cadal.recommender.spi.Rating;

/**
 * @author zhangyin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SortedItemMap {
    
    private TreeMap sIM = null;
	//new TreeMap(new URIRatingComparator());
     
    public SortedItemMap (RatingComparator rc){
	sIM = new TreeMap (rc);
    }
    
    public Map getSortedMap(){
    	return sIM;
    }
    
    public void put(Rating rating, Item item){
        sIM.put(rating, item);
    }
    
    public SortedItemMap subMap(Double fromD,Double toD){
        return (SortedItemMap)sIM.subMap(fromD,toD);
        
    }
    
    public SortedItemMap headMap(Double d){
        return (SortedItemMap)sIM.headMap(d);
    }
    
    public Item get(Rating key){
        return (Item)get(key);
    }
    
    public void putAll( SortedItemMap existedMap){
    	sIM.putAll(existedMap.getSortedMap());
    }
    
    public int size(){
        return sIM.size();
    }
    
    public String toString(){
    	StringBuffer sb = new StringBuffer();
    	Iterator mapIter = sIM.entrySet().iterator();
    	while ( mapIter.hasNext()){
    		Map.Entry entry = (Map.Entry) mapIter.next();
    		sb.append(entry.getKey());
    		sb.append('=');
    		sb.append(entry.getValue());
    		sb.append('\n');
    	}
    	
        return sb.toString();
    }
        

}
