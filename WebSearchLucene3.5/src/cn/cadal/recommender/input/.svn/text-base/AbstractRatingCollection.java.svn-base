/*
 * Created on 2004-12-7
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.input;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

import cn.cadal.recommender.input.eachmovie.EachMovieRating;
import cn.cadal.recommender.spi.Item;
import cn.cadal.recommender.spi.ItemCollection;
import cn.cadal.recommender.spi.Rating;
import cn.cadal.recommender.spi.RatingCollection;
import cn.cadal.recommender.spi.RatingFactory;
import cn.cadal.recommender.spi.User;
import cn.cadal.recommender.spi.Visitor;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class AbstractRatingCollection implements RatingCollection {
	private static Logger logger = Logger
			.getLogger(AbstractRatingCollection.class);

	protected ListInputCollection ratingCollection = new ListInputCollection();

	private int sortedFlag = 0;

	private int sortType = RatingComparator.UIR;

	public void makeAllInstance(RatingFactory ratingFactory) {
		ratingCollection.makeAllInstance(ratingFactory);
	}

	public void add(int idx, Rating rating) {
		ratingCollection.add(idx, rating);
	}

	public int size() {
		return ratingCollection.size();
	}

	public Rating get(int idx) {
		return (Rating) ratingCollection.get(idx);
	}

	public abstract RatingCollection subCollection(User user,
			ItemCollection itemCollection);

	public void sort(Comparator comparator) {

		List collection = ratingCollection.getCollection();

		if (0 == sortedFlag) {
			if (URIRatingComparator.class.isInstance(comparator)) {
				Collections.sort(collection, comparator);
				sortType = RatingComparator.URI;
			}else if (UIRRatingComparator.class.isInstance(comparator)) {
				Collections.sort(collection, comparator);
				sortType = RatingComparator.UIR;
			}else {
				//throw new Co();   // FIXME: design my exception
			}
			
			sortedFlag = 1;
		}
	}

	public Rating get(User user, Item item) {

		return ((Rating) get(user, item, (new UIRRatingComparator())));
	}

	public Rating get(User user, Item item, Comparator comparator) {
		
		if (null == user) return null;
		if (null == item) return null;
		Rating rating = new EachMovieRating(0, user.getUserId(), item
				.getItemId(), 0.0);

		sort(comparator);

		int idx = Collections.binarySearch(ratingCollection.getCollection(),
				rating, comparator);
		if (idx >= 0)
			return ((Rating) ratingCollection.get(idx));
		else
			return null;
	}

	private class AllVisitor implements Visitor {
		public void performedAction(Object obj) {
			System.out.println((Rating) obj);
		}
	}

	public void printAll() {
		traverse("All Rating Instance", new AllVisitor());
	}

	public void traverse(String description, Visitor visitor) {
		logger.info(description);
		ratingCollection.traverse(visitor);
	}

}
