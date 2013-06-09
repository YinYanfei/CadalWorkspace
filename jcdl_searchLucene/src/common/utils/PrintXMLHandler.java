package common.utils;

import java.io.FileReader;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class PrintXMLHandler extends DefaultHandler
{

    public static void main (String args[])
	throws Exception
    {
	XMLReader xr = XMLReaderFactory.createXMLReader();
	PrintXMLHandler handler = new PrintXMLHandler ();
	xr.setContentHandler(handler);
	xr.setErrorHandler(handler);

	// Parse each file provided on the
	// command line.
	for (int i = 0; i < args.length; i++) {
	    FileReader r = new FileReader(args[i]);
	    xr.parse(new InputSource(r));
	}
    }

    private StringBuffer strBuffer = new StringBuffer(4096);
    private static final String lineSeparator = System.getProperty ("line.separator");
    
    public PrintXMLHandler ()
    {
	super();
    }


    ////////////////////////////////////////////////////////////////////
    // Event handlers.
    ////////////////////////////////////////////////////////////////////


    public void startDocument ()
    {
	//System.out.println("Start document");
    }


    public void endDocument ()
    {
	//System.out.println("End document");
    }


    public void startElement (String uri, String name,
			      String qName, Attributes atts)
    {
	if ("".equals (uri))
	    strBuffer.append("Start element: " + qName + lineSeparator);
	else
	    strBuffer.append("Start element: " + name + lineSeparator);
	int num = atts.getLength ();
	//System.out.println ("the num of attributes : "+num);
	for (int idx = 0 ; idx < num ; idx++ ){
	    strBuffer.append ("attribute: " +atts.getQName(idx));
	    strBuffer.append (" value: "+atts.getValue (idx)+lineSeparator);
	}
    }


    public void endElement (String uri, String name, String qName)
    {
	if ("".equals (uri))
	    strBuffer.append("End element: " + qName + lineSeparator);
	else
	    strBuffer.append("End element: " + name + lineSeparator );
	strBuffer.append (lineSeparator);
    }


    public void characters (char ch[], int start, int length)
    {
	strBuffer.append ("Characters:    \"");
	strBuffer.append (ch, start, length);
	/*	for (int i = start; i < start + length; i++) {
	    switch (ch[i]) {
	    case '\\':
		strBuffer.append("\\\\");
		break;
	    case '"':
		strBuffer.append("\\\"");
		break;
	    case '\n':
		strBuffer.append("\\n");
		break;
	    case '\r':
		strBuffer.append("\\r");
		break;
	    case '\t':
		strBuffer.append("\\t");
		break;
	    default:
		strBuffer.append(ch[i]);
		break;
	    }
	    }*/
	strBuffer.append ("\""+lineSeparator);
    }

    public String getContent (){
	return strBuffer.toString ();
    }
}
