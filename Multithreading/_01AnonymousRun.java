public class _01AnonymousRun {
/*
We can use the Thread constructor with an anonymous class
*/

    public static void main(String[] args) {
        Thread thread1 = new Thread(new Runnable() {

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

        });

        thread1.start();
    }

}
