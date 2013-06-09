package cn.cadal.storm.analyze.bolt.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Yanfei
 * 	Function: Using to remove other services like Calligraph 
 *	Use: Recall function JudgeStrEmit, parameter is the string to judge
 *	Return: True for emitting, and false for abandon
 */
public class JudgeStr {
	// regular
	String regPainting = "PaintingDetail";
	String regVideo = "(VideoDetail|VideoShow)";
	String regCalligraphy = "Calligraphy";
	String regIM = "/IM";
	String regBNS = "/bns/";
	String regChineseMedicine = "ChineseMedicine";
	String regMusic = "music";
	String regMed = "/(med|Content|Med)/";
	String regSJWEB = "(WEBAPP|SJ_WEB)";
	String regBrushModule = "/brushModule/";
	String regWebService = "/(WebService|SynService)";
	
	Pattern patPainting = Pattern.compile(regPainting);
	Pattern patVideo = Pattern.compile(regVideo);
	Pattern patCalligraphy = Pattern.compile(regCalligraphy);
	Pattern patIM = Pattern.compile(regIM);
	Pattern patBNS = Pattern.compile(regBNS);
	Pattern patChineseMedicine = Pattern.compile(regChineseMedicine);
	Pattern patMusic = Pattern.compile(regMusic);
	Pattern patMed = Pattern.compile(regMed);
	Pattern patSJWEB = Pattern.compile(regSJWEB);
	Pattern patBrushModule = Pattern.compile(regBrushModule);
	Pattern patWebService = Pattern.compile(regWebService);
	
	Matcher matPainting = null;
	Matcher matVideo = null;
	Matcher matCalligraphy = null;
	Matcher matIM = null;
	Matcher matBNS = null;
	Matcher matChineseMedicine = null;
	Matcher matMusic = null;
	Matcher matMed = null;
	Matcher matSJWEB = null;
	Matcher matBrushModule = null;
	Matcher matWebService = null;
	
	public boolean JudgeStrEmit(String str) {
		// Regest match
		matPainting = patPainting.matcher(str);
		matVideo = patVideo.matcher(str);
		matCalligraphy = patCalligraphy.matcher(str);
		matIM = patIM.matcher(str);
		matBNS = patBNS.matcher(str);
		matChineseMedicine = patChineseMedicine.matcher(str);
		matMusic = patMusic.matcher(str);
		matMed = patMed.matcher(str);
		matSJWEB = patSJWEB.matcher(str);
		matBrushModule = patBrushModule.matcher(str);
		matWebService = patWebService.matcher(str);

		if(matVideo.find() || matCalligraphy.find() || matPainting.find() || matBNS.find() 
		    || matChineseMedicine.find() || matBrushModule.find() || matSJWEB.find() 
		    || matMusic.find() || matMed.find() || matWebService.find() || matIM.find()){
			return false;
		} else {
			return true;
		}
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {		
//		JudgeStr js = new JudgeStr();	
//		
//		boolean retBoolean = js.JudgeStrEmit("GET /IM/data/60003517.jpg HTTP/1.1");
//
//		if(retBoolean) {
//			System.out.println("Good");
//		}else{
//			System.out.println("Bad");
//		}
	}

}


