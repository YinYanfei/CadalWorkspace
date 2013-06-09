package cn.cadal.fulltextsearch.content;

import java.io.File;

public class FileContentGeneratorBuilder {
	public static FileContentGenerator buildFileContentGenerator(File file){
		String filePath = file.getPath();
		String ext = filePath.substring(filePath.indexOf(".")).toUpperCase();
		//return new Class.forName(ext+"FileContentGenerator");
		return new TxtFileContentGenerator();
	}
}
