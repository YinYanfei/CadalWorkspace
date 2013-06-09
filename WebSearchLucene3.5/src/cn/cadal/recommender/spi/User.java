/*
 * Created on 2004-11-17
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package cn.cadal.recommender.spi;

import java.io.Serializable;
import java.util.List;

import cn.cadal.recommender.input.InputObject;


/**
 * @author zhangyin
 *
 * abstract User class
 * 
 */
public interface User extends InputObject,Serializable{
    public int getUserId();
    public void addRatingIdx(int idx);
    public List getRatingIdxList();
}
