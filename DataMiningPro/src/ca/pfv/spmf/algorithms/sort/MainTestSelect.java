package ca.pfv.spmf.algorithms.sort;

public class MainTestSelect {
    /** Test method */
    public static void main( String[ ] args ) {
 
        int[ ] input = { 546, 743, 5, 394,  4, 11, 42 };
        System.out.println(Select.randomizedSelect(input, 0));
        System.out.println(Select.randomizedSelect(input, 1));
        System.out.println(Select.randomizedSelect(input, 2));

    }
}
