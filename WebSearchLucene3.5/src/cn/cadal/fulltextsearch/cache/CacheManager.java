/*
 * Created on 2006-3-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.cadal.fulltextsearch.cache;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryParser.ParseException;
import cn.cadal.fulltextsearch.configure.Configure;
import cn.cadal.fulltextsearch.configure.ConfigureManager;
import cn.cadal.fulltextsearch.search.PageSearcher;
import cn.cadal.fulltextsearch.search.Query;

/**
 * @author lwm
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CacheManager {
	public static List getSearchDocuments(SearchResultCache cache,Query query)throws IOException,ParseException{
			query.hitSize = cache.hitSize;
			int start = query.start-cache.cacheStart*query.pageSize;
			int end = start + query.pageSize;
			if(end>cache.documents.size())
				end = cache.documents.size();
			return cache.documents.subList(start,end);
	}
	public static boolean inCache(SearchResultCache cache,Query query){
		if(cache.documents.size() == 0)
			return false;
		if(!cache.q.equals(query.q)){
			return false;
		}
		if(query.start<cache.cacheStart*query.pageSize+cache.documents.size()&&query.start/query.pageSize-cache.cacheStart>=0)
			return true;
		return false;
	}
	public static void flushCache(SearchResultCache cache,Query query)throws IOException,ParseException{
		Configure conf = ConfigureManager.getConfigure();
		Hits hits = PageSearcher.search(conf.getIndexPath(),query.q,"Content");
		int length = hits.length();
		cache.hitSize = length;
		cache.q = query.q;
		int cacheStart = query.start/query.pageSize/cache.cacheSize*cache.cacheSize;
		int size = cache.cacheSize * query.pageSize;
		if(cacheStart*query.pageSize + size>length)
			size = length-cacheStart*query.pageSize;
		cache.cacheStart = cacheStart;
		for(int i=cacheStart*query.pageSize;i<cacheStart*query.pageSize + size;i++){
			cache.documents.add(hits.doc(i));
		}
	}
}
