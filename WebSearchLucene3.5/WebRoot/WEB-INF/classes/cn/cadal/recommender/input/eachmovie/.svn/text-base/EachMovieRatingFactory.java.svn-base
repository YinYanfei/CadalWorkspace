/*
 * Created on 2004-12-7
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.input.eachmovie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import cn.cadal.recommender.input.InputObject;
import cn.cadal.recommender.spi.RatingCollection;
import cn.cadal.recommender.spi.RatingFactory;
import cn.cadal.util.OptionConverter;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EachMovieRatingFactory implements RatingFactory {
    private static BufferedReader in = null;
    private int recordsSize=10000;

    public EachMovieRatingFactory(String fp) {
        try {
            in = new BufferedReader(new FileReader(fp));
        } catch (IOException e) {
            System.out.println("file:" + fp + " not found");
        }
    }

    private void mapping(String line) {

    }

    public InputObject makeNewInstance(int ratingIdx){
        return null;
    }
    
    private int ratingIdx=0;
    
    public InputObject makeNewInstance() {
        int uId, iId;
        float rScore, weight;
        String line;
        try {
            if ((line = in.readLine()) != null) {
                String[] values = line.split("\t");
                uId = OptionConverter.toInt(values[0]);
                iId = OptionConverter.toInt(values[1]);
                rScore = OptionConverter.toFloat(values[2]);
                weight = OptionConverter.toFloat(values[3]);
    
                return new EachMovieRating(ratingIdx++, uId, iId, rScore);
            }else{
                return null;
            }
        } catch (IOException e) {
            System.out.println("error reading file: " + in);
            return null;
        }
    }

    public static void main(String[] args) {
        String filePath = "/home/zhangyin/work-space/EachMovie/Vote.txt";
        RatingCollection rc = new EachMovieRatingCollection();
        EachMovieRatingFactory erf = new EachMovieRatingFactory(filePath);
    }
    /**
     * @return Returns the recordsSize.
     */
    public int getRecordsSize() {
        return recordsSize;
    }
    /**
     * @param recordsSize The recordsSize to set.
     */
    public void setRecordsSize(int recordsSize) {
        this.recordsSize = recordsSize;
    }
}