package cn.edu.zju.cadal.pattern.strategy;

public abstract class Duck {
	public FlyBehavior flyBehavior;
	public QuackBehavior quackBehavior;
	
	public Duck(){}
	
	public abstract void display();
	
	public void swim() {
	}
	
	public void perfomQuack() {
	}
	
	public void performFly() {	
	}
	
	public void setFlyBehavior() {
	}
	
	public void setQuackBehavior(){
	}	
}
