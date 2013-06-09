/*
 * Created on 2004-12-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.cadal.recommender.input;

import cn.cadal.recommender.spi.Rating;

/**
 * @author zhangyin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class URIRatingComparator extends RatingComparator {
	
	public int compare(Object lo, Object ro) {
		
		Rating lemr = (Rating) lo;
		Rating remr = (Rating) ro;

		int lUserId = lemr.getUserId();
		int rUserId = remr.getUserId();

		int lItemId = lemr.getItemId();
		int rItemId = remr.getItemId();

		double lScore = lemr.getRatingScore();
		double rScore = remr.getRatingScore();

		double[] cmpArray = null;

		cmpArray = new double[] { rUserId, lUserId, rScore, lScore, rItemId, lItemId };

		return super.cmp(cmpArray, 0);
	}

}
