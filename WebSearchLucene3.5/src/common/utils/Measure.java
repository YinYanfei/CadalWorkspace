package common.utils;

import java.util.ArrayList;

import org.apache.log4j.Logger;

public class Measure {
    private static final Logger LOG = Logger.getRootLogger ();

    public static double computeNorm (double[] vector) {
	double norm = 0 ;

	for (int idx = 0 ; idx < vector.length ; idx ++){
	    if (vector[idx] > 0){
		norm += Math.pow (vector[idx],2);
	    }
	}
	if (Double.isNaN (norm)){
	    throw new ArithmeticException ("error: norm is an NaN.");
	}
	/*	if ( norm == 0 ){
	    throw new ArithmeticException ("error: norm must not be zero.");
	    }*/
	norm = Math.sqrt (norm);
	if (Double.isNaN (norm)){
	    throw new ArithmeticException ("error: norm is an NaN.");
	}
	if (LOG.isDebugEnabled ()){
	    LOG.debug (" the value of norm is : "+norm);
	}
	return norm;
    }
    

    public static double computeNorm (ArrayList vector){
	//norm
	double norm = 0 ;
	for ( int i_idx = 0 ; i_idx < vector.size (); i_idx++ ){
	    FVPair p = (FVPair) vector.get (i_idx);
	    norm += Math.pow (p.getValue (), 2);
	}
	if(Double.isNaN (norm)){
	    throw new ArithmeticException ("error: norm is an NaN.");
	}
	/*	if ( norm == 0 ){
	    throw new ArithmeticException ("error: norm must not be zero.");
	    }*/
	norm = Math.sqrt (norm);
	if (Double.isNaN (norm)){
	    throw new ArithmeticException ("error : norm is an NaN.");
	}
	if (LOG.isDebugEnabled ()){
	    LOG.debug (" the value of norm is : "+norm);
	}
	return norm;
    }

    public static double innerproduct (double[] l, double[] r){
	assert l.length==r.length:"the lenth of two vector must be consistent";
	double innerp = 0;
	for (int idx = 0 ; idx < l.length; idx ++){
	    innerp+= l[idx]*r[idx];
	}
	if (Double.isNaN (innerp)){
	    throw new ArithmeticException ("error: inner product is an NaN.");
	}
	return innerp;
    }

    public static double innerproduct (ArrayList l, ArrayList r){
	
	double innerp = 0;
	int l_idx=0;
	int r_idx=0;
	 
	while (l_idx < l.size ()
	       && r_idx < r.size ()) {
	    FVPair l_pair = (FVPair) l.get (l_idx);
	    int l_fidx = l_pair.getIdx ();
	    FVPair r_pair = (FVPair) r.get (r_idx);
	    int r_fidx = r_pair.getIdx ();

	    if (l_fidx < r_fidx ){
		l_idx++;
	    }else if (l_fidx == r_fidx){
		double r_fvalue = r_pair.getValue ();
		double l_fvalue = l_pair.getValue ();
		innerp += r_fvalue*l_fvalue;
		l_idx++;
		r_idx++;
	    }else if (r_fidx < l_fidx){
		r_idx++;
	    }
	}
	return innerp;
    }
    
    public static double innerproduct (double[] l , ArrayList r){
	double innerp = 0;
	for (int idx =0 ; idx < r.size () ; idx++){
	    FVPair pair = (FVPair) r.get (idx);
	    int fidx = pair.getIdx ();
	    if (fidx >= l.length){
		throw new AssertionError ("the lenth of tow vector must be consistent");
	    }
	    double fvalue = pair.getValue ();
	    innerp += fvalue*l[idx];
	}
	if (Double.isNaN (innerp)){
	    throw new ArithmeticException ("error: inner product is an NaN");
	}
	return innerp;
    }
    
    public static double EuclidDistance (double [] l, ArrayList r){
	double distance = 0 ;
	int arrayidx = 0;
	if (LOG.isDebugEnabled ())
	    LOG.debug ("the size of right arraylist:"+r.size());

	double delta = 0;
	for (int idx = 0 ; idx < r.size() ; idx++){
	    
	    FVPair pair = (FVPair) r.get (idx);
	    int fidx = pair.getIdx ();
	    
	    while ( arrayidx < fidx ){
		delta = l[arrayidx++];
		distance+=Math.pow (delta, 2);
	    }

	    delta =  l[arrayidx++] - pair.getValue();
	    distance+=Math.pow (delta, 2);
	}
	for (; arrayidx < l.length ; arrayidx++){
	    delta = l[arrayidx];
	    distance =+ Math.pow (delta, 2);
	}
	return distance;
    }

}
