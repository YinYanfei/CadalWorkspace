/*
 * Created on 2004-11-22
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package cn.cadal.recommender.input.eachmovie;

import java.util.ArrayList;
import java.util.List;

import cn.cadal.recommender.spi.Item;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EachMovieItem implements Item {

    int itemId;

    String itemName;

    String prUrl;

    ArrayList ratingsIdx;//this item's rating data

    EachMovieItem(int iId, String iName, String pr_Url) {
        itemId = iId;
        itemName = iName;
        prUrl = pr_Url;

        ratingsIdx = new ArrayList();
    }
    
    public List getRatingIdxList(){
        return ratingsIdx;
    }

    public String toString() {
        return " itemId: " + itemId + " itemName:" + itemName + // prUrl: "+ prUrl+ 
          " ratingsIdx: " + ratingsIdx;
    }

    public void addRatingIdx(int ratingIdx) {
        ratingsIdx.add(new Integer(ratingIdx));
    }

    public int getId() {
        return getItemId();
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
     * @return Returns the itemName.
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * @param itemName
     *            The itemName to set.
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * @return Returns the prUrl.
     */
    public String getPrUrl() {
        return prUrl;
    }

    /**
     * @param prUrl
     *            The prUrl to set.
     */
    public void setPrUrl(String prUrl) {
        this.prUrl = prUrl;
    }

    /**
     * @return Returns the ratingsIdx.
     */
    public List getRatingsIdx() {
        return ratingsIdx;
    }

    public static void main(String[] args) {
    }
}
