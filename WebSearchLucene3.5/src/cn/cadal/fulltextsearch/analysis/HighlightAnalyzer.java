package cn.cadal.fulltextsearch.analysis;

/**
 * <p>Title: Lucene Test Deom</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

//import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.Reader;
import java.util.ArrayList;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import cn.cadal.fulltextsearch.analysis.cjk.CJKTokenizer;

/**
 * Filters source string with Tokenizer and HighlightFilter
 *
 * @author Che, Dong
 *
 * @since WebLucene 0.1
 */
public final class HighlightAnalyzer extends Analyzer {
    //~ Static fields/initializers ---------------------------------------------

    /**
     * An array containing some common English words that are not  usually
     * useful for searching and some double-byte interpunctions.
     */
    private static String[] stopWords = null;

    //~ Instance fields --------------------------------------------------------

    /** named word list */
    private ArrayList wordList;

    //~ Constructors -----------------------------------------------------------

    /**
     * Builds an analyzer which removes words in the provided array.
     *
     * @param words DOCUMENT ME!
     */
    public HighlightAnalyzer(String[] words) {
        wordList = HighlightFilter.makeStopTable(words);
    }

    /**
     * Builds an analyzer which removes words in the provided array.
     *
     * @param highlightTable high light word list
     */
    public HighlightAnalyzer(ArrayList highlightTable) {
        wordList = highlightTable;
    }

    //~ Methods ----------------------------------------------------------------

    /**
     *  Tokenizer with StopFilter.
     *
     * @param field field name
     * @param reader input reader
     *
     * @return TokenStream
     */
    public final TokenStream tokenStream(String field, Reader reader) {
      return new HighlightFilter(new CJKTokenizer(reader), wordList);
    }
}
