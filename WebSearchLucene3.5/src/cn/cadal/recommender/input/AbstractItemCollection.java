/*
 * Created on 2004-12-8
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.input;

import org.apache.log4j.Logger;

import cn.cadal.recommender.spi.Item;
import cn.cadal.recommender.spi.ItemCollection;
import cn.cadal.recommender.spi.ItemFactory;
import cn.cadal.recommender.spi.Visitor;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public abstract class AbstractItemCollection implements ItemCollection {
    private static Logger logger = Logger
            .getLogger(AbstractItemCollection.class);

    private ListInputCollection itemCollection = new ListInputCollection();

    public void makeAllInstance(ItemFactory itemFactory) {
        itemCollection.makeAllInstance(itemFactory);
    }

    public void add(int idx, Item item) {
        itemCollection.add(idx, item);
    }

    public int size() {
        return itemCollection.size();
    }

    public Item get(int idx) {
        return (Item) itemCollection.get(idx);
    }

    public void printAll() {
        traverse("All Item Instances:", new Visitor() {
            public void performedAction(Object obj) {
                if (null == obj)
                    return;
                System.out.println((Item) obj);
            }
        });
    }

    public void traverse(String description, Visitor visitor) {
        logger.info(description);
        itemCollection.traverse(visitor);
    }
}
