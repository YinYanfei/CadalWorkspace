/*
 * Created on 2004-12-8
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.input.eachmovie;

import cn.cadal.recommender.input.AbstractItemCollection;
import cn.cadal.recommender.spi.ItemCollection;
import cn.cadal.recommender.spi.ItemFactory;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EachMovieItemCollection extends AbstractItemCollection{

    public static void main(String[] args) {
        String fp = "/home/zhangyin/work-space/EachMovie/Movie.txt";
        ItemFactory iF = new EachMovieItemFactory(fp);
        ItemCollection iC = new EachMovieItemCollection();
        iC.makeAllInstance(iF);
    }
}
