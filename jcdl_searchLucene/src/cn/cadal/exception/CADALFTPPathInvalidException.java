package cn.cadal.exception;


public class CADALFTPPathInvalidException extends Exception{
    private static String errMsg = "this ftp path entry violates the standard specification";

    public CADALFTPPathInvalidException (){
	super (errMsg);
    }

    public CADALFTPPathInvalidException (String errMsg){
	super (errMsg);
    }
    
    
}
