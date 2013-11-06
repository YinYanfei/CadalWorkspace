package cn.edu.zju.cadal.hbase.insertInfo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class FileHandle {
	private String dir;
	public List<File> fileInfoList;
	
	/**
	 * Construct function
	 */
	public FileHandle(String dir) {
		this.dir = dir;
		this.fileInfoList = new ArrayList<File>();
	}
	
	/**
	 * Get File handle for this.dir
	 */
	private void FileInfo(File fileDir){
		File [] fileList = fileDir.listFiles();
		
		if(fileList == null || fileList.length == 0) {
			return;
		}
		
		for(File fileSingle : fileList){
			if(fileSingle.isDirectory()) {
				this.FileInfo(fileSingle);
			}else{
				this.fileInfoList.add(fileSingle);
			}
		}
	}

	/**
	 * Interface for this class
	 */
	public List<File> GetFileInfo(){
		File file = new File(this.dir);
		this.FileInfo(file);
		
		return this.fileInfoList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Test
		FileHandle handle = new FileHandle("F:/hadoop/��4��/Sep-2013");
		List<File> listFile = handle.GetFileInfo();
		
		BufferedReader reader = null;
		
		try{
			for(File file : listFile) {
				reader = new BufferedReader(new FileReader(file));
				String line = null;
				while((line = reader.readLine()) != null){
					System.out.println(line);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try{
				if(reader != null) {
					reader.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

}
