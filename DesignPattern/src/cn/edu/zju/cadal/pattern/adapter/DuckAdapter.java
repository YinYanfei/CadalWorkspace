package cn.edu.zju.cadal.pattern.adapter;

public class DuckAdapter implements Turkey {
	private Duck duck;
	
	public DuckAdapter(Duck duck) {
		this.duck = duck;
	}
	
	@Override
	public void fly() {
		this.duck.fly();
	}

	@Override
	public void gobble() {
		this.duck.quack();
	}

}
