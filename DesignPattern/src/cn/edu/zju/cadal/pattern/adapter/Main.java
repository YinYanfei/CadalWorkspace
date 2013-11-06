package cn.edu.zju.cadal.pattern.adapter;

public class Main {

	private static void testDuck(Duck duck) {
		duck.quack();
		duck.fly();
	}
	
	private static void testTurkey(Turkey turkey) {
		turkey.gobble();
		turkey.fly();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MallardDuck duck = new MallardDuck();
		WildTurkey turkey = new WildTurkey();
		Duck turkeyAdapter = new TurkeyAdapter(turkey);
		Turkey duckAdapter = new DuckAdapter(duck);
		
		System.out.println("\nThe Turkey says ...");
		Main.testTurkey(turkey);
		
		System.out.println("\nThe DuckAdapter says ...");
		Main.testTurkey(duckAdapter);
		
		System.out.println("\nThe Duck says ...");
		Main.testDuck(duck);
		
		System.out.println("\nThe TurkeyAdapter says ...");
		Main.testDuck(turkeyAdapter);
	}
}
