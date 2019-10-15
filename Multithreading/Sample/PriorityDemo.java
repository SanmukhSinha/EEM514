public class PriorityDemo implements Runnable {

	public void run () {
		
		int i=0;
		
		while (i<5000) {
			System.out.println(Thread.currentThread().getName()+":"+i);
			i++;
			//try {Thread.sleep(500);} catch (InterruptedException e) {}
		}
	}
	
	public static void main (String [] args) {
	
		Thread t1 = new Thread (new PriorityDemo(), " Deighton");
		Thread t2 = new Thread (new PriorityDemo(), " Maclean");
		//Thread t3 = new Thread (new PriorityDemo(), " Archer");

		t1.setPriority(Thread.MIN_PRIORITY);
		t2.setPriority(Thread.MAX_PRIORITY);
		//t3.setPriority(Thread.NORM_PRIORITY);

		t2.start();
		t1.start();
		//t3.start();
	}

}