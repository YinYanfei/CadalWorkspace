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
import cn.cadal.recommender.spi.UserFactory;
import cn.cadal.util.OptionConverter;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EachMovieUserFactory implements UserFactory {
    private static BufferedReader in = null;

    private int recordsSize = 72915;

    public EachMovieUserFactory(String fp) {
        try {
            in = new BufferedReader(new FileReader(fp));
        } catch (IOException e) {
            System.out.println("file:" + fp + " not found");
        }
    }

    private void mapping(String line) {
        //uId = OptionConverter.toInt(values[0]);
    }

    public InputObject makeNewInstance(int idx) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.cadal.recommendation.input.UserFactory#makeNewUserInstance(int)
     */
    private int count = 0;

    public InputObject makeNewInstance() {
        String line;
        int uId, uAge;
        try {
            if ((line = in.readLine()) != null) {
                String[] values = line.split("\t");
                uId = OptionConverter.toInt(values[0]);
                if (values.length > 1) {
                    uAge = OptionConverter.toInt(values[1]);
                } else {
                    uAge = 0;
                }
                //System.out.println((count++) + ":" + uId + "::" + uAge);
                return new EachMovieUser(uId, uAge);
            } else {
                return null;
            }
        } catch (IOException e) {
            System.out.println("error reading file: " + in);
        }
        return null;
    }

    /**
     * @return Returns the recordsSize.
     */
    public int getRecordsSize() {
        return recordsSize;
    }

    /**
     * @param recordsSize
     *            The recordsSize to set.
     */
    public void setRecordsSize(int recordsSize) {
        this.recordsSize = recordsSize;
    }
}
