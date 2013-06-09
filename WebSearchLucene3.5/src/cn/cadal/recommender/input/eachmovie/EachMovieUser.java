/*
 * Created on 2004-12-6
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.input.eachmovie;

import java.util.ArrayList;
import java.util.List;

import cn.cadal.recommender.spi.User;

/**
 * @author zhangyin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EachMovieUser implements User {
    int userId;
    int userAge;
  
    ArrayList ratingsIdx;// this user's rating data
    
    EachMovieUser(int uId,int age){
        userId=uId;
        userAge=age;
        ratingsIdx=new ArrayList();
    }

    public List getRatingIdxList(){
        return ratingsIdx;
    }

    
    public String toString(){
        return  " userId: "+userId+" userAge: "+userAge+" ratingsIdx: "+ratingsIdx;
    }

    
    public void addRatingIdx(int idx){
        ratingsIdx.add(new Integer(idx));
    }

    public int getId(){
        return getUserId();
    }
    
    /**
     * @return Returns the userId.
     */
    public int getUserId() {
        return userId;
    }
    /**
     * @param userId The userId to set.
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }
    /**
     * @return Returns the userName.
     */
    public int getUserAge() {
        return userAge;
    }
    
    /**
     * @param userName The userName to set.
     */
    public void setUserAge(int uAge) {
        userAge = uAge;
    }
    
    public static void main(String[] args) {
        
    }
}
