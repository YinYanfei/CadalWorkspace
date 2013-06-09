package common.vfs;

import java.io.InputStream;

import org.apache.commons.net.ftp.FTPFile;
import org.apache.log4j.Logger;

public class VRootStringFtpFile extends VFTPFile{
    
    private static final Logger LOG = Logger.getLogger (VRootStringFtpFile.class);
    private FTPSite ftpSite;

    public VRootStringFtpFile (FTPSite fs){
	super (fs, null, fs.getFolder (), null); //fixme, childname instead of pathname
	this.ftpSite = fs;
    }

    public String getPath (){
	return ftpSite.getFolder ();
    }

    public String getName (){
	String path = getPath ();
	int index = path.lastIndexOf ("/");
	String name = path.substring (index+1, path.length ());
	return name;
    }
    
    public FTPSite getFTPSite(){
	return ftpSite;
    }
	
    public String getString(){
	return ftpSite.getFtpAddress();
    }

    public InputStream openStream(){
	return null;
    }

    public boolean exists(){
	assert false:"该节点禁止该方法";
	return false;
    }

    public boolean isDirectory(){
	return true;
    }

    public String getFileContentText(){
	assert false:"该节点禁止该方法";
	return null;
    }

    public VFile[] list(int num){
	VFile[] ret = null;
	String ftpaddr = ftpSite.getFtpAddress();
	if (LOG.isDebugEnabled ()){
	    LOG.debug("server : "+ftpaddr);
	}
	FTPSite.connectFTPSite(ftpSite);
	FTPFile[] fList = pagedFTPDirList(num);
	ret = new VFile[fList.length];
	for(int idx = 0 ; idx < fList.length ; idx ++){
	    String name = fList[idx].getName();
	    if (LOG.isDebugEnabled ()){
		LOG.debug ("the path of child ftp file : "+ftpSite.getFolder ()+"/"+fList[idx].getName());
	    }
	    ret[idx] = new VFTPFile(ftpSite, fList[idx], name, this);
	}

	return ret;
    }

    public String toString(){
	return ftpSite.getFtpAddress();
    }

    public void dispose(){
	ftpSite.dispose();
    }

}
