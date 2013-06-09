/*
 * Created on 2004-12-4
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.spi;

import java.io.Serializable;

/**
 * @author zhangyin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface ItemCollection extends Serializable{
    public void makeAllInstance(ItemFactory itemFactory);
    public void add(int idx, Item item);
    public int size();
    public Item get(int idx);
    public void printAll();
    public void traverse(String description, Visitor visitor);
}
