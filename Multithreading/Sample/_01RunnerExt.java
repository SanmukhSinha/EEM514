/*
Method 1: Our class extends Thread
*/
class Runner extends Thread {
	
	int N=0; 
	
	public Runner (int n) {
		N = n;
	}
	
    public void run() {
	
        for(int i=0; i<N; i++) {
            System.out.println("Hello: " + i);
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
		
    }
    
}

public class _01RunnerExt {
    
    public static void main(String[] args) {
        Runner runner1 = new Runner(5);
        runner1.start();
        // What happens if I call run() instead?
		
        Runner runner2 = new Runner(50);
        runner2.start();

    }

}