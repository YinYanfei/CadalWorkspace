/*
 * Created on 2004-12-4
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.spi;

import java.io.Serializable;
import java.util.Comparator;

/**
 * @author zhangyin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface RatingCollection extends Serializable{
    public void makeAllInstance(RatingFactory ratingFactory);
    //public void addRatingIdxToUserAndItem(UserCollection uC,ItemCollection iC);
    public void add(int idx,Rating rating);
    public int size();
    public Rating get(int idx);
    public Rating get(User user, Item item);
    public Rating get(User user, Item item,Comparator comparator);
    public void printAll();
    public void traverse(String description, Visitor visitor);
    public void sort(Comparator comparator);
    public RatingCollection subCollection(User user,
			ItemCollection itemCollection);
}
