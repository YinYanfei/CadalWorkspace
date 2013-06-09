package Test;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

class SomethingImpl implements Something.Iface {
 public SomethingImpl() {}
 public String ping() throws TException {
     System.out.println( "Recieve ping from client..." );
     
     String strTmp = "One" + "#" + "Two" + "#" + "Three" + "#";
     
     return strTmp;
}
}
