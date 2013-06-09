package common.vfs;

import java.io.File;
import java.io.InputStream;

import javax.swing.filechooser.FileSystemView;

public class VRootStringFile extends VFile {
    private String string;

    public VRootStringFile(String string){
	this.string = string;
    }

    public String getString(){
	return string;
    }

    public InputStream openStream(){
	return null;
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

    public boolean isDirectory(){
	return true;
    }

    public String getFileContentText(){
	if (true) throw new RuntimeException("�ýڵ㲻���ļ�");
	return null;
    }
    
    public VFile[] list(int num){
	FileSystemView fsv = FileSystemView.getFileSystemView ();
	File[] roots = fsv.getRoots();
	VFile[] subfiles = new VFile[roots.length];
	for( int idx = 0 ; idx < roots.length ; idx ++ ){
	    subfiles[idx] = new VLocalFile(roots[idx], fsv);
	}
	return subfiles;
    }
    
    public String toString(){
	return string;
    }
}
