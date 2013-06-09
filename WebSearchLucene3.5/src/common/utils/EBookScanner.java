package common.utils;

import org.apache.log4j.Logger;

import common.vfs.VFile;

abstract public class EBookScanner {
	private static final Logger LOG = Logger.getLogger(EBookScanner.class);

	private static int depCount = -1;

	private int depth = Integer.MAX_VALUE;

	private int maxListNum;

	private int maxBookCount;

	private int bookCount;

	public EBookScanner() {
		maxBookCount = Integer.MAX_VALUE;
			//OptionConverter.toInt(Loader.getConfigurator()
			//	.getProperty("cn.cadal.text.downloadnum"));
		if (LOG.isDebugEnabled())
			LOG.debug("max download book num :" + maxBookCount);
	}

	public EBookScanner(int depth) {
		super();
		this.depth = depth;
	}

	public void setMaxListNum(int max) {
		this.maxListNum = max;
	}

	public void doScan(VFile root) {
		depCount++; // 
		//System.out.println ("the current depth of file : "+depCount);
		if (depth < depCount) {
			depCount--;
			return;
		}
		
		//anti book in book
		if (root.getParent()!=null 
			&&root.getParent().getName().length() ==8
			&&(root.getName().length()==8)){
			if (LOG.isDebugEnabled()){
				LOG.debug("dir:"+root.getParent().getName()+"/"+root.getName());
			}
			return;	
		}	
		///

		VFile[] subs = root.list(maxListNum);
		if (subs == null)
			return;

		for (int idx = 0; idx < subs.length; idx++) {
			if (bookCount < maxBookCount) {

				if (LOG.isDebugEnabled())
					LOG.debug(idx + " file path : " + subs[idx].getPath());
				if (subs[idx].isDirectory()) {

					if (LOG.isDebugEnabled())
						LOG
								.debug("the name of dir is : "
										+ subs[idx].getPath());
					if ("ptiff".equalsIgnoreCase(subs[idx].getName())) {
						bookCount++;
						extractContent(subs[idx]);
					} else if ("meta".equalsIgnoreCase(subs[idx].getName())) {
						extractMetaData(subs[idx]);
					} else {
						doScan(subs[idx]);
					}
				} else {
					continue;
				}
			} else {
				return;
			}
		}
		for (int idx = 0; idx < subs.length; idx++) {
			subs[idx] = null;
		}
		depCount--;
	}

	abstract public void scan();

	abstract protected void extractMetaData(VFile meta);

	abstract protected void extractContent(VFile ptiff);

	abstract public void dispose();

}
