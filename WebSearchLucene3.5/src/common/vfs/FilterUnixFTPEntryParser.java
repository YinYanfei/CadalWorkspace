package common.vfs;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.parser.UnixFTPEntryParser;
import org.apache.log4j.Logger;

public class FilterUnixFTPEntryParser extends UnixFTPEntryParser{
    private static final Logger LOG = Logger.getLogger (FilterUnixFTPEntryParser.class);
    
    public FTPFile parseFTPEntry (String entry){
	FTPFile file = null;
	if (matches (entry)){
	    if (LOG.isDebugEnabled ()){
		LOG.debug ("raw entry : "+entry);
	    }
	    String type = group (1);
	    String name = group (21);
	    if (LOG.isDebugEnabled ())
		LOG.debug ("file name : "+name+" : type : "+type);
	    
	    if((type.charAt(0) == 'd')||
	       VFile.isDisplayable (name)){
		if ( !name.equals (".")
		     &&(!name.equals ("..")))
		    file = super.parseFTPEntry (entry);
	    }
	}
	return file;
    }
    
}
