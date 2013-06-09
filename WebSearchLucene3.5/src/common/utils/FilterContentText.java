package common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FilterContentText {
    String regex;

    public FilterContentText (){
	
    }

    public FilterContentText (String regex){
	this.regex = regex;
    }
    
    public static String getFilteredContentText (String content, String regex){
	Pattern pattern = Pattern.compile (regex);
	Matcher matcher = pattern.matcher (content);
	StringBuffer sb = new StringBuffer (4096);
	while (matcher.find ()){
	    matcher.appendReplacement (sb," ");
	}
	matcher.appendTail (sb);
	return sb.toString ();
    }

    public static void main (String[] args){
	String chEnMix = "．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．．1第一节引言";
	String t = FilterContentText.getFilteredContentText (chEnMix, "．+");
	System.out.println (t);
    }
    
}
