package com.search.analysis.segment;

import com.search.analysis.util.CreateHashMap;

public class Segment {

	/**
	 * hashMap will be used to create,change,delete and so on about key-value map.
	 */
	protected CreateHashMap hashMap;
	
	/**
	 * addWord(String) : add key-value pair.
	 * @return:
	 * 	  true: success.
	 *   false: fail.
	 */
	protected boolean addWord(String word){
		boolean signal = false;
		
		try{
			if(this.hashMap.addAccordWord(word)){
				signal = true;
			}else{
				signal = false;
			}
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * addFile(String) : add all key-value pairs of file. 
	 * @return:
	 * 	  true: success.
	 *   false: fail.
	 */
	protected boolean addFile(String file){
		boolean signal = false;
		
		try{
			if(this.hashMap.addAccordFile(file)){
				signal = true;
			}else{
				signal = false;
			}
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * deleteWord(String) : delete key-value pair.
	 * @return:
	 * 	  true: success.
	 *   false: fail.
	 */
	protected boolean deleteWord(String word){
		boolean signal = false;
		
		try{
			if(this.hashMap.deleteAccordWord(word)){
				signal = true;
			}else{
				signal = false;
			}
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * deleteFile(String) : delete all key-value pairs of file.
	 * @return:
	 * 	  true: success.
	 *   false: fail.
	 */
	protected boolean deleteFile(String file){
		boolean signal = false;
		
		try{
			if(this.hashMap.deleteAccordFile(file)){
				signal = true;
			}else{
				signal = false;
			}
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * restore(String) : restore all of hash-map into file.
	 * @return:
	 * 	  true: find one.
	 *   false: can't find.
	 */	
	protected boolean restore(String file, String reuse){
		boolean signal = false;
		
		try{
			if(this.hashMap.restore(file, reuse)){
				signal = true;
			}else{
				signal = false;
			}
		}catch(Exception e){
			signal = false;
			e.printStackTrace();
		}
		
		return signal;
	}
	
	/**
	 * Just to be used testing.
	 * @param args
	 */
	public static void main(String[] args) {
		// TEST
	}
	
	/**
	 * Set and Get Functions
	 */
	public void setHashMap(CreateHashMap hashMap) {
		this.hashMap = hashMap;
	}
	
	public CreateHashMap getHashMap(){
		return this.hashMap;
	}
	
}
