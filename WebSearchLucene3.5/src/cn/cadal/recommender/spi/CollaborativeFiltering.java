/*
 * Created on 2004-12-18
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.spi;

import cn.cadal.recommender.algorithm.SortedItemMap;
import cn.cadal.recommender.input.RatingComparator;


/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public interface CollaborativeFiltering {
    
    //use trained model to predict user interest
    public double predict(User user, Item item);
    //Important: empty RatingCollection
    public void predict (User user, ItemCollection itemCollection, RatingCollection rC);
    
    //rank  a user's interest to every item
    //public RatingCollection rank(User user,ItemCollection itemCollection);
    public SortedItemMap rank(ItemCollection itemCollection,
			      RatingCollection predRC,
			      RatingComparator ratingComparator);

    //offline training, existed collections
    public void train(UserCollection uC, ItemCollection iC, RatingCollection rC);

    //online training, new rating,collections after modification
    public void onlineTrain(Rating rating, UserCollection uC,
            ItemCollection iC, RatingCollection rC);
    
    //write out trained model param
    public void write(Output output);
}
