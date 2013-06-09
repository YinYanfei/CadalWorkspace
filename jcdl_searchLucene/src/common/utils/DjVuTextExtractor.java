package common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import com.lizardtech.djvu.Codec;
import com.lizardtech.djvu.Document;
import com.lizardtech.djvubean.DjVuImage;

/**
 * @author zhangyin
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */

public class DjVuTextExtractor {

    public static String extractDjVuText(InputStream is) throws IOException {
	String retval = null;

	Document document = new Document();

	document.read(is);
	DjVuImage image = new DjVuImage(document.getPage(0));
	Codec textCodec = image.getTextCodec();
	if (textCodec != null) {
	    retval = textCodec.toString();
	}

	document = null;
	if (null == retval) retval="";
	return retval;
    }

    public static String extractDjVuText(URL url) throws IOException {
	String retval = null;

	//	System.out.println("Trying " + url);

	Document document = new Document(url);

	DjVuImage image = new DjVuImage(document.getPage(0));

	Codec textCodec = image.getTextCodec();
	if (textCodec != null) {
	    retval = textCodec.toString();
	}

	if ( null == retval ) retval = "";
	return retval;
    }

    public static void main(String[] args) {
	String surl = "http://210.32.137.91/dlib3/ebooks/deg/11/011000/11010029/ptiff/00000001.djvu";
	try {
	    URL url = new URL(surl);
	    //DjVuTextExtractor dte = new DjVuTextExtractor();
	    String content =  DjVuTextExtractor.extractDjVuText(url);
	    System.out.println(url + "::\n" + content + " :length: "+content.length ());

	    url = new URL("file:///d:/djvu/00000001.djvu");
	    content = DjVuTextExtractor.extractDjVuText(url);
	    System.out.println(url + "::\n" + content + " :length: "+content.length ());
	} catch (Exception exc) {
	    exc.printStackTrace();
	}
    }
}
