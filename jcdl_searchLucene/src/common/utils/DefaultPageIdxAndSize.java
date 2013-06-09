package common.utils;

import cn.cadal.util.Loader;
import cn.cadal.util.OptionConverter;

public class DefaultPageIdxAndSize implements PageIdxAndSize {
    
    private int pageidx;

    private int pagesize;

    public DefaultPageIdxAndSize (){
	pagesize = OptionConverter.toInt(Loader.getConfigurator ().
					 getProperty ("cn.cadal.db.fetchsize")); 
    }

    public DefaultPageIdxAndSize (int idx, int size){
	this.pageidx = idx;
	this.pagesize = size;
    }
    
    public void increPageIndex () {
	pageidx++;
    }

    public void setPageIndex (int arg){
	this.pageidx = arg;
    }
    
    public int getPageIndex (){
	return this.pageidx;
    }

    public int getPageSize (){
	return pagesize;
    }
}
