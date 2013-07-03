package cn.cadal.rec.tryJava;

import java.io.Reader;
import java.io.StringReader;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

public class IKAnaUsing {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		String str = "���g�Q��";
		Reader reader = new StringReader(str);
		IKSegmenter iks = new IKSegmenter(reader, true);
		Lexeme lexeme = null; 

		System.out.println("----");
	    while((lexeme = iks.next())!=null) 
	    	System.out.println(lexeme.getLexemeText()); 
	    
	    str = "�㽭ʡ�㽭��ѧ";	
	    iks.reset(new StringReader(str));
		System.out.println("----");
	    while((lexeme = iks.next())!=null) 
	    	System.out.println(lexeme.getLexemeText()); 
	    
	}
}