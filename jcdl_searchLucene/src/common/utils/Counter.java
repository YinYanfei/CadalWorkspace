package common.utils;

import java.io.Serializable;

public class Counter implements Serializable, Comparable{
    private int count;
    private boolean isChanged;
    
    public Counter (){
	count = 0;
	isChanged = false;
    }
    
    public String toString (){
	return new Double (count).toString ();
    }
    
    public Counter( int init_value){
	count = init_value;
	isChanged = false;
    }
    
    public void set (int value){
	count = value;
    }

    public int get (){
	return count;
    }

    public void increment (){
	count=count+1;
	isChanged = true;
    }

    public void decrement (){
	count=count-1;
	isChanged = false;
    }

    public boolean isChanged (){
	return isChanged;
    }

    public int compareTo (Object obj){
	Counter right = (Counter)obj;
	int le = get ();
	int ri = right.get ();
	return le == ri? 0 : le > ri?-1:1;
    }
}
