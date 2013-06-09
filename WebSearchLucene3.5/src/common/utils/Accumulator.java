package common.utils;

public class Accumulator{
    private double accu;
    static final private String man = "ÈË¹¤±ê×¢";

    //copy constructor
    public Accumulator (Accumulator accu){
	this(accu.get ());
    }
    
    public Accumulator (){
	this (false);
    }

    public Accumulator (boolean manual){
	if (true == manual){
	    accu = Double.MAX_VALUE;
	}else if(false == manual){
	    accu = 0;
	}
    }

    public Accumulator (double init_value){
	accu = init_value;
    }

    public void accumulate (double value){
	accu += value;
    }

    public void set (double value){
	accu = value;
    }
    
    public double get (){
	return accu;
    }

    public String toString (){
	if (Double.MAX_VALUE == accu)
	    return man;
	else{
	    return new Double (accu).toString ();
	}
    }
}
