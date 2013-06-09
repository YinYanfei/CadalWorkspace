/*
 * Created on 2004-12-7
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.input.eachmovie;

import org.apache.log4j.Logger;

import cn.cadal.recommender.input.AbstractRatingCollection;
import cn.cadal.recommender.spi.Item;
import cn.cadal.recommender.spi.ItemCollection;
import cn.cadal.recommender.spi.Rating;
import cn.cadal.recommender.spi.RatingCollection;
import cn.cadal.recommender.spi.RatingFactory;
import cn.cadal.recommender.spi.User;
import cn.cadal.recommender.spi.UserCollection;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EachMovieRatingCollection extends AbstractRatingCollection {

	private static Logger logger = Logger
			.getLogger(EachMovieRatingCollection.class.getName());

	public RatingCollection subCollection(User user,
			ItemCollection itemCollection) {
		RatingCollection subRC = new EachMovieRatingCollection();
		Rating rating = null;
		Item item = null;
		
		int rIdx  = 0;
		for (int idx = 0; idx < itemCollection.size(); idx++) {
			item = itemCollection.get(idx);
			rating = this.get(user, item);
			//debug
			System.out.println("Rating:"+rating);
			if (null != rating)
				subRC.add(rIdx++, rating);
		}
		return subRC;
	}

	public void addRatingIdxToUserAndItem(UserCollection uC, ItemCollection iC) {
		for (int idx = 0; idx < size(); idx++) {
			Rating emr = get(idx);
			if (logger.isDebugEnabled()) {
				logger.debug(" idx:" + idx + " rating: " + emr);
			}
			emr.addRatingIdxToUser(uC);
			emr.addRatingIdxToItem(iC);
		}
	}

	public static void main(String[] args) {
		String filePath = "/home/zhangyin/work-space/EachMovie/Vote.txt";
		RatingCollection rc = new EachMovieRatingCollection();
		RatingFactory rf = new EachMovieRatingFactory(filePath);
		rc.makeAllInstance(rf);
	}
}