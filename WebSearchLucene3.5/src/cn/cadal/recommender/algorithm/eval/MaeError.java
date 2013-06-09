/*
 * Created on 2004-12-20
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.recommender.algorithm.eval;


class MaeError implements Error{
    private double mae=0.0;
    private int accuNum=0;
    public double accumulate(double error){
        mae =+ Math.abs(error);
        accuNum ++ ;
        return mae;
    }
    public double getError(){
        return mae/accuNum;
    }
    
}
