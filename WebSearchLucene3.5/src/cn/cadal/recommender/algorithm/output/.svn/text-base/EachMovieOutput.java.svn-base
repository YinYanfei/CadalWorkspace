/*
 * Created on 2004-12-20
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.algorithm.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import cn.cadal.recommender.spi.Output;

/**
 * @author zhangyin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class EachMovieOutput implements Output {

    public void write ( Object obj , String fileName){
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    new FileOutputStream(fileName));
            out.writeObject( obj );
        } catch (IOException e) {
            e.printStackTrace();
        }    
    }
    
    public static void main(String[] args) {
    }
}
