package common.utils;

public class JWhich{

    public static void which (String className){
	if (!className.startsWith ("/")){
	    className = "/"+className;
	}
	className = className.replace ('.', '/');
	className = className + ".class";

	java.net.URL classUrl = new JWhich ().getClass ().getResource (className);

	if (classUrl != null){
	    System.out.println ("\nClass: "+className+
				" found in \n"+classUrl.getFile ()+"");
	}else{
	    System.out.println ("\nClass: "+className+
				" not found in \n"+System.getProperty ("java.class.path")+"");
	}
    }

    public static void main (String args[]){
	if ( args.length > 0 ){
	    JWhich.which (args[0]);
	}else{
	    System.err.println ("Usage: java JWhich");
	}
    }

}
