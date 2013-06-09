package cn.cadal.fulltextsearch.analysis;

/**
 * <p>Title: Lucene Test Deom</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

//import org.apache.lucene.analysis.standard.StandardAnalyzer;
import java.io.StringReader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;

public class HighLighter {
  private HighlightAnalyzer analyzer = null;

   /** highligt term */
   private ArrayList terms = null;

   /** default return string limit */
   private int maxReturnSize = 256;

   /** default input buffer size */
   private int maxBufferSize = 2560;

   /** context block max length */
   private int maxContextLen = 48;

   /**
    * default highlight prefix: &lt;u&gt;  example: &lt;b&gt;  &lt;font
    * color="red"&gt;  ...
    */
   private String highLightPrefix = "<font class=\"highlight\">";

   /**
    * default highlight prefix: &lt;/u&gt; <br>
    * example: &lt;/b&gt; &lt;/font&gt;
    */
   private String highLightSuffix = "</font>";

   /** input string buffer */
   private char[] srcBuffer = new char[0];

   //~ Constructors -----------------------------------------------------------

   /**
    * construct Highlighter with String[] t need highlight and use Analyzer
    * anal token from source string.
    *
    * @param t Terms.
    */
   public HighLighter(ArrayList t) {
       terms = t;
       analyzer = new HighlightAnalyzer(terms);
   }

   //~ Methods ----------------------------------------------------------------

   /**
    * Return highlighted string
    *
    * @param srcString source string need to highlight
    *
    * @return highlighted string
    */
   public String highLight(String srcString) {
       if ((srcString == null) || srcString.trim().equals("")) {
           return "";
       }

       int srcLength = srcString.length();

       //truncate src to maxRetrunSize
       if (srcLength >= maxBufferSize) {
           srcString = srcString.substring(0, maxBufferSize);
           srcLength = maxBufferSize;
       }

       //return src if no term to highlight
       if (terms.size() == 0) {
           return srcString.substring(0, maxReturnSize);
       }

       try {
           //reset buffer and last term offset
           //default previous token end place
           int prevEnd = 0;
           srcBuffer = new char[srcLength];

           StringReader stringReader = new StringReader(srcString);
           stringReader.read(srcBuffer);

           StringReader sr = new StringReader(srcString);
           TokenStream tokenStream = analyzer.tokenStream(null, sr);

           //return string buffer
           StringBuffer returnBuffer = new StringBuffer();
           String preContextBlock = ""; //previous text block

           /**
            * MY
            */
           Token t = tokenStream.getAttribute(Token.class);
           while(tokenStream.incrementToken()) {
           /**
            * MY
            */
           // highlight:  [preContextBlock] + <b> + [token] + </b>
           // for (Token t = tokenStream.next(); t != null;
           //     t = tokenStream.next()
           //    ) {
               preContextBlock = getContext(prevEnd, t.startOffset());
               returnBuffer.append(preContextBlock);

               //append highlight string
               returnBuffer.append(highLightPrefix);

               for (int i = t.startOffset(); i < t.endOffset(); i++) {
                   returnBuffer.append(srcBuffer[i]);
               }

               returnBuffer.append(highLightSuffix);

               //record current offset
               prevEnd = t.endOffset();

               if (returnBuffer.length() > maxReturnSize) {
                   break;
               }
               
               /**
                * MY
                */
               t = tokenStream.getAttribute(Token.class);
               /**
                * 
                */
           }

           tokenStream.close();

           //no highlight token find, return first maxReturnSize of string[]
           if (returnBuffer.length() == 0) {
               if (srcLength > maxReturnSize) {
                   returnBuffer.append(srcBuffer, 0, maxReturnSize);
               } else {
                   returnBuffer.append(srcBuffer, 0, srcLength);
               }

               return returnBuffer.toString();
           }

           //expand return string to MaxReturn
           while ((returnBuffer.length() < maxReturnSize)
                      && (prevEnd < srcLength)
                 ) {
               returnBuffer.append(srcBuffer[prevEnd]);
               prevEnd++;
           }

           return returnBuffer.toString();
       } catch (Exception e) {
           e.printStackTrace();

           //return with original value
           return "";
       }
   }

   /**
    * construct Highlighter with String[] t need highlight and use Analyzer
    * anal token from source string
    *
    * @param src src string need to highlight
    * @param mReturn max return string max length
    *
    * @return highlighted string
    */
   public String highLight(String src, int mReturn) {
       maxReturnSize = mReturn;

       return highLight(src);
   }

   /**
    * construct Highlighter with String[] t need highlight and use Analyzer
    * anal token from source string
    *
    * @param src src string need to highlight
    * @param returnSize mReturn return string max length
    * @param readSize mRead highlight string max read
    *
    * @return highted string
    */
   public String highLight(String src, int returnSize, int readSize) {
       maxReturnSize = returnSize;
       maxBufferSize = readSize;

       return highLight(src);
   }

   /**
    * get context block from buffer between previous token end offset and
    * current token start offset
    *
    * @param prevEnd prevEnd previous token end offset
    * @param curStart curStart curren token start offset
    *
    * @return context text
    */
   private String getContext(int prevEnd, int curStart) {
       //added buffer length check
       if (curStart > srcBuffer.length) {
           curStart = srcBuffer.length;
       }

       if (curStart > prevEnd) {
           //return context buffer
           StringBuffer tb = new StringBuffer();
           int between = curStart - prevEnd;

           if (between <= maxContextLen) {
               for (int i = (prevEnd); i < curStart; i++) {
                   tb.append(srcBuffer[i]);
               }
           } else {
               int prevEndTo = getLastPunc(prevEnd,
                                           prevEnd + (maxContextLen / 2)
                                          ) + 1;

               for (int j = prevEnd; j < prevEndTo; j++) {
                   tb.append(srcBuffer[j]);
               }

               tb.append("...");

               int prevStartFrom = getFirstPunc(curStart - (maxContextLen / 2),
                                                curStart
                                               ) + 1;

               for (int k = prevStartFrom; k < curStart; k++) {
                   tb.append(srcBuffer[k]);
               }
           }

           return tb.toString();
       } else {
           //empty text block
           return "";
       }
   }

   /**
    * find first punctuation offset between <code>start</code> and
    * <code>end</code> in buffer[]
    *
    * @param start start offset of the buffer
    * @param end end offset of the buffer
    *
    * @return punctuation offset
    */
   private int getFirstPunc(int start, int end) {
       //if not find return start offset
       int firstFind = start;

       while (start < end) {
           if (Character.isLetterOrDigit(srcBuffer[start]) == false) {
               firstFind = start;

               break;
           }

           start++;
       }

       return firstFind;
   }

   /**
    * find last punctuation offset between <code>start</code> and
    * <code>end</code> in buffer[]
    *
    * @param start start offset of the buffer
    * @param end end offset of the buffer
    *
    * @return last punctuation offset
    */
   private int getLastPunc(int start, int end) {
       //if not find return end offset
       int lastFind = end;

       while (start < end) {
           if (Character.isLetterOrDigit(srcBuffer[start]) == false) {
               lastFind = start;
           }

           start++;
       }

       return lastFind;
   }


}