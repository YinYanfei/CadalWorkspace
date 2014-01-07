package cn.edu.zju.cadal.util.sim;

import java.util.List;

public class PearsonCoefficient implements Similarity {

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
			int n = arrIntOne.length;
			
			double sumMultiOneTwo = 0;
			double sumOne = 0;
			double sumTwo = 0;
			double sumPowOne = 0;
			double sumPowTwo = 0;
			
			for(int idx = 0; idx < n; ++idx) {
				sumMultiOneTwo += arrIntOne[idx] * arrIntTwo[idx];
				sumOne += arrIntOne[idx];
				sumTwo += arrIntTwo[idx];
				sumPowOne += Math.pow(arrIntOne[idx], 2);
				sumPowTwo += Math.pow(arrIntTwo[idx], 2);
			}
			
			return (n * sumMultiOneTwo - sumOne * sumTwo) / (Math.sqrt(n * sumPowOne - Math.pow(sumOne, 2)) * Math.sqrt(n * sumPowTwo - Math.pow(sumTwo, 2)));
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
		
		return factor * res;
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
			int n = arrFloatOne.length;
			
			double sumMultiOneTwo = 0;
			double sumOne = 0;
			double sumTwo = 0;
			double sumPowOne = 0;
			double sumPowTwo = 0;
			
			for(int idx = 0; idx < n; ++idx) {
				sumMultiOneTwo += arrFloatOne[idx] * arrFloatTwo[idx];
				sumOne += arrFloatOne[idx];
				sumTwo += arrFloatTwo[idx];
				sumPowOne += Math.pow(arrFloatOne[idx], 2);
				sumPowTwo += Math.pow(arrFloatTwo[idx], 2);
			}
			
			return (n * sumMultiOneTwo - sumOne * sumTwo) / (Math.sqrt(n * sumPowOne - Math.pow(sumOne, 2)) * Math.sqrt(n * sumPowTwo - Math.pow(sumTwo, 2)));
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
		
		return factor * res;
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
			int n = arrStringOne.length;
			
			double sumMultiOneTwo = 0;
			double sumOne = 0;
			double sumTwo = 0;
			double sumPowOne = 0;
			double sumPowTwo = 0;
			
			double tmpOne = 0;
			double tmpTwo = 0;
			
			for(int idx = 0; idx < n; ++idx) {
				tmpOne = Double.valueOf(arrStringOne[idx]);
				tmpTwo = Double.valueOf(arrStringTwo[idx]);
				
				sumMultiOneTwo += tmpOne * tmpTwo;
				sumOne += tmpOne;
				sumTwo += tmpTwo;
				sumPowOne += Math.pow(tmpOne, 2);
				sumPowTwo += Math.pow(tmpTwo, 2);
			}
			
			return (n * sumMultiOneTwo - sumOne * sumTwo) / (Math.sqrt(n * sumPowOne - Math.pow(sumOne, 2)) * Math.sqrt(n * sumPowTwo - Math.pow(sumTwo, 2)));			
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
		
		return factor * res;
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
			int n = listIntOne.size();
			
			double sumMultiOneTwo = 0;
			double sumOne = 0;
			double sumTwo = 0;
			double sumPowOne = 0;
			double sumPowTwo = 0;
			
			for(int idx = 0; idx < n; ++idx) {
				sumMultiOneTwo += listIntOne.get(idx) * listIntTwo.get(idx);
				sumOne += listIntOne.get(idx);
				sumTwo += listIntTwo.get(idx);
				sumPowOne += Math.pow(listIntOne.get(idx), 2);
				sumPowTwo += Math.pow(listIntTwo.get(idx), 2);
			}
			
			return (n * sumMultiOneTwo - sumOne * sumTwo) / (Math.sqrt(n * sumPowOne - Math.pow(sumOne, 2)) * Math.sqrt(n * sumPowTwo - Math.pow(sumTwo, 2)));
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
			int n = listFloatOne.size();
			
			double sumMultiOneTwo = 0;
			double sumOne = 0;
			double sumTwo = 0;
			double sumPowOne = 0;
			double sumPowTwo = 0;
			
			for(int idx = 0; idx < n; ++idx) {
				sumMultiOneTwo += listFloatOne.get(idx) * listFloatTwo.get(idx);
				sumOne += listFloatOne.get(idx);
				sumTwo += listFloatTwo.get(idx);
				sumPowOne += Math.pow(listFloatOne.get(idx), 2);
				sumPowTwo += Math.pow(listFloatTwo.get(idx), 2);
			}
			
			return (n * sumMultiOneTwo - sumOne * sumTwo) / (Math.sqrt(n * sumPowOne - Math.pow(sumOne, 2)) * Math.sqrt(n * sumPowTwo - Math.pow(sumTwo, 2)));
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
		
		return factor * res;
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
			int n = listStringOne.size();
			
			double sumMultiOneTwo = 0;
			double sumOne = 0;
			double sumTwo = 0;
			double sumPowOne = 0;
			double sumPowTwo = 0;
			
			double tmpOne = 0;
			double tmpTwo = 0;
			
			for(int idx = 0; idx < n; ++idx) {
				tmpOne = Double.valueOf(listStringOne.get(idx));
				tmpTwo = Double.valueOf(listStringTwo.get(idx));
				
				sumMultiOneTwo += tmpOne * tmpTwo;
				sumOne += tmpOne;
				sumTwo += tmpTwo;
				sumPowOne += Math.pow(tmpOne, 2);
				sumPowTwo += Math.pow(tmpTwo, 2);
			}
			
			return (n * sumMultiOneTwo - sumOne * sumTwo) / (Math.sqrt(n * sumPowOne - Math.pow(sumOne, 2)) * Math.sqrt(n * sumPowTwo - Math.pow(sumTwo, 2)));			
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
