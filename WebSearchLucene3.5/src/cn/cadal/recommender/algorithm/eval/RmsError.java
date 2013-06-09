/*
 * Created on 2004-12-20
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.algorithm.eval;


class RmsError implements Error{
    private double rms = 0.0;
    private int accuNum = 0;
    public double accumulate(double error){
        rms += Math.pow(error, 2);
        accuNum ++;
        return rms;
    }
    public double getError(){
        return rms/accuNum;
    }
}
