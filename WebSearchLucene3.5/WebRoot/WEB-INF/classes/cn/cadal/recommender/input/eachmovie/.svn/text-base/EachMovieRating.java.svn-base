/*
 * Created on 2004-12-6
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.input.eachmovie;

import org.apache.log4j.Logger;

import cn.cadal.recommender.spi.Item;
import cn.cadal.recommender.spi.ItemCollection;
import cn.cadal.recommender.spi.Rating;
import cn.cadal.recommender.spi.User;
import cn.cadal.recommender.spi.UserCollection;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EachMovieRating implements Rating {
    private static Logger logger = Logger.getLogger(EachMovieRating.class);

    private int ratingId;
 
    private int userId;

    private int itemId;

    private double ratingScore;

    //private float weight;
    //private String timeStamp;
    public EachMovieRating(int rId, int uId, int iId, double rScore) {
        ratingId = rId;
        userId = uId;
        itemId = iId;
        ratingScore = rScore;
    }
    
    public EachMovieRating(User user , Item item, double rScore){
    	ratingId = -1;
    	userId = user.getUserId();
    	itemId = item.getItemId();
    	ratingScore = rScore;
    }

    public void addRatingIdxToUser(UserCollection userCollection) {
        User user = userCollection.get(userId);
        user.addRatingIdx(ratingId);
        if (logger.isDebugEnabled())
            logger.debug(user);
    }

    public void addRatingIdxToItem(ItemCollection itemCollection) {
        Item item = itemCollection.get(itemId);
        item.addRatingIdx(ratingId);
        if (logger.isDebugEnabled())
            logger.debug(item);

    }

    public String toString() {
        return " ratingId::" + ratingId + " userId::" + userId + " itemId::"
                + itemId + " rScore::" + ratingScore;
    }

    public static void main(String[] args) {
    }

    public int getId() {
        return ratingId;
    }

    /**
     * @return Returns the itemId.
     */
    public int getItemId() {
        return itemId;
    }

    /**
     * @param itemId
     *            The itemId to set.
     */
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    /**
     * @return Returns the ratingId.
     */
    public int getRatingId() {
        return ratingId;
    }

    /**
     * @param ratingId
     *            The ratingId to set.
     */
    public void setRatingId(int ratingId) {
        this.ratingId = ratingId;
    }

    /**
     * @return Returns the ratingScore.
     */
    public double getRatingScore() {
        return ratingScore;
    }

    /**
     * @param ratingScore
     *            The ratingScore to set.
     */
    public void setRatingScore(float ratingScore) {
        this.ratingScore = ratingScore;
    }

    /**
     * @return Returns the userId.
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId
     *            The userId to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
}