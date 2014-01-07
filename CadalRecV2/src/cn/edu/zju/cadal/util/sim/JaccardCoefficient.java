package cn.edu.zju.cadal.util.sim;

import java.util.List;

public class JaccardCoefficient implements Similarity {

	/**
	 * calculate distance between two integer array
	 * 
	 * @param arrIntOne
	 * @param arrIntTwo
	 */
	@Override
	public double distanceOfIntArray(int[] arrIntOne, int[] arrIntTwo) {
		if(arrIntOne.length != arrIntTwo.length) {
			return -1;
		}
		if(arrIntOne.length == 0 || arrIntTwo.length == 0) {
			return 0;
		}
		
		try{
			double multiOneTwo = 0;
			double powOne = 0;
			double powTwo = 0;
			
			for(int idx = 0; idx < arrIntOne.length; ++idx) {
				multiOneTwo += arrIntOne[idx] * arrIntTwo[idx];
				powOne += Math.pow(arrIntOne[idx], 2);
				powTwo += Math.pow(arrIntTwo[idx], 2);
			}
			
			return (multiOneTwo) / (Math.sqrt(powOne) + Math.sqrt(powTwo) - multiOneTwo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * calculate distance between two integer array, with factor
	 * 
	 * @param arrIntOne
	 * @param arrIntTwo
	 * @param factor
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
	 * calculate distance between two float array
	 * 
	 * @param arrFloatOne
	 * @param arrFloatTwo
	 */
	@Override
	public double distanceOfFloatArray(float[] arrFloatOne, float[] arrFloatTwo) {
		if(arrFloatOne.length != arrFloatTwo.length) {
			return -1;
		}
		if(arrFloatOne.length == 0 || arrFloatTwo.length == 0) {
			return 0;
		}
		
		try{
			double multiOneTwo = 0;
			double powOne = 0;
			double powTwo = 0;
			
			for(int idx = 0; idx < arrFloatOne.length; ++idx) {
				multiOneTwo += arrFloatOne[idx] * arrFloatTwo[idx];
				powOne += Math.pow(arrFloatOne[idx], 2);
				powTwo += Math.pow(arrFloatTwo[idx], 2);
			}
			
			return (multiOneTwo) / (Math.sqrt(powOne) + Math.sqrt(powTwo) - multiOneTwo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * calculate distance between two float array, with factor
	 * 
	 * @param arrFloatOne
	 * @param arrFloatTwo
	 * @param factor
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
	 * calculate distance between two string array
	 * 
	 * @param arrStringOne
	 * @param arrStringTwo
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
		
		try{
			double multiOneTwo = 0;
			double powOne = 0;
			double powTwo = 0;
			
			for(int idx = 0; idx < arrStringOne.length; ++idx) {
				multiOneTwo += Double.valueOf(arrStringOne[idx]) * Double.valueOf(arrStringTwo[idx]);
				powOne += Math.pow(Double.valueOf(arrStringOne[idx]), 2);
				powTwo += Math.pow(Double.valueOf(arrStringTwo[idx]), 2);
			}
			
			return (multiOneTwo) / (Math.sqrt(powOne) + Math.sqrt(powTwo) - multiOneTwo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * calculate distance between two string array, with factor
	 * 
	 * @param arrStringOne
	 * @param arrStringTwo
	 * @param factor
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
	 * calculate distance between two integer list
	 * 
	 * @param listIntOne
	 * @param listIntTwo
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
		
		try{
			double multiOneTwo = 0;
			double powOne = 0;
			double powTwo = 0;
			
			for(int idx = 0; idx < listIntOne.size(); ++idx) {
				multiOneTwo += listIntOne.get(idx) * listIntTwo.get(idx);
				powOne += Math.pow(listIntOne.get(idx), 2);
				powTwo += Math.pow(listIntTwo.get(idx), 2);
			}
			
			return (multiOneTwo) / (Math.sqrt(powOne) + Math.sqrt(powTwo) - multiOneTwo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * calculate distance between two integer list, with factor
	 * 
	 * @param listIntOne
	 * @param listIntTwo
	 * @param factor
	 */
	@Override
	public double distanceOfIntList(List<Integer> listIntOne,
			List<Integer> listIntTwo, float factor) {
		double res = this.distanceOfIntList(listIntOne, listIntTwo);
		
		if(res == -1) {
			return -1;
		}
		
		return factor * res;
	}

	/**
	 * calculate distance between two float list
	 * 
	 * @param listFloatOne
	 * @param listFloatTwo
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
		
		try{
			double multiOneTwo = 0;
			double powOne = 0;
			double powTwo = 0;

			for(int idx = 0; idx < listFloatOne.size(); ++idx) {
				multiOneTwo += listFloatOne.get(idx) * listFloatTwo.get(idx);
				powOne += Math.pow(listFloatOne.get(idx), 2);
				powTwo += Math.pow(listFloatTwo.get(idx), 2);
			}
			
			return (multiOneTwo) / (Math.sqrt(powOne) + Math.sqrt(powTwo) - multiOneTwo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * calculate distance between two float list, with factor
	 * 
	 * @param listFloatOne
	 * @param listFloatTwo
	 * @param factor
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
	 * calculate distance between two string list
	 * 
	 * @param listStringOne
	 * @param listStringTwo
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
		
		try{
			double multiOneTwo = 0;
			double powOne = 0;
			double powTwo = 0;

			for(int idx = 0; idx < listStringOne.size(); ++idx) {
				multiOneTwo += Double.valueOf(listStringOne.get(idx)) * Double.valueOf(listStringTwo.get(idx));
				powOne += Math.pow(Double.valueOf(listStringOne.get(idx)), 2);
				powTwo += Math.pow(Double.valueOf(listStringTwo.get(idx)), 2);
			}
			
			return (multiOneTwo) / (Math.sqrt(powOne) + Math.sqrt(powTwo) - multiOneTwo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	/**
	 * calculate distance between two string list, with factor
	 * 
	 * @param listStringOne
	 * @param listStringTwo
	 * @param factor
	 */
	@Override
	public double distanceOfStringList(List<String> listStringOne,
			List<String> listStringTwo, float factor) {
		double res = this.distanceOfStringList(listStringOne, listStringTwo);
		
		if(res == -1) {
			return -1;
		}
		
		return factor * res;
	}

}
