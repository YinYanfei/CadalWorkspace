package cn.edu.zju.cadal.util.file;

import java.io.Writer;

public interface WriterHandler {
	
	// create handler
	public abstract Object createHandler(final String fileName);
	public abstract Object createHandler(final String fileName, final boolean addition);
	
	// close handler
	public abstract void closeHanlder(Writer writer);
}
