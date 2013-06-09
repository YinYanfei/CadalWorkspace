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
public interface UserCollection extends Serializable{
    public void makeAllInstance(UserFactory userFactory);
    public void add(int idx,User user);
    public int size();
    public User get(int idx);
    public void printAll();
    public void traverse(String description, Visitor visitor);
}
