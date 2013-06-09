package common.utils;

import org.htmlparser.beans.StringBean;

public class HtmlStringExtractor extends StringBean{
    public void resetBuffer(){
	//System.out.println("mString : "+ mStrings);
	//System.out.println("mBuffer address: "+ mBuffer.hashCode());
	mBuffer.delete(0, mBuffer.length());
	//System.out.println("mBuffer address: "+ mBuffer.hashCode());
	//System.out.println("getURL :"+ getURL());
	updateStrings(null);
    }
}
