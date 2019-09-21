public class Counter {

    private int count = 0;
    
    public void method() {

	Thread thread1 = new Thread(new Runnable() {
            public void run() {
                for(int i = 0; i < 100000; i++) {
                    count++;
                }
            }
        });
        thread1.start();
        
        Thread thread2 = new Thread(new Runnable() {
            public void run() {
                for(int i = 0; i < 100000; i++) {
                    count++;
                }
            }
        });
	
		thread2.start();
	    
/*
        try {
            thread1.join();
            thread2.join();
			} catch (InterruptedException e) {
            e.printStackTrace();
        }
*/        
	System.out.println("Count is: " + count);
    
    }
	
	public static void main ( String [] args ) {
	
		Counter x = new Counter();
		x.method();
	
	}

}