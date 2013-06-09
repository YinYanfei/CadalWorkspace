package common.vfs;

import java.io.InputStream;

public class VFile {

	public static final String UNHANDLED_FILE_FORMAT = "没有处理的文件格式";

	//    public static final String DJVU_NO_HIDDEN_TEXT = "该djvu没有文本层";

	private VFile parent;

	private String name;

	private String path;

	public VFile() {

	}

	public VFile(String path) {
		this.path = path;
	}

	public VFile(VFile parent, String child) {
		this.parent = parent;
		this.name = child;
	}

	public String getFileContentText() {
		assert false : "cann't directly use the prototype method";
		return null;
	}

	/*
	 protected void setFileContentText(String content){
	 this.fileContentText = content;
	 }*/

	public InputStream openStream() {
		assert false : "cann't directly use the prototype method";
		return null;
	}

	public VFile[] list(int num) {
		assert false : "cann't directly use the prototype method";
		return null;
	}

	public boolean exists() {
		assert false : "cann't directly use the prototype method";
		return false;
	}

	public boolean isDirectory() {
		assert false : "cann't directly use the prototype method";
		return false;
	}

	public VFile getParent() {
		return parent;
	}

	protected void setParent(VFile parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	protected void setName(String n) {
		this.name = n;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String p) {
		this.path = p;
	}

	public VFile getCanonicalVFile() {
		VFile retval = null;
		VFile[] subfiles = parent.list(0);
		for (int idx = 0; idx < subfiles.length; idx++) {
			String rname = subfiles[idx].getName();
			if (rname.equals(name)) {
				retval = subfiles[idx];
			}
		}
		return retval;
	}

	public String toString() {
		return name;
	}

	static final private String[] exts = { ".htm", ".html", ".xml", ".djvu",
			".opf", ".dtd", ".txt" };

	static public boolean isDisplayable(String fName) {

		for (int idx = 0; idx < exts.length; idx++) {
			if (fName.endsWith(exts[idx]))
				return true;
		}
		return false;
	}

}
