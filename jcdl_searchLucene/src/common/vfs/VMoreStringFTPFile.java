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
	    throw new RuntimeException ("�ýڵ㲻���ļ��ڵ�");
	return null;
    }

    public boolean isDirectory(){
	return true;
    }

    public VFile[] list(int num){
	return parent.list(num);
    }

    public String getName(){
	throw new RuntimeException("�ýڵ��ֹ�÷���");	
    }

    public String getPath(){
	throw new RuntimeException("�ýڵ��ֹ�÷���");
    }

    public boolean exists(){
	throw new RuntimeException("�ýڵ��ֹ�÷���");
    }

    public String toString(){
	return "����";
    }

}
