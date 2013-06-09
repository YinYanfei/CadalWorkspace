package common.vfs;

import java.io.InputStream;

public class VMoreStringFTPFile extends VFile{

    private VFTPFile parent;
    
    public VMoreStringFTPFile(VFTPFile parent){
	this.parent = parent;
    }
        
    public InputStream openStream(){
	return null;
    }

    public String getFileContentText(){
	if (true)
	    throw new RuntimeException ("该节点不是文件节点");
	return null;
    }

    public boolean isDirectory(){
	return true;
    }

    public VFile[] list(int num){
	return parent.list(num);
    }

    public String getName(){
	throw new RuntimeException("该节点禁止该方法");	
    }

    public String getPath(){
	throw new RuntimeException("该节点禁止该方法");
    }

    public boolean exists(){
	throw new RuntimeException("该节点禁止该方法");
    }

    public String toString(){
	return "更多";
    }

}
