/*
 * Created on 2004-12-8
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.input.eachmovie;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cn.cadal.recommender.input.InputObject;
import cn.cadal.recommender.spi.ItemFactory;
import cn.cadal.util.OptionConverter;

/**
 * @author zhangyin
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EachMovieItemFactory implements ItemFactory {

    private BufferedReader in = null;

    int recordsSize = 1628;

    /**
     *  
     */
    public EachMovieItemFactory(String filePath) {
        try {
            in = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            System.out.println(filePath + " file not found");
        }
    }

    public InputObject makeNewInstance(int idx) {
        return (InputObject)null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see cn.cadal.recommendation.input.ItemFactory#makeNewItemInstance(int)
     */
    public InputObject makeNewInstance() {
        String line;
        String[] values;
        int iId;
        String iName, pr_Url;

        try {
            if ((line = in.readLine()) != null) {
                values = line.split("\t");
                iId = OptionConverter.toInt(values[0]);
                iName = values[1].trim();
                pr_Url = null;
                if (values.length > 2) {
                    if (values[2] != null)
                        pr_Url = values[2].trim();

                }
                return new EachMovieItem(iId, iName, pr_Url);
            } else {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
    }

    public void getMetaData() { //such as total number of records

    }

    public static void main(String[] args) {

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
