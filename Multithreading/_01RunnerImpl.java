/*
Method 2: Our class implements Runnable
*/
class Runner implements Runnable {

    public void run() {
        for(int i=0; i<5; i++) {
            System.out.println("Hello: " + i);
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
         
    }
    
}

public class _01RunnerImpl {
    
    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runner());
        thread1.start();
    }

}