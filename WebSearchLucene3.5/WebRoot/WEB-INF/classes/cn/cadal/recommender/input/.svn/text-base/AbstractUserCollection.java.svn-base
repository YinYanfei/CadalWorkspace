/*
 * Created on 2004-12-7
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.input;

import org.apache.log4j.Logger;

import cn.cadal.recommender.spi.User;
import cn.cadal.recommender.spi.UserCollection;
import cn.cadal.recommender.spi.UserFactory;
import cn.cadal.recommender.spi.Visitor;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class AbstractUserCollection implements UserCollection {
    private static Logger logger = Logger
            .getLogger(AbstractUserCollection.class);

    private ListInputCollection userCollection = new ListInputCollection();

    public void add(int idx, User user) {
        userCollection.add(idx, user);
    }

    public void makeAllInstance(UserFactory userFactory) {
        userCollection.makeAllInstance(userFactory);
    }

    public int size() {
        return userCollection.size();
    }

    public User get(int idx) {
        return (User) userCollection.get(idx);
    }

    public void printAll() {
        System.out.println(" All User Instance:");
        userCollection.traverse(new Visitor() {
            public void performedAction(Object obj) {
                if (null == obj)
                    return;
                System.out.println((User) obj);
            }
        });

    }

    public void traverse(String description, Visitor visitor) {
        logger.info(description);
        userCollection.traverse(visitor);
    }

}
