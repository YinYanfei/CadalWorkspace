/*
 * Created on 2004-12-18
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.algorithm.eval;

import org.apache.log4j.Logger;

import cn.cadal.recommender.spi.CollaborativeFiltering;
import cn.cadal.recommender.spi.Item;
import cn.cadal.recommender.spi.ItemCollection;
import cn.cadal.recommender.spi.Rating;
import cn.cadal.recommender.spi.RatingCollection;
import cn.cadal.recommender.spi.User;
import cn.cadal.recommender.spi.UserCollection;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class LSMEval {

    private static Logger logger = Logger.getLogger(LSMEval.class);

    public double eval(CollaborativeFiltering lsm, UserCollection uC, ItemCollection iC,
            RatingCollection rC, Error error) {

        for (int ratingId = 0; ratingId < rC.size(); ratingId++) {
            Rating rating = rC.get(ratingId);
            
            User user = uC.get(rating.getUserId());
            if( null == user ){
                logger.error(" null user object");
            }
               
            Item item = iC.get(rating.getItemId());
            if( null == item ){
                logger.error(" null item object");
            }
            
            
            double realScore = rating.getRatingScore();
            double predScore = lsm.predict(user,item); 
            error.accumulate(predScore - realScore);
        }

        return error.getError();

    }
}
