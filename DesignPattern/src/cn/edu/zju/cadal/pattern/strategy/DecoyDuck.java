package cn.edu.zju.cadal.pattern.strategy;

public class DecoyDuck extends Duck {

	public DecoyDuck() {
		this.flyBehavior = new FlyWithWings();
		this.quackBehavior = new Quack();
	}
	
	@Override
	public void display() {
		// TODO Auto-generated method stub
		
	}

}
