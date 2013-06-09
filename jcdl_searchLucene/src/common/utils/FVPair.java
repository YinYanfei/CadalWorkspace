package common.utils;

public final class FVPair{
    int fidx;
    double value;
    public FVPair (int idx, double value){
	fidx = idx;
	this.value = value;
    }
    public int getIdx (){
	return fidx;
    }
    public double getValue (){
	return value;
    }
    public void setValue (double value){
	this.value = value;
    }
}
