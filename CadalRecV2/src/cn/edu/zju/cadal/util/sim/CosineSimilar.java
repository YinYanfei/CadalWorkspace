package cn.edu.zju.cadal.util.sim;

import java.util.List;

public class CosineSimilar implements Similarity {

	/**
	 * This function is used to calculate Cosine Similar, with two parameter integer array.
	 * 
	 * @param arrIntOne
	 * @param arrIntTwo
	 * @return 
	 */
	@Override
	public double distanceOfIntArray(int[] arrIntOne, int[] arrIntTwo) {
		if(arrIntOne.length != arrIntTwo.length) {
			return -1;
		}
		
		if(arrIntOne.length == 0 || arrIntTwo.length == 0) {
			return 0;
		}

		int numerator = 0;
		int denominatorOne = 0;
		int denominatorTwo = 0;
		
		try{
			for(int idx = 0; idx < arrIntOne.length; ++idx) {
				numerator += arrIntOne[idx] * arrIntTwo[idx];
				denominatorOne += arrIntOne[idx] * arrIntOne[idx];
				denominatorTwo += arrIntTwo[idx] * arrIntTwo[idx];
			}
			
			double res = numerator / (Math.sqrt(denominatorOne) * Math.sqrt(denominatorTwo));
			
			return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * This function is used to calculate Cosine Similar, you can add a factor in the end of result.
	 * 
	 * @param arrIntOne
	 * @param arrIntTwo
	 * @param factor
	 * @return 
	 */
	@Override
	public double distanceOfIntArray(int[] arrIntOne, int[] arrIntTwo,
			float factor) {
		double res = this.distanceOfIntArray(arrIntOne, arrIntTwo);
		
		if(res == -1) {
			return -1;
		}
				
		return res * factor;
	}

	/**
	 * This function is used to calculate Cosine Similar, with two parameter float array.
	 * 
	 * @param arrFloatOne
	 * @param arrFloatTwo
	 * @return 
	 */
	@Override
	public double distanceOfFloatArray(float[] arrFloatOne, float[] arrFloatTwo) {
		if(arrFloatOne.length != arrFloatTwo.length) {
			return -1;
		}
		
		if(arrFloatOne.length == 0 || arrFloatTwo.length == 0) {
			return 0;
		}
		
		double numerator = 0;
		double denominatorOne = 0;
		double denominatorTwo = 0;
		
		try{
			for(int idx = 0; idx < arrFloatOne.length; ++idx) {
				numerator += arrFloatOne[idx] * arrFloatTwo[idx];
				denominatorOne += arrFloatOne[idx] * arrFloatOne[idx];
				denominatorTwo += arrFloatTwo[idx] * arrFloatTwo[idx];
			}
			
			double res = numerator / (Math.sqrt(denominatorOne) * Math.sqrt(denominatorTwo));
			
			return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * This function is used to calculate Cosine Similar, with factor.
	 * 
	 * @param arrFloatOne
	 * @param arrFloatTwo
	 * @param factor
	 * @return 
	 */
	@Override
	public double distanceOfFloatArray(float[] arrFloatOne, float[] arrFloatTwo,
			float factor) {
		double res = this.distanceOfFloatArray(arrFloatOne, arrFloatTwo);
		
		if(res == -1) {
			return -1;
		}
		
		return res * factor;
	}

	/**
	 * This function is used to calculate Cosine Similar, with two parameter string array.
	 * 
	 * @param arrStringOne
	 * @param arrStringTwo
	 * @return 
	 */
	@Override
	public double distanceOfStringArray(String[] arrStringOne,
			String[] arrStringTwo) {
		if(arrStringOne.length != arrStringTwo.length) {
			return -1;
		}
		
		if(arrStringOne.length == 0 || arrStringTwo.length == 0) {
			return 0;
		}

		double numerator = 0;
		double denominatorOne = 0;
		double denominatorTwo = 0;
		
		try{
			float oneTmp = 0;
			float twoTmp = 0;
			
			for(int idx = 0; idx < arrStringOne.length; ++idx) {
				oneTmp = Float.valueOf(arrStringOne[idx]);
				twoTmp = Float.valueOf(arrStringTwo[idx]);
				
				numerator += oneTmp * twoTmp;
				denominatorOne += oneTmp * oneTmp;
				denominatorTwo += twoTmp * twoTmp;
			}
			
			double res = numerator / (Math.sqrt(denominatorOne) * Math.sqrt(denominatorTwo));
			
			return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * This function is used to calculate Cosine Similar, with factor.
	 * 
	 * @param arrStringOne
	 * @param arrStringTwo
	 * @param factor
	 * @return 
	 */
	@Override
	public double distanceOfStringArray(String[] arrStringOne,
			String[] arrStringTwo, float factor) {
		double res = this.distanceOfStringArray(arrStringOne, arrStringTwo);
		
		if(res == -1) {
			return -1;
		}
		
		return res * factor;
	}

	/**
	 * This function is used to calculate Cosine Similar, with two parameter integer list.
	 * 
	 * @param listIntOne
	 * @param listIntTwo
	 * @return 
	 */
	@Override
	public double distanceOfIntList(List<Integer> listIntOne,
			List<Integer> listIntTwo) {
		if(listIntOne.size() != listIntTwo.size()) {
			return -1;
		}
		
		if(listIntOne.size() == 0 || listIntTwo.size() == 0) {
			return 0;
		}
		
		int numerator = 0;
		int denominatorOne = 0;
		int denominatorTwo = 0;
		
		try{
			for(int idx = 0; idx < listIntOne.size(); ++idx) {
				numerator += listIntOne.get(idx) * listIntTwo.get(idx);
				denominatorOne += listIntOne.get(idx) * listIntOne.get(idx);
				denominatorTwo += listIntTwo.get(idx) * listIntTwo.get(idx);
			}
			
			double res = numerator / (Math.sqrt(denominatorOne) * Math.sqrt(denominatorTwo));
			
			return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * This function is used to calculate Cosine Similar, with factor.
	 * 
	 * @param listIntOne
	 * @param listIntTwo
	 * @param factor
	 * @return 
	 */
	@Override
	public double distanceOfIntList(List<Integer> listIntOne,
			List<Integer> listIntTwo, float factor) {
		double res = this.distanceOfIntList(listIntOne, listIntTwo);
		
		if(res == -1) {
			return -1;
		}
		
		return res * factor;
	}

	/**
	 * This function is used to calculate Cosine Similar, with two parameter float list.
	 * 
	 * @param listFloatOne
	 * @param listFloatTwo
	 * @return 
	 */
	@Override
	public double distanceOfFloatList(List<Float> listFloatOne,
			List<Float> listFloatTwo) {
		if(listFloatOne.size() != listFloatTwo.size()) {
			return -1;
		}
		
		if(listFloatOne.size() == 0 || listFloatTwo.size() == 0) {
			return 0;
		}
		
		double numerator = 0;
		double denominatorOne = 0;
		double denominatorTwo = 0;
		
		try{
			for(int idx = 0; idx < listFloatOne.size(); ++idx) {
				numerator += listFloatOne.get(idx) * listFloatTwo.get(idx);
				denominatorOne += listFloatOne.get(idx) * listFloatOne.get(idx);
				denominatorTwo += listFloatTwo.get(idx) * listFloatTwo.get(idx);
			}
			
			double res = numerator / (Math.sqrt(denominatorOne) * Math.sqrt(denominatorTwo));
			
			return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * This function is used to calculate Cosine Similar, with factor.
	 * 
	 * @param listFloatOne
	 * @param listFloatTwo
	 * @param factor
	 * @return 
	 */
	@Override
	public double distanceOfFloatList(List<Float> listFloatOne,
			List<Float> listFloatTwo, float factor) {
		double res = this.distanceOfFloatList(listFloatOne, listFloatTwo);
		
		if(res == -1) { 
			return -1;
		}
		
		return res * factor;
	}

	/**
	 * This function is used to calculate Cosine Similar, with two parameter string list.
	 * 
	 * @param listStringOne
	 * @param listStringTwo
	 * @return 
	 */
	@Override
	public double distanceOfStringList(List<String> listStringOne,
			List<String> listStringTwo) {
		if(listStringOne.size() != listStringTwo.size()) {
			return -1;
		}
		
		if(listStringOne.size() == 0 || listStringTwo.size() == 0) {
			return 0;
		}
		
		double numerator = 0;
		double denominatorOne = 0;
		double denominatorTwo = 0;
		
		try{
			float oneTmp = 0;
			float twoTmp = 0;
			
			for(int idx = 0; idx < listStringOne.size(); ++idx) {
				oneTmp = Float.valueOf(listStringOne.get(idx));
				twoTmp = Float.valueOf(listStringTwo.get(idx));
				
				numerator += oneTmp * twoTmp;
				denominatorOne += oneTmp * oneTmp;
				denominatorTwo += twoTmp * twoTmp    ;
			}
			
			double res = numerator / (Math.sqrt(denominatorOne) * Math.sqrt(denominatorTwo));
			
			return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * This function is used to calculate Cosine Similar, with factor.
	 * 
	 * @param listStringOne
	 * @param listStringTwo
	 * @param factor
	 * @return 
	 */
	@Override
	public double distanceOfStringList(List<String> listStringOne,
			List<String> listStringTwo, float factor) {
		double res = this.distanceOfStringList(listStringOne, listStringTwo);
		
		if(res == -1) {
			return -1;
		}
		
		return res * factor ;
	}

}
