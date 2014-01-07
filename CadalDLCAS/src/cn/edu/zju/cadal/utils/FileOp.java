package cn.edu.zju.cadal.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class FileOp {
	private BufferedWriter bw = null;
	
	/**
	 * 初始化，打开一个文件
	 * @param file 
	 */
	public FileOp(String file){
		try{
			bw = new BufferedWriter(new FileWriter(file));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 将一个字符串写入文件的一行
	 * @param record
	 */
	public void write(String record){
		try{
			bw.write(record + "\n");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 关闭文件句柄
	 */
	public void close(){
		if(bw!=null){
			try{
				bw.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileOp fileOp = new FileOp("C:\\Users\\hongxin\\Desktop\\test.out");
		fileOp.write("test text");
		fileOp.close();
	}

}
