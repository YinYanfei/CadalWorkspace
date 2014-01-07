package cn.edu.zju.cadal.util.sim;

import java.util.List;

public interface Similarity {
	// Calculate distance of two integer array
	public abstract double distanceOfIntArray(final int[] arrIntOne, final int[] arrIntTwo);
	public abstract double distanceOfIntArray(final int[] arrIntOne, final int[] arrIntTwo, final float factor);
	
	// Calculate distance of two float array
	public abstract double distanceOfFloatArray(final float[] arrFloatOne, final float[] arrFloatTwo);
	public abstract double distanceOfFloatArray(final float[] arrFloatOne, final float[] arrFloatTwo, final float factor);
	
	// Calculate distance of two string array
	public abstract double distanceOfStringArray(final String[] arrStringOne, final String[] arrStringTwo);
	public abstract double distanceOfStringArray(final String[] arrStringOne, final String[] arrStringTwo, final float factor);
	
	// Calculate distance of two integer list
	public abstract double distanceOfIntList(final List<Integer> listIntOne, final List<Integer> listIntTwo);
	public abstract double distanceOfIntList(final List<Integer> listIntOne, final List<Integer> listIntTwo, final float factor);
	
	// Calculate distance of two float list
	public abstract double distanceOfFloatList(final List<Float> listFloatOne, final List<Float> listFloatTwo);
	public abstract double distanceOfFloatList(final List<Float> listFloatOne, final List<Float> listFloatTwo, final float factor);
	
	// Calculate distance of two string list
	public abstract double distanceOfStringList(final List<String> listStringOne, final List<String> listStringTwo);
	public abstract double distanceOfStringList(final List<String> listStringOne, final List<String> listStringTwo, final float factor);
}
