package cn.cadal.rec.dm.patterns.cluster;

/**
 * This class represents a vector of double values
 * @author Philippe Fournier-Viger
 */
public class DoubleArray {

	// the vector
	public double[] data;
	
	/**
	 * Constructor
	 * @param data an array of double values
	 */
	public DoubleArray(double [] data){
		this.data = data;
	}
	
	/**
	 * Get a string representation of this double array.
	 * @return a string
	 */
	public String toString(){
		StringBuffer buffer = new StringBuffer();
		for(int i=0; i<data.length; i++){
			buffer.append(data[i]);
			if(i < data.length -1){
				buffer.append(",");
			}
		}
		return buffer.toString();
	}
	
	/**
	 * Return a copy of this double array
	 */
	public DoubleArray clone(){
		return new DoubleArray(data.clone());
	}
}
