package cn.cadal.fulltextsearch.index;

import cn.cadal.fulltextsearch.content.IndexBuildType;

public class IndexBuildConfig {
	
	static String bookDir = "d:\\text";
	static String indexDir = "d:\\text_index\\index";
	static int buildType = IndexBuildType.New;
	
	private String[] bookTypes = new String[]{"anc","disseration","minguo","modern","qikan","calligraphy","english"};

}
