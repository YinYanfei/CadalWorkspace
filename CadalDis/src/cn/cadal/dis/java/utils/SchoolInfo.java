package cn.cadal.dis.java.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class SchoolInfo {

	public Map<String, String> IpToSchoolID = null;
	public Map<String, String> SchoolIDToName = null;
	
	private String DIR = "";
	private String IPTOID = "IpToID.txt";
	private String IDTONAME = "IDToName.txt";
	
	/**
	 * Construct
	 */
	public SchoolInfo() {
		this.IpToSchoolID = new HashMap<String, String>();
		this.SchoolIDToName = new HashMap<String, String>();
		
		this.DIR = System.getProperty("user.dir") + "\\info\\";
	}
	
	/**
	 * Read file "IpToID.txt", and store content into this.IpToSchoolID
	 */
	public boolean ReadIpToID(){
		File file = new File(this.DIR + this.IPTOID);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            
            while ((tempString = reader.readLine()) != null) {
            	String []str = tempString.split(" ");
            	this.IpToSchoolID.put(str[0], str[1]);
            }
            
            reader.close(); 
            return true;
        } catch (Exception e) {  
            e.printStackTrace();
            return false;
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (Exception e1) {  
                }  
            }  
        }
    }

	/**
	 * Read file "IDToName.txt", and store content into this.SchoolIDToName
	 */
	public boolean ReadIDToName(){
		File file = new File(this.DIR + this.IDTONAME);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            
            while ((tempString = reader.readLine()) != null) {
            	String []str = tempString.split(" ");
            	this.SchoolIDToName.put(str[0], str[1]);
            }
            
            reader.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (Exception e1) {  
                }  
            }  
        }
    }
	
	/**
	 * To get SchoolID use Key[Ip]
	 * @param ip	: 202.120.4.125
	 * @return		: null -> error
	 * 				  ""   -> unexist
	 */
	public String GetSchoolID(String ip) {
		if(this.IpToSchoolID.size() == 0){
			return null;
		}
		
		try{
			String SchoolID = "";
			String []IpSplit = ip.split("\\.");
			
			if(this.IpToSchoolID.containsKey(ip)){
				SchoolID = this.IpToSchoolID.get(ip);
			}else if(this.IpToSchoolID.containsKey(IpSplit[0] + "." + IpSplit[1] + "." + IpSplit[2])){
				SchoolID = this.IpToSchoolID.get(IpSplit[0] + "." + IpSplit[1] + "." + IpSplit[2]);
			}else if(this.IpToSchoolID.containsKey(IpSplit[0] + "." + IpSplit[1])) {
				SchoolID = this.IpToSchoolID.get(IpSplit[0] + "." + IpSplit[1]);
			}
			
			return SchoolID;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * To get school name by school id
	 * @param id 	: 46
	 * @return		: null -> error
	 * 				  ""   -> unexist[this case will not happen]
	 */
	public String GetSchoolName(String id){
		if(this.SchoolIDToName.size() == 0) {
			return null;
		}
		
		try{
			String SchoolName = "";
			
			if(this.SchoolIDToName.containsKey(id)){
				SchoolName = this.SchoolIDToName.get(id);
			}
			
			return SchoolName;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SchoolInfo si = new SchoolInfo();
		
		si.ReadIpToID();
		si.ReadIDToName();
		
		System.out.println(si.GetSchoolID("218.246.184.10"));
//		System.out.println(si.GetSchoolName("46"));
	}

}
