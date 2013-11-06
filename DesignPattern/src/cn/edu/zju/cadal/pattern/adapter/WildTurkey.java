package cn.edu.zju.cadal.pattern.adapter;

public class WildTurkey implements Turkey {

	@Override
	public void fly() {
		System.out.println("I's flying a short distance...");
	}

	@Override
	public void gobble() {
		System.out.println("Gobble gobble");
	}

}
