/*
 * Created on 2004-12-9
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.input;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;

import cn.cadal.recommender.spi.Visitor;



/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ListInputCollection implements InputCollection{

    private static Logger logger = Logger.getLogger(ListInputCollection.class);
    private List collection = new ArrayList();
    
    public void traverse(Visitor visitor){
        ListIterator listIter = collection.listIterator();
        while(listIter.hasNext()){
            visitor.performedAction(listIter.next());
        }
    }
    /*
     * (non-Javadoc)
     * 
     * @see cn.cadal.recommendation.input.Collection#makeAllInstance(cn.cadal.recommendation.input.Factory)
     */
    public void makeAllInstance(InputFactory factory) {

        int size = factory.getRecordsSize();
        int curIdx = 0;
        int nextIdx = 0;
        for (int idx = 0; idx < size; idx++) {
            InputObject obj = factory.makeNewInstance();
            if (obj != null) {
		//TODO: refactoring to support String type Idx
                nextIdx = obj.getId();
                while (curIdx < nextIdx) {
                    add(curIdx, null);
                    curIdx++;
                }
            }
            add(curIdx, obj);// here curIdx == nextIdx
            curIdx++;
        }
        if( logger.isDebugEnabled())
            logger.debug("nextIdx:: "+nextIdx);	
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.cadal.recommendation.input.Collection#add(int,
     *      cn.cadal.recommendation.input.Object)
     */
    public void add(int idx, InputObject obj) {
        collection.add(idx,obj);
    }
    
    public int size(){
        return collection.size();
    }
    
    public InputObject get (int idx){
        return (InputObject)collection.get(idx);
    }
    
    public List getCollection(){
    	return collection; 
    }
    
}
