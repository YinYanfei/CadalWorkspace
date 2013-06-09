package common.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

public class StackTraceUtil {
    
    public static String getStackTrace (Exception exc){
	StringWriter sw = new StringWriter ();
	PrintWriter pw = new PrintWriter (sw);
	exc.printStackTrace (pw);
	return sw.toString ();
    }
}
