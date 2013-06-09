package common.vfs;

import java.io.File;

import org.apache.log4j.Logger;

import cn.cadal.exception.CADALFTPPathInvalidException;
import cn.cadal.util.Loader;

public class CADALPath{
    private static final Logger LOG = Logger.getRootLogger ();

    public static String[] bookIDtoPath (String bookID) throws CADALFTPPathInvalidException{
	String[] ret = new String[3];
	if (bookID.length ()!=8)
	    throw new CADALFTPPathInvalidException (bookID+" : the length of book id is not 8");
	ret[0]=bookID.substring (0,2);
	ret[1]=deduceRange (bookID);
	return ret;
    }

    public static String deduceRange (String bookID) throws CADALFTPPathInvalidException{
	if (bookID.length () != 8)
	    throw new CADALFTPPathInvalidException (bookID+" : the length of book id is not 8");

	String range = bookID.substring (2,5);

	char s = range.charAt (2);
	char t = range.charAt(1);
	char h = range.charAt(0);

	StringBuffer i_sb = new StringBuffer ();
	int si =  s - '0' + 1;
	if ( si < 10 ){
	    s = (char) (s+1);
	}else{
	    s = '0';
	    int ti = t - '0' +1;
	    if (ti < 10){
		t = (char) (t+1);
	    }else{
		t = '0';
		int hi = h - '0' +1;
		if (hi < 10){
		    h = (char) (h+1);
		}else{
		    throw new CADALFTPPathInvalidException (bookID+" : book id is too large");
		}
	    }
	}

	i_sb.append (h);
	i_sb.append (t);
	i_sb.append (s);
	i_sb.append ("000");
	return i_sb.toString ();
    }
    
    public static boolean validateBookID (String entry)
	throws CADALFTPPathInvalidException {
	entry = entry.toLowerCase ();
	String filesep = null;
	if ("ftp".equals (Loader.getConfigurator ().getProperty ("cn.cadal.text.source"))){
	    filesep = "/";
	}else if("local".equals (Loader.getConfigurator ().getProperty ("cn.cadal.text.source"))){
	    String localroot = Loader.getConfigurator ().getProperty ("cn.cadal.text.textlocation");
	    File root = new File (localroot);
	    localroot = root.getPath ();
	    LOG.debug ("local root :"+localroot);
	    entry = entry.substring (localroot.length ());
	    LOG.debug ("relative path :"+entry);
	    filesep = System.getProperty ("file.separator");
	    if ("\\".equals (filesep))
		filesep = "\\"+filesep;
	}else{
	    assert false:"error source option";
	}

	String [] token = entry.split (filesep);

	if (LOG.isDebugEnabled ()){
	    for (int idx = 0 ; idx < token.length; idx ++){
		LOG.debug (idx+":"+token[idx]);
	    }
	}
	String uniID= token[2];
	String bookIDRange=token[3];
	String bookID= token[4];
	
	if (LOG.isDebugEnabled ()){
	    LOG.debug ("orignal bookid : "+bookID);
	    LOG.debug ("orignal university id: "+uniID);
	    LOG.debug ("orignal bookIDRange : "+bookIDRange);
	}
	String uniCode = bookID.substring (0,2);
	String range = deduceRange (bookID);
	if (LOG.isDebugEnabled ()){
	    LOG.debug("extracted university code :"+uniCode);
	    LOG.debug ("deduced range :"+range);
	}

	boolean ret = false;

	if (uniID.equals (uniCode)
	    &&bookIDRange.equals (range)){
	    ret = true;
	}

	if (!ret){
	    throw new CADALFTPPathInvalidException ("invalid ftp path: "+entry);
	}
	return ret;
    }

}
