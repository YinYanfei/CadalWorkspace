package ch2;

public class JavaVMStackSOFThread {

	private void dontStop() {
		while(true){
		}
	}

	public void stackLeakByThread(){
		while(true) {
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run(){
					dontStop();
				}
			});
			thread.start();
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		JavaVMStackSOFThread oom = new JavaVMStackSOFThread();
		oom.stackLeakByThread();
	}

}
