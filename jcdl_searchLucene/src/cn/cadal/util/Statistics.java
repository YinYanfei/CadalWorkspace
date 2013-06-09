/*
 * Created on 2004-12-16
 *
 * Copyright (C) The Zhejiang University Libraries. All rights reserved.
 * 
 */
package cn.cadal.util;

/**
 * @author zhangyin
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class Statistics {
    
    public static double normPdf(double point, double mean, double variance){
        double temp = 0 ;
        try {
        temp = 1.0/(Math.sqrt(2*Math.PI*variance));
        temp = temp * Math.exp(-(Math.pow(point-mean,2)/(2*variance)));
        }catch ( ArithmeticException ae){
        	ae.printStackTrace();
        }
        return temp;
    }

}
