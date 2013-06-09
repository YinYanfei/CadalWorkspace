/*
 * Created on 2004-12-1
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package cn.cadal.util;

/**
 * @author zhangyin
 * 
 * Convert properties string to according type value
 *  
 */
public class OptionConverter {

    public static boolean toBoolean (String option){
	if (null == option)
	    return false;
	boolean retval = false;
	if ("true".equalsIgnoreCase(option)){
	    retval = true;
	}else if("false".equalsIgnoreCase (option)){
	    retval = false;
	}
	return retval;
    }
    
    public static int toInt(String option) {
        if (option != null) {
            String s = option.trim();
            try {
                return Integer.valueOf(s).intValue();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    public static float toFloat(String option) {
        if (option != null) {
            String s = option.trim();
            try {
                return Float.valueOf(s).floatValue();
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return 0;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
    }
}
