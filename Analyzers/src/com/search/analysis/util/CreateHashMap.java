package com.search.analysis.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Iterator;

/**
 * CreateHashMap will create a hash-map, which is used to store
 * the dictionary before segment strings.
 * @author  Yanfei
 * @version 1.0
 */
public class CreateHashMap {
	/**
	 * @param args
	 *      word: store the first value of a line in file;
	 *   integer: store the second value of a line in file;
	 * curNumber: this object store current max number of integer,
	 *            will used when add variables;
	 *  fileName: URL and name of file.
	 *       map: used to map word and integer and efficiently searching.
	 */
	private String word;
	private IntegerMy integer;
	private ManageNumber curNumber;
	private String fileName;
	private String reuseFile;
	private HashMap<String, IntegerMy> map;
	
	/**
	 * Construct function, to create and fill variable 'map'.
	 * @param args
	 */
	public CreateHashMap(String fileName, String reuseFile){
		// Initialize variables
		curNumber = new ManageNumber();
		this.fileName = fileName;
		this.reuseFile = reuseFile;
		map = new HashMap<String, IntegerMy>();
		
		// Read File
		File file = new File(this.fileName);
		
		BufferedReader reader = null;
		try{
			FileReader fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);
			
			String strLine = null;
			int lineNum = 0;
			int lineNumTmp = 0;
			
			while((strLine = reader.readLine()) != null){
				// deal with one line
				// System.out.println(strLine);			// ----------------------
				word = new String(strLine.substring(0, strLine.indexOf(" ")));
				lineNumTmp = Integer.parseInt((strLine.substring(strLine.indexOf(" ") + 1)));
				integer = new IntegerMy(lineNumTmp);
				map.put(word, integer);
				
				// Get max of lineNum
				if(lineNum < lineNumTmp){
					lineNum = lineNumTmp;
				}
			}
			
			this.curNumber.setCurInteger(lineNum);
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			// Close File Stream
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		// Read reuseInteger from file
		if(!readReuseInt()) {
			System.out.println("Create Failed!");
			System.exit(1);
		}
	}
	
	/**
	 * Read curNumber.reuseInteger from file, which is placed in a default URL.
	 * @return 
	 * 	 true: success.
	 *  false: fail.
	 */
	private boolean readReuseInt() {
		boolean signal = false;
		
		// Get curNumber.reuseInteger
		File fileReuse = new File(this.getReuseFile());
		
		BufferedReader readerReuse = null;
		try{
			FileReader fileReaderReuse = new FileReader(fileReuse);
			readerReuse = new BufferedReader(fileReaderReuse);
			
			String strLine = null;
			
			while((strLine = readerReuse.readLine()) != null){
				// deal with one line
				curNumber.getReuseInteger().add(new IntegerMy(Integer.parseInt(strLine)));
			}
			
			signal= true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}finally{
			// Close File Stream
			if(readerReuse != null){
				try{
					readerReuse.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		return signal;
	}
	
	/**
	 * Restore all values of 'map' into file. 
	 * @return 
	 * 	 true: success.
	 *  false: fail.
	 */
	public boolean restore(){
		boolean tmpReturn = false;
		
		// Write into file
		File file = new File(this.fileName);
		FileWriter fileWriter = null;
		
		try{
			fileWriter = new FileWriter(file);
			// read and store all content of 'map' into file.			
			// A good way to deal with this problem: from Zhoufen
			for(String s:map.keySet()) {
				String strLine = "";
				
				strLine += s;
				strLine += " ";
				strLine += String.valueOf(map.get(s).getNumber());
				strLine += "\r\n";
				
				fileWriter.write(strLine);
			}
			// success write into file and change the value of 'tmpReturn'
			tmpReturn = true;
		}catch(Exception e){
			e.printStackTrace();

		}finally{
			if(fileWriter != null) {
				try{
					fileWriter.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		// Restore ReuseInt
		if(!(restoreReuseInt())){
			System.out.println("Restore Failed!");
			System.exit(1);
		}
		
		return tmpReturn;
	}
	
	/**
	 * Restore all values of 'map' into file. 
	 * @return 
	 * 	 true: success.
	 *  false: fail.
	 */
	public boolean restore(String fileName, String reuseFileName){
		boolean tmpReturn = false;
		
		// Write into file
		File file = new File(fileName);
		FileWriter fileWriter = null;
		
		try{
			fileWriter = new FileWriter(file);
			// read and store all content of 'map' into file.			
			// A good way to deal with this problem: from Zhoufen
			for(String s:map.keySet()) {
				String strLine = "";
				
				strLine += s;
				strLine += " ";
				strLine += String.valueOf(map.get(s).getNumber());
				strLine += "\r\n";
				
				fileWriter.write(strLine);
			}
			// success write into file and change the value of 'tmpReturn'
			tmpReturn = true;
		}catch(Exception e){
			e.printStackTrace();

		}finally{
			if(fileWriter != null) {
				try{
					fileWriter.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		// Restore ReuseInt
		if(!(restoreReuseInt(reuseFileName))){
			System.out.println("Restore Failed!");
			System.exit(1);
		}

		return tmpReturn;
	}
	/**
	 * This function is used to restore the curNumber.reuseInteger values.
	 * @return
	 *  true: success.
	 * false: fail.
	 */
	private boolean restoreReuseInt(){
		boolean signal = false;

		// Write into file
		File file = new File(this.getReuseFile());
		FileWriter fileWriter = null;
		
		try{
			fileWriter = new FileWriter(file);
			// read and store all content of 'curNumber.reuseInteger' into file.			

			Iterator<IntegerMy> iter = curNumber.getReuseInteger().iterator();
			while(iter.hasNext()){
				fileWriter.write(String.valueOf(iter.next().getNumber()) + "\r\n");
			}
			
			// success write into file and change the value of 'tmpReturn'
			signal = true;
		}catch(Exception e){
			e.printStackTrace();

		}finally{
			if(fileWriter != null) {
				try{
					fileWriter.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		return signal;
	}

	/**
	 * This function is used to restore the curNumber.reuseInteger values,
	 * this time the file is a parameter.
	 * @return
	 *  true: success.
	 * false: fail.
	 */
	private boolean restoreReuseInt(String reuseFileName){
		boolean signal = false;

		// Write into file
		File file = new File(reuseFileName);
		FileWriter fileWriter = null;
		
		try{
			fileWriter = new FileWriter(file);
			// read and store all content of 'curNumber.reuseInteger' into file.			

			Iterator<IntegerMy> iter = curNumber.getReuseInteger().iterator();
			while(iter.hasNext()){
				fileWriter.write(String.valueOf(iter.next().getNumber()) + "\r\n");
			}
			
			// success write into file and change the value of 'tmpReturn'
			signal = true;
		}catch(Exception e){
			e.printStackTrace();

		}finally{
			if(fileWriter != null) {
				try{
					fileWriter.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		return signal;
	}
	
	/**
	 * Judge whether a word in map or not.
	 * @return 
	 * 	 true: have one.
	 *  false: not have.
	 * @param  args
	 */
	public boolean search(String word){
		boolean signal = false;
		IntegerMy tmpInt = map.get(word);
		
		if(tmpInt != null){
			signal = true;
		}else{
			signal = false;
		}
		
		return signal;
	}

	/**
	 * Add values into map.
	 * @param 
	 *  word: the first value of map.
	 * @return
	 *   true: success.
	 *  false: failed.
	 */
	public boolean addAccordWord(String word) {
		boolean signal = false;
		int numTmp = curNumber.getCurInteger();
		try{
			if(!(map.containsKey(word))){
				int tmpInt = this.curNumber.acquireNumber();
				IntegerMy integerMy = null;
				if(tmpInt == -1) {
					integerMy = new IntegerMy(++numTmp);
				}else{
					integerMy = new IntegerMy(tmpInt);
				}
				map.put(word, integerMy);
				
				signal = true;
			}else{
				signal = false;
			}
			
			this.curNumber.setCurInteger(numTmp);
		}catch(Exception e){
			signal = false;
		}
		
		return signal;
	}
	
	/**
	 * Add values into map.
	 * @param 
	 *  fileName: the name of file will be added into map
	 * 	          like "C:\\URL\\filename.txt".
	 * @return
	 *   true: success.
	 *  false: failed.
	 */
	public boolean addAccordFile(String fileNameTmp) {
		boolean signal = false;
		
		// Read File
		File file = new File(fileNameTmp);
		BufferedReader reader = null;
		try{
			FileReader fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);
			
			String strLine = null;
			int numTmp = curNumber.getCurInteger();
			int numReuseTmp = 0;
			IntegerMy integerMyTmp = null;
			while((strLine = reader.readLine()) != null){
				// deal with one line
				if(!(map.containsKey(strLine))){
					numReuseTmp = curNumber.acquireNumber();
					
					if(numReuseTmp == -1) {
						integerMyTmp = new IntegerMy(++numTmp);
					}else{
						integerMyTmp = new IntegerMy(numReuseTmp);
					}
					
					map.put(strLine, integerMyTmp);
				}
			}
			
			curNumber.setCurInteger(numTmp);
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}finally{
			// Close File Stream
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		return signal;
	}
	
	/**
	 * Delete one value from map.
	 * @param 
	 *  word: the first value of map.
	 * @return
	 *   true: success.
	 *  false: failed.
	 */
	public boolean deleteAccordWord(String word){
		boolean signal = false;
		
		try{
			IntegerMy integerTmp = map.remove(word);
			if(integerTmp != null 
			   && curNumber.add(integerTmp.getNumber())){
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
	 * Delete all values of a file from map.
	 * @param 
	 *  fileName: the name of file will be deleted from map
	 * 	          like "C:\\URL\\filename.txt".
	 * @return
	 *   true: success.
	 *  false: failed.
	 */
	public boolean deleteAccordFile(String fileNameTmp){
		boolean signal = false;

		// Read File
		File file = new File(fileNameTmp);
		BufferedReader reader = null;
		try{
			FileReader fileReader = new FileReader(file);
			reader = new BufferedReader(fileReader);
			
			String strLine = null;
			IntegerMy integerMyTmp = null;
			while((strLine = reader.readLine()) != null){
				// deal with one line
				integerMyTmp = map.remove(strLine);
				if(integerMyTmp != null
				   && curNumber.add(integerMyTmp.getNumber())){
					signal = true;
				}else{
					signal = false;
				}
			}
			
			signal = true;
		}catch(Exception e) {
			signal = false;
			e.printStackTrace();
		}finally{
			// Close File Stream
			if(reader != null){
				try{
					reader.close();
				}catch(Exception e1){
					e1.printStackTrace();
				}
			}
		}
		
		return signal;
	}	
	
	/**
	 * Print all keys and values of hash-map.
	 */
	private void printAll(){
		//
		System.out.println("-=-=-=-=-=-=-=Print=-=-=-=-=-=-=-");
		//
		
		for(String s:map.keySet()) {			
			System.out.println(s + "  " + String.valueOf(map.get(s).getNumber()));
			s = null;
		}
		
		System.out.println("CurNumber: " + curNumber.getCurInteger());
		
		System.out.println("CurNumber, reuseInteger: ");
		for(int i = 0; i < curNumber.getReuseInteger().size(); i++){
			System.out.println(curNumber.getReuseInteger().get(i).getNumber());
		}
	}
		
	/**
	 * Main function, only used test.
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("-------------------------");
		System.out.println("Test 1: Create");
		CreateHashMap hashMap = new CreateHashMap("E:\\IntervalDict.txt", "E:\\reuse.txt");		
		
		hashMap.printAll();
/*		
		System.out.println("-------------------------");
		System.out.println("Test 2: Search");
		System.out.println("国家: " + hashMap.search("国家"));
		System.out.println("中国: " + hashMap.search("中国"));
		System.out.println("新华社：" + hashMap.search("新华社"));
		System.out.println("这个：" + hashMap.search("这个"));
		System.out.println("国：" + hashMap.search("国"));
		
		System.out.println("-------------------------");
		System.out.println("Test 3: Delete According Word");
		System.out.println("==========1");
		hashMap.deleteAccordWord("中国");
		hashMap.printAll();
		System.out.println("==========2");
		hashMap.deleteAccordWord("新华社");
		hashMap.printAll();
		System.out.println("==========3");
		hashMap.deleteAccordWord("首页");
		hashMap.printAll();
		System.out.println("==========4");
		hashMap.deleteAccordWord("国");
		hashMap.printAll();
		System.out.println("==========5");
		hashMap.deleteAccordWord("工商");
		hashMap.printAll();
		System.out.println("==========5");
		hashMap.deleteAccordWord("国际");
		hashMap.printAll();
		
		System.out.println("--------------------------");
		System.out.println("Test 4: Delete According File");
		hashMap.deleteAccordFile("F:\\dic\\deleteAccordFile.txt");
		hashMap.printAll();
		
		System.out.println("--------------------------");
		System.out.println("Test 5: Add According Word");
		System.out.println("==========1");
		hashMap.addAccordWord("这是什么");
		hashMap.printAll();
		System.out.println("==========2");
		hashMap.addAccordWord("新华");
		hashMap.printAll();
		System.out.println("==========3");
		hashMap.addAccordWord("中华人民共和国");
		hashMap.printAll();		
		System.out.println("==========4");
		hashMap.addAccordWord("浙江大学");
		hashMap.printAll();		
		System.out.println("==========5");
		hashMap.addAccordWord("计算机学院");
		hashMap.printAll();		
		System.out.println("==========5");
		hashMap.addAccordWord("计算机");
		hashMap.printAll();	
		System.out.println("==========5");
		hashMap.addAccordWord("学院");
		hashMap.printAll();	
		System.out.println("==========5");
		hashMap.addAccordWord("中央政府");
		hashMap.printAll();	
		
		System.out.println("----------------------------");
		System.out.println("Test 6: Add According File");
		hashMap.addAccordFile("F:\\dic\\dict0_0.txt");
		hashMap.printAll();
			
		System.out.println("----------------------------");
		System.out.println("Test 7: Restore");
		hashMap.restore();
		System.out.println("Finished!");
		
		System.out.println("----------------------------");
		System.out.println("Test 8: Others");
		CreateHashMap hashMap2 = new CreateHashMap("F:\\dic\\create.txt");
		hashMap2.addAccordFile("F:\\dic\\addNone.txt");
		hashMap2.deleteAccordFile("F:\\dic\\deleteNone.txt");
		hashMap2.printAll();
*/	
	}
	
	/**
	 * Set and Get Functions of variables.
	 */
	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public IntegerMy getInteger() {
		return integer;
	}

	public void setInteger(IntegerMy integer) {
		this.integer = integer;
	}
	
	public ManageNumber getCurInteger() {
		return curNumber;
	}
	
	public void setCurInteger(ManageNumber curInteger) {
		this.curNumber = curInteger;
	}
	
	public HashMap<String, IntegerMy> getMap() {
		return map;
	}

	public void setMap(HashMap<String, IntegerMy> map) {
		this.map = map;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}
	public ManageNumber getCurNumber() {
		return curNumber;
	}

	public void setCurNumber(ManageNumber curNumber) {
		this.curNumber = curNumber;
	}

	public String getReuseFile() {
		return reuseFile;
	}

	public void setReuseFile(String reuseFile) {
		this.reuseFile = reuseFile;
	}

}