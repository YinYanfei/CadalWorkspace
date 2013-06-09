/*
 * Created on 2004-11-17
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package cn.cadal.recommender.spi;

import java.io.Serializable;

import cn.cadal.recommender.input.InputObject;


/**
 * @author zhangyin
 *
 * Abstract class for Rating
 * 
 */
public interface Rating extends InputObject,Serializable{
    public int getRatingId();
    public int getUserId();
    public int getItemId();
    public double getRatingScore();
    public void addRatingIdxToUser(UserCollection userCollection);
    public void addRatingIdxToItem(ItemCollection itemCollection);
}
