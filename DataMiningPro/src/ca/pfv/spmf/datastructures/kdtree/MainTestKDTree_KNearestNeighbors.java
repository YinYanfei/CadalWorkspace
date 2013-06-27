package ca.pfv.spmf.datastructures.kdtree;

import ca.pfv.spmf.datastructures.redblacktree.RedBlackTree;

/**
 *This test show how to use the KDTree structure to find 
 * the K nearest neighboor to a given point.
 * @author Philippe Fournier-Viger, 2011
 */
public class MainTestKDTree_KNearestNeighbors {

	public static void main(String[] args) {
		// create kd tree with two dimensions  and of type double
		KDTree tree = new KDTree();
		
		// Use a list of point to create the kd-tree
		double[][] points = new double[6][2];
		points[0] = new double[]{2d,3d};
		points[1] = new double[]{5d,4d};
		points[2] = new double[]{9d,6d};
		points[3] = new double[]{4d,7d};
		points[4] = new double[]{8d,1d};
		points[5] = new double[]{7d,2d};
		
		// Create a KD Tree with the points
		tree.buildtree(points);
		
		// Print the tree for debugging
		System.out.println("\nTREE: \n" + tree.toString() + "  \n\n Number of elements in tree: " + tree.size());
	
		// Find the nearest neighboor to the point 4,4
		double query [] = new double[]{4d,4d};
		int k = 3;
		RedBlackTree<KNNPoint> result = tree.knearest(query, k);
		
		System.out.println("THE K NEAREST NEIGHBOORS ARE : " + result.toString());	
	}
	
	public static String toString(double [] values){
		StringBuffer buffer = new StringBuffer();
		for(Double element : values ){
			buffer.append("   " + element);
		}
		return buffer.toString();
	}
}
