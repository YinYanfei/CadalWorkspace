package ch2;

public class JavaVMStackSOF {

	private int stackLength = 1;
	
	public void stackLeak() {
		stackLength++;
		stackLeak();
	}
	
	/**
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		JavaVMStackSOF oom = new JavaVMStackSOF();
		try {
			oom.stackLeak();
		}catch(Throwable e) {
			System.out.println("stack length:" + oom.stackLength);
			throw e;
		}
	}

}
