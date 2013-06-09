package common.utils;

import java.util.Map;

public class MPair implements Comparable{
    //Map.Entry mapping;
    String word;
    Counter counter;

    public MPair (Map.Entry pair){
	this.word = (String) pair.getKey ();
	this.counter = (Counter) pair.getValue ();
    }

    public MPair (String word, Counter counter){
	this.word = word;
	this.counter = counter;
    }
    public String toString (){
	
	return ("word :"+word+" :count: "+ counter.get ());
    }
    
    public String getKey (){
	return word;
    }

    public Counter getValue (){
	return counter;
    }
	
    public int compareTo (Object obj){
	MPair right = (MPair)obj;
	Counter le =  getValue ();
	Counter ri =  right.getValue ();
	return le.compareTo (ri);
    }
	
}
    